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
import academy.hub.app.enrollment.services.interfaces.EnrollmentCommandService;
import academy.hub.app.student.comparators.StudentAgeComparator;
import academy.hub.app.student.dtos.*;
import academy.hub.app.student.exceptions.StudentNotFound;
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

    public Runner(StudentCommandService studentCommandService, StudentQueryService studentQueryService,
                  BookCommandService bookCommandService, BookQueryService bookQueryService, CourseCommandService courseCommandService, CourseQueryService courseQueryService, EnrollmentCommandService enrollmentCommandService) {
        this.studentCommandService = studentCommandService;
        this.studentQueryService = studentQueryService;
        this.bookCommandService = bookCommandService;
        this.bookQueryService = bookQueryService;
        this.courseCommandService = courseCommandService;
        this.courseQueryService = courseQueryService;
        this.enrollmentCommandService = enrollmentCommandService;
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
        ecsTEST();

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

        void banner(String title){
            System.out.println("============"+title+"============");
        }

    }


