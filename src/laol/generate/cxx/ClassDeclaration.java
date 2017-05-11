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

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import static java.util.Objects.nonNull;
import java.util.stream.Collectors;
import laol.ast.AccessModifier;
import laol.ast.ClassExtends;
import laol.ast.MethodDeclaration;
import laol.ast.MethodType;
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
                .hereMethods()
                .methodByName()
                //todo
                .close();
    }

    private ClassDeclaration hereMethods() {
        m_decl.getBody().getStatements().forEach(statement -> {
            if (statement.getStmt() instanceof laol.ast.MethodDeclaration) {
                final MethodDeclaration decl = MethodDeclaration.class.cast(statement.getStmt());
                final MethodType type = decl.getType();
                if (type.isDefaultDef() || type.isDef()) {
                    m_methods.add(decl.getName().asScopedName().toString());
                }
            }
        });
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
        hxx().println("}; //" + m_clsName);
        return this;
    }

    /**
     * Declare methodByName related.
     *
     * @return this.
     */
    private ClassDeclaration methodByName() {
        hxx().println("protected:\n"
                + "virtual const METHOD_BY_NAME& getMethodByName() override;\n"
                + "private:\n"
                + "static METHOD_BY_NAME stMethodByName;");
        cxx()
                .format("//static\nLaol::METHOD_BY_NAME %s::stMethodByName;\n", m_clsName)
                .format("const Laol::METHOD_BY_NAME&\n%s::getMethodByName() {\n", m_clsName)
                .format("if (stMethodByName.empty()) {\nstMethodByName = Laol::join(stMethodByName,\n")
                .format("Laol::getMethodByName()");
        if (nonNull(m_decl.getExtends())) {
            m_decl.getExtends().getAll().forEach(name -> {
                cxx().format(",\n%s::getMethodbyName()", name.toString());
            });
        }
        cxx().println(",\nMETHOD_BY_NAME({");
        cxx().print(String.join(",\n", m_methods.stream().map(ele -> {
            return String.format("{\"%s\", reinterpret_cast<TPMethod> (&%s::%s)}",
                    ele, m_clsName, ele);
        }).collect(Collectors.toList())));
        cxx().println("}));}\nreturn stMethodByName;\n}");
        return this;
    }

    /*todo
    private void declareMembersAndAccessors() {
        Collection<Member> members = getMembers(m_decl);
        os().println("//{{ Member declarations\n//** All are mutable (for now)!");
        for (Member mbr : members) {
            os()
                    .printf("\n// %s\n", mbr.getWhere())
                    .printf("private ILaol m_%s ;\n", mbr.getName())
                    .printf("%s ILaol %s() {return m_%s;}\n",
                            mbr.getAccess().toString(),
                            mbr.getName(),
                            mbr.getName())
                    .printf("%s ILaol %s(ILaol _val) {m_%s = _val; return _val;}\n",
                            mbr.getAccess().toString(),
                            mbr.getName(),
                            mbr.getName());
        }
        os().println("//Member declarations }}");
    }
     */
    private ClassDeclaration(final laol.ast.ClassDeclaration decl, final Context ctx) {
        m_decl = decl;
        m_ctx = ctx;
        m_clsName = m_decl.getIdent().toString();
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

    /**
     * Methods defined here or declared/abstract
     */
    private final List<String> m_methods = new LinkedList<>();
}
