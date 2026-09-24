package academy.hub.app.book.services;

import academy.hub.app.book.dtos.*;
import academy.hub.app.book.exceptions.BookAlreadyAssignedToThisStudent;
import academy.hub.app.book.exceptions.BookAlreadyExists;
import academy.hub.app.book.exceptions.BookNotFound;
import academy.hub.app.book.models.Book;
import academy.hub.app.book.repository.BookRepository;
import academy.hub.app.book.services.interfaces.BookCommandService;
import academy.hub.app.student.exceptions.StudentNotFound;
import academy.hub.app.student.models.Student;
import academy.hub.app.student.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.UUID;

@Service
@Validated
public class BookCommandServiceImpl implements BookCommandService {

    private final BookRepository bookRepository;
    private final StudentRepository studentRepository;

    public BookCommandServiceImpl(BookRepository bookRepository, StudentRepository studentRepository) {
        this.bookRepository = bookRepository;
        this.studentRepository = studentRepository;
    }


    @Override
    @Transactional
    public BookCreateResponse createBook(BookCreateRequest bookCreateRequest) {

        if(!bookRepository.findByStudentId(bookCreateRequest.studentId()).isEmpty() &&
                bookRepository.existsByName(bookCreateRequest.name())) {
            throw new BookAlreadyAssignedToThisStudent();
        }

        LocalDate createdAt = LocalDate.now();

        Student student = studentRepository.findById(bookCreateRequest.studentId()).orElseThrow();

        Book newbook = new Book(
                bookCreateRequest.name(),
                createdAt
        );

        student.addBook(newbook);

        bookRepository.save(newbook);

        return new BookCreateResponse(
                newbook.getId(),
                newbook.getName(),
                newbook.getCreatedAt()
        );

    }

    @Override
    public BookDeleteResponse deletebook(UUID id) {
        if(!bookRepository.existsById(id)){
            throw new BookNotFound();
        }
        Book book = bookRepository.findById(id).orElseThrow();
        bookRepository.delete(book);
        return new BookDeleteResponse(book.getId(), book.getName());
    }

    @Override
    public BookUpdateResponse updatebook(UUID id, BookUpdateRequest bookUpdateRequest) {

        Book book = bookRepository.findById(id).orElseThrow(BookNotFound::new);
        book.setName(bookUpdateRequest.name());
        book.setCreatedAt(bookUpdateRequest.createdAt());
        bookRepository.save(book);
        return new BookUpdateResponse(book.getId(), book.getName(), book.getCreatedAt());
    }


}
