package store.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class OrderResource implements OrderController {

    @Autowired
    private OrderService orderService;

    @Override
    public ResponseEntity<OrderOut> create(
        String token,
        String accountId,
        OrderIn in
    ) {
        Order order = orderService.create(in, accountId);

        return ResponseEntity
            .created(
                ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(order.id())
                    .toUri()
            )
            .body(OrderParser.toOrderOut(order));
    }

    @Override
    public ResponseEntity<OrderOut> findById(
        String token,
        String accountId,
        String id
    ) {
        Order order = orderService.findByIdAndAccountId(id, accountId);

        if (order == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Order not found or does not belong to current user!"
            );
        }

        return ResponseEntity.ok(OrderParser.toOrderOut(order));
    }

    @Override
    public ResponseEntity<List<OrderOut>> findAll(
        String token,
        String accountId
    ) {
        List<Order> orders = orderService.findByAccountId(accountId);

        return ResponseEntity.ok(OrderParser.toOrderOutListSummary(orders));
    }

}
