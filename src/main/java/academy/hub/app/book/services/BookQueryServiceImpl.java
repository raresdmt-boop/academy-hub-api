package academy.hub.app.book.services;

import academy.hub.app.book.dtos.BookResponse;
import academy.hub.app.book.exceptions.BookNotFound;
import academy.hub.app.book.exceptions.NoBookFound;
import academy.hub.app.book.models.Book;
import academy.hub.app.book.repository.BookRepository;
import academy.hub.app.book.services.interfaces.BookQueryService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class BookQueryServiceImpl implements BookQueryService {

    private final BookRepository bookRepository;

    public BookQueryServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<BookResponse> getBooks() {


        return bookRepository.findAll()
                .stream()
                .map(BookResponse::from)
                .toList();
    }

    @Override
    public List<Book> getStudentBooks(UUID id) {

        return bookRepository.findByStudentId(id);
    }

    @Override
    public BookResponse getBookById(UUID id) {

        return BookResponse.from(bookRepository.findById(id).orElseThrow(BookNotFound::new));
    }

    @Override
    public long countBooksByStudentId(UUID id) {

        return bookRepository.countByStudentId(id);
    }




}