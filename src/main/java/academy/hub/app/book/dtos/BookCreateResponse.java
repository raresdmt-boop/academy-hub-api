package academy.hub.app.book.dtos;

import java.time.LocalDate;
import java.util.UUID;

public record BookCreateResponse(
        UUID id, String name, LocalDate createdAt
) {
}
