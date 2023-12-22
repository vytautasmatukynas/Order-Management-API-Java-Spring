package lt.ordermanagement.api.exeptions;

import org.springframework.http.HttpStatus;

/**
 * Represents a DTO (Data Transfer Object) for API error responses.
 *
 * <p>
 * This class encapsulates information about an error, including HTTP status and an error message.
 * It is typically used to provide meaningful error responses in the API.
 * </p>
 */
public record ApiErrorDTO(HttpStatus status, String message) {
}