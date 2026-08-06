package academy.hub.app.student.exceptions;

public class EmailAlreadyUsed extends RuntimeException {
    public EmailAlreadyUsed() {
        super(ExceptionConstants.EMAIL_ALREADY_USED);
    }
}
