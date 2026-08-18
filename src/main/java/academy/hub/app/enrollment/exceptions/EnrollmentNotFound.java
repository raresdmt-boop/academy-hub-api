package academy.hub.app.enrollment.exceptions;

public class EnrollmentNotFound extends RuntimeException {
    public EnrollmentNotFound() {
        super(ExceptionConstants.ENROLLMENT_NOT_FOUND);
    }
}
