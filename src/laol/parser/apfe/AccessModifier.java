
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class AccessModifier extends Acceptor {

    public AccessModifier() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new PrioritizedChoice(new PrioritizedChoice.Choices() {
@Override
public Acceptor getChoice(int ix) {
Acceptor acc = null;
switch (ix) {
case 0:
acc = new KPRIVATE() ;
break;
case 1:
acc = new KPROTECTED() ;
break;
case 2:
acc = new KPUBLIC() ;
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
    public AccessModifier create() {
        return new AccessModifier();
    }

 


}


