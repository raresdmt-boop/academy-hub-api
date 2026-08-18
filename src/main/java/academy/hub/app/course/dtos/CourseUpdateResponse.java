package academy.hub.app.course.dtos;

import java.util.UUID;

public record CourseUpdateResponse(UUID id, String name, String department) {
}
