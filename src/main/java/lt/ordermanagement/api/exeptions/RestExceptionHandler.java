package lt.ordermanagement.api.exeptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

/**
 * Global exception handler for REST controllers.
 *
 * <p>
 * This class handles specific exceptions and generates standardized API error responses.
 * In this implementation, it provides handling for {@link NoSuchElementException} by returning
 * a {@link ResponseEntity} with a {@link ApiErrorDTO} containing the HTTP status code
 * {@link HttpStatus#NOT_FOUND} and a message describing the error.
 * </p>
 *
 * <p>
 * Extend this class to include additional exception handling for other scenarios as needed.
 * </p>
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handles {@link RuntimeException} and {@link Exception} by generating a standardized API error response
     * for internal server errors. This exception handler is designed to catch unexpected runtime exceptions
     * and general exceptions, providing a consistent error format for internal server errors.
     *
     * @param e The exception indicating an unexpected error or exception.
     * @return A {@link ResponseEntity} with a {@link ApiErrorDTO} representing the standardized error response.
     */
    @ExceptionHandler({RuntimeException.class, Exception.class})
    public ResponseEntity<ApiErrorDTO> handleInternalException(Exception e) {
        ApiErrorDTO apiError = new ApiErrorDTO(
                HttpStatus.INTERNAL_SERVER_ERROR,
                e.getMessage());
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.status());
    }

    /**
     * Handles {@link NoSuchElementException} by generating a standardized API error response.
     *
     * @param e The exception indicating that the requested element was not found.
     * @return A {@link ResponseEntity} with a {@link ApiErrorDTO} representing the error response.
     */
    @ExceptionHandler({NoSuchElementException.class})
    public ResponseEntity<ApiErrorDTO> handleNotFound(Exception e) {
        ApiErrorDTO apiError = new ApiErrorDTO(
                HttpStatus.NOT_FOUND,
                e.getMessage());
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.status());
    }

    /**
     * Handles {@link AccessDeniedException} by generating a standardized API error response.
     *
     * @param e The exception indicating that the access is denied.
     * @return A {@link ResponseEntity} with a {@link ApiErrorDTO} representing the error response.
     */
    @ExceptionHandler({AccessDeniedException.class, IllegalArgumentException.class})
    public ResponseEntity<ApiErrorDTO> handleAccessException(Exception e) {
        ApiErrorDTO apiError = new ApiErrorDTO(
                HttpStatus.FORBIDDEN,
                e.getMessage());
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.status());
    }

    /**
     * Handles authentication-related exceptions (e.g., {@link BadCredentialsException},
     * {@link UsernameNotFoundException}) by generating a standardized API error response.
     *
     * @param e The exception indicating authentication failure.
     * @return A {@link ResponseEntity} with a {@link ApiErrorDTO} representing the error response.
     */
    @ExceptionHandler({BadCredentialsException.class, UsernameNotFoundException.class})
    public ResponseEntity<ApiErrorDTO> handleAuthenticationException(Exception e) {
        ApiErrorDTO apiError = new ApiErrorDTO(
                HttpStatus.UNAUTHORIZED,
                e.getMessage());
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.status());
    }

}
