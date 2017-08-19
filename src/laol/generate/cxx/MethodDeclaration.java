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
package laol.generate.cxx;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Objects;
import static java.util.Objects.nonNull;
import static laol.generate.cxx.ClassDeclaration.METHOD_SIGNATURE;
import static laol.generate.cxx.ClassDeclaration.METHOD_SIGNATURE_NO_CONST;

/**
 * Generate body of method.
 *
 * @author kpfalzer
 */
public class MethodDeclaration {

    /**
     * Generate method definition.
     *
     * @param item method declaration with body.
     * @param ctx context. getClassName() used to find class/interface owner
     * name,
     */
    public static void process(final laol.ast.MethodDeclaration item, final Context ctx) {
        final MethodDeclaration mdecl = new MethodDeclaration(item, ctx);
        mdecl.process();
    }

    /**
     * Generate definition unless abstract or constructor method.
     */
    private void process() {
        if (m_decl.isAbstract() || m_methodName.equals(m_className)) {
            return;
        }
        final boolean isMemberMethod = nonNull(m_className);
        cxx().printf("\nRef %s %s", 
                (isMemberMethod ? (m_className + "::") : "") + m_methodName, 
                (isMemberMethod ? METHOD_SIGNATURE : METHOD_SIGNATURE_NO_CONST));
        MethodBody.process(m_decl, m_ctx);
    }

    private MethodDeclaration(final laol.ast.MethodDeclaration decl, final Context ctx) {
        m_decl = decl;
        m_ctx = ctx;
        m_className = m_ctx.getClassName();
        m_methodName = m_decl.getName().toString();
    }

    private PrintStream cxx() {
        return m_ctx.cxx();
    }

    private final laol.ast.MethodDeclaration m_decl;
    private final Context m_ctx;
    private final String m_className; //could be null
    private final String m_methodName;
}
