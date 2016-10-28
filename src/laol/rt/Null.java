package laol.rt;

/**
 *
 * @author gburdell
 */
public class Null extends LaolObject {

    @Override
    public boolean isNull() {
        return true;
    }
    
    private static final Null THE_ONE = new Null();
    
    public static Null getNull() {
        return THE_ONE;
    }
    
}
