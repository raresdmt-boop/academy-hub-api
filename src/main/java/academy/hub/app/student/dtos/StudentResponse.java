package academy.hub.app.student.dtos;

import academy.hub.app.book.models.Book;
import academy.hub.app.course.models.Course;
import academy.hub.app.enrollment.models.Enrollment;
import academy.hub.app.student.models.Student;

import java.util.List;
import java.util.UUID;

public record StudentResponse(
        UUID id,
        String fullName,
        String email,
        List<String> books,
        List<String> courses
) {

    public static StudentResponse from(Student student) {
        return new StudentResponse(
                student.getId(),
                student.getFirstName() + " " + student.getLastName(),
                student.getEmail(),
                student.getBooks().stream().map(Book::getName).toList(),
                student.getEnrollments().stream().map(Enrollment::getCourse).map(Course::getName).toList()
        );
    }

}
