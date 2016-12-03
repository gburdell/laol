package laol.rt;

import java.lang.invoke.MethodHandle;
import static java.lang.invoke.MethodHandles.publicLookup;
import java.lang.invoke.MethodType;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kpfalzer
 */
public class Test2 {
    public static class B {
        
    }
    
    public static class C extends B {
        public B f1(int a) {
            m_val += a;
            return this;
        }
        
        private int m_val = 0;
    }
    
    
    public static void main(String argv[]) {
        C c = new C();
        B b = c.f1(3);
        b = c;
        
        /** We can't compile this
        B x = b.f1(5);
        * We need to do dynamic dispatch...
        * b.getClass().getMethods()...
        */
        assert (b instanceof C);
        
        try {
            MethodHandle f1 = publicLookup().findVirtual(b.getClass(), "f1", MethodType.methodType(B.class, int.class));
            B b2 = (B)f1.invokeExact((C)b, 123);
            //
            Object b3 = f1.invoke(b, 123);
            boolean debug = true;
        } catch (NoSuchMethodException | IllegalAccessException ex) {
            Logger.getLogger(Test2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Throwable ex) {
            Logger.getLogger(Test2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
