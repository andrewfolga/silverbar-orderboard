package creditsuisse.marketplace.orderboard.infrastructure.persistence;

import creditsuisse.marketplace.orderboard.domain.Order;
import creditsuisse.marketplace.orderboard.domain.OrderBoardRepository;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by andrzejfolga on 08/04/2017.
 */
public class InMemoryOrderBoardRepository implements OrderBoardRepository {

    private final ConcurrentMap<String, Order> liveOrderBoard = new ConcurrentHashMap<>();

    @Override
    public String addOrder(Order order) {
        String key = computeKey(order);
        liveOrderBoard.put(key, order);
        return key;
    }

    @Override
    public Order removeOrder(String key) {
        return liveOrderBoard.remove(key);
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
