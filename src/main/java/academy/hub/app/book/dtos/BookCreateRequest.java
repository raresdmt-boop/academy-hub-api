package academy.hub.app.book.dtos;

import jakarta.validation.constraints.NotBlank;

public record BookCreateRequest(
        @NotBlank(message = "Book name is required")
        String name
)
        {
}
