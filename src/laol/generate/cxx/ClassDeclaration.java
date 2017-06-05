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
import laol.ast.BaseClassInitializer;
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
public class ClassDeclaration {

    public static void process(final laol.ast.ClassDeclaration item, final Context ctx) throws FileNotFoundException, Util.EarlyTermination {
        final ClassDeclaration cdecl = new ClassDeclaration(item, ctx);
        cdecl.process();
    }

    private void process() {
        declare()
                .constructorDecl()
                .memberAccessors()
                .hereMethods()
                .methodByName()
                .constructorDefn()
                .close();
    }

    public static final String METHOD_SIGNATURE = "(const LaolObj& self, const LaolObj& args) const";

    private ClassDeclaration memberAccessors() {
        if (!m_members.isEmpty()) {
            hxx().println("\n//accessors {");
        }
        m_members.forEach(parm -> {
            hxx().format("Ref %s %s {return m_%s;}\n", parm, METHOD_SIGNATURE, parm);
            m_helper.getMethods().add(parm);
        });
        if (!m_members.isEmpty()) {
            hxx().println("\nprivate:");
            m_members.forEach(parm -> hxx().format("LaolObj m_%s;\n", parm));
            hxx().println("\n//accessors }\n\npublic:");
        }
        return this;
    }

    private ClassDeclaration constructorDecl() {
        m_decl.getConstructorParmDecls().forEach(parms -> {
            final List<String> constructorParms = getNames(parms);
            m_members.addAll(constructorParms);
            hxx()
                    .format("explicit %s(", m_clsName)
                    .format("%s);\n", getCxxDeclNames(constructorParms));
        });
        hxx()
                .format("NO_COPY_CONSTRUCTORS(%s);\n", m_clsName)
                .format("virtual ~%s();\n", m_clsName);
        return this;
    }

    private ClassDeclaration constructorDefn() {
        m_decl.getConstructorParmDecls().forEach(parms -> {
            final List<String> constructorParms = getNames(parms);
            cxx()
                    .format("%s::%s(", m_clsName, m_clsName)
                    .format("%s)\n", getCxxDeclNames(constructorParms));
            String colon = ": ";
            if (!m_decl.getBaseInitializers().isEmpty()) {
                cxx().println(":");
                m_ctx.setCurrStream(Context.EType.eCxx);
                boolean doComma = false;
                for (BaseClassInitializer init : m_decl.getBaseInitializers()) {
                    if (doComma) {
                        cxx().println(",");
                    } else {
                        doComma = true;
                    }
                    cxx().format("%s(", init.getBaseName());
                    ParamExpressionList.process(init.getInitializer(), m_ctx);
                    cxx().print(")");
                    colon = ",\n";
                }
            }
            if (!constructorParms.isEmpty()) {
                cxx()
                        .format("%s%s\n", colon,
                                String.join(",\n",
                                        constructorParms
                                                .stream()
                                                .map(parm -> String.format("m_%s(%s)", parm, parm))
                                                .collect(Collectors.toList())));
            }
            cxx().println("{/*TODO*/}");
        });

        return this;
    }

    private ClassDeclaration hereMethods() {
        m_helper.hereMethods();
        return this;
    }

    private ClassDeclaration declare() {
        StringBuilder ext = new StringBuilder(": public virtual Laol");
        getBaseNames().forEach(ext.append(", ")::append);
        hxx().format("class %s %s {\npublic:\n", m_clsName, ext);
        return this;
    }

    private ClassDeclaration close() {
        m_helper.close();
        return this;
    }

    /**
     * Declare methodByName related.
     *
     * @return this.
     */
    private ClassDeclaration methodByName() {
        m_helper.methodByName(getBaseNames());
        return this;
    }

    private ClassDeclaration(final laol.ast.ClassDeclaration decl, final Context ctx) {
        m_decl = decl;
        m_ctx = ctx;
        m_clsName = m_decl.getIdent().toString();
        m_helper = new ClassInterfaceDeclaration(m_clsName, m_decl.getBody(), hxx(), cxx());
    }

    private PrintStream hxx() {
        return m_ctx.hxx();
    }

    private PrintStream cxx() {
        return m_ctx.cxx();
    }

    private List<String> getBaseNames() {
        return m_decl.getBaseNames();
    }
    
    private final laol.ast.ClassDeclaration m_decl;
    private final Context m_ctx;
    private final String m_clsName;
    private final ClassInterfaceDeclaration m_helper;

    /**
     * Constructor parameters w/ member attribute.
     */
    private final Set<String> m_members = new HashSet<>();
}
