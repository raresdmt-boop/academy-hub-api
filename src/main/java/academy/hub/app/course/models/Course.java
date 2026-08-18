package academy.hub.app.course.models;


import academy.hub.app.enrollment.models.Enrollment;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Entity(name="Course")
@Table(name="course",
uniqueConstraints = @UniqueConstraint (name="uk_course_name", columnNames = "name"))
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Setter
    @NotBlank(message = "Course name necessary")
    @Column(nullable = false, unique = true)
    private String name;

    @Setter
    @NotBlank(message = "Course department necessary")
    @Column(nullable = false)
    private String department;

    @OneToMany(mappedBy = "course",
    cascade = CascadeType.ALL,
    fetch = FetchType.LAZY,
    orphanRemoval = true)
    private Set<Enrollment> enrollments;

    public  Set<Enrollment> getEnrollments() {
        return Collections.unmodifiableSet(enrollments);
    }

    public void addEnrollment(Enrollment enrollment) {
        enrollments.add(enrollment);
        enrollment.setCourse(this);
    }

    public void removeEnrollment(Enrollment enrollment) {
        enrollments.remove(enrollment);
        enrollment.setCourse(null);
    }

    protected Course() {
    }

    public Course(String name, String department) {
        this.name = name;
        this.department = department;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course course)){
            return false;
        }
        return id != null && id.equals(course.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }


    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                '}';
    }
}
