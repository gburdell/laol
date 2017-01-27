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

import gblib.Config;
import gblib.MessageMgr;
import gblib.Options;
import static gblib.Util.error;
import java.util.Collection;

/**
 *
 * @author kpfalzer
 */
public class Main {

    public static void main(final String argv[]) {
        final int status = process(argv);
        System.exit(status);
    }

    private static int process(final String argv[]) {
        int status = 1;
        if (1 > argv.length) {
            usage();
        } else {
            Collection<String> srcFiles = CMD_OPTIONS.process(argv);
            if (srcFiles.isEmpty()) {
                error("LG-NOSRC");
            } else if (checkOptions()) {
                Parse parse = new Parse(srcFiles);
                if (parse.hasErrors()) {
                    error("LG-EXIT", parse.getErrorCnt());
                    status = 2;
                } else {
                    //TODO: processing
                    status = 0;
                }
            }
        }
        return status;
    }

    private static boolean checkOptions() {
        //TODO: error() here on usage errors
        return true;
    }

    private static void usage() {
        System.err.printf("Usage: %s option* file.laol+\n%s",
                PROGNM, CMD_OPTIONS.getUsage());
    }

    public static final String PROGNM = "laol2j";

    private static final Config CONFIG = Config.create()
            .add(new String[]{
        "packageName" + " laol.java.user"
    });

    private static final Options CMD_OPTIONS = Options.create();

    static {
        String dflt = CONFIG.get("packageName").toString();
        CMD_OPTIONS.add(
                "-p|--package name",
                "Java package name " + defaultOpt(dflt),
                (opt) -> {
                    CONFIG.put("packageName", opt);
                }
        );
    }

    private static String defaultOpt(final String dflt) {
        return "(default: " + dflt + ")";
    }
    
    /**
     * These messages are added to those in apfe/messages.txt.
     */
    private static final String MESSAGES[] = new String[]{
        "LG-FILE-1 | %s: processing ...",
        "LG-FILE-2 | %s: could not read file (%s).",
        "LG-NOSRC | Missing source file(s).",
        "LG-EXIT | Cannot continue due to %d error(s)."
    };

    static {
        MessageMgr.addMessages(MESSAGES);
    }
}
