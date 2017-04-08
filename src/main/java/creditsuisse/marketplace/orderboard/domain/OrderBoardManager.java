package creditsuisse.marketplace.orderboard.domain;

import java.util.Collections;
import java.util.Map;

/**
 * Created by andrzejfolga on 06/04/2017.
 */
public class OrderBoardManager {

    private final OrderBoardRepository orderBoardRepository;
    private final OrderBoardSummaryPrinter orderBoardSummaryPrinter;

    public OrderBoardManager(OrderBoardRepository orderBoardRepository, OrderBoardSummaryPrinter orderBoardSummaryPrinter) {
        this.orderBoardRepository = orderBoardRepository;
        this.orderBoardSummaryPrinter = orderBoardSummaryPrinter;
    }

    public String registerOrder(Order order) {
        return orderBoardRepository.addOrder(order);
    }

    public Order cancelOrder(Order order) {
        return orderBoardRepository.removeOrder(order);
    }

    public String getOrderBoardSummary() {
        return orderBoardSummaryPrinter.print(orderBoardRepository.getOrders());
    }

}
