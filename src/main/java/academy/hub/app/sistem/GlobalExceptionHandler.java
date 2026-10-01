package academy.hub.app.sistem;


import academy.hub.app.book.exceptions.BookNotFound;
import academy.hub.app.course.exceptions.NoCourseFound;
import academy.hub.app.enrollment.exceptions.EnrollmentNotFound;
import academy.hub.app.student.exceptions.EmailAlreadyUsed;
import academy.hub.app.student.exceptions.EmailNotFound;
import academy.hub.app.student.exceptions.StudentNotFound;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.Instant;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({BookNotFound.class, EnrollmentNotFound.class,
            StudentNotFound.class, EmailNotFound.class, NoCourseFound.class,
    })
    public ResponseEntity<ApiError> handleParticularNotFound(RuntimeException exception, HttpServletRequest request) {
        return build(HttpStatus.NOT_FOUND, exception.getMessage(), request, List.of());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleBodyValidation(MethodArgumentNotValidException exception,
                                                         HttpServletRequest request) {

        List<String> details = exception.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .sorted()
                .toList();

        return build(HttpStatus.BAD_REQUEST, "Validation failed", request, details);
    }

    @ExceptionHandler(EmailAlreadyUsed.class)
    public ResponseEntity<ApiError> handleConflict(EmailAlreadyUsed exception, HttpServletRequest request) {
        return build(HttpStatus.CONFLICT, exception.getMessage(), request, List.of());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleParameterValidation(ConstraintViolationException exception,
                                                              HttpServletRequest request) {
        List<String> details = exception.getConstraintViolations().stream()
                .map(this::describe)
                .sorted()
                .toList();

        return build(HttpStatus.BAD_REQUEST, "Validation failed", request, details);

    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ApiError> handleParamValidation(HandlerMethodValidationException exception,
                                                          HttpServletRequest request) {

        List<String> details = exception.getParameterValidationResults().stream()
                .flatMap(result -> result.getResolvableErrors().stream()
                        .map(error -> result.getMethodParameter().getParameterName() + ": "
                                + error.getDefaultMessage()))
                .sorted()
                .toList();

        return build(HttpStatus.BAD_REQUEST, "Validation failed", request, details);
    }



    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiError> handleTypeMismatch(MethodArgumentTypeMismatchException exception,
                                                       HttpServletRequest request) {

        String message = "Parameter '" + exception.getName() + "' has an invalid value: " + exception.getValue();

        return build(HttpStatus.BAD_REQUEST, message, request, List.of());

    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiError> handleMissingParameter(MissingServletRequestParameterException exception,
                                                           HttpServletRequest request) {

        String message = "Required query parameter '" + exception.getParameterName() + "' is missing";

        return build(HttpStatus.BAD_REQUEST, message, request, List.of());
    }



    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleDataIntegrity(DataIntegrityViolationException exception,
                                                        HttpServletRequest request) {

        return build(HttpStatus.CONFLICT, "Request violates a database constraint", request, List.of());
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiError> handleUnknownRoute(NoResourceFoundException exception,
                                                       HttpServletRequest request) {

        return build(HttpStatus.NOT_FOUND, "No endpoint for this path", request, List.of());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleUnreadableBody(HttpMessageNotReadableException exception,
                                                         HttpServletRequest request) {

        return build(HttpStatus.BAD_REQUEST, "Malformed JSON request", request, List.of());
    }




    private String describe(ConstraintViolation<?> violation){
        String path = violation.getPropertyPath().toString();
        String field = path.contains(".") ? path.substring(path.lastIndexOf(".")+1) : path;
        return field + ": " + violation.getMessage();
    }

    private ResponseEntity<ApiError> build(HttpStatus status,
                                           String message,
                                           HttpServletRequest request,
                                           List<String> details) {
        ApiError body = new ApiError(
                Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                request.getRequestURI(),
                details
        );
        return ResponseEntity.status(status).body(body);
    }

}
