
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class IfStatement extends Acceptor {

    public IfStatement() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new PrioritizedChoice(new PrioritizedChoice.Choices() {
@Override
public Acceptor getChoice(int ix) {
Acceptor acc = null;
switch (ix) {
case 0:
acc = new KIF() ;
break;
case 1:
acc = new KUNLESS() ;
break;
}
return acc;
}
}),
new Expression(),
new Statement(),
new Repetition(new Sequence(new KELSIF(),
new Expression(),
new Statement()), Repetition.ERepeat.eZeroOrMore),
new Repetition(new Sequence(new KELSE(),
new Statement()), Repetition.ERepeat.eOptional)) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public IfStatement create() {
        return new IfStatement();
    }

 


}


