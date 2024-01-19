package lt.ordermanagement.api.security.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Data Transfer Object (DTO) for authentication responses.
 */
@Schema(description = "Response DTO with generated JWT token")
public record AuthenticationResponseDTO(String token) {
}
