package lt.ordermanagement.api.security.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for authentication requests.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request DTO for generating JWT token")
public class AuthenticationRequestDTO {

    @NotBlank()
    @Size(min = 5, max = 20)
    private String username;

    @NotBlank()
    @Size(min = 8)
    private String password;

}
