package fit.iuh.se;


import fit.iuh.se.tuan02.bai2.bai2_1.strategy.ExpressShippingStrategy;
import fit.iuh.se.tuan02.bai2.bai2_1.strategy.InternationalShippingStrategy;
import fit.iuh.se.tuan02.bai2.bai2_1.strategy.OrderContext;
import fit.iuh.se.tuan02.bai2.bai2_1.strategy.StandardShippingStrategy;

/**
 * @author : user664dntp
 * @mailto : phatdang19052004@gmail.com
 * @created : 18/03/2026, Wednesday
 **/
public class Main {
    public static void main(String[] args) {
        // Khách hàng mua đơn hàng trị giá 500k
        OrderContext myOrder = new OrderContext(500000);

        // Kịch bản 1: Khách chọn giao hàng tiêu chuẩn
        myOrder.setShippingStrategy(new StandardShippingStrategy());
        myOrder.calculateTotalWithShipping();

        // Kịch bản 2: Khách đổi ý, cần hàng gấp nên chọn Hỏa tốc
        // Chỉ cần set lại Strategy mới, không cần tạo mới object Order
        myOrder.setShippingStrategy(new ExpressShippingStrategy());
        myOrder.calculateTotalWithShipping();

        // Kịch bản 3: Khách gửi tặng bạn ở nước ngoài
        myOrder.setShippingStrategy(new InternationalShippingStrategy());
        myOrder.calculateTotalWithShipping();
    }
}