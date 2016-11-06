/*
 * The MIT License
 *
 * Copyright 2016 gburdell.
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
package laol.parser;

import gblib.File;
import java.util.LinkedList;
import java.util.List;

/**
 * Manage require statements.
 * @author gburdell
 */
public class Require {
    public static void addSearchPath(String... paths) {
        for (String path : paths) {
            stSearchPaths.add(File.getCanonicalPath(path));
        }
    }
    
    public File search(final String fileName) {
        for (String dir : stSearchPaths) {
            File fullPath = new File(dir, fileName);
            if (fullPath.exists()) {
                return fullPath;
            }
        }
        return null;
    }
    
    private static final List<String> stSearchPaths = new LinkedList<>();
    
    static {
        //add from env
        final String paths = System.getenv("LAOL_LIBPATH");
        if (null != paths) {
            addSearchPath(paths.split(":"));
        }
        //add our cwd
        addSearchPath(".");
    }
}
