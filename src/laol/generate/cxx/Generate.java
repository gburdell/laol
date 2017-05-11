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

import gblib.Config;
import gblib.MessageMgr;
import static gblib.Util.abnormalExit;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import static java.util.Objects.nonNull;
import java.util.logging.Level;
import java.util.logging.Logger;
import laol.generate.Parse;
import laol.generate.Parse.Ast;
import laol.ast.Contents;
import laol.ast.Item;
import laol.generate.Util;

/**
 *
 * @author gburdell
 */
public class Generate extends laol.generate.Generate {

    public Generate(Collection<Parse.Ast> asts, Config config) {
        super(asts, config);
    }

    public static Generate create(Collection<Parse.Ast> asts, Config config) {
        return new Generate(asts, config);
    }

    @Override
    public int generate() {
        for (Ast ast : m_asts) {
            try {
                process(ast);
            } catch (Util.EarlyTermination ex) {
                break;
            }
        }
        return MessageMgr.getErrorCnt();
    }

    private void process(final Ast ast) throws Util.EarlyTermination {
        final Contents contents = ast.getGrammar().getContents();
        if (nonNull(contents)) {
            try (Context ctx = new Context(ast.getFileName(), contents, m_config)) {
                contents
                        .getFileItems()
                        .stream()
                        .forEachOrdered((fileItem) -> {
                            callProcess(fileItem.getStatement(), ctx);
                        });
            } catch (Util.EarlyTermination ex) {
                throw ex;
            } catch (Exception ex) {
                Util.handleException(ex);
            }
        }
    }

    private static final String THIS_PKG = Generate.class.getPackage().getName();

    /*package*/
    static void callProcess(final Item item, final Context ctx) {
        try {
            String itemName = item.getClass().getSimpleName();
            final String genClsName = THIS_PKG + "." + itemName;
            final Class genCls = Class.forName(genClsName);
            final Method genMethod = genCls.getMethod("process", item.getClass(), Context.class);
            genMethod.invoke(null, item, ctx);
        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Util.handleException(ex);
        }
    }
}
