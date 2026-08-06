package academy.hub.app.student.models;


import academy.hub.app.book.models.Book;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.util.*;


@Getter
@Entity(name = "Student")
@Table(name = "student",
        uniqueConstraints = @UniqueConstraint(name = "uk_student_email", columnNames = "email"))
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Setter
    @NotBlank(message = "First name is required")
    @Column(nullable = false)
    private String firstName;

    @Setter
    @NotBlank(message = "Last name is required")
    @Column(nullable = false)
    private String lastName;

    @Setter
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be a valid address")
    @Column(nullable = false, unique = true)
    private String email;

    @Setter
    @Positive(message = "Age must be greater than zero")
    @Max(value = 120, message = "Age must be at most 120")
    @Column(nullable = false)
    private int age;

    @OneToMany(mappedBy = "student",
    cascade = CascadeType.ALL,
    fetch = FetchType.LAZY,
    orphanRemoval = true)
    private Set<Book> books = new HashSet<>();

    public Student() {
    }

    public Student(String firstName, String lastName, String email, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
    }

    public Set<Book> getBooks() {
        return Collections.unmodifiableSet(books);
    }

    public void addBook(Book book) {
        books.add(book);
        book.setStudent(this);
    }

    public void removeBook(Book book) {
        books.remove(book);
        book.setStudent(null);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student student)) return false;
        return id != null && id.equals(student.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }


    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                '}';
    }
}
