package academy.hub.app.book.exceptions;

public class BookAlreadyAssignedToThisStudent extends RuntimeException {
    public BookAlreadyAssignedToThisStudent() {
        super(ExceptionConstants.BOOK_ALREADY_ASSIGNED_TO_THIS_STUDENT);
    }
}
