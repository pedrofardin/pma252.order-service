package store.order;

import java.util.List;

public class OrderParser {

    public static OrderOut toOrderOut(Order order) {
        return order == null ? null :
            OrderOut.builder()
                .id(order.id())
                .date(order.date())
                .items(order.items() != null ?
                    order.items().stream().map(OrderParser::toItemOut).toList() :
                    null)
                .total(order.total())
                .build();
    }

    public static OrderOut.ItemOut toItemOut(OrderItem item) {
        return item == null ? null :
            OrderOut.ItemOut.builder()
                .id(item.id())
                .product(OrderOut.ProductId.builder().id(item.productId()).build())
                .quantity(item.quantity())
                .total(item.total())
                .build();
    }

    public static List<OrderOut> toOrderOutList(List<Order> orders) {
        return orders == null ? null :
            orders.stream().map(OrderParser::toOrderOut).toList();
    }

    public static List<OrderOut> toOrderOutListSummary(List<Order> orders) {
        return orders == null ? null :
            orders.stream().map(order -> OrderOut.builder()
                .id(order.id())
                .date(order.date())
                .total(order.total())
                .build()
            ).toList();
    }

}
