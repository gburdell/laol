/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package laol.rt;

import laol.rt.primitives.Integer;
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
        Integer instance = new Integer(ival);
        instance.setMutable();
        Integer expResult = new Integer(ival + 1);
        Integer result = (Integer)instance.preIncrOp();
        assertEquals(expResult, instance);
    }

    @Test
    public void testPostIncrOp() {
        int ival = 99;
        Integer instance = new Integer(ival);
        instance.setMutable();
        Integer expResult = new Integer(ival);
        Integer result = (Integer)instance.postIncrOp();
        assertEquals(expResult, result);
        expResult = new Integer(ival + 1);
        assertEquals(expResult, instance);
    }

    @Test
    public void testPreDecrOp() {
        int ival = 1;
        Integer instance = new Integer(ival);
        instance.setMutable();
        Integer expResult = new Integer(ival - 1);
        Integer result = (Integer)instance.preDecrOp();
        assertEquals(expResult, result);
    }

    @Test
    public void testPostDecrOp() {
        int ival = 99;
        Integer instance = new Integer(ival);
        instance.setMutable();
        Integer expResult = new Integer(ival);
        Integer result = (Integer)instance.postDecrOp();
        assertEquals(expResult, result);
        expResult = new Integer(ival - 1);
        assertEquals(expResult, instance);
    }

}
