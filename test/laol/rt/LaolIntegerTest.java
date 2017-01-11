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
        int ival = 1;
        LaolInteger instance = new LaolInteger(ival);
        instance.setMutable();
        LaolInteger expResult = new LaolInteger(ival + 1);
        LaolInteger result = (LaolInteger)instance.preIncrOp();
        assertEquals(expResult, instance);
    }

    @Test
    public void testPostIncrOp() {
        int ival = 99;
        LaolInteger instance = new LaolInteger(ival);
        instance.setMutable();
        LaolInteger expResult = new LaolInteger(ival);
        LaolInteger result = (LaolInteger)instance.postIncrOp();
        assertEquals(expResult, result);
        expResult = new LaolInteger(ival + 1);
        assertEquals(expResult, instance);
    }

    @Test
    public void testPreDecrOp() {
        int ival = 1;
        LaolInteger instance = new LaolInteger(ival);
        instance.setMutable();
        LaolInteger expResult = new LaolInteger(ival - 1);
        LaolInteger result = (LaolInteger)instance.preDecrOp();
        assertEquals(expResult, result);
    }

    @Test
    public void testPostDecrOp() {
        int ival = 99;
        LaolInteger instance = new LaolInteger(ival);
        instance.setMutable();
        LaolInteger expResult = new LaolInteger(ival);
        LaolInteger result = (LaolInteger)instance.postDecrOp();
        assertEquals(expResult, result);
        expResult = new LaolInteger(ival - 1);
        assertEquals(expResult, instance);
    }

}
