
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class ArraySelectExpression extends Acceptor {

    public ArraySelectExpression() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new PrioritizedChoice(new PrioritizedChoice.Choices() {
@Override
public Acceptor getChoice(int ix) {
Acceptor acc = null;
switch (ix) {
case 0:
acc = new Sequence(new Expression(),
new PrioritizedChoice(new PrioritizedChoice.Choices() {
@Override
public Acceptor getChoice(int ix) {
Acceptor acc = null;
switch (ix) {
case 0:
acc = new DOT2() ;
break;
case 1:
acc = new COLON() ;
break;
}
return acc;
}
}),
new Expression()) ;
break;
case 1:
acc = new Sequence(new Expression(),
new Repetition(new Sequence(new COMMA(),
new Expression()), Repetition.ERepeat.eOptional)) ;
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
    public ArraySelectExpression create() {
        return new ArraySelectExpression();
    }

 


}


