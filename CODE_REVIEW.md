# Code review — academy-hub-api

**Commit:** `8b84a6d` (2026-08-19) · **Runda:** 2 · **Scop:** modulele `course` + `enrollment` nou adăugate, plus regresiile din `student` și `book`

Toate constatările 🔴 au fost **reproduse prin rulare**, nu doar prin citire: proiectul a fost copiat în afara repo-ului, cu H2 în loc de MySQL, iar fiecare ipoteză a fost pusă la treabă printr-un `CommandLineRunner` de test. Codul din repo nu a fost modificat.

Proiectul **compilează** (`mvn compile` ✅) și **pornește** (`Started AppApplication` ✅). Toate bug-urile de mai jos sunt invizibile la compilare — apar doar când chemi metodele.

---

## 🔴 Critice

### B1 — `Course.enrollments` nu e inițializat → NPE pe orice curs nou

`src/main/java/academy/hub/app/course/models/Course.java:40`

```java
private Set<Enrollment> enrollments;   // fara = new HashSet<>()
```

`getEnrollments()` (linia 42) și `addEnrollment()` (linia 46) lucrează direct pe câmp. Pe un curs **încărcat din DB** merge, pentru că Hibernate înlocuiește câmpul cu un `PersistentSet`. Pe un curs **nou** (`new Course("X","Y")`) sare în aer:

```
NullPointerException: Cannot invoke "java.util.Set.getClass()" because "s" is null      <- getEnrollments()
NullPointerException: Cannot invoke "java.util.Set.add(Object)" because "this.enrollments" is null   <- addEnrollment()
```

În `Student.java:53` și `:59` ai făcut exact corect (`= new HashSet<>()`). Deci nu e lacună de cunoștințe — e o linie pierdută la copiere. **Asta e diferența dintre „știu regula" și „regula e aplicată peste tot".**

### B2 — `StudentSummary.getId()` declarat `Long`, dar entitatea are `UUID`

`src/main/java/academy/hub/app/student/dtos/StudentSummary.java:7`

```
UnsupportedOperationException: Cannot project java.util.UUID to java.lang.Long;
Target type is not an interface and no matching Converter found
```

Bug-ul e **exact cel semnalat la runda 1** (`Long` peste `GenerationType.UUID`). A supraviețuit pentru că în `Runner.sqsTEST()` (linia 127) afișezi doar `getFirstName()` și `getEmail()` — proiecțiile interfață sunt leneșe, metoda `getId()` nu e chemată niciodată, deci bug-ul nu se arată. Prima linie de cod real care cere id-ul îl scoate la suprafață.

### B3 — `@EntityGraph("Student.withBooks")` nu există → adnotarea e ignorată în tăcere

`src/main/java/academy/hub/app/student/repository/StudentRepository.java:33`

```java
@EntityGraph(value = "Student.withBooks")
@Query("select s from Student s")
List<Student> findAllWithBooks();
```

Pe `Student` nu există niciun `@NamedEntityGraph` cu numele ăsta. Rezultat: query-ul rulează ca un `select s from Student s` obișnuit, iar `books` rămâne LAZY. Accesat în afara tranzacției:

```
LazyInitializationException: Cannot lazily initialize collection of role
'academy.hub.app.student.models.Student.books' ... (no session)
```

În `Runner` pare că merge — dar **doar pentru că `run()` e adnotat `@Transactional`** și sesiunea e încă deschisă. Metoda se numește `findAllStudentsWithBooks` și nu aduce cărțile: numele minte.

### B4 — `createBook` nu poate reuși niciodată

`src/main/java/academy/hub/app/book/services/BookCommandServiceImpl.java:29-48`

`Book.student` e `@ManyToOne(optional = false)` + `@JoinColumn(nullable = false)` (`Book.java:36-38`), dar `BookCreateRequest` are un singur câmp (`name`) și serviciul nu setează niciodată studentul:

```
DataIntegrityViolationException: NULL not allowed for column "STUDENT_ID";
insert into book (created_at,name,student_id,id) values (?,?,?,?)
```

Adică `bcsTEST()` din `Runner` cade pe prima linie dacă îl decomentezi. Cartea nu există fără proprietar — deci ori cererea poartă `studentId`, ori cărțile se creează prin `Student.addBook(...)` (decizia de la runda 1: `Student` = aggregate root).

### B5 — `updateEnrollment` duplică perechea (student, curs)

`src/main/java/academy/hub/app/enrollment/services/EnrollmentCommandServiceImpl.java:75-86` + `Enrollment.java:16`

`createEnrollment` verifică `existsByStudentIdAndCourseId` — foarte bine. `updateEnrollment` **nu verifică nimic**, iar tabela `enrollment` nu are constrângere unică pe `(student_id, course_id)`. Reprodus: Rareș înscris la Java și la SQL, apoi mut înscrierea de SQL pe Java →

```
perechi (Rares, Java) in DB = 2
```

Regula pe care ai apărat-o în `create` cade prin `update`, pentru că singurul loc unde e apărată e codul, nu baza. Consecință văzută imediat după: ștergerea cursului crapă cu `TransientPropertyValueException`, pentru că starea din DB e deja incoerentă.

### B6 — `Enrollment.equals` crapă pe obiecte fără student/curs

`src/main/java/academy/hub/app/enrollment/models/Enrollment.java:47`

```java
return student.getId().equals(other.student.getId()) && course.getId().equals(other.course.getId());
```

```
NullPointerException: Cannot invoke "academy.hub.app.student.models.Student.getId()" because "this.student" is null
```

`equals` e chemat de orice `Set`, `List.contains`, `removeEnrollment` — inclusiv pe un `Enrollment` proaspăt construit, unde `student`/`course` sunt încă `null`. În plus, comparând asocieri LAZY forțezi încărcarea proxy-urilor la fiecare comparație.

### B7 — `deleteStudent` ignoră emailul din request

`src/main/java/academy/hub/app/student/services/StudentCommandServiceImpl.java:54-62`

`StudentDeleteRequest` poartă `id` **și** `email`, dar emailul nu e citit niciodată. Reprodus: cerere cu id-ul Mariei și emailul `nimeni@x.com` → Maria a fost ștearsă. Un câmp validat cu `@NotBlank @Email`, care nu apără nimic, e mai rău decât lipsa lui: creează impresia că e o a doua verificare.

---

## 🟡 Importante

**M1 — `updateCourse` scrie orbește ce primește.** `CourseCommandServiceImpl.java:53-62`. `CourseUpdateRequest` nu are nicio adnotare de validare, iar serviciul face `setName(request.name())` necondiționat. Cu câmpuri `null` → `ConstraintViolationException` la commit; cu un nume deja folosit → `DataIntegrityViolationException` în loc de `CourseNameAlreadyInUse` (excepția pe care ai scris-o și n-o arunci niciodată la update). Ambele reproduse. `updateStudent` face update parțial corect — cele două servicii nu se poartă la fel.

**M2 — `updateStudent` nu verifică unicitatea emailului.** `StudentCommandServiceImpl.java:78-80`. Schimbi emailul pe unul existent → `DataIntegrityViolationException` din DB, nu `EmailAlreadyUsed`. Regula e verificată la `add`, nu și la `update` — același tipar ca B5.

**M3 — excepție greșită.** `StudentCommandServiceImpl.java:56`: id inexistent → aruncă `EmailNotFound`. Ai `StudentIdNotFound` chiar în același pachet.

**M4 — fiecare query rulează de două ori.** Tipar prezent în tot `StudentQueryServiceImpl`, `CourseQueryServiceImpl`, `BookQueryServiceImpl`, `EnrollmentQueryServiceImpl`:

```java
if (studentRepository.findAll().isEmpty()) { throw new NoStudentsFound(); }
return studentRepository.findAll();     // al doilea SELECT identic
```

Dublezi SQL-ul pentru fiecare citire. Se rezolvă cu o variabilă locală.

**M5 — verificări `== null` care nu se declanșează niciodată.** `StudentQueryServiceImpl.java:45,52,60` și `BookQueryServiceImpl.java:31,39`. Un derived query care întoarce `List` întoarce **listă goală**, niciodată `null`. Reprodus: `getStudentsWithAgeGreaterThan(200)` → `0`, fără excepție. Verificarea care trebuia acolo era `.isEmpty()`.

**M6 — `@Transactional` lipsă.** `CourseCommandServiceImpl`, `EnrollmentCommandServiceImpl` (niciun `@Transactional`), `BookCommandServiceImpl.deletebook/updatebook`. Merge doar pentru că fiecare `save()`/`delete()` își deschide singur tranzacția — dar „verific dacă există, apoi scriu" devine două tranzacții separate. La `createEnrollment` asta înseamnă că verificarea de duplicat poate fi depășită de o a doua execuție.

**M7 — `StudentFactoryImpl` validează pe jumătate.** `StudentFactoryImpl.java:11-17`: verifici că sunt 4 câmpuri, dar vârsta trece prin `Integer.parseInt` fără plasă → `NumberFormatException: For input string: "abc"` iese brută spre apelant. În plus, `split(",")` se face de două ori: o dată în factory, o dată în `Student(String text)` (`Student.java:71-77`). Constructorul ăla face parsing într-o entitate — locul lui e în factory.

**M8 — `Enrollment` incomplet ca entitate.** `Enrollment.java:21-34`: câmpuri package-private (restul entităților au `private`), `createdAt` fără `@Column(nullable = false)`, `student`/`course` fără `optional = false` deși o înscriere fără student n-are sens.

---

## 🟢 Cleanups

- **C1** — `Enrollment.java:9`: `import org.springframework.cglib.core.Local;` — import greșit, ales din auto-complete, complet nefolosit. La fel `NotBlank` (linia 6).
- **C2** — `StudentSummary.java:3`: un DTO importă `CourseRepository`. Nefolosit.
- **C3** — `BookQueryServiceImpl.java:12` e `@Component`; toate celelalte servicii sunt `@Service`.
- **C4** — `@Valid` pe parametri `UUID` nu face nimic (`BookCommandService:11-12`, `CourseCommandService:12-13`). `@Valid` validează bean-uri, nu tipuri simple.
- **C5** — redeclarări inutile în repo-uri: `existsById`, `findById`, `findAll` sunt deja în `JpaRepository` (`StudentRepository:21-22`, `BookRepository:14-16`, `CourseRepository:17-19`, `EnrollmentRepository:13`).
- **C6** — `StudentAgeComparator.java:12`: `;` în plus după blocul `if`. Toată clasa e `Comparator.comparingInt(Student::getAge)`.
- **C7** — `ExceptionConstants.java:7` (student): `"Email not found  "` cu două spații la final.
- **C8** — `deletebook`, `updatebook` cu literă mică; restul proiectului folosește camelCase corect.

---

## Before / After (doar pentru 🔴)

| # | Acum | Corect |
|---|---|---|
| B1 | `private Set<Enrollment> enrollments;` | `private Set<Enrollment> enrollments = new HashSet<>();` |
| B2 | `Long getId();` | `UUID getId();` |
| B3 | `@EntityGraph(value = "Student.withBooks")` peste un graf inexistent | fie `@NamedEntityGraph(name="Student.withBooks", attributeNodes=@NamedAttributeNode("books"))` pe `Student`, fie `@EntityGraph(attributePaths = "books")` direct pe metodă (și fără `@Query`) |
| B4 | `new Book(name, createdAt)` salvat fără student | `BookCreateRequest(UUID studentId, String name)` → încarci studentul, `student.addBook(book)`, apoi salvezi |
| B5 | `updateEnrollment` scrie direct noua pereche | `if (enrollmentRepository.existsByStudentIdAndCourseId(...)) throw new StudentAlreadyEnrolledInThisCourse();` **plus** `@Table(name="enrollment", uniqueConstraints = @UniqueConstraint(name="uk_enrollment_student_course", columnNames={"student_id","course_id"}))` |
| B6 | `student.getId().equals(other.student.getId()) && ...` | `id != null && id.equals(other.id)` — același tipar pe care îl folosești deja în `Student`, `Book`, `Course` |
| B7 | emailul din request nu e citit | ori îl verifici (`if (!s.getEmail().equals(req.email())) throw new EmailNotFound();`), ori îl scoți din DTO |

---

## Q&A — de răspuns înainte de runda 3

1. **B3.** De ce `findAllStudentsWithBooks()` „merge" când o chemi din `Runner`, dar aruncă `LazyInitializationException` când o chemi din `Probe`? Ce anume din `Runner` ține sesiunea deschisă — și de ce asta face testul tău să nu dovedească nimic?

2. **B5.** Ai apărat regula „un student nu se înscrie de două ori la același curs" în `createEnrollment`. Explică pe ce drum a ajuns totuși perechea duplicată în baza de date și de ce o constrângere `UNIQUE` în tabelă ar fi prins-o indiferent pe unde intra.

3. **B1 + B2.** Ambele sunt lucruri pe care le-ai făcut **corect** în altă parte a aceluiași proiect (`Student.books` inițializat, `Student.id` de tip `UUID`). Ce ai schimba în felul în care lucrezi ca a doua clasă să nu mai plece fără regula pe care prima o are deja?
