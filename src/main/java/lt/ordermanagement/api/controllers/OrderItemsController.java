package lt.ordermanagement.api.controllers;

import lombok.RequiredArgsConstructor;
import lt.ordermanagement.api.dtos.ResponseDeleteDTO;
import lt.ordermanagement.api.models.OrderItem;
import lt.ordermanagement.api.services.Interfaces.OrderItemsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * REST Controller for managing order items.
 *
 * <p>
 * This controller provides endpoints for retrieving, creating, updating, and deleting order items.
 * The controller communicates with the OrderItemsService to perform these operations.
 * Exception handling is in place to handle potential errors and return appropriate HTTP status codes.
 * </p>
 */
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class OrderItemsController {

    public static final String ITEMS_PATH = "/order/{orderId}/items";
    public static final String ITEM_PATH = "/order/item/{itemId}";
    public static final String SEARCH_ORDER_ITEM_PATH = "/order/{orderId}/items/search/{itemName}";
    public static final String ADD_ITEM_PATH = "/order/{orderId}/add/item";
    public static final String UPDATE_ITEM_PATH = "/order/update/item/{itemId}";
    public static final String DELETE_ITEM_PATH = "/order/delete/item/{itemId}";

    private final OrderItemsService orderItemsService;

    /**
     * Retrieves the list of order items for a given order ID.
     *
     * @param orderId The ID of the order for which to retrieve items.
     * @return ResponseEntity containing a list of order items or a NOT_FOUND status if the order or items are not found.
     *         Throws a ResponseStatusException with INTERNAL_SERVER_ERROR if an unexpected error occurs.
     *         Possible Exceptions: BadCredentialsException, UsernameNotFoundException, AccessDeniedException,
     *         NoSuchElementException, Exception
     */
    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    @GetMapping(ITEMS_PATH)
    public ResponseEntity<List<OrderItem>> getOrderItems(@PathVariable Long orderId) {
        try {
            return ResponseEntity.ok(orderItemsService.getOrderItems(orderId));

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
                    "Unexpected error fetching order or order items: " + e.getMessage());
        }
    }

    /**
     * Deletes an order item by its ID.
     *
     * @param itemId The ID of the order item to delete.
     * @return ResponseEntity containing a delete response or a NOT_FOUND status if the item is not found.
     *         Throws a ResponseStatusException with INTERNAL_SERVER_ERROR if an unexpected error occurs.
     *         Possible Exceptions: BadCredentialsException, UsernameNotFoundException, AccessDeniedException,
     *         NoSuchElementException, Exception
     */
    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    @GetMapping(ITEM_PATH)
    public ResponseEntity<OrderItem> getOrderItemById(@PathVariable Long itemId) {
        try {
            return ResponseEntity.ok(orderItemsService.getOrderItem(itemId));

        } catch (BadCredentialsException | UsernameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "Unauthorized: " + e.getMessage());
        } catch (AccessDeniedException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Forbidden: " + e.getMessage());
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Order item not found: " + e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Unexpected error fetching order item: " + e.getMessage());
        }
    }

    /**
     * Exports order items to an Excel file for a given order ID.
     *
     * @param orderId The ID of the order for which to export items to Excel.
     * @return ResponseEntity containing an export response or a NOT_FOUND status if the items are not found.
     *         Throws a ResponseStatusException with INTERNAL_SERVER_ERROR if an unexpected error occurs.
     *         Possible Exceptions: BadCredentialsException, UsernameNotFoundException, AccessDeniedException,
     *         IOException
     */
    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    @GetMapping(SEARCH_ORDER_ITEM_PATH)
    public ResponseEntity<List<OrderItem>> findOrderItemsByName(@PathVariable Long orderId,
                                                                @PathVariable String itemName) {
        try {
            return ResponseEntity.ok(orderItemsService.findOrderItemsByName(orderId, itemName));

        } catch (BadCredentialsException | UsernameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "Unauthorized: " + e.getMessage());
        } catch (AccessDeniedException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Forbidden: " + e.getMessage());
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Order items not found: " + e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Unexpected error fetching order items: " + e.getMessage());
        }
    }

    /**
     * Adds a new order item to the specified order and updates order price in the order table.
     *
     * @param orderId   The ID of the order to which the item should be added.
     * @param orderItem The order item data to be added.
     * @return ResponseEntity containing the added order item or a NOT_FOUND status if an error occurs.
     *         Throws a ResponseStatusException with INTERNAL_SERVER_ERROR if an unexpected error occurs.
     *         Possible Exceptions: BadCredentialsException, UsernameNotFoundException, AccessDeniedException,
     *         NoSuchElementException, Exception
     */
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @PostMapping(ADD_ITEM_PATH)
    public ResponseEntity<OrderItem> addItemToOrder(@PathVariable Long orderId,
                                                    @RequestBody OrderItem orderItem) {
        try {
            return ResponseEntity.ok(orderItemsService.addItemToOrder(orderId, orderItem));

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
                    "Unexpected error creating order item: " + e.getMessage());
        }
    }

    /**
     * Updates an existing order item and updates order price in the order table.
     *
     * @param itemId    The ID of the order item to update.
     * @param orderItem The updated order item data.
     * @return ResponseEntity containing the updated order item or a NOT_FOUND status if the item is not found.
     *         Throws a ResponseStatusException with INTERNAL_SERVER_ERROR if an unexpected error occurs.
     *         Possible Exceptions: BadCredentialsException, UsernameNotFoundException, AccessDeniedException,
     *         NoSuchElementException, Exception
     */
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @PutMapping(UPDATE_ITEM_PATH)
    public ResponseEntity<OrderItem> updateOrderItem(@PathVariable Long itemId,
                                                     @RequestBody OrderItem orderItem) {
        try {
            return ResponseEntity.ok(orderItemsService.updateOrderItem(itemId, orderItem));

        } catch (BadCredentialsException | UsernameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "Unauthorized: " + e.getMessage());
        } catch (AccessDeniedException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Forbidden: " + e.getMessage());
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Order item not found: " + e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Unexpected error updating order item: " + e.getMessage());
        }
    }

    /**
     * Deletes an order item by its ID.
     *
     * @param itemId The ID of the order item to delete.
     * @return ResponseEntity containing a delete response or a NOT_FOUND status if the item is not found.
     *         Throws a ResponseStatusException with INTERNAL_SERVER_ERROR if an unexpected error occurs.
     *         Possible Exceptions: BadCredentialsException, UsernameNotFoundException, AccessDeniedException,
     *         NoSuchElementException, Exception
     */
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @DeleteMapping(DELETE_ITEM_PATH)
    public ResponseEntity<ResponseDeleteDTO> deleteOrderItem(@PathVariable Long itemId) {
        try {
            orderItemsService.deleteOrderItem(itemId);

            ResponseDeleteDTO responseItemDeleteDTO =
                    new ResponseDeleteDTO("success",
                            "order item was deleted with ID: " + itemId);

            return ResponseEntity.ok(responseItemDeleteDTO);

        } catch (BadCredentialsException | UsernameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "Unauthorized: " + e.getMessage());
        } catch (AccessDeniedException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Forbidden: " + e.getMessage());
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Order item not found: " + e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Unexpected error while deleting order item: "  + e.getMessage());
        }
    }

}

