package fit.iuh.se.tuan02.bai2.bai2_1.state;

/**
 * @author : user664dntp
 * @mailto : phatdang19052004@gmail.com
 * @created : 18/03/2026, Wednesday
 **/
public class NewOrderState implements OrderState{
    @Override
    public void verifyOrder(OrderContext order) {
        System.out.println("Kiểm tra thông tin đơn hàng... Hợp lệ!");
        System.out.println(">> Chuyển trạng thái: Đang xử lý");
        order.setState(new ProcessingOrderState());
    }

    @Override
    public void processOrder(OrderContext order) {
        System.out.println("Lỗi: Chưa kiểm tra thông tin, không thể đóng gói!");
    }

    @Override
    public void deliverOrder(OrderContext order) {
        System.out.println("Lỗi: Đơn hàng mới tạo, chưa thể giao!");
    }

    @Override
    public void cancelOrder(OrderContext order) {
        System.out.println("Đã hủy đơn hàng thành công.");
        System.out.println(">> Chuyển trạng thái: Đã hủy");
        order.setState(new CancelledOrderState());
    }
}
