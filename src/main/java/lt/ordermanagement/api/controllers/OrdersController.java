package lt.ordermanagement.api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lt.ordermanagement.api.dtos.ResponseDeleteDTO;
import lt.ordermanagement.api.models.Order;
import lt.ordermanagement.api.services.Interfaces.OrdersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * REST Controller for managing orders.
 *
 * <p>
 * This controller provides endpoints for retrieving, creating, updating, and deleting orders.
 * The controller communicates with the OrdersService to perform these operations.
 * Exception handling is in place to handle potential errors and return appropriate HTTP status codes.
 * </p>
 */
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class OrdersController {

    public static final String ORDERS_PATH = "/orders";
    public static final String ORDER_PATH = "/order/{orderId}";
    public static final String SEARCH_ORDER_PATH = "/order/search/{orderParam}";
    public static final String ADD_ORDER_PATH = "/add/order";
    public static final String UPDATE_ORDER_PATH = "/update/order/{orderId}";
    public static final String DELETE_ORDER_PATH = "/delete/order/{orderId}";

    public static final String CORS_URL = "http://localhost:3000";

    private final OrdersService orderService;

    /**
     * Retrieves all orders.
     *
     * @return ResponseEntity containing a list of orders or an INTERNAL_SERVER_ERROR status if an unexpected error occurs.
     */
    @CrossOrigin(origins = CORS_URL)
    @GetMapping(ORDERS_PATH)
    public ResponseEntity<List<Order>> getOrders() {
        try {
            return ResponseEntity.ok(orderService.getOrders());

        } catch (BadCredentialsException | UsernameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "Unauthorized: " + e.getMessage());
        } catch (AccessDeniedException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Forbidden: " + e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Unexpected error fetching orders:" + e.getMessage());
        }
    }

    /**
     * Retrieves an order by its ID.
     *
     * @param orderId The ID of the order to retrieve.
     * @return ResponseEntity containing the retrieved order or a NOT_FOUND status if the order is not found.
     *         Throws a ResponseStatusException with INTERNAL_SERVER_ERROR if an unexpected error occurs.
     *         Possible Exceptions: BadCredentialsException, UsernameNotFoundException, AccessDeniedException,
     *         NoSuchElementException, Exception
     */
    @CrossOrigin(origins = CORS_URL)
    @GetMapping(ORDER_PATH)
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId) {
        try {
            return ResponseEntity.ok(orderService.getOrderById(orderId));

        } catch (BadCredentialsException | UsernameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "Unauthorized: " + e.getMessage());
        } catch (AccessDeniedException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Forbidden: " + e.getMessage());
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Order not found: " + e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Unexpected error fetching order: " + e.getMessage());
        }
    }

    /**
     * Retrieves a list of orders based on a search parameter.
     *
     * @param orderParam The parameter to search for in order attributes
     *                   (orderNumber, orderName, client, clientPhoneNumber, clientEmail).
     * @return ResponseEntity containing a list of orders matching the specified search parameter.
     *         Throws a ResponseStatusException with NOT_FOUND if no order is found with the specified parameter.
     *         Throws a ResponseStatusException with INTERNAL_SERVER_ERROR if an unexpected error occurs.
     *         Possible Exceptions: BadCredentialsException, UsernameNotFoundException, AccessDeniedException,
     *         NoSuchElementException, Exception
     */
    @CrossOrigin(origins = CORS_URL)
    @GetMapping(SEARCH_ORDER_PATH)
    public ResponseEntity<List<Order>> findOrderByParam(@PathVariable String orderParam) {
        try {
            return ResponseEntity.ok(orderService.findOrdersByParameters(orderParam));

        } catch (BadCredentialsException | UsernameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "Unauthorized: " + e.getMessage());
        } catch (AccessDeniedException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Forbidden: " + e.getMessage());
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Orders not found: " + e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Unexpected error fetching orders: " + e.getMessage());
        }
    }

    /**
     * Adds a new order.
     *
     * @param order The order to add.
     * @return ResponseEntity containing the added order or INTERNAL_SERVER_ERROR status if an unexpected error occurs.
     *         Possible Exceptions: BadCredentialsException, UsernameNotFoundException, AccessDeniedException, Exception
     */
    @CrossOrigin(origins = CORS_URL)
    @PostMapping(ADD_ORDER_PATH)
    public ResponseEntity<Order> addOrder(@Valid @RequestBody Order order) {
        try {
            orderService.addOrder(order);

            return ResponseEntity.ok(order);

        } catch (BadCredentialsException | UsernameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "Unauthorized: " + e.getMessage());
        } catch (AccessDeniedException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Forbidden: " + e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Unexpected error creating order: " + e.getMessage());
        }
    }

    /**
     * Updates an existing order.
     *
     * @param orderId The ID of the order to update.
     * @param order   The updated order data.
     * @return ResponseEntity containing the updated order or a NOT_FOUND status if the order is not found.
     *         Throws a ResponseStatusException with INTERNAL_SERVER_ERROR if an unexpected error occurs.
     *         Possible Exceptions: BadCredentialsException, UsernameNotFoundException, AccessDeniedException,
     *         NoSuchElementException, Exception
     */
    @CrossOrigin(origins = CORS_URL)
    @PutMapping(UPDATE_ORDER_PATH)
    public ResponseEntity<Order> updateOrder(@PathVariable Long orderId,
                                             @Valid @RequestBody Order order) {
        try {
            return ResponseEntity.ok(orderService.updateOrder(orderId, order));

        } catch (BadCredentialsException | UsernameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "Unauthorized: " + e.getMessage());
        } catch (AccessDeniedException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Forbidden: " + e.getMessage());
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Order not found: " + e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Unexpected error updating order: " + e.getMessage());
        }
    }

    /**
     * Deletes an order by its ID.
     *
     * @param orderId The ID of the order to delete.
     * @return ResponseEntity containing a delete response or a NOT_FOUND status if the order is not found.
     *         Throws a ResponseStatusException with INTERNAL_SERVER_ERROR if an unexpected error occurs.
     *         Possible Exceptions: BadCredentialsException, UsernameNotFoundException, AccessDeniedException,
     *         NoSuchElementException, Exception
     */
    @CrossOrigin(origins = CORS_URL)
    @DeleteMapping(DELETE_ORDER_PATH)
    public ResponseEntity<ResponseDeleteDTO> deleteOrder(@PathVariable Long orderId) {
        try {
            orderService.deleteOrder(orderId);

            ResponseDeleteDTO responseItemDeleteDTO =
                    new ResponseDeleteDTO("success",
                            "order was deleted with ID: " + orderId);

            return ResponseEntity.ok(responseItemDeleteDTO);

        } catch (BadCredentialsException | UsernameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "Unauthorized: " + e.getMessage());
        } catch (AccessDeniedException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Forbidden: " + e.getMessage());
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Order not found: " + e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Unexpected error while deleting order:" + e.getMessage());
        }
    }

}
