/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package laol.rt;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kpfalzer
 */
public class LaolIntegerTest {

    @Test
    public void testPreIncrOp() {
        System.out.println("preIncrOp");
        int ival = 1;
        LaolInteger instance = new LaolInteger(ival);
        instance.setMutable();
        LaolInteger expResult = new LaolInteger(ival + 1);
        LaolInteger result = instance.preIncrOp();
        assertEquals(expResult, result);
    }

    @Test
    public void testPostIncrOp() {
        System.out.println("postIncrOp");
        int ival = 99;
        LaolInteger instance = new LaolInteger(ival);
        instance.setMutable();
        LaolInteger expResult = new LaolInteger(ival);
        LaolInteger result = instance.postIncrOp();
        assertEquals(expResult, result);
        expResult = new LaolInteger(ival + 1);
        assertEquals(expResult, instance);
    }

    @Test
    public void testPreDecrOp() {
        System.out.println("preDecrOp");
        int ival = 1;
        LaolInteger instance = new LaolInteger(ival);
        instance.setMutable();
        LaolInteger expResult = new LaolInteger(ival - 1);
        LaolInteger result = instance.preDecrOp();
        assertEquals(expResult, result);
    }

    @Test
    public void testPostDecrOp() {
        System.out.println("postDecrOp");
        int ival = 99;
        LaolInteger instance = new LaolInteger(ival);
        instance.setMutable();
        LaolInteger expResult = new LaolInteger(ival);
        LaolInteger result = instance.postDecrOp();
        assertEquals(expResult, result);
        expResult = new LaolInteger(ival - 1);
        assertEquals(expResult, instance);
    }

    @Test
    public void testCallPublicMethod() {
        System.out.println("callPublicMethod");
        int ival = 1234;
        LaolInteger instance = (new LaolInteger(ival)).setMutable();
        LaolObject rval = instance.callPublicMethod("preDecrOp");
        LaolInteger expect = new LaolInteger(--ival);
        assertEquals(expect, rval);
        rval = rval.callPublicMethod("preDecrOp");
        expect = new LaolInteger(--ival);
        assertEquals(expect, rval);
    }
}
