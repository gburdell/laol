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
package laol.generate.java;

import apfe.runtime.Acceptor;
import apfe.runtime.CharBufState;
import apfe.runtime.CharBuffer;
import apfe.runtime.InputStream;
import apfe.runtime.ParseError;
import gblib.MessageMgr;
import gblib.Pair;
import static gblib.Util.error;
import static gblib.Util.info;
import java.util.LinkedList;
import java.util.List;
import laol.ast.Grammar;

/**
 *
 * @author kpfalzer
 */
public class Parse {

    public Parse(final String sources[]) {
        for (String fname : sources) {
            try {
                parse(fname);
            } catch (Exception ex) {
                error("LG-FILE-2", fname, ex.toString());
            }
        }
    }

    public static class Ast extends Pair<String, Grammar> {

        private Ast(final String fn, final Grammar g) {
            super(fn, g);
        }
    }

    public int getErrorCnt() {
        return m_errorCnt + MessageMgr.getErrorCnt();
    }

    public boolean hasErrors() {
        return (0 < getErrorCnt());
    }

    public List<Ast> getAsts() {
        return m_asts;
    }

    private void parse(final String fname) throws Exception {
        InputStream fis = new InputStream(fname);
        info("LG-FILE-1", fname);
        CharBuffer cbuf = fis.newCharBuffer();
        CharBufState.create(cbuf, true);
        laol.parser.apfe.Grammar gram = new laol.parser.apfe.Grammar();
        Acceptor acc = gram.accept();
        boolean ok = (null != acc) && CharBufState.getTheOne().isEOF();
        if (ok) {
            Grammar g = new Grammar(gram);
            m_asts.add(new Ast(fname, g));
        } else {
            ParseError.printTopMessage();
            m_errorCnt++;
        }
    }

    private final List<Ast> m_asts = new LinkedList<>();
    private int m_errorCnt = 0;
}
