package academy.hub.app.book.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record BookUpdateRequest(
        @NotBlank(message = "name is required for update")
        String name,

        @NotNull(message = "Created At is required")
        LocalDate createdAt
) {
}
