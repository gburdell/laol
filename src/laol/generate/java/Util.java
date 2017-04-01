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
package laol.generate.java;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import laol.ast.AccessModifier;
import laol.ast.ClassDeclaration;
import laol.ast.ClassExtends;
import laol.ast.ScopedName;
import static laol.generate.Util.createDirectory;

/**
 * Utilities for Java target generation.
 *
 * @author kpfalzer
 */
public class Util {

    /**
     * Get output directory based on root and package name.
     *
     * @param rootDir root directory.
     * @param pkgName package name (or null).
     * @return output directory.
     * @throws laol.generate.Util.EarlyTermination
     */
    public static Path getOutputDir(final String rootDir, String pkgName) throws laol.generate.Util.EarlyTermination {
        Path outdir = Paths.get(rootDir, pkgName.replace(".", "/"));
        createDirectory(outdir);
        return outdir;
    }

}
