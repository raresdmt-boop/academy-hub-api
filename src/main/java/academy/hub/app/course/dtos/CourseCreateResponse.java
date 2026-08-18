package academy.hub.app.course.dtos;

import java.util.UUID;

public record CourseCreateResponse(UUID id, String name, String department) {
}
