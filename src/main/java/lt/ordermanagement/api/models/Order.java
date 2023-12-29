package lt.ordermanagement.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 13)
    @Column(name = "order_number",
            unique = true,
            length = 13,
            nullable = false)
    private String orderNumber;

    @NotBlank()
    @Size(min = 1, max = 50)
    @Column(name = "order_name",
            length = 50,
            nullable = false)
    private String orderName;

    @NotBlank()
    @Size(min = 1, max = 50)
    @Column(name = "client_name",
            length = 50,
            nullable = false)
    private String clientName;

    @Size(max = 20)
    @Column(name = "client_phone_number",
            length = 20,
            nullable = false)
    private String clientPhoneNumber;

    @Email()
    @Size(max = 50)
    @Column(name = "client_email",
            length = 50,
            nullable = false
    )
    private String clientEmail;

    @Column(name = "order_term",
            nullable = false)
    private String orderTerm;

    @Column(name = "order_status",
            nullable = false)
    private String orderStatus;

    /**
     * The default value for this field is set to 0.0 to handle cases where the
     * value is not explicitly set, ensuring that the field is never null.
     */
    @Column(name = "order_price",
            nullable = false)
    private Double orderPrice = 0D;

    @Size(max = 200)
    @Column(name = "comments",
            length = 200,
            nullable = false)
    private String comments;

    @Column(name = "order_update_date",
            nullable = false)
    private String orderUpdateDate;

    @OneToMany(mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
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