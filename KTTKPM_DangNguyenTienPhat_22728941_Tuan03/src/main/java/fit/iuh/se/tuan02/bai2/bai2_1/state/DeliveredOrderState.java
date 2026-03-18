package fit.iuh.se.tuan02.bai2.bai2_1.state;

/**
 * @author : user664dntp
 * @mailto : phatdang19052004@gmail.com
 * @created : 18/03/2026, Wednesday
 **/
public class DeliveredOrderState implements OrderState {
    @Override
    public void verifyOrder(OrderContext order) {
        System.out.println("Lỗi: Đơn hàng đã giao, không thể kiểm tra lại!");
    }

    @Override
    public void processOrder(OrderContext order) {
        System.out.println("Lỗi: Đơn hàng đã giao, không thể đóng gói lại!");
    }

    @Override
    public void deliverOrder(OrderContext order) {
        System.out.println("Cập nhật trạng thái: Đã giao thành công tới tay khách hàng!");
    }

    @Override
    public void cancelOrder(OrderContext order) {
        System.out.println("Lỗi: Đơn hàng đã giao thành công, không thể hủy!");
    }
}