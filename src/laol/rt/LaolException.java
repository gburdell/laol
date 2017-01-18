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
public class LaolException extends RuntimeException {

    public LaolException(final String detail) {
        super(detail);
    }

    public LaolException(final Throwable thrown) {
        super(thrown);
    }

    public LaolException(final Exception thrown) {
        super(thrown);
    }

    public static class ImmutableException extends LaolException {

        public ImmutableException(final String detail) {
            super(detail);
        }

        public ImmutableException() {
            this("cannot change immutable object");
        }
    }

    public static class InvalidTypeException extends LaolException {

        public InvalidTypeException(final String detail) {
            super(detail);
        }

        public InvalidTypeException() {
            this("invalid type");
        }
    }

    public static class IOException extends LaolException {

        public IOException(final Exception ex) {
            super(ex);
        }
    }

    public static class FileNotFoundException extends LaolException {

        public FileNotFoundException(final Exception ex) {
            super(ex);
        }
    }
}
