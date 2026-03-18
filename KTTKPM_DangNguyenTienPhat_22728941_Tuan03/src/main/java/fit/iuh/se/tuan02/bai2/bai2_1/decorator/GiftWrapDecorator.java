package fit.iuh.se.tuan02.bai2.bai2_1.decorator;

/**
 * @author : user664dntp
 * @mailto : phatdang19052004@gmail.com
 * @created : 18/03/2026, Wednesday
 **/
public class GiftWrapDecorator extends OrderDecorator {
    public GiftWrapDecorator(Order order) {
        super(order);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " + Dịch vụ gói quà cao cấp";
    }

    @Override
    public double getCost() {
        return super.getCost() + 50000.0; // Cộng thêm 50k phí gói quà
    }
}
