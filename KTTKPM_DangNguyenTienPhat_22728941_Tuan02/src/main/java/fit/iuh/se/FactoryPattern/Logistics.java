package fit.iuh.se.FactoryPattern;

/**
 * @author : user664dntp
 * @mailto : phatdang19052004@gmail.com
 * @created : 11/03/2026, Wednesday
 **/
public abstract class Logistics implements Transport {
    protected void planDelivery() {
        System.out.println("Planning delivery logistics.");
    }

    protected Transport createTransport() {
        return null;
    }
}
