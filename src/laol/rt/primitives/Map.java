/*
 * The MIT License
 *
 * Copyright 2016 gburdell.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package laol.rt.primitives;

import laol.rt.primitives.Integer;
import laol.rt.primitives.Boolean;
import java.util.LinkedHashMap;
import java.util.Objects;
import laol.rt.Laol;
import laol.rt.LaolBase;

/**
 * Map implementation which uses LinkedHashMap to maintain (insertion) key
 * order.
 *
 * @author kpfalzer
 */
public class Map extends LaolBase {

    public Map() {
    }

    //operator []=
    public Laol set(final Laol key, final Laol val) {
        mutableCheck();
        m_map.put(key, val);
        return val;
    }

    //operator []
    public Laol get(final Laol key) {
        return m_map.get(key);
    }

    //empty?
    public Laol isEmpty() {
        return new Boolean(m_map.isEmpty());
    }

    public Laol size() {
        return new Integer(m_map.size());
    }

    @Override
    public int hashCode() {
        return m_map.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (Objects.isNull(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Map other = (Map) obj;
        return Objects.equals(this.m_map, other.m_map);
    }

    private final java.util.Map<Laol, Laol> m_map = new LinkedHashMap<>();
}
