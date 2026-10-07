package academy.hub.app.book.exceptions;

public class BookNotFound extends RuntimeException {
    public BookNotFound() {
        super(BookExceptionConstants.BOOK_NOT_FOUND);
    }
}
