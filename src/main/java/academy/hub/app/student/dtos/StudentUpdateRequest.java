package academy.hub.app.student.dtos;

import jakarta.validation.constraints.*;

import java.util.UUID;

public record StudentUpdateRequest(

        @NotBlank(message="First name is required for update")
        String firstName,

        @NotBlank(message="Last name is required for update")
        String lastName,

        @NotBlank(message="email is required for update")
        @Email(message = "email must be a valid email address")
        String email,

        @NotNull(message="Age is required for update")
        @Positive(message = "Age must be positive")
        int age) {
}
