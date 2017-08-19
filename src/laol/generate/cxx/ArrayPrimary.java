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

import laol.ast.ArrayPrimary.EType;
import static laol.generate.cxx.Util.print;
import static laol.generate.cxx.Util.printSymbol;

/**
 *
 * @author kpfalzer
 */
public class ArrayPrimary {

    public static void process(final laol.ast.ArrayPrimary prim, final Context ctx) {
        //generate list, as in: {v1, v2, ...}
        final EType type = prim.getType();
        print(ctx, "(new Array(");
        if (EType.eUnknown != type) {
            print(ctx, "{");
            if (EType.eExpressions == type) {
                Util.processAsCSV(prim.asExprList().getExpressions(), ctx,
                        (laol.ast.Expression expr) -> {
                            Expression.process(expr, ctx);
                        });
            } else {
                Util.processAsCSV(prim.asIdents(), ctx,
                        (laol.ast.Ident id) -> {
                            if (EType.eSymbols == type) {
                                printSymbol(ctx, id);
                            } else {
                               print(ctx, String.format("\"%s\"", id.toString())); 
                            }
                        });
            }
            print(ctx, "}");
        }
        print(ctx, "))");
    }
}
