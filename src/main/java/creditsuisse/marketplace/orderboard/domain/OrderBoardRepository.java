package creditsuisse.marketplace.orderboard.domain;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by andrzejfolga on 08/04/2017.
 */
public interface OrderBoardRepository {

    String addOrder(Order order);
    Optional<Order> removeOrder(String orderKey);
    Map<String, Order> getOrders();
}
