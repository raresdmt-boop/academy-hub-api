package academy.hub.app.book.services.interfaces;

import academy.hub.app.book.dtos.BookResponse;

import java.util.List;
import java.util.UUID;

public interface BookQueryService {

    List<BookResponse> getBooks();
    List<BookResponse> getStudentBooks(UUID id);
    BookResponse getBookById(UUID id);

    long countBooksByStudentId(UUID id);
}
