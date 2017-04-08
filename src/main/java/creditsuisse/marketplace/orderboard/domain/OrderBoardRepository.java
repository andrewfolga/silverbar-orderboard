package creditsuisse.marketplace.orderboard.domain;

import java.util.Map;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by andrzejfolga on 08/04/2017.
 */
public interface OrderBoardRepository {

    String addOrder(Order order);
    Order removeOrder(Order order);
    Map<String, Order> getOrders();
}
