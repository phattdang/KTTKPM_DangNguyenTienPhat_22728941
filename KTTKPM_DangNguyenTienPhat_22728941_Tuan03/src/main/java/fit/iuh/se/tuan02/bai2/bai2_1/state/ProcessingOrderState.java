package fit.iuh.se.tuan02.bai2.bai2_1.state;

/**
 * @author : user664dntp
 * @mailto : phatdang19052004@gmail.com
 * @created : 18/03/2026, Wednesday
 **/
public class ProcessingOrderState implements OrderState {
    @Override
    public void verifyOrder(OrderContext order) {
        System.out.println("Lỗi: Đơn hàng đã được xác nhận rồi!");
    }

    @Override
    public void processOrder(OrderContext order) {
        System.out.println("Đang đóng gói và vận chuyển...");
        System.out.println(">> Chuyển trạng thái: Đã giao");
        order.setState(new DeliveredOrderState());
    }

    @Override
    public void deliverOrder(OrderContext order) {
        System.out.println("Lỗi: Đang đóng gói, chưa thể giao ngay được!");
    }

    @Override
    public void cancelOrder(OrderContext order) {
        System.out.println("Đang thu hồi hàng và hoàn tiền...");
        System.out.println(">> Chuyển trạng thái: Đã hủy");
        order.setState(new CancelledOrderState());
    }
}