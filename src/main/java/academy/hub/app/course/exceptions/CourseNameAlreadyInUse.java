package academy.hub.app.course.exceptions;

public class CourseNameAlreadyInUse extends RuntimeException {
    public CourseNameAlreadyInUse() {
        super(ExceptionConstants.COURSE_NAME_ALREADY_IN_USE);
    }
}
