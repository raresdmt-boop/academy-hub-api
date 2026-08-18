package academy.hub.app.student.exceptions;

public class StudentIdNotFound extends RuntimeException {
    public StudentIdNotFound() {
        super(ExceptionConstants.STUDENT_ID_NOT_FOUND);
    }
}
