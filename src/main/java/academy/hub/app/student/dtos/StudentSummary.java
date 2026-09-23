package academy.hub.app.student.dtos;

import academy.hub.app.course.repository.CourseRepository;

import java.util.UUID;

public interface StudentSummary {

    UUID getId();

    String getFirstName();

    String getEmail();


}
