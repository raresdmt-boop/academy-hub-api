package academy.hub.app.enrollment.models;

import academy.hub.app.course.models.Course;
import academy.hub.app.student.models.Student;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Entity(name = "Enrollment")
@Table(name = "enrollment")
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Setter
   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "student_id")
   Student student;

    @Setter
   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "course_id")
   Course course;

   @Setter
   LocalDate createdAt;

    protected Enrollment() {
    }

    public Enrollment(LocalDate createdAt){
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(!(o instanceof Enrollment other)) return false;
        return student.getId().equals(other.student.getId()) && course.getId().equals(other.course.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
