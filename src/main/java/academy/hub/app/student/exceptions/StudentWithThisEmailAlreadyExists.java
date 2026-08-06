package academy.hub.app.student.exceptions;

public class StudentWithThisEmailAlreadyExists extends RuntimeException {
    public StudentWithThisEmailAlreadyExists() {
        super(ExceptionConstants.STUDENT_WITH_THIS_EMAIL_ALREADY_EXISTS);
    }
}
