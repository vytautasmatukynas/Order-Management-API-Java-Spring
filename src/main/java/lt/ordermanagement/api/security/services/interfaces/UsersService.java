package lt.ordermanagement.api.security.services.interfaces;

import lt.ordermanagement.api.security.dtos.AuthenticationRequestDTO;
import lt.ordermanagement.api.security.dtos.ChangePasswordRequestDTO;
import lt.ordermanagement.api.security.dtos.DeleteUserRequestDTO;
import lt.ordermanagement.api.security.models.User;

/**
 * Service interface for managing user-related operations.
 */
public interface UsersService {

    User registerUser(User user);

    String authenticateUser(AuthenticationRequestDTO request);

    void changePassword(ChangePasswordRequestDTO request);

    void deleteUser(DeleteUserRequestDTO request);

}
