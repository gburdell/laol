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
import java.util.Objects;
import laol.ast.AccessModifier;
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

    private void process() throws FileNotFoundException, Util.EarlyTermination {
        m_ctx
                .createOS(m_decl.getName() + ".java")
                .header(m_decl)
                .packageAndImports();
        //print declaration
        os().printf("%s class %s extends LaolBase %s {\n",
                AccessModifier.toString(m_decl.getModifiers()),
                m_decl.getName(),
                getExtends()
        );
        //todo declareMembersAndAccessors();
        //todo
        os().println("}");
    }

    private String getExtends() {
        return (Objects.isNull(m_decl.getExtends()))
                ? ""
                : "implements " + m_decl.getExtends().toString();
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
    private PrintStream os() {
        return m_ctx.os();
    }

    private ClassDeclaration(final laol.ast.ClassDeclaration decl, final Context ctx) {
        m_decl = decl;
        m_ctx = ctx;
    }

    private final laol.ast.ClassDeclaration m_decl;
    private Context m_ctx;
}
