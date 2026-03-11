package fit.iuh.se.FactoryPattern;

/**
 * @author : user664dntp
 * @mailto : phatdang19052004@gmail.com
 * @created : 11/03/2026, Wednesday
 **/
public class RoadLogistics extends Logistics {
    @Override
    protected Transport createTransport() {
        return new Truck();
    }

    @Override
    public void deliver() {

    }
}
