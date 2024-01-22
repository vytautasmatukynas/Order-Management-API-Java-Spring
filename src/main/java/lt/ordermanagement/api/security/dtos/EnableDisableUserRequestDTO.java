package lt.ordermanagement.api.security.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) representing the request to delete a user.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request DTO for deleting a user")
public class EnableDisableUserRequestDTO {

    @Schema(description = "The username (must be between 5 and 20 characters)",
            example = "john_doe")
    @NotBlank
    @Size(min = 5, max = 20)
    private String username;

    @Schema(description = "The password (must be at least 8 characters)",
            example = "securePassword123")
    @NotBlank
    @Size(min = 8)
    private String password;

    @Schema(description = "The username to enable/disable (must be between 5 and 20 characters)",
            example = "admin_user")
    @NotBlank
    @Size(min = 5, max = 20)
    private String usernameToEnableDisable;

}
