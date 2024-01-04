package lt.ordermanagement.api.security.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Data Transfer Object (DTO) representing the response for a password change operation.
 *
 * @param status  The status of the password change operation.
 * @param message A descriptive message providing additional information about the password change status.
 */
@Schema(description = "Response DTO for password change operation")
public record ChangePasswordResponseDTO(
        @Schema(description = "Status of the password change operation") String status,
        @Schema(description = "Detailed message about the password change operation") String message) {
}
