package academy.hub.app;

import academy.hub.app.book.models.Book;
import academy.hub.app.book.repositories.BookRepository;
import academy.hub.app.course.models.Course;
import academy.hub.app.course.repositories.CourseRepository;
import academy.hub.app.course.services.interfaces.CourseCommandService;
import academy.hub.app.course.services.interfaces.CourseQueryService;
import academy.hub.app.enrollment.models.Enrollment;
import academy.hub.app.enrollment.repositories.EnrollmentRepository;
import academy.hub.app.enrollment.services.interfaces.EnrollmentCommandService;
import academy.hub.app.student.models.Student;
import academy.hub.app.student.repositories.StudentRepository;
import academy.hub.app.student.services.interfaces.StudentQueryService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class Runner implements CommandLineRunner {

    private final StudentQueryService studentQueryService;
    private final CourseCommandService courseCommandService;
    private final CourseQueryService courseQueryService;
    private final EnrollmentCommandService enrollmentCommandService;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final BookRepository bookRepository;
    private final EnrollmentRepository enrollmentRepository;


    public Runner(StudentQueryService studentQueryService,
                  CourseCommandService courseCommandService, CourseQueryService courseQueryService,
                  EnrollmentCommandService enrollmentCommandService, CourseRepository courseRepository,
                  StudentRepository studentRepository, BookRepository bookRepository,
                  EnrollmentRepository enrollmentRepository) {
        this.studentQueryService = studentQueryService;
        this.courseCommandService = courseCommandService;
        this.courseQueryService = courseQueryService;
        this.enrollmentCommandService = enrollmentCommandService;
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

        void banner(String title){
            System.out.println("============"+title+"============");
        }

    }


