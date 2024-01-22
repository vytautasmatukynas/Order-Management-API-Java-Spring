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

/**
 * Unit tests for the {@link OrderItemsServiceImpl} class.
 */
class OrderItemsServiceImplTest {

    /**
     * Mock repository for order items.
     */
    @Mock
    private OrderItemsRepository orderItemsRepository;

    /**
     * Mock repository for orders.
     */
    @Mock
    private OrdersRepository ordersRepository;

    /**
     * Service to be tested.
     */
    @InjectMocks
    private OrderItemsServiceImpl orderItemsService;

    /**
     * Set up method to initialize mocks.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test for the {@link OrderItemsServiceImpl#getOrderItems(Long)} method.
     */
    @Test
    public void testGetOrderItems() {
        Long orderId = 1L;
        Order order = new Order();
        order.setId(orderId);

        OrderItem orderItem1 = new OrderItem("Item1",
                "Code1",
                "Rev1",
                1L,
                10.0,
                10.0,
                "2024-12-12",
                "");
        OrderItem orderItem2 = new OrderItem("Item2",
                "Code2",
                "Rev2",
                2L,
                20.0,
                40.0,
                "2024-12-12",
                "");

        order.setOrderItems(Arrays.asList(orderItem2, orderItem1));

        when(ordersRepository.findById(orderId)).thenReturn(Optional.of(order));

        List<OrderItem> result = orderItemsService.getOrderItems(orderId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Item1", result.get(0).getItemName());
        assertEquals("Item2", result.get(1).getItemName());
    }

    /**
     * Test for the {@link OrderItemsServiceImpl#getOrderItem(Long)} method.
     */
    @Test
    public void testGetOrderItem() {
        Long itemId = 1L;

        OrderItem orderItem = new OrderItem("Item1",
                "Code1",
                "Rev1",
                1L,
                10.0,
                10.0,
                "2024-12-12",
                "");

        when(orderItemsRepository.findById(itemId)).thenReturn(Optional.of(orderItem));

        OrderItem result = orderItemsService.getOrderItem(itemId);

        assertNotNull(result);
        assertEquals("Item1", result.getItemName());
    }

    /**
     * Test for the {@link OrderItemsServiceImpl#findOrderItemsByName(Long, String)} method.
     */
    @Test
    public void testFindOrderItemsByName() {
        Long orderId = 1L;
        String itemName = "Item";
        Order order = new Order();
        order.setId(orderId);

        OrderItem orderItem1 = new OrderItem("Item1",
                "Code1",
                "Rev1",
                1L,
                10.0,
                10.0,
                "2024-12-12",
                "");
        OrderItem orderItem2 = new OrderItem("Item2",
                "Code2",
                "Rev2",
                2L,
                20.0,
                40.0,
                "2024-12-12",
                "");

        order.setOrderItems(Arrays.asList(orderItem2, orderItem1));

        when(ordersRepository.findById(orderId)).thenReturn(Optional.of(order));

        List<OrderItem> result = orderItemsService.findOrderItemsByName(orderId, itemName);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.get(0).getItemName().toLowerCase().contains(itemName.toLowerCase()));
        assertTrue(result.get(1).getItemName().toLowerCase().contains(itemName.toLowerCase()));
    }
}