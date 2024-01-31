package lt.ordermanagement.api.exeptions;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for handling validation errors.
 *
 * <p>
 * This class provides a centralized mechanism for handling validation errors
 * thrown during request processing. It specifically handles
 * {@link MethodArgumentNotValidException} by returning a standardized API error response
 * with the HTTP status code {@link HttpStatus#BAD_REQUEST}.
 * </p>
 *
 * <p>
 * The {@link Order} annotation with {@link Ordered#HIGHEST_PRECEDENCE} is used
 * to ensure that this exception handler is evaluated before other exception handlers
 * in the application context, allowing it to handle validation errors as a priority.
 * </p>
 */
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ValidationExceptionHandler {

    /**
     * Handles {@link MethodArgumentNotValidException} by generating a standardized API error response
     * for validation errors.
     *
     * @param e The exception indicating validation failure.
     * @return A {@link ResponseEntity} with a {@link Map} containing validation errors.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
