package academy.hub.app.student.exceptions;

public class EmailNotFound extends RuntimeException {
    public EmailNotFound() {
        super(StudentExceptionConstants.EMAIL_NOT_FOUND);
    }
}
