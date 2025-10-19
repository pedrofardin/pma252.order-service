package store.order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import store.product.ProductController;
import store.product.ProductOut;

@Service
public class OrderService {

    private Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductController productController;

    public Order create(OrderIn orderIn, String accountId) {
        if (orderIn.items() == null || orderIn.items().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Order must have at least one item!"
            );
        }

        List<OrderItem> items = new ArrayList<>();
        double total = 0.0;

        for (OrderIn.ItemIn itemIn : orderIn.items()) {
            ResponseEntity<ProductOut> productResponse = productController.findById(itemIn.idProduct());
            ProductOut product = productResponse.getBody();

            if (product == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Product with id " + itemIn.idProduct() + " does not exist!"
                );
            }

            if (itemIn.quantity() <= 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Quantity must be greater than zero!"
                );
            }

            double itemTotal = product.price() * itemIn.quantity();
            total += itemTotal;

            OrderItem item = OrderItem.builder()
                .productId(itemIn.idProduct())
                .quantity(itemIn.quantity())
                .total(itemTotal)
                .build();

            items.add(item);
        }

        Order order = Order.builder()
            .accountId(accountId)
            .date(LocalDateTime.now())
            .items(items)
            .total(total)
            .build();

        return orderRepository.save(new OrderModel(order)).to();
    }

    public List<Order> findByAccountId(String accountId) {
        return orderRepository.findByAccountId(accountId)
            .stream()
            .map(OrderModel::to)
            .toList();
    }

    public Order findByIdAndAccountId(String id, String accountId) {
        return orderRepository.findByIdAndAccountId(id, accountId)
            .map(OrderModel::to)
            .orElse(null);
    }

}
