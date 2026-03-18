package fit.iuh.se.tuan02.bai2.bai2_1.strategy;

/**
 * @author : user664dntp
 * @mailto : phatdang19052004@gmail.com
 * @created : 18/03/2026, Wednesday
 **/
public class StandardShippingStrategy implements ShippingStrategy {
    @Override
    public double calculateFee(double orderTotal) {
        System.out.println("Áp dụng: Giao hàng Tiêu chuẩn (Standard).");
        return 30000.0;
    }
}
