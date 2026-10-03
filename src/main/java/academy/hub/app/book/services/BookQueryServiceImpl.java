package academy.hub.app.book.services;

import academy.hub.app.book.dtos.BookResponse;
import academy.hub.app.book.exceptions.BookNotFound;
import academy.hub.app.book.repositories.BookRepository;
import academy.hub.app.book.services.interfaces.BookQueryService;
import academy.hub.app.student.services.interfaces.StudentQueryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BookQueryServiceImpl implements BookQueryService {

    private final BookRepository bookRepository;
    private final StudentQueryService studentQueryService;

    public BookQueryServiceImpl(
            BookRepository bookRepository,
            StudentQueryService studentQueryService) {
        this.bookRepository = bookRepository;
        this.studentQueryService = studentQueryService;
    }

    @Override
    public List<BookResponse> getBooks() {


        return bookRepository.findAll()
                .stream()
                .map(BookResponse::from)
                .toList();
    }

    @Override
    public List<BookResponse> getStudentBooks(UUID id) {

        studentQueryService.getById(id);

        return bookRepository.findByStudentId(id)
                .stream()
                .map(BookResponse::from)
                .toList();
    }

    @Override
    public BookResponse getBookById(UUID id) {

        return BookResponse.from(bookRepository.findById(id).orElseThrow(BookNotFound::new));
    }

    @Override
    public long countBooksByStudentId(UUID id) {

        studentQueryService.getById(id);

        return bookRepository.countByStudentId(id);
    }


}