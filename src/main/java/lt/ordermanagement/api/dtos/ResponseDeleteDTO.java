package lt.ordermanagement.api.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Data Transfer Object (DTO) for representing the response of a delete operation.
 *
 * <p>
 * This class encapsulates information about the status and message resulting from a delete operation.
 * </p>
 */
@Schema(description = "Response DTO for delete operation")
public record ResponseDeleteDTO(String status, String message) {
}
