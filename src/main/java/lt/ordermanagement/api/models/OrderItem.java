package lt.ordermanagement.api.models;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity class represents information about an item in an order.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_items")
@Schema(description = "OrderItem entity representing detailed information about an order associated order item")
public class OrderItem {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Item name", example = "Product A")
    @NotBlank()
    @Size(min = 1, max = 50)
    @Column(name = "item_name",
            length = 50,
            nullable = false)
    private String itemName;

    @Schema(description = "Item code", example = "A123")
    @Size(max = 50)
    @Column(name = "item_code",
            length = 50,
            nullable = false)
    private String itemCode;

    @Schema(description = "Item revision", example = "Rev1")
    @Size(max = 50)
    @Column(name = "item_revision",
            length = 50,
            nullable = false)
    private String itemRevision;

    /**
     * The default value for this field is set to 0 to handle cases where the
     * value is not explicitly set, ensuring that the field is never null.
     */
    @Schema(description = "Item count", example = "10")
    @Column(name = "item_count",
            nullable = false)
    private Long itemCount = 0L;

    /**
     * The default value for this field is set to 0.0 to handle cases where the
     * value is not explicitly set, ensuring that the field is never null.
     */
    @Schema(description = "Item price", example = "20.0")
    @Column(name = "item_price",
            nullable = false)
    private Double itemPrice = 0D;

    /**
     * The default value for this field is set to 0.0 to handle cases where the
     * value is not explicitly set, ensuring that the field is never null.
     */
    @Schema(description = "Total price for the item", example = "200.0")
    @Column(name = "total_price",
            nullable = false)
    private Double totalPrice = 0D;

    /**
     * The default value for this field is set to empty string to handle cases where the
     * value is not explicitly set, ensuring that the field is never null.
     */
    @Schema(description = "Link to item image", example = "https://example.com/image.jpg")
    @Column(name = "link_to_img",
            nullable = false)
    private String linkToImg = "";

    @Schema(description = "Item update date", example = "2024-01-22",
            accessMode = Schema.AccessMode.READ_ONLY)
    @Column(name = "item_update_date",
            nullable = false)
    private String itemUpdateDate;

    /**
     * Represents the deletion status of an order item.
     * By default, the 'isDeleted' property is initialized to 'false' unless explicitly set.
     */
    @Schema(description = "Deletion status of an order item", example = "false",
            accessMode = Schema.AccessMode.READ_ONLY)
    @Column(name = "is_deleted",
            nullable = false)
    private Boolean isDeleted = false;

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
                     Double totalPrice,
                     String itemUpdateDate,
                     String linkToImg) {
        this.itemName = itemName;
        this.itemCode = itemCode;
        this.itemRevision = itemRevision;
        this.itemCount = itemCount;
        this.itemPrice = itemPrice;
        this.totalPrice = totalPrice;
        this.itemUpdateDate = itemUpdateDate;
        this.linkToImg = linkToImg;
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
