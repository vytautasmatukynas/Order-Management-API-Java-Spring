package lt.ordermanagement.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity class represents information about an item in an order.
 */
@Entity
@Table(name = "order_items")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 50, message = "Item name must be max 50 characters")
    @Column(name = "item_name",
            length = 50,
            nullable = false)
    private String itemName;

    @Size(max = 50, message = "First name must be max 50 characters")
    @Column(name = "item_code",
            length = 50,
            nullable = false)
    private String itemCode;

    @Size(max = 50, message = "First name must be max 50 characters")
    @Column(name = "item_revision",
            length = 50,
            nullable = false)
    private String itemRevision;

    /**
     * The default value for this field is set to 0 to handle cases where the
     * value is not explicitly set, ensuring that the field is never null.
     */
    @Column(name = "item_count",
            nullable = false)
    private Long itemCount = 0L;

    /**
     * The default value for this field is set to 0.0 to handle cases where the
     * value is not explicitly set, ensuring that the field is never null.
     */
    @Column(name = "item_price",
            nullable = false)
    private Double itemPrice = 0D;

    /**
     * The default value for this field is set to 0.0 to handle cases where the
     * value is not explicitly set, ensuring that the field is never null.
     */
    @Column(name = "total_price",
            nullable = false)
    private Double totalPrice = 0D;

    @ManyToOne(cascade = {CascadeType.MERGE},
            fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private Order order;

    public OrderItem(String itemName,
                     String itemCode,
                     String itemRevision,
                     Long itemCount,
                     Double itemPrice,
                     Double totalPrice) {
        this.itemName = itemName;
        this.itemCode = itemCode;
        this.itemRevision = itemRevision;
        this.itemCount = itemCount;
        this.itemPrice = itemPrice;
        this.totalPrice = totalPrice;
    }

    /**
     * Calculates and returns the total price of the item.
     *
     * @return The total price of the item.
     */
    public Double getTotalPrice() {
        return itemCount * itemPrice;
    }

}
