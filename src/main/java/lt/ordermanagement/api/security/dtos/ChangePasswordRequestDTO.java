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

    @NotBlank()
    @Size(min = 5, max = 20)
    private String username;

    @NotBlank()
    @Size(min = 8)
    private String oldPassword;

    @NotBlank()
    @Size(min = 8)
    private String newPassword;

}
