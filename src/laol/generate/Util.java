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
import java.util.Objects;
import java.util.function.Supplier;
import java.util.function.Function;

/**
 * Utilities for target agnostic generation.
 * 
 * @author kpfalzer
 */
public class Util {

    /**
     * Exception to indicate early termination, with all relevant
     * messaging done already.
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
     * Generate non-null value.
     * @param <T> value type of check.
     * @param <R> return value type.
     * @param check check for non-null.
     * @param onNonNull apply function to check if check != null.
     * @param onNull produce value if check is null.
     * @return value of type R.
     */
    public static <T,R> R getNonNullValue(T check, Function<T,R> onNonNull, Supplier<R> onNull) {
        return Objects.nonNull(check) ? onNonNull.apply(check) : onNull.get();
    }
}
