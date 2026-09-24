package academy.hub.app.course.dtos;

import academy.hub.app.course.models.Course;

import java.util.function.Function;

public record CourseSummary(
        String name
) {



    public static CourseSummary from(Course course){
        return new CourseSummary(
                course.getName()
        );
    }

}
