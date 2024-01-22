package lt.ordermanagement.api.security.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) representing the request to change a user's password.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request DTO for password change operation")
public class ChangePasswordRequestDTO {

    @Schema(description = "The username for password change (must be between 5 and 20 characters)",
            example = "john_doe")
    @NotBlank
    @Size(min = 5, max = 20)
    private String username;

    @Schema(description = "The old password for authentication (must be at least 8 characters)",
            example = "oldPassword123")
    @NotBlank
    @Size(min = 8)
    private String oldPassword;

    @Schema(description = "The new password for the password change (must be at least 8 characters)",
            example = "newSecurePassword")
    @NotBlank
    @Size(min = 8)
    private String newPassword;

}
