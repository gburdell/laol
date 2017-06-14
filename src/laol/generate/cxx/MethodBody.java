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

import java.io.PrintStream;
import java.util.List;
import laol.ast.IStatements;

/**
 *
 * @author gburdell
 */
public class MethodBody {
    public static void process(final laol.ast.MethodBody item, final Context ctx) {
        (new MethodBody(item, ctx)).process();
    }
    
    public static void processStmts(IStatements stmts, final Context ctx) {
        ctx.cxx().println("{");
        ctx.cxx().println("//MethodBody: todo");
        ctx.cxx().println("}");
    }
        
    private void process() {
        processStmts(m_body, m_ctx);
    }
    
    private MethodBody(final laol.ast.MethodBody body, final Context ctx) {
        m_ctx = ctx;
        m_body = body;
    }
    
    private final laol.ast.MethodBody m_body;
    private final Context m_ctx; 
}
