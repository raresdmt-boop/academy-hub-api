package academy.hub.app.book.repository;

import academy.hub.app.book.models.Book;
import jakarta.validation.Valid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {

    boolean existsByName(String name);
    boolean existsById(UUID id);
    Optional<Book> findById(UUID id);
    List<Book> findAll();
    List<Book> findByStudentId(UUID id);

    long countByStudentId(UUID id);
}
