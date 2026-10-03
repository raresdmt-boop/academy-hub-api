package academy.hub.app.course.exceptions;

public class CourseHasEnrollments extends RuntimeException {
    public CourseHasEnrollments() {
        super(ExceptionConstants.COURSE_HAS_ENROLLMENTS);
    }
}
