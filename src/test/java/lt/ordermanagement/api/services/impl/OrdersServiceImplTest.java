package lt.ordermanagement.api.services.impl;

import lt.ordermanagement.api.models.Order;
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
 * Unit tests for the {@link OrdersServiceImpl} class.
 */
class OrdersServiceImplTest {

    /**
     * Mock repository for orders.
     */
    @Mock
    private OrdersRepository ordersRepository;

    /**
     * Service to be tested.
     */
    @InjectMocks
    private OrdersServiceImpl ordersService;

    /**
     * Set up method to initialize mocks.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test for the {@link OrdersServiceImpl#getOrders()} method.
     */
    @Test
    public void testGetOrders() {
        List<Order> mockOrders = Arrays.asList(new Order(), new Order());

        when(ordersRepository.findAllSorted()).thenReturn(mockOrders);

        List<Order> result = ordersService.getOrders();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    /**
     * Test for the {@link OrdersServiceImpl#getOrderById(Long)} method.
     */
    @Test
    public void testGetOrderById() {
        Long orderId = 1L;
        Order mockOrder = new Order();
        mockOrder.setId(orderId);

        when(ordersRepository.findById(orderId)).thenReturn(Optional.of(mockOrder));

        Order result = ordersService.getOrderById(orderId);

        assertNotNull(result);
        assertEquals(orderId, result.getId());
    }

    /**
     * Test for the {@link OrdersServiceImpl#findOrdersByParameters(String)} method.
     */
    @Test
    public void testFindOrdersByParameters() {
        String searchParam = "search";
        List<Order> mockOrders = Arrays.asList(new Order(), new Order());

        when(ordersRepository.findSortedOrdersByParameters(
                searchParam, searchParam, searchParam, searchParam, searchParam))
                .thenReturn(mockOrders);

        List<Order> result = ordersService.findOrdersByParameters(searchParam);

        assertNotNull(result);
        assertEquals(2, result.size());
    }
}