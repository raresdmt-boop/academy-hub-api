package academy.hub.app.support;

import academy.hub.app.book.models.Book;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.UUID;

public class BookFixtures {

    public static final UUID KNOWN_ID =
            UUID.fromString("11111111-1111-1111-1111-111111111111");

    public static final UUID OTHER_ID =
            UUID.fromString("22222222-2222-2222-2222-222222222222");

    public static Book book() {
        return new Book(
                "Clean Code",
                LocalDate.of(2026, 1, 10)
        );
    }

    public static Book persisted() {
        Book book = book();

        ReflectionTestUtils.setField(book, "id", KNOWN_ID);

        return book;
    }

    public static Book persisted(String name){
        Book book = new Book(
                name,
                LocalDate.of(2026, 1, 10)
        );

        ReflectionTestUtils.setField(book, "id", KNOWN_ID);

        return book;
    }

    public static Book withId(UUID id) {
        Book book = book();

        ReflectionTestUtils.setField(book, "id", id);

        return book;
    }
}