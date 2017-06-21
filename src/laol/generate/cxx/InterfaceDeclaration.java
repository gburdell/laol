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
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import static java.util.Objects.nonNull;
import java.util.stream.Collectors;
import laol.ast.MethodDeclaration;
import laol.ast.MethodType;
import laol.generate.Util;
import static laol.generate.cxx.ClassDeclaration.METHOD_SIGNATURE;

/**
 *
 * @author gburdell
 */
public class InterfaceDeclaration {

    public static void process(final laol.ast.InterfaceDeclaration item, final Context ctx) throws FileNotFoundException, Util.EarlyTermination {
        final InterfaceDeclaration idecl = new InterfaceDeclaration(item, ctx);
        idecl.process();
    }

    public InterfaceDeclaration(final laol.ast.InterfaceDeclaration decl, final Context ctx) {
        m_decl = decl;
        m_ctx = ctx;
        m_clsName = m_decl.getIdent().toString();
        m_helper = new ClassInterfaceDeclaration(m_clsName, m_decl.getBody(), hxx(), cxx());
        m_ctx.setClassName(m_clsName);
    }

    private InterfaceDeclaration declare() {
        StringBuilder ext = new StringBuilder(": public virtual Laol");
        if (nonNull(m_decl.getImplements())) {
            m_decl.getImplements().getNames()
                    .forEach(name -> ext.append(", ").append(name.toString()));
        }
        hxx().format("class %s %s {\npublic:\n", m_clsName, ext);
        return this;
    }

    private InterfaceDeclaration hereMethods() {
        m_helper.hereMethods();
        return this;
    }

    private InterfaceDeclaration emptyConstructor() {
        hxx().format("protected:\n%s(){/*empty*/}\nvirtual ~%s() = 0;\n",
                m_clsName, m_clsName);
        return this;
    }

    private InterfaceDeclaration methodLookup() {
        m_helper.methodLookup(m_decl.getBaseNames());
        return this;
    }

    private InterfaceDeclaration close() {
        m_helper.close();
        return this;
    }

    private PrintStream hxx() {
        return m_ctx.hxx();
    }

    private PrintStream cxx() {
        return m_ctx.cxx();
    }

    private void process() {
        declare()
                .hereMethods()
                .emptyConstructor()
                .methodLookup()
                .methodDefinitions()
                .close();
        m_ctx.clrClassName();
    }

    private InterfaceDeclaration methodDefinitions() {
        m_ctx.setCurrStream(Context.EType.eCxx);
        m_helper.getMethodDeclarations()
                .forEachOrdered(methodDecl -> laol.generate.cxx.MethodDeclaration.process(methodDecl, m_ctx));
        return this;
    }

    private final laol.ast.InterfaceDeclaration m_decl;
    private final Context m_ctx;
    private final String m_clsName;
    private final ClassInterfaceDeclaration m_helper;
}
