package academy.hub.app.book.dtos;

import jakarta.validation.constraints.NotBlank;

public record BookUpdateRequest(
        @NotBlank(message = "name is required for update")
        String name
) {
}
