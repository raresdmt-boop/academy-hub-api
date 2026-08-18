package academy.hub.app.student.exceptions;

public class EmailNotFound extends RuntimeException {
    public EmailNotFound() {
        super(ExceptionConstants.EMAIL_NOT_FOUND);
    }
}
