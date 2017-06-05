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

import gblib.AtomicBoolean;
import java.util.List;
import laol.ast.ParamEle;
import laol.ast.UnnamedParam;

/**
 *
 * @author gburdell
 */
public class ParamExpressionList {
    public static void process(final laol.ast.ParamExpressionList items, final Context ctx) {
        final List<ParamEle> eles = items.getEles();
        AtomicBoolean doComma = new AtomicBoolean(false);
        eles.stream()
                .map(ele -> ele.getEle())
                .forEachOrdered(item -> {
            assert(item instanceof UnnamedParam); //todo
            final UnnamedParam parm = gblib.Util.downCast(item);
            if (doComma.get()) {
                ctx.os().print(", ");
            } else {
                doComma.set(true);
            }
            Expression.process(parm, ctx);
        });
    }
}
