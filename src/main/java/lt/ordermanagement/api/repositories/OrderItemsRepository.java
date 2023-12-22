package lt.ordermanagement.api.repositories;

import lt.ordermanagement.api.models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing order items.
 */
@Repository
public interface OrderItemsRepository extends JpaRepository<OrderItem, Long> {
}