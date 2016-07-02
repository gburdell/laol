
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class StatementModifier extends Acceptor {

    public StatementModifier() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new Repetition(new Sequence(new PrioritizedChoice(new PrioritizedChoice.Choices() {
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
case 2:
acc = new KWHILE() ;
break;
case 3:
acc = new KUNTIL() ;
break;
}
return acc;
}
}),
new Expression()), Repetition.ERepeat.eOneOrMore),
new SEMI()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public StatementModifier create() {
        return new StatementModifier();
    }

 


}


