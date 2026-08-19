package academy.hub.app.student.factory;

import academy.hub.app.student.exceptions.TextArgumentNumberInvalid;
import academy.hub.app.student.models.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentFactoryImpl implements StudentFactory{

    @Override
    public Student createStudentFromText(String text) {
        String[] props = text.split(",");
        if(props.length!=4){
            throw new TextArgumentNumberInvalid();
        }
        return new Student(text);
    }


}
