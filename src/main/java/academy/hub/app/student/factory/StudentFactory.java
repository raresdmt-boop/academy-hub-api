package academy.hub.app.student.factory;

import academy.hub.app.student.models.Student;

public interface StudentFactory {

    Student createStudentFromText(String text);

}
