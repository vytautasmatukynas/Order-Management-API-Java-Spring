package lt.ordermanagement.api.security.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) representing the request to delete a user.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteUserRequestDTO {

    @NotBlank()
    private String username;

    @NotBlank()
    private String password;

    @NotBlank()
    private String usernameToDelete;

}
