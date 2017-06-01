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
import java.util.LinkedList;
import java.util.List;
import static java.util.Objects.nonNull;
import java.util.stream.Collectors;
import laol.ast.MethodDeclaration;
import laol.ast.MethodParamDeclEle;
import laol.ast.MethodType;
import laol.ast.ParamName;
import laol.generate.Util;

/**
 *
 * @author gburdell
 */
public class ClassDeclaration {

    public static void process(final laol.ast.ClassDeclaration item, final Context ctx) throws FileNotFoundException, Util.EarlyTermination {
        final ClassDeclaration cdecl = new ClassDeclaration(item, ctx);
        cdecl.process();
    }

    private void process() {
        declare()
                .constructors()
                .memberAccessors()
                .hereMethods()
                .methodByName()
                //todo
                .close();
    }

    public static final String METHOD_SIGNATURE = "(const LaolObj& self, const LaolObj& args) const";

    private ClassDeclaration memberAccessors() {
        if (!m_parmNames.isEmpty()) {
            hxx().println("\n//accessors");
        }
        m_parmNames.forEach(parm -> {
            if (parm.v2) {
                hxx().format("Ref %s %s final {return m_%s;}\n", parm.v1, METHOD_SIGNATURE, parm.v1);
                m_helper.getMethods().add(parm.v1);
            }
        });
        return this;
    }

    private ClassDeclaration constructors() {
        hxx().format("explicit class %s(", m_clsName);
        m_decl.getParms().forEach((MethodParamDeclEle parm) -> {
            final ParamName pname = parm.getParamName();
            m_parmNames.add(new Pair(pname.getName().toString(), pname.isMember()));
        });
        hxx().format("%s);\n",
                String.join(",\n", m_parmNames.stream().map(parm -> {
                    return String.format("const LaolObj& %s", parm.v1);
                }).collect(Collectors.toList())));
        hxx()
                .format("NO_COPY_CONSTRUCTORS(%s);\n", m_clsName)
                .format("virtual ~%s();\n", m_clsName);
        return this;
    }

    private ClassDeclaration hereMethods() {
        m_helper.hereMethods();
        return this;
    }

    private ClassDeclaration declare() {
        StringBuilder ext = new StringBuilder(": public virtual Laol");
        if (nonNull(m_decl.getExtends())) {
            m_decl.getExtends().getAll().forEach(name -> {
                ext.append(", ").append(name.toString());
            });
        }
        hxx().format("class %s %s {\npublic:\n", m_clsName, ext);
        return this;
    }

    private ClassDeclaration close() {
        m_helper.close(m_clsName);
        return this;
    }

    /**
     * Declare methodByName related.
     *
     * @return this.
     */
    private ClassDeclaration methodByName() {
        m_helper.methodByName(m_clsName, nonNull(m_decl.getExtends()) ? m_decl.getExtends().getAll() : null);
        return this;
    }

    private ClassDeclaration(final laol.ast.ClassDeclaration decl, final Context ctx) {
        m_decl = decl;
        m_ctx = ctx;
        m_clsName = m_decl.getIdent().toString();
        m_helper = new ClassInterfaceDeclaration(m_decl.getBody(), hxx(), cxx());
    }

    private PrintStream hxx() {
        return m_ctx.hxx();
    }

    private PrintStream cxx() {
        return m_ctx.cxx();
    }

    private final laol.ast.ClassDeclaration m_decl;
    private final Context m_ctx;
    private final String m_clsName;
    private final ClassInterfaceDeclaration m_helper;

    /**
     * Constructor parameters w/ member attribute.
     */
    private final List<Pair<String, Boolean>> m_parmNames = new LinkedList<>();
}
