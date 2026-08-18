package academy.hub.app.book.models;

import academy.hub.app.student.models.Student;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Entity(name="Book")
@Table(name="book")
public class Book {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Setter
    @NotBlank(message = "Book name is required")
    @Column(nullable = false)
    private String name;

    @Setter
    @NotNull(message = "Created at is required")
    @PastOrPresent(message = "Created at cannot be in the future")
    @Column(nullable = false)
    private LocalDate createdAt;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="student_id", nullable = false)
    private Student student;

    protected Book() {
    }

    public Book(String name, LocalDate createdAt) {
        this.name = name;
        this.createdAt = createdAt;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book book)) return false;
        return id != null && id.equals(book.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }


    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
