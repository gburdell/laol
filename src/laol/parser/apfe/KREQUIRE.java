
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class KREQUIRE extends Acceptor {

    public KREQUIRE() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new CharSeq("require"),
new KTail()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public KREQUIRE create() {
        return new KREQUIRE();
    }

 


}


