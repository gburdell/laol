
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class AssignmentRhs extends Acceptor {

    public AssignmentRhs() {
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
new Repetition(new Sequence(new COMMA(),
new Expression()), Repetition.ERepeat.eZeroOrMore),
new STail()) ;
break;
case 1:
acc = new Sequence(new AnonymousFunction(),
new Repetition(new StatementModifier(), Repetition.ERepeat.eOptional)) ;
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
    public AssignmentRhs create() {
        return new AssignmentRhs();
    }

 


}


