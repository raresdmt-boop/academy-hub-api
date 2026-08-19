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


    public Runner(StudentCommandService studentCommandService, StudentQueryService studentQueryService,
                  BookCommandService bookCommandService, BookQueryService bookQueryService,
                  CourseCommandService courseCommandService, CourseQueryService courseQueryService,
                  EnrollmentCommandService enrollmentCommandService, EnrollmentQueryService enrollmentQueryService,
                  StudentFactory studentFactory) {
        this.studentCommandService = studentCommandService;
        this.studentQueryService = studentQueryService;
        this.bookCommandService = bookCommandService;
        this.bookQueryService = bookQueryService;
        this.courseCommandService = courseCommandService;
        this.courseQueryService = courseQueryService;
        this.enrollmentCommandService = enrollmentCommandService;
        this.enrollmentQueryService = enrollmentQueryService;
        this.studentFactory = studentFactory;
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
//        seed();
//        seedBooks();

    }


        void scsTEST() {
        banner("StudentCommandService TEST");
            StudentCreateRequest newStudent = new StudentCreateRequest("Rares", "Dumitru",
                    "rares123@gmail.ro", 31);
            StudentCreateResponse scr = studentCommandService.addStudent(newStudent);

            System.out.println("Student created successfully " + scr.firstName() + " " + scr.lastName() + " " + scr.id());


            StudentUpdateRequest updateStudent = new StudentUpdateRequest(scr.id(), "Paul", null, null, 0);
            StudentUpdateResponse updateStudentResponse = studentCommandService.updateStudent(updateStudent);
            System.out.println("Student updated successfully " + updateStudentResponse.firstName() + " " + updateStudentResponse.lastName()
                    + " " + updateStudentResponse.email() + " " + updateStudentResponse.age());

            StudentDeleteRequest delStu = new StudentDeleteRequest(scr.id(), scr.email());
            StudentDeleteResponse sdr = studentCommandService.deleteStudent(delStu);
            System.out.println("Student " + sdr.firstName() + " " + sdr.lastName() + " has been deleted.");
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
            banner("BookCommandService TEST");
            BookCreateRequest bookCreateRequest = new BookCreateRequest("Alba Ca Zapada");
            BookCreateResponse bcr = bookCommandService.createBook(bookCreateRequest);
            System.out.println(bcr.name() + " has been sucessfully created at " + bcr.createdAt());

            BookUpdateRequest bookUpdateRequest = new BookUpdateRequest("Cenusareasa");
            BookUpdateResponse bus = bookCommandService.updatebook(bcr.id(), bookUpdateRequest);
            System.out.println(bus.name() + " has been updated");

            BookDeleteResponse bdr = bookCommandService.deletebook(bcr.id());
            System.out.println(bdr.name()+ " has successfully been deleted.");
        }
        void bqsTEST(){
        banner("BookQueryService TEST");

        List<Book>  books = bookQueryService.getBooks();
        for(Book b: books){
            System.out.println(b.getName());
        }

        Student s = studentQueryService.getOldestStudent();
        books = bookQueryService.getStudentBooks(s.getId());
        for(Book b: books){
            System.out.println(b.getName() + " -> " + b.getStudent().getFirstName() + " " + b.getStudent().getLastName());
        }

        long bookNr = bookQueryService.countBooksByStudentId(s.getId());
        System.out.println(s.getFirstName()+" are "+bookNr + " books");

        Student sFetch = studentQueryService.getByIdJoinFetchBooks(s.getId());
        System.out.println(sFetch.getFirstName()+sFetch.getLastName()+ " books: " + sFetch.getBooks());
        for(Book b: sFetch.getBooks()){
            System.out.println(s.getFirstName()+" book: "+b.getName());
        }

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

        List<Course> courses = courseQueryService.findAll();
        for(Course c: courses){
            System.out.println(c.getName()+" - department ->"+c.getDepartment());
        }

        courses = courseQueryService.findByDepartment("Computer Science");
        for(Course c: courses){
            System.out.println(c.getName());
        }

        long count = courseQueryService.countByDepartment("Computer Science");
        System.out.println("Computer Science department has " + count + " courses");

        List<CourseSummary> courseSummaries = courseQueryService.findByDepartmentOrderByNameAsc("Computer Science");
        for(CourseSummary cs: courseSummaries){
            System.out.println(cs.getName());
        }

        List<CoursePerDepartmentCount> perDep = courseQueryService.findAndCountPerDepartment();
        for(CoursePerDepartmentCount p: perDep){
            System.out.println(p.department() + " " + p.count());
        }

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

        List<Enrollment> enrollments = enrollmentQueryService.getAllEnrollments();
        for(Enrollment e: enrollments){
            System.out.println(e.getStudent().getFirstName()+" -> "+e.getCourse().getName());
        }


        }
        void factoryTest(){
        banner("Testing Factory");

        Student newS = studentFactory.createStudentFromText("Bogdan,Horghidan,bgdhrg@gmail.ro,28");
        System.out.println(newS);

        }
        void seed(){


                banner("SEED FOR QUERIES");

                // =========================
                // STUDENTS
                // =========================

                StudentCreateResponse rares = studentCommandService.addStudent(
                        new StudentCreateRequest(
                                "Rares",
                                "Dumitru",
                                "rares.dumitru@gmail.com",
                                31
                        )
                );

                StudentCreateResponse maria = studentCommandService.addStudent(
                        new StudentCreateRequest(
                                "Maria",
                                "Popescu",
                                "maria.popescu@gmail.com",
                                22
                        )
                );

                StudentCreateResponse andrei = studentCommandService.addStudent(
                        new StudentCreateRequest(
                                "Andrei",
                                "Ionescu",
                                "andrei.ionescu@gmail.com",
                                27
                        )
                );

                StudentCreateResponse ana = studentCommandService.addStudent(
                        new StudentCreateRequest(
                                "Ana",
                                "Dumitrescu",
                                "ana.dumitrescu@gmail.com",
                                24
                        )
                );

                StudentCreateResponse mihai = studentCommandService.addStudent(
                        new StudentCreateRequest(
                                "Mihai",
                                "Georgescu",
                                "mihai.georgescu@gmail.com",
                                35
                        )
                );

                StudentCreateResponse elena = studentCommandService.addStudent(
                        new StudentCreateRequest(
                                "Elena",
                                "Stan",
                                "elena.stan@gmail.com",
                                29
                        )
                );


                // =========================
                // COURSES
                // =========================

                CourseCreateResponse java = courseCommandService.createCourse(
                        new CourseCreateRequest(
                                "Java Fundamentals",
                                "Computer Science"
                        )
                );

                CourseCreateResponse spring = courseCommandService.createCourse(
                        new CourseCreateRequest(
                                "Spring Boot",
                                "Computer Science"
                        )
                );

                CourseCreateResponse sql = courseCommandService.createCourse(
                        new CourseCreateRequest(
                                "SQL Fundamentals",
                                "Computer Science"
                        )
                );

                CourseCreateResponse economics = courseCommandService.createCourse(
                        new CourseCreateRequest(
                                "Microeconomics",
                                "Economics"
                        )
                );


                // =========================
                // ENROLLMENTS
                // =========================

                // Rares -> Java, Spring, SQL
                enrollmentCommandService.createEnrollment(
                        new EnrollmentCreateRequest(
                                rares.id(),
                                java.id()
                        )
                );

                enrollmentCommandService.createEnrollment(
                        new EnrollmentCreateRequest(
                                rares.id(),
                                spring.id()
                        )
                );

                enrollmentCommandService.createEnrollment(
                        new EnrollmentCreateRequest(
                                rares.id(),
                                sql.id()
                        )
                );


                // Maria -> Java, SQL
                enrollmentCommandService.createEnrollment(
                        new EnrollmentCreateRequest(
                                maria.id(),
                                java.id()
                        )
                );

                enrollmentCommandService.createEnrollment(
                        new EnrollmentCreateRequest(
                                maria.id(),
                                sql.id()
                        )
                );


                // Andrei -> Java, Spring
                enrollmentCommandService.createEnrollment(
                        new EnrollmentCreateRequest(
                                andrei.id(),
                                java.id()
                        )
                );

                enrollmentCommandService.createEnrollment(
                        new EnrollmentCreateRequest(
                                andrei.id(),
                                spring.id()
                        )
                );


                // Ana -> SQL
                enrollmentCommandService.createEnrollment(
                        new EnrollmentCreateRequest(
                                ana.id(),
                                sql.id()
                        )
                );


                // Mihai -> Java, Spring, SQL
                enrollmentCommandService.createEnrollment(
                        new EnrollmentCreateRequest(
                                mihai.id(),
                                java.id()
                        )
                );

                enrollmentCommandService.createEnrollment(
                        new EnrollmentCreateRequest(
                                mihai.id(),
                                spring.id()
                        )
                );

                enrollmentCommandService.createEnrollment(
                        new EnrollmentCreateRequest(
                                mihai.id(),
                                sql.id()
                        )
                );


                // Elena -> Microeconomics
                enrollmentCommandService.createEnrollment(
                        new EnrollmentCreateRequest(
                                elena.id(),
                                economics.id()
                        )
                );

        }
        void seedBooks(){

                Student rares = studentQueryService
                        .getByEmail("rares.dumitru@gmail.com")
                        .orElseThrow();

                Student maria = studentQueryService
                        .getByEmail("maria.popescu@gmail.com")
                        .orElseThrow();

                Student andrei = studentQueryService
                        .getByEmail("andrei.ionescu@gmail.com")
                        .orElseThrow();

                Student ana = studentQueryService
                        .getByEmail("ana.dumitrescu@gmail.com")
                        .orElseThrow();

                Student mihai = studentQueryService
                        .getByEmail("mihai.georgescu@gmail.com")
                        .orElseThrow();

                Student elena = studentQueryService
                        .getByEmail("elena.stan@gmail.com")
                        .orElseThrow();

                LocalDate date = LocalDate.now();

                rares.addBook(new Book("Effective Java", date));
                rares.addBook(new Book("Clean Code", date));
                rares.addBook(new Book("Spring Start Here", date));

                maria.addBook(new Book("Head First Java", date));
                maria.addBook(new Book("Learning SQL", date));

                andrei.addBook(
                        new Book("Java Concurrency in Practice", date)
                );

                ana.addBook(new Book("SQL Cookbook", date));
                ana.addBook(new Book("Design Patterns", date));

                mihai.addBook(new Book("Effective Java", date));
                mihai.addBook(new Book("Clean Architecture", date));
                mihai.addBook(new Book("Spring in Action", date));

                // Elena o lăsăm intenționat fără cărți

                studentRepository.saveAll(
                        List.of(rares, maria, andrei, ana, mihai)
                );

        }




        void banner(String title){
            System.out.println("============"+title+"============");
        }

    }


