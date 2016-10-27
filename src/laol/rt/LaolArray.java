package laol.rt;

import java.util.ArrayList;

/**
 * Array/List implementation.
 * 
 * @author kpfalzer
 */
public final class LaolArray extends LaolObject {

    public LaolArray() {
    }
    
    public LaolObject add(final LaolObject val) {
        mutableCheck(true);
        m_eles.add(val);
        return this;
    }
    
    
    private final ArrayList<LaolObject> m_eles = new ArrayList<>();
}
