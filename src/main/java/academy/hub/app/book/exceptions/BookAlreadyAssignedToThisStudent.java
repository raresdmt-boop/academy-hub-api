package academy.hub.app.book.exceptions;

public class BookAlreadyAssignedToThisStudent extends RuntimeException {
    public BookAlreadyAssignedToThisStudent() {
        super(BookExceptionConstants.BOOK_ALREADY_ASSIGNED_TO_THIS_STUDENT);
    }
}
