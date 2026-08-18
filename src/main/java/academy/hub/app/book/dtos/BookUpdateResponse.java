package academy.hub.app.book.dtos;

import java.time.LocalDate;
import java.util.UUID;

public record BookUpdateResponse(UUID id, String name, LocalDate createdAt) {
}
