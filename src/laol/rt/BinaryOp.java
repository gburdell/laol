package laol.rt;

/**
 *
 * @author gburdell
 */
public class BinaryOp {

    public static LaolBoolean notOp(final LaolBoolean a) {
        return new LaolBoolean(!a.get());
    }

    public static LaolBoolean andOp(final LaolBoolean a, final LaolBoolean b) {
        return new LaolBoolean(a.get() & b.get());
    }

    public static LaolBoolean orOp(final LaolBoolean a, final LaolBoolean b) {
        return new LaolBoolean(a.get() | b.get());
    }

    public static LaolBoolean xorOp(final LaolBoolean a, final LaolBoolean b) {
        return new LaolBoolean(a.get() ^ b.get());
    }

    public static LaolDouble addOp(final LaolDouble a, final LaolDouble b) {
        return new LaolDouble(a.get() + b.get());
    }

    public static LaolDouble subOp(final LaolDouble a, final LaolDouble b) {
        return new LaolDouble(a.get() - b.get());
    }

    public static LaolDouble multOp(final LaolDouble a, final LaolDouble b) {
        return new LaolDouble(a.get() * b.get());
    }

    public static LaolDouble divOp(final LaolDouble a, final LaolDouble b) {
        return new LaolDouble(a.get() / b.get());
    }

    public static LaolInteger addOp(final LaolInteger a, final LaolInteger b) {
        return new LaolInteger(a.get() + b.get());
    }

    public static LaolInteger subOp(final LaolInteger a, final LaolInteger b) {
        return new LaolInteger(a.get() - b.get());
    }

    public static LaolInteger multOp(final LaolInteger a, final LaolInteger b) {
        return new LaolInteger(a.get() * b.get());
    }

    public static LaolInteger divOp(final LaolInteger a, final LaolInteger b) {
        return new LaolInteger(a.get() / b.get());
    }

    public static LaolInteger remOp(final LaolInteger a, final LaolInteger b) {
        return new LaolInteger(a.get() % b.get());
    }
    
    public static LaolString addOp(final LaolString a, final LaolString b) {
        return new LaolString(a.get() + b.get());
    }
}
