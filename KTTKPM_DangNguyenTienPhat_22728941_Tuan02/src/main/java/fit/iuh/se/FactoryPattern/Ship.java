package fit.iuh.se.FactoryPattern;

/**
 * @author : user664dntp
 * @mailto : phatdang19052004@gmail.com
 * @created : 11/03/2026, Wednesday
 **/
public class Ship implements Transport {
    @Override
    public void deliver() {
        System.out.println("Đang giao hàng bằng tàu biển...");
    }
}
