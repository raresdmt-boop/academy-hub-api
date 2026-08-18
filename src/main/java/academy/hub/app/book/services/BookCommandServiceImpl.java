package academy.hub.app.book.services;

import academy.hub.app.book.dtos.*;
import academy.hub.app.book.exceptions.BookAlreadyExists;
import academy.hub.app.book.exceptions.BookNotFound;
import academy.hub.app.book.models.Book;
import academy.hub.app.book.repository.BookRepository;
import academy.hub.app.book.services.interfaces.BookCommandService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.UUID;

@Service
@Validated
public class BookCommandServiceImpl implements BookCommandService {

    private final BookRepository bookRepository;

    public BookCommandServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    @Override
    @Transactional
    public BookCreateResponse createBook(BookCreateRequest bookCreateRequest) {
        if(bookRepository.existsByName(bookCreateRequest.name())){
            throw new BookAlreadyExists();
        }

        LocalDate createdAt = LocalDate.now();

        Book newbook = new Book(
                bookCreateRequest.name(),
                createdAt
        );

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
        if(!bookRepository.existsById(id)){
            throw new BookNotFound();
        }
        Book book = bookRepository.findById(id).orElseThrow();
        book.setName(bookUpdateRequest.name());
        bookRepository.save(book);
        return new BookUpdateResponse(book.getId(), book.getName(), book.getCreatedAt());
    }


}
