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
package laol.generate;

import gblib.MessageMgr;
import static gblib.Util.error;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import laol.ast.etc.ISymbol;
import laol.ast.etc.SymbolTable;

/**
 * Utilities for target agnostic generation.
 *
 * @author kpfalzer
 */
public class Util {

    public static boolean hasErrors() {
        return (0 < getErrorCount());
    }

    public static int getErrorCount() {
        return MessageMgr.getErrorCnt();
    }

    /**
     * Exception to indicate early termination, with all relevant messaging done
     * already.
     */
    public static class EarlyTermination extends Exception {

        @Override
        public String getMessage() {
            return "Terminate early due to previous error(s)";
        }

    }

    /**
     * Create directory if not already exists.
     *
     * @param dirName directory to create.
     * @throws laol.generate.Util.EarlyTermination
     */
    public static void createDirectory(final String dirName) throws EarlyTermination {
        Path outdir = Paths.get(dirName);
        createDirectory(outdir);
    }

    /**
     * Create directory if not already exists.
     *
     * @param outdir directory to create.
     * @throws laol.generate.Util.EarlyTermination
     */
    public static void createDirectory(final Path outdir) throws EarlyTermination {
        if (!Files.exists(outdir)) {
            try {
                Files.createDirectories(outdir);
            } catch (IOException ex) {
                error("LG-DIR", outdir);
                throw new EarlyTermination();
            }
        }
    }

    public static void handleException(Exception ex) {
        error("LG-EXCPT");
        ex.printStackTrace(MessageMgr.getOstrm('E'));
        System.exit(3);
    }

    /**
     * Resolve import name against SymbolTable by package name.
     *
     * @param stabByPackage map of SymbolTable by package name.
     * @param importNm name to import (including package.* style)
     * @return SymbolTable with result; null if not found.
     */
    public static SymbolTable resolve(Map<String, SymbolTable> stabByPackage, final String importNm) {
        SymbolTable stab = null;
        Predicate<ISymbol> takeSymbol;
        final int n = importNm.lastIndexOf('.');
        String pkgName = importNm.substring(0, n);
        if (stabByPackage.containsKey(pkgName)) {
            if (importNm.endsWith(".*")) {
                takeSymbol = s -> {
                    return true;
                };
            } else {
                takeSymbol = (ISymbol s) -> {
                    return s.getName().equals(importNm.substring(n + 1));
                };
            }
            stab = new SymbolTable();
            stab.insert(stabByPackage.get(pkgName), takeSymbol);
        }
        return stab;
    }
}
