
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class BinaryOp extends Acceptor {

    public BinaryOp() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new PrioritizedChoice(new PrioritizedChoice.Choices() {
@Override
public Acceptor getChoice(int ix) {
Acceptor acc = null;
switch (ix) {
case 0:
acc = new STAR() ;
break;
case 1:
acc = new DIV() ;
break;
case 2:
acc = new PCNT() ;
break;
case 3:
acc = new PLUS() ;
break;
case 4:
acc = new MINUS() ;
break;
case 5:
acc = new LT2() ;
break;
case 6:
acc = new GT2() ;
break;
case 7:
acc = new LT() ;
break;
case 8:
acc = new LTEQ() ;
break;
case 9:
acc = new GT() ;
break;
case 10:
acc = new GTEQ() ;
break;
case 11:
acc = new NEQ() ;
break;
case 12:
acc = new EQ2() ;
break;
case 13:
acc = new AND() ;
break;
case 14:
acc = new CARET() ;
break;
case 15:
acc = new OR() ;
break;
case 16:
acc = new AND2() ;
break;
case 17:
acc = new OR2() ;
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
    public BinaryOp create() {
        return new BinaryOp();
    }

 


}


