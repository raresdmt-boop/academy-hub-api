package academy.hub.app.student.dtos;

import jakarta.validation.constraints.*;

import java.util.UUID;

public record StudentUpdateRequest(
        @NotNull(message="id is required for update")
        UUID id,


        String firstName,


        String lastName,


        String email,


        int age) {
}
