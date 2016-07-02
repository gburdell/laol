
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class UnaryExpression extends Acceptor {

    public UnaryExpression() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new PrioritizedChoice(new PrioritizedChoice.Choices() {
@Override
public Acceptor getChoice(int ix) {
Acceptor acc = null;
switch (ix) {
case 0:
acc = new Sequence(new PrioritizedChoice(new PrioritizedChoice.Choices() {
@Override
public Acceptor getChoice(int ix) {
Acceptor acc = null;
switch (ix) {
case 0:
acc = new PLUS2() ;
break;
case 1:
acc = new MINUS2() ;
break;
case 2:
acc = new AND() ;
break;
case 3:
acc = new STAR() ;
break;
}
return acc;
}
}),
new UnaryExpression(),
new UnaryOp(),
new UnaryExpression()) ;
break;
case 1:
acc = new PostfixExpression() ;
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
    public UnaryExpression create() {
        return new UnaryExpression();
    }

 


}


