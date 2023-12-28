package lt.ordermanagement.api.security.services.impl;

import lombok.RequiredArgsConstructor;
import lt.ordermanagement.api.security.dtos.AuthenticationRequestDTO;
import lt.ordermanagement.api.security.dtos.ChangePasswordRequestDTO;
import lt.ordermanagement.api.security.dtos.DeleteUserRequestDTO;
import lt.ordermanagement.api.security.enums.Role;
import lt.ordermanagement.api.security.jwt.JwtService;
import lt.ordermanagement.api.security.models.User;
import lt.ordermanagement.api.security.repositories.UserRepository;
import lt.ordermanagement.api.security.services.interfaces.UsersService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for user authentication and registration.
 */
@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

    private static final int MAX_NAME_LENGTH = 50;
    private static final int MIN_USERNAME_LENGTH = 5;
    private static final int MAX_USERNAME_LENGTH = 20;
    private static final int MIN_PASSWORD_LENGTH = 8;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * Registers a new user based on the provided registration request.
     *
     * @param user The registration user.
     * @return The registered {@link User}.
     */
    @Transactional
    @Override
    public User registerUser(User user) {
        String newFirstName = user.getFirstName();
        String newLastname = user.getLastName();
        String newUsername = user.getUsername().toLowerCase();
        String newPassword = user.getPassword();

        validateNewUserInfo(newUsername, newFirstName, newLastname, newPassword);

        user.setFirstName(newFirstName);
        user.setLastName(newLastname);
        user.setUsername(newUsername);
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setRole(Role.ROLE_USER);

        userRepository.save(user);

        return user;
    }

    /**
     * Checks the validity of new user information before creating a new user.
     *
     * @param newUsername  The new username to be checked.
     * @param newFirstName The new first name to be checked.
     * @param newLastName  The new last name to be checked.
     * @param newPassword  The new password to be checked.
     * @throws IllegalArgumentException If any validation rule is not met.
     */
    private void validateNewUserInfo(String newUsername,
                                     String newFirstName,
                                     String newLastName,
                                     String newPassword) {
        if (userRepository.findByUsername(newUsername).isPresent()) {
            throw new IllegalArgumentException("Username already exists.");

        } else if ((newFirstName.isEmpty() || newFirstName.length() > MAX_NAME_LENGTH) &&
                (newLastName.isEmpty() || newLastName.length() > MAX_NAME_LENGTH)) {
            throw new IllegalArgumentException(
                    "First name or last name must be between 1 and 50 characters long.");

        } else if (newUsername.length() < MIN_USERNAME_LENGTH || newUsername.length() > MAX_USERNAME_LENGTH) {
            throw new IllegalArgumentException(
                    "Username must be between 5 and 20 characters long.");

        } else if (newPassword.length() < MIN_PASSWORD_LENGTH) {
            throw new IllegalArgumentException(
                    "Password must be at least 8 characters long.");
        }
    }

    /**
     * Authenticates a user based on the provided authentication request.
     *
     * @param request The authentication request containing the username and password.
     * @return An authentication response containing a JWT token.
     * @throws BadCredentialsException   If authentication fails.
     * @throws UsernameNotFoundException If the user with the provided username is not found.
     */
    @Transactional
    @Override
    public String authenticateUser(AuthenticationRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername().toLowerCase(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByUsername(request.getUsername().toLowerCase())
                .orElseThrow(
                        () -> new UsernameNotFoundException(
                                "User not found with username: " + request.getUsername()));

        return jwtService.generateToken(user);
    }

    /**
     * Changes the password for the authenticated user based on the provided {@link ChangePasswordRequestDTO}.
     *
     * @param request The {@link ChangePasswordRequestDTO} containing the new password and the old password
     *                for verification. The username in the request must match the authenticated user's username.
     * @throws IllegalArgumentException  If input isn't between allowed character length.
     * @throws UsernameNotFoundException If the authenticated user is not found.
     * @throws BadCredentialsException   If the old password is invalid or the new password is too short.
     */
    @Transactional
    @Override
    public void changePassword(ChangePasswordRequestDTO request) {
        User user = userRepository.findByUsername(getStoredUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));

        if (user.getUsername().equalsIgnoreCase(request.getUsername()) &&
                passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {

            if (request.getNewPassword().length() < MIN_PASSWORD_LENGTH) {
                throw new IllegalArgumentException(
                        "New password must be at least 8 characters long.");
            }

            user.setPassword(passwordEncoder.encode(request.getNewPassword()));

            userRepository.save(user);

        } else {
            throw new BadCredentialsException("Invalid credentials.");
        }
    }

    /**
     * Deletes the specified user based on the provided {@link DeleteUserRequestDTO}.
     *
     * @param request The {@link DeleteUserRequestDTO} containing the username of the user to be deleted.
     * @throws UsernameNotFoundException If the specified user is not found.
     * @throws AccessDeniedException     If the authenticated user does not have the 'ROLE_ADMIN' role.
     */
    @Transactional
    @Override
    public void deleteUser(DeleteUserRequestDTO request) {
        if (getStoredUsername().equalsIgnoreCase(request.getUsername()) &&
                passwordEncoder.matches(request.getPassword(), getStoredEncodedPassword())) {

            User user = userRepository.findByUsername(request.getUsernameToDelete())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found."));

            userRepository.delete(user);

        } else {
            throw new BadCredentialsException("Invalid credentials.");
        }
    }

    /**
     * Retrieves the username of the currently authenticated user.
     *
     * @return The username of the authenticated user in lowercase.
     */
    @Override
    public String getStoredUsername() {
        return getAuthenticationContext().getName().toLowerCase();
    }

    /**
     * Retrieves the encoded password of the currently authenticated user from the user repository.
     *
     * @return The encoded password of the authenticated user.
     * @throws UsernameNotFoundException If the user is not found in the repository.
     */
    @Override
    public String getStoredEncodedPassword() {
        return userRepository.findByUsername(getStoredUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found."))
                .getPassword();
    }

    /**
     * Retrieves the current authentication context.
     *
     * @return The current Authentication object representing the authentication context.
     */
    private Authentication getAuthenticationContext() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

}
