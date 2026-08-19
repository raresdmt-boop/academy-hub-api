package academy.hub.app.enrollment.exceptions;

public class NoEnrollmentsFound extends RuntimeException {
  public NoEnrollmentsFound(String message) {
    super(message);
  }
}
