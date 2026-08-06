package academy.hub.app.student.models;


import academy.hub.app.book.models.Book;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
@Entity(name="Student")
@Table(name="student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private Long UUID;

    private String firstName;
    private String lastName;
    private String email;
    private int age;

    @ToString.Exclude
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

    public void addBook(Book book) {
        books.add(book);
        book.setStudent(this);
    }

    public void removeBook(Book book) {
        books.remove(book);
        book.setStudent(null);
    }






}
