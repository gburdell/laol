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

    protected void mutableCheck(final boolean enable) {
        if (enable && !m_mutable) {
            throw new LaolException.Immutable();
        }
    }

    protected void mutableCheck() {
        mutableCheck(true);
    }

    public static <T extends LaolObject> T valOrNull(final LaolObject val) {
        return Util.downCast(val);
    }

    public static boolean isNull(final LaolObject obj) {
        return (null == obj);
    }
    
    public LaolString toS() {
        return new LaolString(super.toString());
    }
    
    private boolean m_mutable = false;
}
