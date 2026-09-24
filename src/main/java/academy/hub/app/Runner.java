package academy.hub.app;

import academy.hub.app.book.dtos.*;
import academy.hub.app.book.models.Book;
import academy.hub.app.book.repository.BookRepository;
import academy.hub.app.book.services.interfaces.BookCommandService;
import academy.hub.app.book.services.interfaces.BookQueryService;
import academy.hub.app.course.dtos.*;
import academy.hub.app.course.models.Course;
import academy.hub.app.course.repository.CourseRepository;
import academy.hub.app.course.services.interfaces.CourseCommandService;
import academy.hub.app.course.services.interfaces.CourseQueryService;
import academy.hub.app.enrollment.dtos.*;
import academy.hub.app.enrollment.models.Enrollment;
import academy.hub.app.enrollment.repository.EnrollmentRepository;
import academy.hub.app.enrollment.services.EnrollmentQueryServiceImpl;
import academy.hub.app.enrollment.services.interfaces.EnrollmentCommandService;
import academy.hub.app.enrollment.services.interfaces.EnrollmentQueryService;
import academy.hub.app.student.comparators.StudentAgeComparator;
import academy.hub.app.student.dtos.*;
import academy.hub.app.student.exceptions.StudentNotFound;
import academy.hub.app.student.factory.StudentFactory;
import academy.hub.app.student.models.Student;
import academy.hub.app.student.repository.StudentRepository;
import academy.hub.app.student.services.interfaces.StudentCommandService;
import academy.hub.app.student.services.interfaces.StudentQueryService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Component
public class Runner implements CommandLineRunner {

    private final StudentCommandService studentCommandService;
    private final StudentQueryService studentQueryService;
    private final BookCommandService bookCommandService;
    private final BookQueryService bookQueryService;
    private final CourseCommandService courseCommandService;
    private final CourseQueryService courseQueryService;
    private final EnrollmentCommandService enrollmentCommandService;
    private final EnrollmentQueryService enrollmentQueryService;
    private final StudentFactory studentFactory;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final BookRepository bookRepository;
    private final EnrollmentRepository enrollmentRepository;


    public Runner(StudentCommandService studentCommandService, StudentQueryService studentQueryService,
                  BookCommandService bookCommandService, BookQueryService bookQueryService,
                  CourseCommandService courseCommandService, CourseQueryService courseQueryService,
                  EnrollmentCommandService enrollmentCommandService, EnrollmentQueryService enrollmentQueryService,
                  StudentFactory studentFactory, CourseRepository courseRepository, StudentRepository studentRepository, BookRepository bookRepository, EnrollmentRepository enrollmentRepository) {
        this.studentCommandService = studentCommandService;
        this.studentQueryService = studentQueryService;
        this.bookCommandService = bookCommandService;
        this.bookQueryService = bookQueryService;
        this.courseCommandService = courseCommandService;
        this.courseQueryService = courseQueryService;
        this.enrollmentCommandService = enrollmentCommandService;
        this.enrollmentQueryService = enrollmentQueryService;
        this.studentFactory = studentFactory;
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.bookRepository = bookRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void run(String... args) throws Exception {


//        scsTEST();
//        sqsTEST();
//        bcsTEST();
//        bqsTEST();
//        ccsTEST();
//        cqsTest();
//        ecsTEST();
//        eqsTEST();
       // factoryTest();
        reset();
        seed();
        clearPersistence();


    }

    void reset(){
        bookRepository.deleteAllInBatch();
        enrollmentRepository.deleteAllInBatch();
        courseRepository.deleteAllInBatch();
        studentRepository.deleteAllInBatch();
    }

    void clearPersistence(){
        entityManager.flush();
        entityManager.clear();
    }

    void seed(){




            // =====================================================
            // CLEAR DATABASE
            // =====================================================

            bookRepository.deleteAll();
            enrollmentRepository.deleteAll();
            courseRepository.deleteAll();
            studentRepository.deleteAll();


            // =====================================================
            // STUDENTS
            // =====================================================

            Student rares = new Student(
                    "Rares",
                    "Dumitru",
                    "rares.dumitru@gmail.com",
                    31
            );

            Student maria = new Student(
                    "Maria",
                    "Popescu",
                    "maria.popescu@gmail.com",
                    22
            );

            Student andrei = new Student(
                    "Andrei",
                    "Ionescu",
                    "andrei.ionescu@gmail.com",
                    27
            );

            Student ana = new Student(
                    "Ana",
                    "Dumitrescu",
                    "ana.dumitrescu@gmail.com",
                    24
            );

            Student mihai = new Student(
                    "Mihai",
                    "Georgescu",
                    "mihai.georgescu@gmail.com",
                    35
            );

            Student elena = new Student(
                    "Elena",
                    "Stan",
                    "elena.stan@gmail.com",
                    29
            );

            Student bogdan = new Student(
                    "Bogdan",
                    "Horghidan",
                    "bgdhrg@gmail.ro",
                    28
            );

            Student ioana = new Student(
                    "Ioana",
                    "Marinescu",
                    "ioana.marinescu@gmail.com",
                    19
            );


            // =====================================================
            // BOOKS
            // =====================================================

            // Rares - 3 books

            rares.addBook(new Book(
                    "Effective Java",
                    LocalDate.of(2018, 1, 6)
            ));

            rares.addBook(new Book(
                    "Clean Code",
                    LocalDate.of(2008, 8, 1)
            ));

            rares.addBook(new Book(
                    "Spring Start Here",
                    LocalDate.of(2021, 9, 21)
            ));


            // Maria - 2 books

            maria.addBook(new Book(
                    "Head First Java",
                    LocalDate.of(2022, 5, 1)
            ));

            maria.addBook(new Book(
                    "Learning SQL",
                    LocalDate.of(2020, 9, 1)
            ));


            // Andrei - 1 book

            andrei.addBook(new Book(
                    "Java Concurrency in Practice",
                    LocalDate.of(2006, 5, 9)
            ));


            // Ana - 2 books

            ana.addBook(new Book(
                    "SQL Cookbook",
                    LocalDate.of(2020, 12, 1)
            ));

            ana.addBook(new Book(
                    "Design Patterns",
                    LocalDate.of(1994, 10, 31)
            ));


            // Mihai - 4 books

            mihai.addBook(new Book(
                    "Clean Architecture",
                    LocalDate.of(2017, 9, 20)
            ));

            mihai.addBook(new Book(
                    "Spring in Action",
                    LocalDate.of(2022, 2, 1)
            ));

            mihai.addBook(new Book(
                    "Designing Data-Intensive Applications",
                    LocalDate.of(2017, 3, 16)
            ));

            mihai.addBook(new Book(
                    "Effective Java",
                    LocalDate.of(2018, 1, 6)
            ));


            // Elena intentionally has 0 books


            // Bogdan - 2 books

            bogdan.addBook(new Book(
                    "Java: The Complete Reference",
                    LocalDate.of(2021, 11, 1)
            ));

            bogdan.addBook(new Book(
                    "SQL Antipatterns",
                    LocalDate.of(2010, 7, 1)
            ));


            // Ioana - 1 book

            ioana.addBook(new Book(
                    "Head First Design Patterns",
                    LocalDate.of(2020, 12, 1)
            ));


            // =====================================================
            // COURSES
            // =====================================================

            Course java = new Course(
                    "Java Fundamentals",
                    "Computer Science"
            );

            Course spring = new Course(
                    "Spring Boot",
                    "Computer Science"
            );

            Course sql = new Course(
                    "SQL Fundamentals",
                    "Databases"
            );

            Course algorithms = new Course(
                    "Algorithms and Data Structures",
                    "Computer Science"
            );

            Course economics = new Course(
                    "Microeconomics",
                    "Economics"
            );

            Course python = new Course(
                    "Python Fundamentals",
                    "Computer Science"
            );

            Course docker = new Course(
                    "Docker Fundamentals",
                    "DevOps"
            );


            // =====================================================
            // IMPORTANT:
            // SAVE STUDENTS + COURSES BEFORE ENROLLMENTS
            // =====================================================

            studentRepository.saveAll(List.of(
                    rares,
                    maria,
                    andrei,
                    ana,
                    mihai,
                    elena,
                    bogdan,
                    ioana
            ));

            courseRepository.saveAll(List.of(
                    java,
                    spring,
                    sql,
                    algorithms,
                    economics,
                    python,
                    docker
            ));


            // =====================================================
            // ENROLLMENTS
            // =====================================================

            // Rares -> Java
            Enrollment e1 = new Enrollment(
                    LocalDate.of(2026, 1, 10)
            );
            rares.addEnrollment(e1);
            java.addEnrollment(e1);


            // Rares -> Spring
            Enrollment e2 = new Enrollment(
                    LocalDate.of(2026, 2, 5)
            );
            rares.addEnrollment(e2);
            spring.addEnrollment(e2);


            // Rares -> SQL
            Enrollment e3 = new Enrollment(
                    LocalDate.of(2026, 3, 1)
            );
            rares.addEnrollment(e3);
            sql.addEnrollment(e3);


            // Maria -> Java
            Enrollment e4 = new Enrollment(
                    LocalDate.of(2026, 1, 15)
            );
            maria.addEnrollment(e4);
            java.addEnrollment(e4);


            // Maria -> SQL
            Enrollment e5 = new Enrollment(
                    LocalDate.of(2026, 2, 20)
            );
            maria.addEnrollment(e5);
            sql.addEnrollment(e5);


            // Andrei -> Java
            Enrollment e6 = new Enrollment(
                    LocalDate.of(2026, 1, 20)
            );
            andrei.addEnrollment(e6);
            java.addEnrollment(e6);


            // Andrei -> Spring
            Enrollment e7 = new Enrollment(
                    LocalDate.of(2026, 2, 10)
            );
            andrei.addEnrollment(e7);
            spring.addEnrollment(e7);


            // Andrei -> Algorithms
            Enrollment e8 = new Enrollment(
                    LocalDate.of(2026, 3, 10)
            );
            andrei.addEnrollment(e8);
            algorithms.addEnrollment(e8);


            // Ana -> SQL
            Enrollment e9 = new Enrollment(
                    LocalDate.of(2026, 1, 25)
            );
            ana.addEnrollment(e9);
            sql.addEnrollment(e9);


            // Ana -> Python
            Enrollment e10 = new Enrollment(
                    LocalDate.of(2026, 4, 5)
            );
            ana.addEnrollment(e10);
            python.addEnrollment(e10);


            // Mihai -> Java
            Enrollment e11 = new Enrollment(
                    LocalDate.of(2026, 1, 5)
            );
            mihai.addEnrollment(e11);
            java.addEnrollment(e11);


            // Mihai -> Spring
            Enrollment e12 = new Enrollment(
                    LocalDate.of(2026, 2, 1)
            );
            mihai.addEnrollment(e12);
            spring.addEnrollment(e12);


            // Mihai -> SQL
            Enrollment e13 = new Enrollment(
                    LocalDate.of(2026, 2, 15)
            );
            mihai.addEnrollment(e13);
            sql.addEnrollment(e13);


            // Mihai -> Algorithms
            Enrollment e14 = new Enrollment(
                    LocalDate.of(2026, 3, 15)
            );
            mihai.addEnrollment(e14);
            algorithms.addEnrollment(e14);


            // Elena -> Economics
            Enrollment e15 = new Enrollment(
                    LocalDate.of(2026, 3, 20)
            );
            elena.addEnrollment(e15);
            economics.addEnrollment(e15);


            // Bogdan -> Java
            Enrollment e16 = new Enrollment(
                    LocalDate.of(2026, 4, 1)
            );
            bogdan.addEnrollment(e16);
            java.addEnrollment(e16);


            // Bogdan -> Spring
            Enrollment e17 = new Enrollment(
                    LocalDate.of(2026, 4, 10)
            );
            bogdan.addEnrollment(e17);
            spring.addEnrollment(e17);


            // Ioana -> Python
            Enrollment e18 = new Enrollment(
                    LocalDate.of(2026, 5, 1)
            );
            ioana.addEnrollment(e18);
            python.addEnrollment(e18);

            // Docker intentionally has 0 enrollments


            // =====================================================
            // SAVE ENROLLMENTS
            // =====================================================

            enrollmentRepository.saveAll(List.of(
                    e1,
                    e2,
                    e3,
                    e4,
                    e5,
                    e6,
                    e7,
                    e8,
                    e9,
                    e10,
                    e11,
                    e12,
                    e13,
                    e14,
                    e15,
                    e16,
                    e17,
                    e18
            ));

        }




    void scsTEST() {
        banner("StudentCommandService TEST");
//        StudentCreateRequest newStudent = new StudentCreateRequest("Rares", "Dumitru",
//                "rares123@gmail.ro", 31);
//        StudentCreateResponse scr = studentCommandService.addStudent(newStudent);
//
//        System.out.println("Student created successfully " + scr.firstName() + " " + scr.lastName() + " " + scr.id());
//
//
//        StudentUpdateRequest updateStudent = new StudentUpdateRequest(scr.id(), "Paul", null, null, 0);
//        StudentUpdateResponse updateStudentResponse = studentCommandService.updateStudent(updateStudent);
//        System.out.println("Student updated successfully " + updateStudentResponse.firstName() + " " + updateStudentResponse.lastName()
//                + " " + updateStudentResponse.email() + " " + updateStudentResponse.age());
//
//        StudentDeleteRequest delStu = new StudentDeleteRequest(scr.id(), scr.email());
//        StudentDeleteResponse sdr = studentCommandService.deleteStudent(delStu);
//        System.out.println("Student " + sdr.firstName() + " " + sdr.lastName() + " has been deleted.");
    }
    void sqsTEST(){
        banner("StudentQueryService TEST");
        List<Student> students = studentQueryService.getStudents();
        for(Student s: students){
            System.out.println(s.getFirstName() + " " + s.getLastName());
        }

        Student oldestStudent = studentQueryService.getOldestStudent();
        System.out.println(oldestStudent.getFirstName() + " " + oldestStudent.getLastName());

        students = studentQueryService.getStudentsWithAgeGreaterThan(25);
        for(Student s: students){
            System.out.println(s.getFirstName() + " " + s.getLastName());
        }
        students = studentQueryService.getStudentsWithAgeLessThan(25);
        for(Student s: students){
            System.out.println(s.getFirstName() + " " + s.getLastName());
        }
        List<StudentSummary> stusum = studentQueryService.findByFirstNameOrderByAgeAsc("Rares");
        for(StudentSummary s: stusum){
            System.out.println(s.getFirstName()+" "+ s.getEmail());
        }

        System.out.println(studentQueryService.getBestStudentWithComparator(new StudentAgeComparator()));
    }
    void bcsTEST(){
//        banner("BookCommandService TEST");
//        BookCreateRequest bookCreateRequest = new BookCreateRequest("Alba Ca Zapada");
//        BookCreateResponse bcr = bookCommandService.createBook(bookCreateRequest);
//        System.out.println(bcr.name() + " has been sucessfully created at " + bcr.createdAt());
//
//        BookUpdateRequest bookUpdateRequest = new BookUpdateRequest("Cenusareasa");
//        BookUpdateResponse bus = bookCommandService.updatebook(bcr.id(), bookUpdateRequest);
//        System.out.println(bus.name() + " has been updated");
//
//        BookDeleteResponse bdr = bookCommandService.deletebook(bcr.id());
//        System.out.println(bdr.name()+ " has successfully been deleted.");
    }
    void bqsTEST(){
        banner("BookQueryService TEST");



        List<Student> allWithBooks = studentQueryService.findAllStudentsWithBooks();
        for(Student st: allWithBooks){
            System.out.println(st.getFirstName()+" books: "+st.getBooks());
        }

        List<StudentBookCount> allWithBookCount = studentQueryService.getStudentBookCounts();
        banner("Student Book Count");
        for(StudentBookCount sb: allWithBookCount){
            System.out.println(sb.firstName()+" "+sb.lastName()+" book count: "+sb.bookCount());
        }

        List<Student> orderedList = studentQueryService.getStudentsOrderByBooksDesc();
        for(Student st: orderedList){
            System.out.println(st.getFirstName() + st.getBooks().size());
        }

    }
    void ccsTEST() {
        banner("CourseCommandService TEST");
        CourseCreateRequest ccr = new CourseCreateRequest("Informatica", "Stiinte");
        CourseUpdateRequest cup = new CourseUpdateRequest("Alchimie avansata", "Stiinte pe bune");

        CourseCreateResponse courseRep = courseCommandService.createCourse(ccr);
        System.out.println("Course created successfully " + courseRep.name());


        CourseUpdateResponse courseup = courseCommandService.updateCourse(courseRep.id(), cup);
        System.out.println("Course updated successfully " + courseup.name());

        CourseDeleteResponse cdr = courseCommandService.deleteCourse(courseRep.id());
        System.out.println("Course deleted successfully " + cdr.name());
    }
    void cqsTest(){
        banner("CourseQueryService TEST");

//
//
//        long count = courseQueryService.countByDepartment("Computer Science");
//        System.out.println("Computer Science department has " + count + " courses");
//
//        List<CourseSummary> courseSummaries = courseQueryService.findByDepartmentOrderByNameAsc("Computer Science");
//        for(CourseSummary cs: courseSummaries){
//            System.out.println(cs.getName());
//        }
//
//        List<CoursePerDepartmentCount> perDep = courseQueryService.findAndCountPerDepartment();
//        for(CoursePerDepartmentCount p: perDep){
//            System.out.println(p.department() + " " + p.count());
//        }

    }
    void ecsTEST(){
        banner("EnrollmentCommandService TEST");


        //Rares inrolat la Introduction to programming
//            UUID studId = UUID.fromString("a5e634cd-9f00-4bd1-a2eb-7c8eb99570f4");
//            UUID cursId = UUID.fromString("00830ada-9722-11f1-bb98-eecd9f76e24b");
//            EnrollmentCreateRequest ecr = new EnrollmentCreateRequest(studId, cursId);
//
//            EnrollmentCreateResponse raspuns = enrollmentCommandService.createEnrollment(ecr);
//            System.out.println("Enrollment creat: \n" +
//                    studentQueryService.getByIdJoinFetchBooks(raspuns.studentId()).getFirstName()+
//                    " s-a inrolat la "+
//                    courseQueryService.findById(raspuns.courseID()).getName());

        //Maria inrolata la Organic Chemistry, apoi update si delete
        banner("Maria inrolata la Organic Chemistry, apoi update si delete");
        UUID studId = UUID.fromString("8ee10b70-f532-4692-9d8f-847f1bf819e4");
        UUID cursId = UUID.fromString("00843a38-9722-11f1-bb98-eecd9f76e24b");
        EnrollmentCreateRequest ecr = new EnrollmentCreateRequest(studId, cursId);

        EnrollmentCreateResponse raspuns = enrollmentCommandService.createEnrollment(ecr);
        System.out.println("Enrollment creat: \n" +
                studentQueryService.getByIdJoinFetchBooks(raspuns.studentId()).getFirstName()+
                " s-a inrolat la "+
                courseQueryService.findById(raspuns.courseID()).getName());

        banner("Update");
        UUID microEconomicId = UUID.fromString("00852067-9722-11f1-bb98-eecd9f76e24b");
        EnrollmentUpdateRequest eur = new EnrollmentUpdateRequest(studId, microEconomicId);

        EnrollmentUpdateResponse update = enrollmentCommandService
                .updateEnrollment(raspuns.enrollmentId(), eur);

        System.out.println("Student " + studentQueryService.getStudentById(update.studentId()).getFirstName()+
                " enrollment update from " + courseQueryService.getById(raspuns.courseID()).get().getName()+
                " to " + courseQueryService.getById(update.courseId()).get().getName());

        banner("Delete");
        EnrollmentDeleteRequest edr = new EnrollmentDeleteRequest(update.studentId(), update.courseId());
        EnrollmentDeleteResponse delete = enrollmentCommandService.deleteEnrollment(edr);
        System.out.println("Enrollment-ul lui " +
                studentQueryService.getStudentById(delete.studentID()).getFirstName() +
                " la cursul " + courseQueryService.findById(delete.courseID()).getName() +
                " a fost sters");

    }
    void eqsTEST(){
        banner("EnrollmentQueryService TEST");




    }
    void factoryTest(){
        banner("Testing Factory");

        Student newS = studentFactory.createStudentFromText("Bogdan,Horghidan,bgdhrg@gmail.ro,28");
        System.out.println(newS);

    }

        void banner(String title){
            System.out.println("============"+title+"============");
        }

    }


