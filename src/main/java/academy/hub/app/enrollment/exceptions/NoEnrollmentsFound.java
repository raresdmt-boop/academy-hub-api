package academy.hub.app.enrollment.exceptions;

public class NoEnrollmentsFound extends RuntimeException {
    public NoEnrollmentsFound() {
        super(ExceptionConstants.NO_ENROLLMENTS_FOUND);
    }
}
