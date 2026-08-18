package academy.hub.app.student.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record StudentDeleteRequest(
        @NotNull(message = "id necessary for deletion")
        UUID id,


        @NotBlank(message = "Email must be entered")
        @Email(message = "Email must be a valid email address")
        String email) {
}
