package academy.hub.app.student.exceptions;

public class TextArgumentNumberInvalid extends RuntimeException {
    public TextArgumentNumberInvalid() {
        super(ExceptionConstants.TEXT_ARGUMENT_NUMBER_INVALID);
    }
}
