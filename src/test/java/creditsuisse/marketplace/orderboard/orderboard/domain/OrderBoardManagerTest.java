package creditsuisse.marketplace.orderboard.orderboard.domain;

import creditsuisse.marketplace.orderboard.domain.Order;
import creditsuisse.marketplace.orderboard.domain.OrderBoardManager;
import creditsuisse.marketplace.orderboard.domain.OrderBoardRepository;
import creditsuisse.marketplace.orderboard.domain.OrderBoardSummaryPrinter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static creditsuisse.marketplace.orderboard.TestHelpers.entriesToMap;
import static creditsuisse.marketplace.orderboard.TestHelpers.entry;
import static creditsuisse.marketplace.orderboard.domain.OrderType.BUY;
import static creditsuisse.marketplace.orderboard.domain.OrderType.SELL;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

/**
 * Created by andrzejfolga on 06/04/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class OrderBoardManagerTest {

    @Mock
    private OrderBoardRepository orderBoardRepository;
    @Mock
    private OrderBoardSummaryPrinter orderBoardSummaryPrinter;

    private OrderBoardManager orderBoardManager;

    @Before
    public void setUp() throws Exception {
        orderBoardManager = new OrderBoardManager(orderBoardRepository, orderBoardSummaryPrinter);
    }

    @Test
    public void shouldRegisterOrder() throws Exception {
        Order order = new Order(1L, new BigDecimal(3.5), new BigDecimal(306), SELL);
        Mockito.when(orderBoardRepository.addOrder(order)).thenReturn("orderKey");

        String key = orderBoardManager.registerOrder(order);

        assertThat(key, is(equalTo("orderKey")));
        verify(orderBoardRepository).addOrder(order);
    }

    @Test
    public void shouldCancelOrder() throws Exception {
        Order order = new Order(1L, new BigDecimal(3.5), new BigDecimal(306), SELL);
        Mockito.when(orderBoardRepository.removeOrder(order)).thenReturn(order);

        Order removedOrder = orderBoardManager.cancelOrder(order);

        assertThat(removedOrder, is(equalTo(order)));
        verify(orderBoardRepository).removeOrder(order);
    }

    @Test
    public void shouldGetOrderBoardSummary() throws Exception {
        Order order1 = new Order(1L, new BigDecimal(3.5), new BigDecimal(306), SELL);
        Order order2 = new Order(1L, new BigDecimal(7.0), new BigDecimal(307), BUY);
        Map<String, Order> orders = Collections.unmodifiableMap(
                Stream.of(entry(order1.computeKey(), order1), entry(order2.computeKey(), order2)).collect(entriesToMap()));
        Mockito.when(orderBoardRepository.getOrders()).thenReturn(orders);
        String ordersSummary = "SELL BOARD:\n9.0kg for £306\nBUY BOARD:";
        Mockito.when(orderBoardSummaryPrinter.print(orders)).thenReturn(ordersSummary);

        String orderBoardSummary = orderBoardManager.getOrderBoardSummary();

        assertThat(orderBoardSummary, is(equalTo(ordersSummary)));
        verify(orderBoardRepository).getOrders();
        verify(orderBoardSummaryPrinter).print(orders);
    }

}