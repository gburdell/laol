
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class Spacing extends Acceptor {

    public Spacing() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Repetition(new PrioritizedChoice(new PrioritizedChoice.Choices() {
@Override
public Acceptor getChoice(int ix) {
Acceptor acc = null;
switch (ix) {
case 0:
acc = new Space() ;
break;
case 1:
acc = new Comment() ;
break;
}
return acc;
}
}), Repetition.ERepeat.eZeroOrMore) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public Spacing create() {
        return new Spacing();
    }

 


}


