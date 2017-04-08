package creditsuisse.marketplace.orderboard.orderboard.domain.infrastructure.repository;

import creditsuisse.marketplace.orderboard.domain.Order;
import creditsuisse.marketplace.orderboard.infrastructure.persistence.InMemoryOrderBoardRepository;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Map;

import static creditsuisse.marketplace.orderboard.domain.OrderType.BUY;
import static creditsuisse.marketplace.orderboard.domain.OrderType.SELL;
import static java.util.Collections.emptyMap;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.junit.Assert.assertThat;

/**
 * Created by andrzejfolga on 08/04/2017.
 */
public class InMemoryOrderBoardRepositoryTest {

    private InMemoryOrderBoardRepository inMemoryOrderBoardRepository = new InMemoryOrderBoardRepository();

    @Test
    public void shouldRegisterNewOrder() throws Exception {
        Order buyOrder = new Order(1l, new BigDecimal(6.5), new BigDecimal(306), BUY);
        Order sellOrder = new Order(1l, new BigDecimal(3.5), new BigDecimal(307), SELL);
        String buyOrderKey = inMemoryOrderBoardRepository.addOrder(buyOrder);
        String sellOrderKey = inMemoryOrderBoardRepository.addOrder(sellOrder);

        Map<String, Order> orders = inMemoryOrderBoardRepository.getOrders();

        assertThat(orders, hasEntry(buyOrderKey, buyOrder));
        assertThat(orders, hasEntry(sellOrderKey, sellOrder));
    }


    @Test
    public void shouldRemoveAddedOrder() throws Exception {
        Order order = new Order(1L, new BigDecimal(3.5), new BigDecimal(306), SELL);
        inMemoryOrderBoardRepository.addOrder(order);
        Order removedOrder = inMemoryOrderBoardRepository.removeOrder(order);

        Map<String, Order> orders = inMemoryOrderBoardRepository.getOrders();

        assertThat(removedOrder, is(equalTo(order)));
        assertThat(orders, is(emptyMap()));
    }

    @Test
    public void shouldFailSafeIfNoOrderToRemove() throws Exception {
        Order order = new Order(1L, new BigDecimal(3.5), new BigDecimal(306), SELL);
        Order removedOrder = inMemoryOrderBoardRepository.removeOrder(order);

        Map<String, Order> orders = inMemoryOrderBoardRepository.getOrders();

        assertThat(removedOrder, nullValue());
        assertThat(orders, is(emptyMap()));
    }
}
