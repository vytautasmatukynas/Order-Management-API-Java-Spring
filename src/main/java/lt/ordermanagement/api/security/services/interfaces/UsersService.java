package lt.ordermanagement.api.security.services.interfaces;

import lt.ordermanagement.api.security.dtos.AuthenticationRequestDTO;
import lt.ordermanagement.api.security.dtos.ChangePasswordRequestDTO;
import lt.ordermanagement.api.security.dtos.EnableDisableUserRequestDTO;
import lt.ordermanagement.api.security.models.User;

import java.util.List;

/**
 * Service interface for managing user-related operations.
 */
public interface UsersService {

    List<User> getAllUser();

    User registerUser(User user);

    String authenticateUser(AuthenticationRequestDTO request);

    void changePassword(ChangePasswordRequestDTO request);

    void disableEnableUser(EnableDisableUserRequestDTO request);

}
