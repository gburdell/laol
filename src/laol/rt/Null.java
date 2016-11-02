package laol.rt;

import java.util.Random;

/**
 *
 * @author gburdell
 */
public class Null extends LaolObject {

    @Override
    public boolean isNull() {
        return true;
    }

    @Override
    public int hashCode() {
        return HASHCODE;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return getClass() == obj.getClass();
    }

    private static final Null THE_ONE = new Null();
    private static final int HASHCODE = 0xdeadbeef;
    
    public static Null getNull() {
        return THE_ONE;
    }
    
}
