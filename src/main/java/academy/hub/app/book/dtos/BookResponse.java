package academy.hub.app.book.dtos;

import academy.hub.app.book.models.Book;

import java.util.UUID;

public record BookResponse(
        UUID id,
        String name,
        String studentName
) {

    public static BookResponse from(Book book) {
        return new BookResponse(
                book.getId(),
                book.getName(),
                book.getStudent().getFirstName() + " " + book.getStudent().getLastName()
        );
    }

}
