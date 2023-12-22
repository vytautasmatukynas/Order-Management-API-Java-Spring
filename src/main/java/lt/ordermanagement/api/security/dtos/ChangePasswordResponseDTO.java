package lt.ordermanagement.api.security.dtos;

/**
 * Data Transfer Object (DTO) representing the response for a password change operation.
 *
 * @param status  The status of the password change operation.
 * @param message A descriptive message providing additional information about the password change status.
 */
public record ChangePasswordResponseDTO(String status, String message) {
}
