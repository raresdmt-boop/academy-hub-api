package academy.hub.app.book.repository;

import academy.hub.app.book.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {



}
