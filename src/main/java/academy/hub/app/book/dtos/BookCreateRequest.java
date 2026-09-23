package academy.hub.app.book.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record BookCreateRequest(
        @NotBlank(message = "Book name is required")
        String name,

        @NotNull(message = "Student required")
        UUID studentId
)
        {
}
