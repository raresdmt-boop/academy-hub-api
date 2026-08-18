package academy.hub.app.book.services.interfaces;

import academy.hub.app.book.models.Book;

import java.util.List;
import java.util.UUID;

public interface BookQueryService {

    List<Book> getBooks();
    List<Book> getStudentBooks(UUID id);

    long countBooksByStudentId(UUID id);
}
