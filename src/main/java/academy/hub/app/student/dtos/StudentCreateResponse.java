package academy.hub.app.student.dtos;

import java.util.UUID;

public record StudentCreateResponse(UUID id,String firstName, String lastName, String email, int age) {
}
