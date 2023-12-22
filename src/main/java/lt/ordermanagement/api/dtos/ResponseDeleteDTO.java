package lt.ordermanagement.api.dtos;

/**
 * Data Transfer Object (DTO) for representing the response of a delete operation.
 *
 * <p>
 * This class encapsulates information about the status and message resulting from a delete operation.
 * </p>
 */
public record ResponseDeleteDTO(String status, String message) {
}
