package creditsuisse.marketplace.orderboard.domain;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Stream;

import static creditsuisse.marketplace.orderboard.TestHelpers.entriesToMap;
import static creditsuisse.marketplace.orderboard.TestHelpers.entry;
import static creditsuisse.marketplace.orderboard.domain.OrderType.BUY;
import static creditsuisse.marketplace.orderboard.domain.OrderType.SELL;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

/**
 * Created by andrzejfolga on 08/04/2017.
 */
public class OrderBoardSummaryPrinterTest {

    private OrderBoardSummaryPrinter orderBoardSummaryPrinter = new OrderBoardSummaryPrinter();

    @Test
    public void shouldPrintSellOrdersInCorrectPriceOrder() throws Exception {

        Order order = new Order(1L, new BigDecimal(3.5), new BigDecimal(310), SELL);
        Order order2 = new Order(3L, new BigDecimal(1.5), new BigDecimal(307), SELL);
        Order order3 = new Order(2L, new BigDecimal(4.5), new BigDecimal(309), SELL);
        Map<String, Order> orders = Collections.unmodifiableMap(
                Stream.of(entry("S1", order), entry("S2", order2), entry("S3", order3)).collect(entriesToMap()));

        String result = orderBoardSummaryPrinter.print(orders);

        assertThat(result, equalTo("SELL BOARD:\n" +
                "1.5kg for £307\n" +
                "4.5kg for £309\n" +
                "3.5kg for £310\n" +
                "BUY BOARD:"));

    }

    @Test
    public void shouldPrintBuyOrdersInCorrectPriceOrder() throws Exception {

        Order order = new Order(1L, new BigDecimal(3.5), new BigDecimal(310), BUY);
        Order order2 = new Order(3L, new BigDecimal(1.5), new BigDecimal(307), BUY);
        Order order3 = new Order(2L, new BigDecimal(4.5), new BigDecimal(309), BUY);
        Map<String, Order> orders = Collections.unmodifiableMap(
                Stream.of(entry("B1", order), entry("B2", order2), entry("B3", order3)).collect(entriesToMap()));

        String result = orderBoardSummaryPrinter.print(orders);

        assertThat(result, equalTo("" +
                "SELL BOARD:\n" +
                "BUY BOARD:\n" +
                "3.5kg for £310\n" +
                "4.5kg for £309\n" +
                "1.5kg for £307"));
    }

    @Test
    public void shouldCombineSamePriceOrders() throws Exception {
        Order sellOrder = new Order(1L, new BigDecimal(3.5), new BigDecimal(306), SELL);
        Order sellOrder2 = new Order(1L, new BigDecimal(3.5), new BigDecimal(306), SELL);
        Order buyOrder = new Order(1L, new BigDecimal(9.5), new BigDecimal(309), BUY);
        Order buyOrder2 = new Order(2L, new BigDecimal(2), new BigDecimal(309), BUY);
        Map<String, Order> orders = Collections.unmodifiableMap(
                Stream.of(entry("S1", sellOrder), entry("S2", sellOrder2), entry("B1", buyOrder), entry("B2", buyOrder2)).collect(entriesToMap()));

        String result = orderBoardSummaryPrinter.print(orders);

        assertThat(result, equalTo("" +
                "SELL BOARD:\n" +
                "7.0kg for £306\n" +
                "BUY BOARD:\n" +
                "11.5kg for £309"));
    }

}