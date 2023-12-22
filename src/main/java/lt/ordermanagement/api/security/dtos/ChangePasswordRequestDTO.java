package lt.ordermanagement.api.security.dtos;

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
public class ChangePasswordRequestDTO {

    @Size(min = 5, max = 20, message = "Username must be between 5 and 20 characters")
    @NotBlank(message = "Username cannot be blank")
    private String username;

    @Size(min = 8, max = 20, message = "Old password must be between 8 and 20 characters")
    @NotBlank(message = "Password cannot be blank")
    private String oldPassword;

    @Size(min = 8, max = 20, message = "New password must be between 8 and 20 characters")
    @NotBlank(message = "Password cannot be blank")
    private String newPassword;

}
