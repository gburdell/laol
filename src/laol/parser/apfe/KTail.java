
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class KTail extends Acceptor {

    public KTail() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new NotPredicate(new IdentCont()),
new Spacing()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public KTail create() {
        return new KTail();
    }

 


}


