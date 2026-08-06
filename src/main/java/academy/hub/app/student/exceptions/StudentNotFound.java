package academy.hub.app.student.exceptions;

public class StudentNotFound extends RuntimeException {
    public StudentNotFound() {
        super(ExceptionConstants.STUDENT_NOT_FOUND);
    }
}
