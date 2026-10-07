package academy.hub.app.student.exceptions;

public class EmailAlreadyUsed extends RuntimeException {
    public EmailAlreadyUsed() {
        super(StudentExceptionConstants.EMAIL_ALREADY_USED);
    }
}
