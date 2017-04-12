package creditsuisse.marketplace.orderboard.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

/**
 * Created by andrzejfolga on 08/04/2017.
 */
public class OrderBoardSummaryPrinter {

    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    public String print(Map<String, Order> orderBoard) {
        List<String> sellOrderBook = new ArrayList();
        sellOrderBook.add("SELL BOARD:");
        sellOrderBook.addAll(orderBoard.
                entrySet().
                parallelStream().
                filter(e -> e.getKey().charAt(0) == 'S').
                map(Map.Entry::getValue).
                collect(groupingByConcurrent(Order::getPrice, reducing(BigDecimal.ZERO, Order::getQuantity, BigDecimal::add))).
                entrySet().
                stream().
                sorted(Map.Entry.comparingByKey(Comparator.naturalOrder())).
                map(e -> e.getValue() + "kg for £" + e.getKey()).
                collect(toList()));

        List<String> buyOrderBook = new ArrayList();
        buyOrderBook.add("BUY BOARD:");
        buyOrderBook.addAll(orderBoard.
                entrySet().
                parallelStream().
                filter(e -> e.getKey().charAt(0) == 'B').
                map(Map.Entry::getValue).
                collect(groupingByConcurrent(Order::getPrice, reducing(BigDecimal.ZERO, Order::getQuantity, BigDecimal::add))).
                entrySet().
                stream().
                sorted(Map.Entry.comparingByKey(Comparator.reverseOrder())).
                map(e -> e.getValue() + "kg for £" + e.getKey()).
                collect(toList()));

        sellOrderBook.addAll(buyOrderBook);

        return sellOrderBook.stream().collect(joining(LINE_SEPARATOR));
    }
}
