package fit.iuh.se.tuan02.bai2.bai2_1.state;

/**
 * @author : user664dntp
 * @mailto : phatdang19052004@gmail.com
 * @created : 18/03/2026, Wednesday
 **/
public class OrderContext {
    private OrderState state;

    public OrderContext() {
        this.state = new NewOrderState();
        System.out.println("--- Đơn hàng vừa được tạo mới ---");
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public void verifyOrder() {
        state.verifyOrder(this);
    }

    public void processOrder() {
        state.processOrder(this);
    }

    public void deliverOrder() {
        state.deliverOrder(this);
    }

    public void cancelOrder() {
        state.cancelOrder(this);
    }
}
