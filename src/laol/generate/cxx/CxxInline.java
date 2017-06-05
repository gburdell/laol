/*
 * The MIT License
 *
 * Copyright 2017 gburdell.
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
package laol.generate.cxx;

import gblib.Pair;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import static java.util.Objects.nonNull;
import java.util.Set;
import java.util.stream.Collectors;
import laol.ast.MethodDeclaration;
import laol.ast.MethodParamDeclEle;
import laol.ast.MethodType;
import laol.ast.ParamName;
import laol.generate.Util;
import static laol.generate.cxx.Util.getNames;
import static laol.generate.cxx.Util.getCxxDeclNames;

/**
 *
 * @author gburdell
 */
public class CxxInline {

    public static void process(final laol.ast.CxxInline item, final Context ctx) throws FileNotFoundException, Util.EarlyTermination {
        final CxxInline cdecl = new CxxInline(item, ctx);
        cdecl.process();
    }

    private void process() {
        (m_decl.isHxx() ? hxx() : cxx()).println(m_decl.toString());
    }

    private CxxInline(final laol.ast.CxxInline decl, final Context ctx) {
        m_decl = decl;
        m_ctx = ctx;
    }

    private PrintStream hxx() {
        return m_ctx.hxx();
    }

    private PrintStream cxx() {
        return m_ctx.cxx();
    }

    private final laol.ast.CxxInline m_decl;
    private final Context m_ctx;
}
