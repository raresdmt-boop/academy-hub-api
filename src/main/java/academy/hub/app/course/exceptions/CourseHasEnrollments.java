package academy.hub.app.course.exceptions;

public class CourseHasEnrollments extends RuntimeException {
  public CourseHasEnrollments(String message) {
    super(message);
  }
}
