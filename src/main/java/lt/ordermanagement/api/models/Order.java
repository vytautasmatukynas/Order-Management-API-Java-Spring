package lt.ordermanagement.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity class represents information about order.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
@Schema(description = "Order entity representing information about an order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Order number", example = "1234567890123")
    @Size(max = 13)
    @Column(name = "order_number", unique = true, length = 13, nullable = false)
    private String orderNumber;

    @Schema(description = "Order name", example = "Sample Order")
    @NotBlank()
    @Size(min = 1, max = 50)
    @Column(name = "order_name", length = 50, nullable = false)
    private String orderName;

    @Schema(description = "Client name", example = "John Doe")
    @NotBlank()
    @Size(min = 1, max = 50)
    @Column(name = "client_name", length = 50, nullable = false)
    private String clientName;

    @Schema(description = "Client phone number", example = "+1234567890")
    @Size(max = 20)
    @Column(name = "client_phone_number", length = 20, nullable = false)
    private String clientPhoneNumber;

    @Schema(description = "Client email", example = "john.doe@example.com")
    @Email()
    @Size(max = 50)
    @Column(name = "client_email", length = 50, nullable = false)
    private String clientEmail;

    @Schema(description = "Order term", example = "2024-12-31")
    @Column(name = "order_term", nullable = false)
    private String orderTerm;

    @Schema(description = "Order status", example = "Pending")
    @Column(name = "order_status", nullable = false)
    private String orderStatus;

    /**
     * The default value for this field is set to 0.0 to handle cases where the
     * value is not explicitly set, ensuring that the field is never null.
     */
    @Schema(description = "Order price", example = "100.0")
    @Column(name = "order_price", nullable = false)
    private Double orderPrice = 0D;

    @Schema(description = "Comments", example = "Additional comments about the order")
    @Size(max = 200)
    @Column(name = "comments", length = 200, nullable = false)
    private String comments;

    @Schema(description = "Order update date", example = "2024-01-22")
    @Column(name = "order_update_date", nullable = false)
    private String orderUpdateDate;

    /**
     * Represents the deletion status of an order.
     * By default, the 'isDeleted' property is initialized to 'false' unless explicitly set.
     */
    @Schema(description = "Deletion status of an order", example = "false")
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @OneToMany(mappedBy = "order",
            cascade = {CascadeType.PERSIST,
                    CascadeType.MERGE,
                    CascadeType.REFRESH,
                    CascadeType.DETACH},
            fetch = FetchType.LAZY)
    @JsonIgnore
    List<OrderItem> orderItems;

    public Order(String orderNumber,
                 String orderName,
                 String clientName,
                 String clientPhoneNumber,
                 String clientEmail,
                 String orderTerm,
                 String orderStatus,
                 Double orderPrice,
                 String comments,
                 String orderUpdateDate) {
        this.orderNumber = orderNumber;
        this.orderName = orderName;
        this.clientName = clientName;
        this.clientPhoneNumber = clientPhoneNumber;
        this.clientEmail = clientEmail;
        this.orderTerm = orderTerm;
        this.orderStatus = orderStatus;
        this.orderPrice = orderPrice;
        this.comments = comments;
        this.orderUpdateDate = orderUpdateDate;
    }

    /**
     * Adds an order detail to the list of order details associated with this order.
     *
     * @param orderItem The order detail to be added.
     */
    public void addOrderItem(OrderItem orderItem) {
        if (orderItems == null) {
            orderItems = new ArrayList<>();
        }

        orderItems.add(orderItem);

        orderItem.setOrder(this);
    }

}