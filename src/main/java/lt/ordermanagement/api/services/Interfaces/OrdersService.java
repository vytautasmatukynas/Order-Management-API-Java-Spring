package lt.ordermanagement.api.services.Interfaces;

import lt.ordermanagement.api.models.Order;

import java.io.IOException;
import java.util.List;

/**
 * Interface for managing orders.
 *
 * <p>
 * This interface defines methods for retrieving, creating, updating, and deleting orders.
 * It also includes a method for generating unique order numbers.
 * </p>
 */
public interface OrdersService {

    List<Order> getOrders();

    Order getOrderById(Long orderId);

    List<Order> findOrdersByParameters(String searchParam);

    void addOrder(Order order);

    Order updateOrder(Long orderId, Order order);

    void deleteOrder(Long orderId);

    String generateOrderNumber();

    Double countTotalOrderPrice(Long orderId);

}
