
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class EXCL extends Acceptor {

    public EXCL() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new CharSeq('!'),
new NotPredicate(new CharSeq('=')),
new Spacing()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public EXCL create() {
        return new EXCL();
    }

 


}


