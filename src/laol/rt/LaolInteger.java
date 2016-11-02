package laol.rt;

/**
 *
 * @author kpfalzer
 */
public class LaolInteger extends LaolPrimitive<Integer> {

    public LaolInteger(final Integer val) {
        super(val);
    }

    public LaolInteger preIncrOp() {
        set(get() + 1);
        return this;
    }

    public LaolInteger postIncrOp() {
        final LaolInteger rval = new LaolInteger(get());
        set(get() + 1);
        return rval;
    }

    public LaolInteger preDecrOp() {
        set(get() - 1);
        return this;
    }

    public LaolInteger postDecrOp() {
        final LaolInteger rval = new LaolInteger(get());
        set(get() - 1);
        return rval;
    }
}
