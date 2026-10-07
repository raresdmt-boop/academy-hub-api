package academy.hub.app.support;


import academy.hub.app.student.models.Student;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Locale;
import java.util.UUID;

public class StudentFixtures {

    public static final UUID KNOWN_ID =
            UUID.fromString("33333333-3333-3333-3333-333333333333");

    public static final UUID OTHER_ID =
            UUID.fromString("44444444-4444-4444-4444-444444444444");

    public static Student student() {
        return new Student(
                "Harry",
                "Potter",
                "harry.p@hogwarts.edu",
                18
        );
    }

    public static Student persisted() {
        Student student = student();

        ReflectionTestUtils.setField(student, "id", KNOWN_ID);

        return student;
    }

    public static Student withId(UUID id) {
        Student student = student();

        ReflectionTestUtils.setField(student, "id", id);

        return student;
    }
}