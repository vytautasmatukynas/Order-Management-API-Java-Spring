package lt.ordermanagement.api.services.impl;

import lt.ordermanagement.api.models.Order;
import lt.ordermanagement.api.models.OrderItem;
import lt.ordermanagement.api.repositories.OrderItemsRepository;
import lt.ordermanagement.api.repositories.OrdersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderItemsServiceImplTest {

    @Mock
    private OrderItemsRepository orderItemsRepository;

    @Mock
    private OrdersRepository ordersRepository;

    @InjectMocks
    private OrderItemsServiceImpl orderItemsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetOrderItems() {
        // Set up mock data
        Long orderId = 1L;
        Order order = new Order();
        order.setId(orderId);

        OrderItem orderItem1 = new OrderItem("Item1", "Code1", "Rev1", 1L, 10.0, 10.0);
        OrderItem orderItem2 = new OrderItem("Item2", "Code2", "Rev2", 2L, 20.0, 40.0);
        order.setOrderItems(Arrays.asList(orderItem2, orderItem1));

        when(ordersRepository.findById(orderId)).thenReturn(Optional.of(order));

        // Repeat the test 10000 times
        for (int i = 0; i < 10000; i++) {
            // Call the method
            List<OrderItem> result = orderItemsService.getOrderItems(orderId);

            // Assert the results
            assertNotNull(result);
            assertEquals(2, result.size());
            assertEquals("Item1", result.get(0).getItemName());
            assertEquals("Item2", result.get(1).getItemName());
        }
    }

    @Test
    public void testGetOrderItem() {
        // Set up mock data
        Long itemId = 1L;
        OrderItem orderItem = new OrderItem("Item1", "Code1", "Rev1", 1L, 10.0, 10.0);

        when(orderItemsRepository.findById(itemId)).thenReturn(Optional.of(orderItem));

        // Repeat the test 10000 times
        for (int i = 0; i < 10000; i++) {
            // Call the method
            OrderItem result = orderItemsService.getOrderItem(itemId);

            // Assert the results
            assertNotNull(result);
            assertEquals("Item1", result.getItemName());
        }
    }

    @Test
    public void testFindOrderItemsByName() {
        // Set up mock data
        Long orderId = 1L;
        String itemName = "Item";
        Order order = new Order();
        order.setId(orderId);

        OrderItem orderItem1 = new OrderItem("Item1", "Code1", "Rev1", 1L, 10.0, 10.0);
        OrderItem orderItem2 = new OrderItem("Item2", "Code2", "Rev2", 2L, 20.0, 40.0);
        order.setOrderItems(Arrays.asList(orderItem2, orderItem1));

        when(ordersRepository.findById(orderId)).thenReturn(Optional.of(order));

        // Repeat the test 10000 times
        for (int i = 0; i < 10000; i++) {
            // Call the method
            List<OrderItem> result = orderItemsService.findOrderItemsByName(orderId, itemName);

            // Assert the results
            assertNotNull(result);
            assertEquals(2, result.size());
            assertTrue(result.get(0).getItemName().toLowerCase().contains(itemName.toLowerCase()));
            assertTrue(result.get(1).getItemName().toLowerCase().contains(itemName.toLowerCase()));
        }
    }

}