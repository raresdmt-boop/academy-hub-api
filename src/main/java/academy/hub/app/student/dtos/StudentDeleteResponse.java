package academy.hub.app.student.dtos;

import java.util.UUID;

public record StudentDeleteResponse(UUID id, String firstName, String lastName, String email) {
}
