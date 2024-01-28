package lt.ordermanagement.api.services.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lt.ordermanagement.api.models.Order;
import lt.ordermanagement.api.models.OrderItem;
import lt.ordermanagement.api.repositories.OrdersRepository;
import lt.ordermanagement.api.services.Interfaces.OrdersService;
import lt.ordermanagement.api.utils.GenerateDate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

/**
 * Service class for managing orders.
 *
 * <p>
 * This service provides methods for retrieving, creating, updating, and deleting orders.
 * It also includes a method for generating unique order numbers.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class OrdersServiceImpl implements OrdersService {

    private final OrdersRepository ordersRepository;

    /**
     * Retrieves a list of all orders.
     *
     * @return Sorted list of orders.
     */
    @Override
    public List<Order> getOrders() {
        return ordersRepository.findAllSorted();
    }

    /**
     * Retrieves an order by its ID.
     *
     * @param orderId The ID of the order to retrieve.
     * @return The retrieved order.
     */
    @Override
    public Order getOrderById(Long orderId) {
        return ordersRepository.findById(orderId).orElseThrow(
                () -> new EntityNotFoundException("Order not found with ID: " + orderId));
    }

    /**
     * Retrieves a list of orders based on a search parameter.
     *
     * @param searchParam The parameter to search for in order attributes
     *                    (orderNumber, orderName, client, clientPhoneNumber, clientEmail).
     * @return A sorted list of orders matching the specified search parameter.
     */
    @Override
    public List<Order> findOrdersByParameters(String searchParam) {
        return ordersRepository.findSortedOrdersByParameters(searchParam,
                                                            searchParam,
                                                            searchParam,
                                                            searchParam,
                                                            searchParam);
    }

    /**
     * Saves a new order with an automatically generated order number, calculated order price and
     * automatically generated date order was created.
     *
     * @param order The order to save. It should contain order items for accurate price calculation.
     */
    @Transactional
    @Override
    public Order addOrder(Order order) {
        order.setOrderNumber(generateOrderNumber());
        order.setOrderUpdateDate(GenerateDate.generateCurrentDate());

        ordersRepository.save(order);

        return order;
    }

    /**
     * Updates an existing newOrder.
     *
     * @param order The order to update.
     */
    @Transactional
    @Override
    public Order updateOrder(Long orderId, Order order) {
        Order oldOrder = getOrderById(orderId);

        oldOrder.setOrderName(order.getOrderName());
        oldOrder.setClientName(order.getClientName());
        oldOrder.setClientPhoneNumber(order.getClientPhoneNumber());
        oldOrder.setClientEmail(order.getClientEmail());
        oldOrder.setOrderTerm(order.getOrderTerm());
        oldOrder.setOrderStatus(order.getOrderStatus());
        oldOrder.setOrderPrice(countTotalOrderPrice(orderId));
        oldOrder.setComments(order.getComments());
        oldOrder.setOrderUpdateDate(GenerateDate.generateCurrentDate());

        ordersRepository.save(oldOrder);

        return oldOrder;
    }

    /**
     * Sets isDeleted order and all order items to 'true'.
     *
     * @param orderId The ID of the order to set isDeleted.
     */
    @Transactional
    @Override
    public void deleteOrder(Long orderId) {
        Order order = ordersRepository.findById(orderId).orElseThrow(
                () -> new EntityNotFoundException("Order not found with ID: " + orderId));

        order.setIsDeleted(true);

        List<OrderItem> orderItems = order.getOrderItems();

        orderItems.forEach(item -> item.setIsDeleted(true));

        ordersRepository.save(order);
    }

    /**
     * Calculates and returns the total price of all orders.
     *
     * @return The total price of all orders in the system. Returns 0.0 if no orders exist.
     */
    @Override
    public Double countTotalOrderPrice(Long orderId) {
        Order order = ordersRepository.findById(orderId).orElseThrow(
                () -> new EntityNotFoundException("Order not found with ID: " + orderId));

        List<OrderItem> orderItemList = order.getOrderItems();

        return orderItemList.stream()
                .map(OrderItem::getTotalPrice)
                .reduce(0D, Double::sum);
    }

    /**
     * Generates a unique order number.
     *
     * @return The generated order number.
     */
    private String generateOrderNumber() {
        String characters = "0123456789";
        int length = 10;

        while (true) {
            StringBuilder orderNumber = new StringBuilder("ON-");

            for (int i = 0; i < length; i++) {
                int index = new Random().nextInt(characters.length());
                orderNumber.append(characters.charAt(index));
            }

            if (!orderNumberExists(orderNumber.toString())) {
                return orderNumber.toString();
            }
        }
    }

    /**
     * Checks if an order number already exists in the database.
     *
     * @param orderNumber The order number to check.
     * @return True if the order number exists, false otherwise.
     */
    private boolean orderNumberExists(String orderNumber) {
        return ordersRepository.existsByOrderNumber(orderNumber);
    }

}
