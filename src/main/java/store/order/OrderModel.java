package store.order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "orders")
@Getter @Setter @Accessors(chain = true, fluent = true)
@NoArgsConstructor @AllArgsConstructor
public class OrderModel {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "account_id", nullable = false)
    private String accountId;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItemModel> items = new ArrayList<>();

    @Column(name = "total", nullable = false)
    private Double total;

    public OrderModel(Order order) {
        this.id = order.id();
        this.accountId = order.accountId();
        this.date = order.date();
        this.total = order.total();
        if (order.items() != null) {
            this.items = order.items().stream()
                .map(item -> {
                    OrderItemModel itemModel = new OrderItemModel(item);
                    itemModel.order(this);
                    return itemModel;
                })
                .toList();
        }
    }

    public Order to() {
        return Order.builder()
            .id(this.id)
            .accountId(this.accountId)
            .date(this.date)
            .items(this.items != null ? this.items.stream().map(OrderItemModel::to).toList() : null)
            .total(this.total)
            .build();
    }

}
