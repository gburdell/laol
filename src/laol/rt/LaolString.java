package laol.rt;

/**
 *
 * @author kpfalzer
 */
public class LaolString extends LaolPrimitive<String> {
    public LaolString(final String val) {
        super(val);
    }
    
    public LaolString plusEqOp(final LaolString a) {
        set(get() + a.get());
        return this;
    }
}
