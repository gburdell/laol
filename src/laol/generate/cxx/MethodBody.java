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
import laol.ast.etc.IStatements;
import laol.ast.MethodParamDeclEle;
import laol.ast.ParamName;

/**
 *
 * @author gburdell
 */
public class MethodBody {

    public static void process(final laol.ast.MethodDeclaration item, final Context ctx) {
        (new MethodBody(item, ctx)).process();
    }

    /**
     * Process statements which are not method declarations.
     *
     * @param stmts candidate statements.
     * @param ctx context.
     * @param doEnclose true to enclose in {...}".
     */
    public static void processStmts(IStatements stmts, final Context ctx, boolean doEnclose) {
        final PrintStream os = ctx.cxx();
        if (doEnclose) {
            os.println("{");
        }
        stmts.getStatements()
                .stream()
                .map(stmt -> stmt.getStmt())
                .filter(stmt -> !(stmt instanceof laol.ast.MethodDeclaration))
                .forEachOrdered(item -> Generate.callProcess(item, ctx));
        if (doEnclose) {
            os.println("}");
        }
    }

    public static void processStmts(IStatements stmts, final Context ctx) {
        processStmts(stmts, ctx, true);
    }

    /**
     * Expand args into actual parameters.
     */
    private void expandArgs() {
        if (m_decl.hasParmDecl()) {
            List<MethodParamDeclEle> parms = m_decl.getParmDecl().getDecl();
            int i = 0;
            for (MethodParamDeclEle p : parms) {
                //todo: support anon func decl (lambda)
                assert (p.isNamed());
                final String name = p.getParamName().getName().toString();
                os().printf("LaolObj %s = args[%d];\n", name, i++);
            }
        }
    }

    private PrintStream os() {
        return m_ctx.os();
    }

    private void process() {
        os().println("{");
        expandArgs();
        processStmts(m_body, m_ctx, false);
        os().println("}");
    }

    private MethodBody(final laol.ast.MethodDeclaration decl, final Context ctx) {
        m_decl = decl;
        m_ctx = ctx;
        m_body = m_decl.getBody();
    }

    private final laol.ast.MethodBody m_body;
    private final laol.ast.MethodDeclaration m_decl;
    private final Context m_ctx;
}
