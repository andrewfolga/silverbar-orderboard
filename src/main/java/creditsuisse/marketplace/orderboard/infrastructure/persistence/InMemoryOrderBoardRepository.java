package creditsuisse.marketplace.orderboard.infrastructure.persistence;

import creditsuisse.marketplace.orderboard.domain.Order;
import creditsuisse.marketplace.orderboard.domain.OrderBoardRepository;
import org.apache.commons.lang3.Validate;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static java.util.Optional.ofNullable;

/**
 * Created by andrzejfolga on 08/04/2017.
 */
public class InMemoryOrderBoardRepository implements OrderBoardRepository {

    private final ConcurrentMap<String, Order> liveOrderBoard = new ConcurrentHashMap<>();

    @Override
    public String addOrder(Order order) {
        Validate.notNull(order, "Order has to be provided!");
        String key = computeKey(order);
        liveOrderBoard.put(key, order);
        return key;
    }

    @Override
    public Optional<Order> removeOrder(String key) {
        Validate.notNull(key, "Order key has to be provided!");
        Order removedOrder = liveOrderBoard.remove(key);
        return ofNullable(removedOrder);
    }

    @Override
    public Map<String, Order> getOrders() {
        return liveOrderBoard;
    }

    private String computeKey(Order order) {
        StringBuilder sb = new StringBuilder();
        sb.append(order.getOrderType().toString().charAt(0));
        sb.append(order.getPrice());
        sb.append(order.getUserId());
        sb.append(new DateTime().toDateTime(DateTimeZone.UTC));
        return sb.toString();
    }

}
