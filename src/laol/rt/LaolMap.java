package laol.rt;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Map implementation which uses LinkedHashMap to maintain (insertion) key order.
 * 
 * @author kpfalzer
 */
public final class LaolMap extends LaolObject {

    public LaolMap() {
    }
    
    public LaolObject add(final LaolObject key, final LaolObject val) {
        mutableCheck(true);
        m_map.put(key, val);
        return this;
    }
    
    
    private final Map<LaolObject, LaolObject> m_map = new LinkedHashMap<>();
}
