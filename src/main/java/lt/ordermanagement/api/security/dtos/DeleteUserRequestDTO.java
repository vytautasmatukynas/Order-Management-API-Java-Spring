package lt.ordermanagement.api.security.dtos;

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
public class DeleteUserRequestDTO {

    @Size(min = 5, max = 20, message = "Username must be between 5 and 20 characters")
    @NotBlank(message = "Username cannot be blank")
    private String username;

}
