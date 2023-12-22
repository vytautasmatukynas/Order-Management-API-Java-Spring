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

class OrdersServiceImplTest {

    @Mock
    private OrdersRepository ordersRepository;

    @InjectMocks
    private OrdersServiceImpl ordersService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetOrders() {
        List<Order> mockOrders = Arrays.asList(new Order(), new Order());

        when(ordersRepository.findAllSorted()).thenReturn(mockOrders);

        // Repeat the test 10000 times
        for (int i = 0; i < 10000; i++) {
            List<Order> result = ordersService.getOrders();

            assertNotNull(result);
            assertEquals(2, result.size());
        }
    }

    @Test
    public void testGetOrderById() {
        Long orderId = 1L;
        Order mockOrder = new Order();
        mockOrder.setId(orderId);

        when(ordersRepository.findById(orderId)).thenReturn(Optional.of(mockOrder));

        // Repeat the test 10000 times
        for (int i = 0; i < 10000; i++) {
            Order result = ordersService.getOrderById(orderId);

            assertNotNull(result);
            assertEquals(orderId, result.getId());
        }
    }

    @Test
    public void testFindOrdersByParameters() {
        String searchParam = "search";
        List<Order> mockOrders = Arrays.asList(new Order(), new Order());

        when(ordersRepository.findSortedOrdersByParameters(searchParam, searchParam, searchParam, searchParam, searchParam))
                .thenReturn(mockOrders);

        // Repeat the test 10000 times
        for (int i = 0; i < 10000; i++) {
            List<Order> result = ordersService.findOrdersByParameters(searchParam);

            assertNotNull(result);
            assertEquals(2, result.size());
        }
    }

}