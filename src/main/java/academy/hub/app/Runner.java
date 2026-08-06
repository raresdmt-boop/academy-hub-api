package academy.hub.app;

import academy.hub.app.book.models.Book;
import academy.hub.app.book.repository.BookRepository;
import academy.hub.app.student.models.Student;
import academy.hub.app.student.repository.StudentRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Component
public class Runner implements CommandLineRunner {

    private final BookRepository bookRepo;
    private final StudentRepository studentRepo;

    public Runner(BookRepository bookRepo, StudentRepository studentRepo) {
        this.bookRepo = bookRepo;
        this.studentRepo = studentRepo;
    }
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        reset();
        seed();
        clearPersistenceContext();
        testQueries();
        clearPersistenceContext();


    }

    public void seed(){

        Student rares = new Student(
                "Rares",
                "Dumitru",
                "rares@gmail.com",
                31
        );

        rares.addBook(new Book(
                "Clean Code",
                LocalDate.of(2026, 8, 1)
        ));

        rares.addBook(new Book(
                "Spring in Action",
                LocalDate.of(2026, 8, 5)
        ));

        Student maria = new Student(
                "Maria",
                "Popescu",
                "maria@gmail.com",
                24
        );

        maria.addBook(new Book(
                "Effective Java",
                LocalDate.of(2026, 7, 20)
        ));

        studentRepo.save(rares);
        studentRepo.save(maria);

    }

    public void reset(){
        bookRepo.deleteAll();
        studentRepo.deleteAll();
    }
    public void clearPersistenceContext(){
        entityManager.flush();
        entityManager.clear();
    }

    void testQueries(){
        banner("test start");
        List<Book> books = bookRepo.findAll();
        for(Book book : books){
            System.out.println(book + " " + book.getStudent());
        }

        List<Student> students = studentRepo.findAll();
        for(Student student : students){
            System.out.println(student);
        }
        banner("find by id");
        UUID id = studentRepo.findByFirstName("Rares");
        Student s = studentRepo.findById(id).orElseThrow();
    }

    public void banner(String title){
        System.out.println("==========="+title.toUpperCase()+"===========");
    }


}
