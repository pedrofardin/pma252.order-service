package store.order;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "order_item")
@Setter @Accessors(chain = true, fluent = true)
@NoArgsConstructor @AllArgsConstructor
public class OrderItemModel {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private OrderModel order;

    @Column(name = "product_id", nullable = false)
    private String productId;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "total", nullable = false)
    private double total;

    public OrderItemModel(OrderItem item) {
        this.id = item.id();
        this.productId = item.productId();
        this.quantity = item.quantity();
        this.total = item.total();
    }

    public OrderItem to() {
        return OrderItem.builder()
            .id(this.id)
            .productId(this.productId)
            .quantity(this.quantity)
            .total(this.total)
            .build();
    }

}
