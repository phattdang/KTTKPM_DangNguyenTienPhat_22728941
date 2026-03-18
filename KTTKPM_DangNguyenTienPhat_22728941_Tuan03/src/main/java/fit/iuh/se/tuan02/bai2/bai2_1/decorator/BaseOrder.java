package fit.iuh.se.tuan02.bai2.bai2_1.decorator;

/**
 * @author : user664dntp
 * @mailto : phatdang19052004@gmail.com
 * @created : 18/03/2026, Wednesday
 **/
public class BaseOrder implements Order {
    private double baseCost;

    public BaseOrder(double baseCost) {
        this.baseCost = baseCost;
    }

    @Override
    public String getDescription() {
        return "Đơn hàng cơ bản";
    }

    @Override
    public double getCost() {
        return baseCost;
    }
}
