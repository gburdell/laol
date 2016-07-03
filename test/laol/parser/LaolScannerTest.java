package laol.parser;

import apfe.runtimev2.Scanner;
import gblib.*;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author gburdell
 */
public class LaolScannerTest {

    public LaolScannerTest() {

    }

    @Test
    public void testSlurp() {
        final String fns[] = {
            "test/data/t1.txt",
            "test/data/collections.laol"
        };
        try {
            for (String fn : fns) {
                Scanner scn = new LaolScanner(fn);
                int tokCnt = scn.slurp();
                System.out.printf("%s: %d tokens\n", fn, tokCnt);
            }
        } catch (Exception ex) {
            Util.abnormalExit(ex);
        }
    }

}
