
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class UnaryOp extends Acceptor {

    public UnaryOp() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new PrioritizedChoice(new PrioritizedChoice.Choices() {
@Override
public Acceptor getChoice(int ix) {
Acceptor acc = null;
switch (ix) {
case 0:
acc = new TILDE() ;
break;
case 1:
acc = new EXCL() ;
break;
case 2:
acc = new PLUS() ;
break;
case 3:
acc = new MINUS() ;
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
    public UnaryOp create() {
        return new UnaryOp();
    }

 


}


