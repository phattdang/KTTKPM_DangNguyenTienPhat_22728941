package fit.iuh.se.tuan02.bai2.bai2_1.strategy;

/**
 * @author : user664dntp
 * @mailto : phatdang19052004@gmail.com
 * @created : 18/03/2026, Wednesday
 **/
public class ExpressShippingStrategy implements ShippingStrategy {
    @Override
    public double calculateFee(double orderTotal) {
        System.out.println("Áp dụng: Giao hàng Hỏa tốc (Express).");
        double fee = orderTotal * 0.10;
        return Math.max(fee, 50000.0);
    }
}