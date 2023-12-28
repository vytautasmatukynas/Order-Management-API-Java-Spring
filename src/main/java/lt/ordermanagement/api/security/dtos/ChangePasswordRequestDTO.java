package lt.ordermanagement.api.security.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) representing the request to change a user's password.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordRequestDTO {

    @NotBlank()
    private String username;

    @NotBlank()
    private String oldPassword;

    @NotBlank()
    private String newPassword;

}
