package academy.hub.app.enrollment.exceptions;

public class EnrollmentNotFound extends RuntimeException {
    public EnrollmentNotFound() {
        super(EnrollmentExceptionConstants.ENROLLMENT_NOT_FOUND);
    }
}
