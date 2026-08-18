package academy.hub.app.course.exceptions;

public class CourseIdNotFound extends RuntimeException {
    public CourseIdNotFound() {
        super(ExceptionConstants.COURSE_ID_NOT_FOUND);
    }
}
