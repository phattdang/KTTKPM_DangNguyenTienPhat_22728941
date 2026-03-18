package fit.iuh.se.tuan02.bai2.bai2_1.decorator;

/**
 * @author : user664dntp
 * @mailto : phatdang19052004@gmail.com
 * @created : 18/03/2026, Wednesday
 **/
public class DiscountDecorator extends OrderDecorator {
    public DiscountDecorator(Order order) {
        super(order);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " + (Đã áp dụng Voucher giảm 20k)";
    }

    @Override
    public double getCost() {
        return super.getCost() - 20000.0; // Trừ đi 20k
    }
}