
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class HashKey extends Acceptor {

    public HashKey() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new PrioritizedChoice(new PrioritizedChoice.Choices() {
@Override
public Acceptor getChoice(int ix) {
Acceptor acc = null;
switch (ix) {
case 0:
acc = new STRING() ;
break;
case 1:
acc = new IDENT() ;
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
    public HashKey create() {
        return new HashKey();
    }

 


}


