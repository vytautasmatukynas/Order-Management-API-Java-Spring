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
public class DeleteUserRequestDTO {

    @Schema(description = "The username of the authenticated user")
    @NotBlank()
    @Size(min = 5, max = 20)
    private String username;

    @Schema(description = "The password of the authenticated user")
    @NotBlank()
    @Size(min = 8)
    private String password;

    @Schema(description = "The username of the user to be deleted")
    @NotBlank()
    @Size(min = 5, max = 20)
    private String usernameToDelete;

}
