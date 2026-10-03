package academy.hub.app.book.repositories;

import academy.hub.app.book.models.Book;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {

    boolean existsByName(String name);
    boolean existsById(UUID id);
    Optional<Book> findById(UUID id);

    @EntityGraph(attributePaths = "student")
    List<Book> findAll();


    List<Book> findByStudentId(UUID id);
    long countByStudentId(UUID id);

    boolean existsByStudentIdAndName(UUID studentId, String name);
}
