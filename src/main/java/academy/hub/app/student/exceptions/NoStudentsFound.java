package academy.hub.app.student.exceptions;

public class NoStudentsFound extends RuntimeException {
    public NoStudentsFound() {
        super(ExceptionConstants.NO_STUDENTS_FOUND);
    }
}
