package store.order;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@Builder @Data @Accessors(fluent = true, chain = true)
public class OrderItem {

    private String id;
    private String productId;
    private int quantity;
    private double total;

}
