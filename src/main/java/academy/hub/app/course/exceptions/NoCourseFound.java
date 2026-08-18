package academy.hub.app.course.exceptions;

public class NoCourseFound extends RuntimeException {
    public NoCourseFound() {
        super(ExceptionConstants.NO_COURSE_FOUND);
    }
}
