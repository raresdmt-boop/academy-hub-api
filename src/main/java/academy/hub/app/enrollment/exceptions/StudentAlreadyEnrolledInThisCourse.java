package academy.hub.app.enrollment.exceptions;

import academy.hub.app.enrollment.exceptions.ExceptionConstants;

public class StudentAlreadyEnrolledInThisCourse extends RuntimeException {
    public StudentAlreadyEnrolledInThisCourse() {
        super(ExceptionConstants.
        STUDENT_ALREADY_ENROLLED_IN_THIS_COURSE);
    }
}
