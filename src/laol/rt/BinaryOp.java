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
