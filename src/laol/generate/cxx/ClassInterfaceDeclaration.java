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

import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import static java.util.Objects.nonNull;
import java.util.stream.Collectors;
import laol.ast.IStatements;
import laol.ast.MethodDeclaration;
import laol.ast.MethodType;
import laol.ast.ScopedName;
import static laol.generate.cxx.ClassDeclaration.METHOD_SIGNATURE;

/**
 *
 * @author kpfalzer
 */
public class ClassInterfaceDeclaration {

    public ClassInterfaceDeclaration(IStatements stmts, PrintStream hxx, PrintStream cxx) {
        m_stmts = stmts;
        m_hxx = hxx;
        m_cxx = cxx;
    }

    private PrintStream hxx() {
        return m_hxx;
    }

    private PrintStream cxx() {
        return m_cxx;
    }

    public void hereMethods() {
        m_stmts.getStatements()
                .stream()
                .filter(statement -> {
                    return statement.getStmt() instanceof laol.ast.MethodDeclaration;
                })
                .forEach(statement -> {
                    final MethodDeclaration decl = MethodDeclaration.class.cast(statement.getStmt());
                    final MethodType type = decl.getType();
                    final String name = decl.getName().toString();//asScopedName().toString();
                    if (type.isDefaultDef() || type.isDef()) {
                        m_methods.add(name);
                    }
                    hxx().format("virtual Ref %s %s", name, METHOD_SIGNATURE);
                    if (type.isOverrideDef()) {
                        hxx().print(" override");
                    } else if (decl.isAbstract()) {
                        hxx().print(" = 0");
                    }
                    hxx().println(";");
                });
    }

    public void methodByName(String clsName, List<ScopedName> names) {
        names = Objects.isNull(names) ? ScopedName.EMPTY_LIST : names;
        hxx().println("protected:\n"
                + "virtual const METHOD_BY_NAME& getMethodByName() override;\n"
                + "private:\n"
                + "static METHOD_BY_NAME stMethodByName;");
        
        cxx()
                .format("//static\nLaol::METHOD_BY_NAME %s::stMethodByName;\n", clsName)
                .format("const Laol::METHOD_BY_NAME&\n%s::getMethodByName() {\n", clsName)
                .format("if (stMethodByName.empty()) {\nstMethodByName = Laol::join(stMethodByName");
        names.forEach(name -> {
            cxx().format(",\n%s::getMethodByName()", name.toString());
        });
        cxx().println(",\nMETHOD_BY_NAME({");
        cxx().print(String.join(",\n", getMethods().stream().map(ele -> {
            return String.format("{\"%s\", reinterpret_cast<TPMethod> (&%s::%s)}",
                    ele, clsName, ele);
        }).collect(Collectors.toList())));
        cxx().println("}));}\nreturn stMethodByName;\n}");
    }

    public void close(String clsName) {
        hxx().println("}; //" + clsName);
    }

    public List<String> getMethods() {
        return m_methods;
    }

    private final IStatements m_stmts;
    private final PrintStream m_hxx, m_cxx;
    /**
     * Methods defined here or declared/abstract.
     */
    private final List<String> m_methods = new LinkedList<>();

}
