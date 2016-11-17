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
package laol.rt;

import gblib.Util;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Base class of all objects.
 *
 * @author kpfalzer
 */
public abstract class LaolObject {

    public final <T extends LaolObject> T setMutable(boolean val) {
        m_mutable = val;
        return Util.downCast(this);
    }

    public final <T extends LaolObject> T setMutable() {
        return setMutable(true);
    }

    public final boolean isMutable() {
        return m_mutable;
    }

    public boolean isNull() {
        return false;
    }

    protected void mutableCheck(final boolean enable) {
        if (enable && !m_mutable) {
            throw new LaolException.Immutable();
        }
    }

    protected void mutableCheck() {
        mutableCheck(true);
    }

    public static <T extends LaolObject> T getNull(final Class<T> cls) {
        try {
            return cls.newInstance().getNull();
        } catch (InstantiationException | IllegalAccessException ex) {
            throw new LaolException(ex.getMessage());
        }
    }

    public static <T extends LaolObject> T valOrNull(final Class<T> valCls, final LaolObject val) {
        return (null != val) ? Util.downCast(val) : getNull(valCls);
    }

    public <T extends LaolObject> T getNull() {
        return Util.downCast(new LaolObject.Null());
    }

    public static class Null extends LaolObject {

        @Override
        public boolean isNull() {
            return true;
        }

    }

    private boolean m_mutable = false;
}
