package academy.hub.app.course.dtos;

import academy.hub.app.course.models.Course;

import java.util.UUID;

public record CourseResponse(
        UUID id,
        String name,
        String department,
        Long enrollmentsCount
) {

    public static CourseResponse from(Course course){
        return new CourseResponse(
            course.getId(),
            course.getName(),
            course.getDepartment(),
                (long) course.getEnrollments().size());

    }
}
