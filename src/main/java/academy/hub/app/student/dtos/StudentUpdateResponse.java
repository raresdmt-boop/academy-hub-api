package academy.hub.app.student.dtos;

import java.util.UUID;

public record StudentUpdateResponse(UUID id, String firstName, String lastName, String email, int age) {
}
