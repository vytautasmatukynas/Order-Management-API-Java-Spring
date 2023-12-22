package lt.ordermanagement.api.security.dtos;

/**
 * Data Transfer Object (DTO) representing the response for a user deletion operation.
 *
 * @param status  The status of the user deletion operation.
 * @param message A descriptive message providing additional information about the user deletion status.
 */
public record DeleteUserResponseDTO(String status, String message) {
}
