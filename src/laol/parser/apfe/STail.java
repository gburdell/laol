
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class STail extends Acceptor {

    public STail() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new PrioritizedChoice(new PrioritizedChoice.Choices() {
@Override
public Acceptor getChoice(int ix) {
Acceptor acc = null;
switch (ix) {
case 0:
acc = new StatementModifier() ;
break;
case 1:
acc = new SEMI() ;
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
    public STail create() {
        return new STail();
    }

 


}


