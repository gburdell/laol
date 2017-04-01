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
 * @author kpfalzer
 */
public class LaolInteger extends LaolBox<Integer> implements LaolNumber {

    public LaolInteger(final Integer val) {
        super(val);
    }

    @Override
    public Laol toDouble() {
        return new LaolDouble(get().doubleValue());
    }

    @Override
    public Laol toInteger() {
        return this;
    }

    @Override
    public Laol addOp(Laol b) {
        return (b instanceof LaolInteger) 
                ? binaryIntOp(this, b, Math::addExact)
                : binaryDblOp(this, b, (x, y)-> x + y);
    }

    @Override
    public Laol subOp(Laol b) {
        return (b instanceof LaolInteger) 
                ? binaryIntOp(this, b, Math::subtractExact)
                : binaryDblOp(this, b, (x, y)-> x - y);
    }

    @Override
    public Laol multOp(Laol b) {
        return (b instanceof LaolInteger) 
                ? binaryIntOp(this, b, Math::multiplyExact)
                : binaryDblOp(this, b, (x, y)-> x * y);
    }

    @Override
    public Laol divOp(Laol b) {
        return (b instanceof LaolInteger) 
                ? binaryIntOp(this, b, (x, y)-> x / y)
                : binaryDblOp(this, b, (x, y)-> x / y);
    }

    @Override
    public void set(Laol val) {
        super.set(LaolNumber.toInteger(val).get());
    }

    @Override
    public LaolInteger clone() {
        return new LaolInteger(get());
    }
}
