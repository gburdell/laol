
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class MethodNameOp extends Acceptor {

    public MethodNameOp() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new PrioritizedChoice(new PrioritizedChoice.Choices() {
@Override
public Acceptor getChoice(int ix) {
Acceptor acc = null;
switch (ix) {
case 0:
acc = new UnaryOp() ;
break;
case 1:
acc = new BinaryOp() ;
break;
case 2:
acc = new QMARK() ;
break;
case 3:
acc = new EXCL() ;
break;
case 4:
acc = new AssignmentOp() ;
break;
case 5:
acc = new PLUS2() ;
break;
case 6:
acc = new MINUS2() ;
break;
case 7:
acc = new CharSeq("++_") ;
break;
case 8:
acc = new CharSeq("--_") ;
break;
}
return acc;
}
}) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public MethodNameOp create() {
        return new MethodNameOp();
    }

 


}


