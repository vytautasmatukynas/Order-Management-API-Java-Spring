package lt.ordermanagement.api.repositories;

import lt.ordermanagement.api.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing orders.
 */
@Repository
public interface OrdersRepository extends JpaRepository<Order, Long> {

    /**
     * Retrieves a list of orders where isDeleted is false and sorted by orderUpdateDate, orderTerm, clientName
     * and orderName.
     *
     * @return A sorted list of orders.
     */
    @Query("SELECT o FROM Order o WHERE o.isDeleted = false " +
            "ORDER BY o.orderUpdateDate DESC, o.orderTerm, o.clientName, o.orderName")
    List<Order> findAllSorted();

    /**
     * Retrieves a list of orders based on multiple parameters with case-insensitive partial matches.
     * Where isDeleted is false and sorted by orderUpdateDate, orderTerm, clientName and orderName.
     *
     * @param orderNumber        The partial match for the order number.
     * @param orderName          The partial match for the order name.
     * @param clientName            The partial match for the client name.
     * @param clientPhoneNumber  The partial match for the client phone number.
     * @param clientEmail        The partial match for the client email.
     * @return A list of orders matching the specified criteria.
     */
    @Query("SELECT o FROM Order o WHERE o.isDeleted = false AND " +
    "(:orderNumber IS NULL OR LOWER(o.orderNumber) LIKE LOWER(CONCAT('%', :orderNumber, '%'))) OR " +
    "(:orderName IS NULL OR LOWER(o.orderName) LIKE LOWER(CONCAT('%', :orderName, '%'))) OR " +
    "(:clientName IS NULL OR LOWER(o.clientName) LIKE LOWER(CONCAT('%', :clientName, '%'))) OR " +
    "(:clientPhoneNumber IS NULL OR LOWER(o.clientPhoneNumber) LIKE LOWER(CONCAT('%', :clientPhoneNumber, '%'))) OR " +
    "(:clientEmail IS NULL OR LOWER(o.clientEmail) LIKE LOWER(CONCAT('%', :clientEmail, '%')))" +
    "ORDER BY o.orderUpdateDate DESC, o.orderTerm ASC, o.clientName ASC, o.orderName ASC")
    List<Order> findSortedOrdersByParameters(@Param("orderNumber") String orderNumber,
                                             @Param("orderName") String orderName,
                                             @Param("clientName") String clientName,
                                             @Param("clientPhoneNumber") String clientPhoneNumber,
                                             @Param("clientEmail") String clientEmail);

    /**
     * Checks if an order with the given order number exists in the database.
     *
     * @param orderNumber The order number to check.
     * @return True if an order with the specified order number exists, false otherwise.
     */
    boolean existsByOrderNumber(String orderNumber);

}
