
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class PrimaryExpression extends Acceptor {

    public PrimaryExpression() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new PrioritizedChoice(new PrioritizedChoice.Choices() {
@Override
public Acceptor getChoice(int ix) {
Acceptor acc = null;
switch (ix) {
case 0:
acc = new Sequence(new KNIL(),
new Repetition(new QMARK(), Repetition.ERepeat.eOptional)) ;
break;
case 1:
acc = new KNEW() ;
break;
case 2:
acc = new KFILE() ;
break;
case 3:
acc = new KTARGET() ;
break;
case 4:
acc = new KTRUE() ;
break;
case 5:
acc = new KFALSE() ;
break;
case 6:
acc = new KTHIS() ;
break;
case 7:
acc = new KSUPER() ;
break;
case 8:
acc = new HereDoc() ;
break;
case 9:
acc = new Sequence(new VariableName(),
new Repetition(new PrioritizedChoice(new PrioritizedChoice.Choices() {
@Override
public Acceptor getChoice(int ix) {
Acceptor acc = null;
switch (ix) {
case 0:
acc = new QMARK() ;
break;
case 1:
acc = new EXCL() ;
break;
}
return acc;
}
}), Repetition.ERepeat.eOptional)) ;
break;
case 10:
acc = new STRING() ;
break;
case 11:
acc = new SYMBOL() ;
break;
case 12:
acc = new Number() ;
break;
case 13:
acc = new HashPrimary() ;
break;
case 14:
acc = new ArrayPrimary() ;
break;
case 15:
acc = new RegexpPrimary() ;
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
    public PrimaryExpression create() {
        return new PrimaryExpression();
    }

 


}


