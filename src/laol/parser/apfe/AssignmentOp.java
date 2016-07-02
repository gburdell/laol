
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class AssignmentOp extends Acceptor {

    public AssignmentOp() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new PrioritizedChoice(new PrioritizedChoice.Choices() {
@Override
public Acceptor getChoice(int ix) {
Acceptor acc = null;
switch (ix) {
case 0:
acc = new EQ() ;
break;
case 1:
acc = new LT2EQ() ;
break;
case 2:
acc = new GT2EQ() ;
break;
case 3:
acc = new ANDEQ() ;
break;
case 4:
acc = new OREQ() ;
break;
case 5:
acc = new STAREQ() ;
break;
case 6:
acc = new MINUSEQ() ;
break;
case 7:
acc = new PLUSEQ() ;
break;
case 8:
acc = new DIVEQ() ;
break;
case 9:
acc = new PCNTEQ() ;
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
    public AssignmentOp create() {
        return new AssignmentOp();
    }

 


}


