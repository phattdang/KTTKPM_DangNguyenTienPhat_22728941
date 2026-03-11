package fit.iuh.se.SingletonPattern;

/**
 * @author : user664dntp
 * @mailto : phatdang19052004@gmail.com
 * @created : 11/03/2026, Wednesday
 **/
public class Singleton {
    // Static member to hold the single instance
    private static Singleton instance = new Singleton();
    // Private constructor to prevent external instantiation
    private Singleton() { }
    // Static factory method for global access
    public static Singleton getInstance() {
        return instance;
    }
}
