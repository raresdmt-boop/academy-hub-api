package academy.hub.app.book.services;

import academy.hub.app.book.exceptions.NoBookFound;
import academy.hub.app.book.models.Book;
import academy.hub.app.book.repository.BookRepository;
import academy.hub.app.book.services.interfaces.BookQueryService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class BookQueryServiceImpl implements BookQueryService {

    private final BookRepository bookRepository;

    public BookQueryServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> getBooks() {
        if(bookRepository.findAll().isEmpty()) {
            throw new NoBookFound();
        }
        return bookRepository.findAll();
    }

    @Override
    public List<Book> getStudentBooks(UUID id) {
        if(bookRepository.findByStudentId(id) == null) {
            throw new NoBookFound();
        }
        return bookRepository.findByStudentId(id);
    }

    @Override
    public long countBooksByStudentId(UUID id) {
        if(bookRepository.findByStudentId(id) == null) {
            throw new NoBookFound();
        }
        return bookRepository.countByStudentId(id);
    }


}