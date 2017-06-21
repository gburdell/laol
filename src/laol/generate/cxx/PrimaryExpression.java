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

import laol.ast.AString;
import laol.ast.Item;
import laol.ast.ScopedName;
import static laol.generate.cxx.Util.print;
import static laol.generate.cxx.Util.printSymbol;

/**
 *
 * @author kpfalzer
 */
public class PrimaryExpression {

    public static void process(final laol.ast.PrimaryExpression item, final Context ctx) {
        if (item.isNull()) {
            print(ctx, "NULLOBJ");
        } else {
            Item expr = item.getExpr();
            if (item.isKeyword()) {
                print(ctx, expr);
            } else if (item.isScopedName()) {
                print(ctx, gblib.Util.<ScopedName>downCast(expr));
            } else if (item.isSymbol()) {
                printSymbol(ctx, gblib.Util.downCast(expr));
            } else if (item.isString()) {
                print(ctx, String.format("\"%s\"", gblib.Util.<AString>downCast(expr).toString()));
            } else if (item.isParenthesizedExpr()) {
                print(ctx, "(");
                Expression.process(gblib.Util.<laol.ast.Expression>downCast(expr), ctx);
                print(ctx, ")");
            } else {
                Generate.callProcess(expr, ctx);
            }
        }
    }

 }
