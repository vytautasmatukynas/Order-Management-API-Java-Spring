package lt.ordermanagement.api.services.Interfaces;

import lt.ordermanagement.api.models.OrderItem;

import java.util.List;

/**
 * Service interface for managing order items.
 *
 * <p>
 * This interface defines methods for retrieving, creating, updating, and deleting order items.
 * </p>
 */
public interface OrderItemsService {

    List<OrderItem> getOrderItems(Long orderId);

    OrderItem getOrderItem(Long itemId);

    List<OrderItem> findOrderItemsByName(Long orderId, String searchItemName);

    OrderItem addItemToOrder(Long orderId, OrderItem orderItem);

    OrderItem updateOrderItem(Long itemId, OrderItem orderItem);

    void deleteOrderItem(Long orderItemId);

}
