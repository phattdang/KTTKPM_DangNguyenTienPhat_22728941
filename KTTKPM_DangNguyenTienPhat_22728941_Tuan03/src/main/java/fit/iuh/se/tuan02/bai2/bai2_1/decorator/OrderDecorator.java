package fit.iuh.se.tuan02.bai2.bai2_1.decorator;

/**
 * @author : user664dntp
 * @mailto : phatdang19052004@gmail.com
 * @created : 18/03/2026, Wednesday
 **/
public class OrderDecorator implements Order {
    protected Order decoratedOrder; // Biến chứa đơn hàng đang được bọc

    public OrderDecorator(Order order) {
        this.decoratedOrder = order;
    }

    @Override
    public String getDescription() {
        return decoratedOrder.getDescription();
    }

    @Override
    public double getCost() {
        return decoratedOrder.getCost();
    }
}
