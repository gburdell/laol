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
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import laol.rt.*;

/**
 *
 * @author kpfalzer
 */
public class FileInputStream extends LaolObject {

    private final BufferedReader m_rdr;

    public FileInputStream(LaolObject fname) {
        try {
            m_rdr = new BufferedReader(new FileReader(Util.<LaolString>downCast(fname).get()));
        } catch (FileNotFoundException ex) {
            throw new LaolException.FileNotFound(ex);
        }
    }

    public final Void close() {
        if (null != m_rdr) {
            try {
                m_rdr.close();
            } catch (IOException ex) {
                Util.abnormalExit(ex);
            }
        }
        return null;
    }

    public Void eachLine(LaolObject cb) {
        final LaolConsumer consumer = Util.downCast(cb);
        String s;
        try {
            while (true) {
                s = m_rdr.readLine();
                if (null == s) {
                    break;
                }
                consumer.accept(new LaolString(s));
            }
        } catch (IOException ex) {
            throw new LaolException.IO(ex);
        } finally {
            try {
                m_rdr.close();
            } catch (IOException ex) {
                Util.abnormalExit(ex);
            }
        }
        return null;
    }
}
