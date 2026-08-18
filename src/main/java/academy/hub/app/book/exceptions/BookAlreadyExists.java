package academy.hub.app.book.exceptions;

import academy.hub.app.book.exceptions.ExceptionConstants;

public class BookAlreadyExists extends RuntimeException {
    public BookAlreadyExists() {
        super(ExceptionConstants.BOOK_ALREADY_EXISTS);
    }
}
