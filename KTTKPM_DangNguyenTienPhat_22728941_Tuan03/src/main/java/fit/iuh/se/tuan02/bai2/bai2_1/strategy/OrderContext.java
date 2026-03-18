package fit.iuh.se.tuan02.bai2.bai2_1.strategy;

/**
 * @author : user664dntp
 * @mailto : phatdang19052004@gmail.com
 * @created : 18/03/2026, Wednesday
 **/
public class OrderContext {
    private double orderTotal; // Tổng tiền hàng hóa
    private ShippingStrategy shippingStrategy; // Lắp ráp thuật toán vào đây

    public OrderContext(double orderTotal) {
        this.orderTotal = orderTotal;
    }

    // Hàm này cho phép thay đổi thuật toán tính phí bất cứ lúc nào (lúc runtime)
    public void setShippingStrategy(ShippingStrategy shippingStrategy) {
        this.shippingStrategy = shippingStrategy;
    }

    public void calculateTotalWithShipping() {
        if (this.shippingStrategy == null) {
            System.out.println("Vui lòng chọn phương thức giao hàng trước!");
            return;
        }

        // Ủy quyền việc tính phí cho Strategy
        double shippingFee = shippingStrategy.calculateFee(orderTotal);
        double finalTotal = orderTotal + shippingFee;

        System.out.println("Tiền hàng: " + orderTotal + " VND");
        System.out.println("Phí ship: " + shippingFee + " VND");
        System.out.println("=> TỔNG CỘNG: " + finalTotal + " VND\n");
    }
}
