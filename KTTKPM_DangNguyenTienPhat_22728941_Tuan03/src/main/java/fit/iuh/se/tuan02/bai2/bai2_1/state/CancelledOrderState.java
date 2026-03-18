package fit.iuh.se.tuan02.bai2.bai2_1.state;

/**
 * @author : user664dntp
 * @mailto : phatdang19052004@gmail.com
 * @created : 18/03/2026, Wednesday
 **/
public class CancelledOrderState implements OrderState {
    @Override
    public void verifyOrder(OrderContext order) {
        System.out.println("Lỗi: Đơn hàng đã bị hủy!");
    }

    @Override
    public void processOrder(OrderContext order) {
        System.out.println("Lỗi: Đơn hàng đã bị hủy!");
    }

    @Override
    public void deliverOrder(OrderContext order) {
        System.out.println("Lỗi: Đơn hàng đã bị hủy!");
    }

    @Override
    public void cancelOrder(OrderContext order) {
        System.out.println("Đơn hàng này đã ở trạng thái hủy rồi.");
    }
}