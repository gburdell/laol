/*
 * The MIT License
 *
 * Copyright 2016 kpfalzer.
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

import java.util.function.DoubleBinaryOperator;
import java.util.function.IntBinaryOperator;

/**
 * We use exact methods here to catch over/underflow. TODO: If we catch these,
 * use BigInteger...
 *
 * @author kpfalzer
 */
public interface LaolNumber {

    public LaolInteger toInteger();

    public LaolDouble toDouble();

    public LaolNumber addOp(LaolNumber b);

    public LaolNumber subOp(LaolNumber b);

    public LaolNumber multOp(LaolNumber b);

    public LaolNumber divOp(LaolNumber b);

    static final LaolInteger ONE = new LaolInteger(1);

    default public LaolNumber preIncrOp() {
        set(addOp(ONE));
        return this;
    }

    default public LaolNumber preDecrOp() {
        set(subOp(ONE));
        return this;
    }

    default public LaolNumber postIncrOp() {
        LaolNumber currVal = clone();
        set(addOp(ONE));
        return currVal;
    }

    default public LaolNumber postDecrOp() {
        LaolNumber currVal = clone();
        set(subOp(ONE));
        return currVal;
    }

    public void set(LaolNumber val);

    public LaolNumber clone();

    static LaolDouble binaryDblOp(LaolNumber a, LaolNumber b, DoubleBinaryOperator op) {
        final double r = op.applyAsDouble(a.toDouble().get(), b.toDouble().get());
        return new LaolDouble(r);
    }

    static LaolInteger binaryIntOp(LaolNumber a, LaolNumber b, IntBinaryOperator op) {
        final int r = op.applyAsInt(a.toInteger().get(), b.toInteger().get());
        return new LaolInteger(r);
    }
}
