package creditsuisse.marketplace.orderboard.domain;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Created by andrzejfolga on 06/04/2017.
 */
public class Order {
    private final long userId;
    private final BigDecimal quantity;
    private final BigDecimal price;
    private final OrderType orderType;

    public Order(long userId, BigDecimal quantity, BigDecimal price, OrderType orderType) {
        this.userId = userId;
        this.quantity = quantity;
        this.price = price;
        this.orderType = orderType;
    }

    public long getUserId() {
        return userId;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public String computeKey() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.orderType.toString().charAt(0));
        sb.append(this.price);
        sb.append(this.userId);
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return userId == order.userId &&
                Objects.equals(quantity, order.quantity) &&
                Objects.equals(price, order.price) &&
                orderType == order.orderType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, quantity, price, orderType);
    }

    @Override
    public String toString() {
        return "Order{" +
                "userId=" + userId +
                ", quantity=" + quantity +
                ", price=" + price +
                ", orderType=" + orderType +
                '}';
    }
}
