package academy.hub.app.student.dtos;

public record StudentCreateRequest(String  firstName, String lastName, String email, int age) {
}
