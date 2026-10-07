package academy.hub.app.enrollment.exceptions;

public class StudentAlreadyEnrolledInThisCourse extends RuntimeException {
    public StudentAlreadyEnrolledInThisCourse() {
        super(EnrollmentExceptionConstants.
        STUDENT_ALREADY_ENROLLED_IN_THIS_COURSE);
    }
}
