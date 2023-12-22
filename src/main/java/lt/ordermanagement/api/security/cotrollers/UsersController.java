package lt.ordermanagement.api.security.cotrollers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lt.ordermanagement.api.security.dtos.*;
import lt.ordermanagement.api.security.models.User;
import lt.ordermanagement.api.security.services.interfaces.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/**
 * Controller class handling user related endpoints.
 */
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UsersController {

    private final String REGISTER_PATH = "/user/register";
    private final String AUTH_PATH = "/user/authenticate";
    private final String CHANGE_PASSWORD_PATH = "/user/change/password";
    private final String DELETE_PATH = "/user/delete";

    private final UsersService usersService;

    /**
     * Handles the user registration endpoint.
     *
     * @param user The {@link User} object containing the registration user data.
     * @return A {@link ResponseEntity} containing the details of the registered user upon successful registration.
     *         Returns 200 OK if registration is successful.
     * @throws ResponseStatusException with HTTP status INTERNAL_SERVER_ERROR (500) if an unexpected error occurs during the registration process.
     */
    @PostMapping(REGISTER_PATH)
    public ResponseEntity<User> register(@Valid @RequestBody User user) {
        try {
            return ResponseEntity.ok(usersService.registerUser(user));

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Unexpected error occurred while registering new user: " + e.getMessage());
        }
    }

    /**
     * Handles the user authentication endpoint.
     *
     * @param request The {@link AuthenticationRequestDTO} containing the authentication request data.
     * @return A {@link ResponseEntity} with the result of the authentication operation wrapped in an {@link AuthenticationResponseDTO}.
     *         Returns 200 OK if authentication is successful.
     * @throws ResponseStatusException with HTTP status INTERNAL_SERVER_ERROR (500) if an unexpected error occurs during the authentication process.
     */
    @PostMapping(AUTH_PATH)
    public ResponseEntity<AuthenticationResponseDTO> authenticate(
                        @Valid @RequestBody AuthenticationRequestDTO request) {
        try {
            String token = usersService.authenticateUser(request);

            AuthenticationResponseDTO response = new AuthenticationResponseDTO(token);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Unexpected error occurred while authenticating user: " + e.getMessage());
        }
    }

    /**
     * Handles the HTTP PUT request to change the password for a user.
     *
     * @param request The {@link ChangePasswordRequestDTO} containing the necessary information to change the password.
     * @return A {@link ResponseEntity} with the result of the password change operation wrapped in a {@link ChangePasswordResponseDTO}.
     *         Returns 200 OK if successful.
     * @throws ResponseStatusException with HTTP status INTERNAL_SERVER_ERROR (500) if an unexpected error occurs during the operation.
     */
    @PutMapping(CHANGE_PASSWORD_PATH)
    public ResponseEntity<ChangePasswordResponseDTO> changePassword(
                        @Valid @RequestBody ChangePasswordRequestDTO request) {
        try {
            usersService.changePassword(request);

            ChangePasswordResponseDTO response =
                    new ChangePasswordResponseDTO("success",
                            "password was changed");

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Unexpected error occurred while authenticating user: " + e.getMessage());
        }
    }

    /**
     * Handles the HTTP DELETE request to delete a user.
     *
     * @param request The {@link DeleteUserRequestDTO} containing the username of the user to be deleted.
     * @return A {@link ResponseEntity} with the result of the user deletion operation wrapped in a {@link DeleteUserResponseDTO}.
     *         Returns 200 OK if the user is deleted successfully.
     * @throws ResponseStatusException with HTTP status INTERNAL_SERVER_ERROR (500) if an unexpected error occurs during the deletion operation.
     */
    @DeleteMapping(DELETE_PATH)
    public ResponseEntity<DeleteUserResponseDTO> deleteUser(
                        @Valid @RequestBody DeleteUserRequestDTO request) {
        try {
            usersService.deleteUser(request);

            DeleteUserResponseDTO response =
                    new DeleteUserResponseDTO("success",
                            "user was deleted");

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Unexpected error occurred while deleting user: " + e.getMessage());
        }
    }

}