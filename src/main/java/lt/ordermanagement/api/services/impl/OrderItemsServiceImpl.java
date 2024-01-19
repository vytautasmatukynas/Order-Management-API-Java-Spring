package lt.ordermanagement.api.services.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lt.ordermanagement.api.models.OrderItem;
import lt.ordermanagement.api.models.Order;
import lt.ordermanagement.api.services.Interfaces.OrderItemsService;
import lt.ordermanagement.api.repositories.OrderItemsRepository;
import lt.ordermanagement.api.repositories.OrdersRepository;
import lt.ordermanagement.api.services.Interfaces.OrdersService;
import lt.ordermanagement.api.utils.GenerateDate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing order items.
 *
 * <p>
 * This service provides methods for retrieving, creating, updating, and deleting order items.
 * It interacts with the OrderItemsRepository and OrdersRepository to perform these operations.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class OrderItemsServiceImpl implements OrderItemsService {

    private final OrderItemsRepository orderItemsRepository;
    private final OrdersRepository ordersRepository;
    private final OrdersService ordersService;

    /**
     * Retrieves sorted list of order items for a given order ID.
     *
     * @param orderId The ID of the order for which to retrieve items.
     * @return List of order items for the specified order.
     */
    @Override
    public List<OrderItem> getOrderItems(Long orderId) {
        Order order = ordersRepository.findById(orderId).orElseThrow(
                () -> new EntityNotFoundException("Order for this Order Items not found with ID: " + orderId));

        return order.getOrderItems()
                .stream()
                .filter(ordItm -> ordItm.getIsDeleted().equals(false))
                .sorted(Comparator.comparing(OrderItem::getItemName,
                        String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves an order item by its ID.
     *
     * @param orderItemId The ID of the order item to retrieve.
     * @return The retrieved order item.
     */
    @Override
    public OrderItem getOrderItem(Long orderItemId) {
        return orderItemsRepository.findById(orderItemId).orElseThrow(
                () -> new EntityNotFoundException("Order Item not found with ID: " + orderItemId));
    }

    /**
     * Retrieves sorted list of order items for a given order ID.
     *
     * @param orderId The ID of the order for which to retrieve items.
     * @return List of order items for the specified order.
     */
    @Override
    public List<OrderItem> findOrderItemsByName(Long orderId, String itemName) {
        Order order = ordersRepository.findById(orderId).orElseThrow(
                () -> new EntityNotFoundException("Order for this Order Items not found with ID: " + orderId));

        return order.getOrderItems()
                .stream()
                .filter(orderItem -> orderItem.getItemName().toLowerCase()
                        .contains(itemName.toLowerCase()))
                .sorted(Comparator.comparing(OrderItem::getItemName,
                        String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
    }

    /**
     * Saves a new order item to the specified order and updates order price in order table.
     *
     * @param orderId The ID of the order to which the item should be added.
     */
    @Transactional
    @Override
    public OrderItem addItemToOrder(Long orderId, OrderItem orderItem) {
        Order order = ordersRepository.findById(orderId).orElseThrow(
                () -> new EntityNotFoundException("Order to add this Order Item to not found with ID: " + orderId));

        OrderItem newOrderItem = new OrderItem(orderItem.getItemName(),
                                            orderItem.getItemCode(),
                                            orderItem.getItemRevision(),
                                            orderItem.getItemCount(),
                                            orderItem.getItemPrice(),
                                            orderItem.getTotalPrice(),
                                            GenerateDate.generateCurrentDate(),
                                            orderItem.getLinkToImg());

        order.addOrderItem(newOrderItem);

        // Updates order price in orders table
        order.setOrderPrice(ordersService.countTotalOrderPrice(orderId));

        ordersRepository.save(order);

        return newOrderItem;
    }

    /**
     * Updates an existing order item.
     *
     * @param orderItem The order item to update.
     */
    @Transactional
    @Override
    public OrderItem updateOrderItem(Long itemId, OrderItem orderItem) {
        OrderItem oldOrderItem = getOrderItem(itemId);

        oldOrderItem.setItemName(orderItem.getItemName());
        oldOrderItem.setItemCode(orderItem.getItemCode());
        oldOrderItem.setItemRevision(orderItem.getItemRevision());
        oldOrderItem.setItemCount(orderItem.getItemCount());
        oldOrderItem.setItemPrice(orderItem.getItemPrice());
        oldOrderItem.setTotalPrice(orderItem.getTotalPrice());
        oldOrderItem.setItemUpdateDate(GenerateDate.generateCurrentDate());
        oldOrderItem.setLinkToImg(orderItem.getLinkToImg());

        Order currentOrder = oldOrderItem.getOrder();

        // Updates order price and update date in orders table
        currentOrder.setOrderPrice(ordersService.countTotalOrderPrice(currentOrder.getId()));
        currentOrder.setOrderUpdateDate(GenerateDate.generateCurrentDate());

        orderItemsRepository.save(oldOrderItem);

        return oldOrderItem;
    }

    /**
     * Sets isDeleted order item to 'true'.
     *
     * @param orderItemId The ID of the order item to set isDeleted.
     */
    @Transactional
    @Override
    public void deleteOrderItem(Long orderItemId) {
        OrderItem orderItem = orderItemsRepository.findById(orderItemId).orElseThrow(
                () -> new EntityNotFoundException("Order Item not found with ID: " + orderItemId));

        Order order = orderItem.getOrder();

        // Order price minus item total price
        order.setOrderPrice(order.getOrderPrice() - orderItem.getTotalPrice());

        orderItem.setIsDeleted(true);

        ordersRepository.save(order);
    }

}
