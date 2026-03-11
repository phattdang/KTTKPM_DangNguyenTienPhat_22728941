package fit.iuh.se.FactoryPattern;

/**
 * @author : user664dntp
 * @mailto : phatdang19052004@gmail.com
 * @created : 11/03/2026, Wednesday
 **/
public class SeaLogistics extends Logistics {
    @Override
    protected Transport createTransport() {
        return new Ship();
    }

    @Override
    public void deliver() {

    }
}
