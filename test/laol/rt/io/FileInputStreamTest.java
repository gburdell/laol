/*
 * The MIT License
 *
 * Copyright 2017 kpfalzer.
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
package laol.rt.io;

import gblib.Util;
import java.util.stream.Stream;
import laol.rt.LaolConsumer;
import laol.rt.LaolInteger;
import laol.rt.ILaol;
import laol.rt.LaolString;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kpfalzer
 */
public class FileInputStreamTest {

    final static LaolString FNAMES[] = Stream
            .of(
                    "lib/laol/collection.laol",
                    "lib/laol/io.laol"
            )
            .map(e -> new LaolString(e))
            .toArray(LaolString[]::new);

    @Test
    public void testEachLine() throws Exception {
        for (LaolString fname : FNAMES) {
            ILaol fis = new FileInputStream(fname);
            System.out.println(fname.get());
            fis.callPublic("eachLine",
                    new LaolConsumer((ILaol line) -> {
                        ILaol r = line.callPublic("length");
                        m_len += Util.<LaolInteger>downCast(r).get();
                    })
            );
        }
    }

    private int m_len = 0;
}
