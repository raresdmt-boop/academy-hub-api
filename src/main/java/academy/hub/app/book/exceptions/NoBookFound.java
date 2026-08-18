package academy.hub.app.book.exceptions;

public class NoBookFound extends RuntimeException {
    public NoBookFound() {
        super(ExceptionConstants.NO_BOOK_FOUND);
    }
}
