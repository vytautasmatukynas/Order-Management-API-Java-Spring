package lt.ordermanagement.api.security.repositories;

import lt.ordermanagement.api.security.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing User entities.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Retrieves a user by their email address.
     *
     * @param username The username of the user.
     * @return An Optional containing the user if found, or an empty Optional if not found.
     */
    Optional<User> findByUsername(String username);

}
