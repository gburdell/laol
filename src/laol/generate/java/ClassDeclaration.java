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
package laol.generate.java;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Collection;
import static java.util.Objects.nonNull;
import laol.ast.Item;
import laol.generate.ClassDeclaration.Member;
import laol.generate.Symbol;
import laol.generate.Util;
import static laol.generate.java.Util.getAccessModifier;
import static laol.generate.java.Util.getExtends;

/**
 *
 * @author gburdell
 */
public class ClassDeclaration extends laol.generate.ClassDeclaration {

    public static void process(final laol.ast.ClassDeclaration item, final Context ctx) throws FileNotFoundException, Util.EarlyTermination {
        final ClassDeclaration cdecl = new ClassDeclaration(item, ctx);
        cdecl.process();
    }

    private void process() throws FileNotFoundException, Util.EarlyTermination {
        //add class to current scope
        m_clsSym = new Symbol.Class(m_decl);
        m_ctx.getScope().add(m_clsSym);
        //create new context
        final boolean isTopClass = !m_ctx.hasParent();
        //NOTE: we switch to new/our context
        m_ctx = new Context(m_ctx);
        if (isTopClass) {
            m_ctx
                    .createOS(m_clsSym.getName() + ".java")
                    .header(m_decl)
                    .packageAndImports();
        }
        //print declaration
        os().printf("%s class %s %s {\n",
                getAccessModifier(m_decl),
                m_clsSym.getName(),
                getExtends(m_decl)
        );
        declareMembersAndAccessors();
        //todo
        os().println("}");
    }

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

    private ClassDeclaration(final laol.ast.ClassDeclaration decl, final Context ctx) {
        m_decl = decl;
        m_ctx = ctx;
    }

    private PrintStream os() {
        return m_ctx.os();
    }

    private Symbol m_clsSym;
    private final laol.ast.ClassDeclaration m_decl;
    private Context m_ctx;
}
