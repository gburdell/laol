
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class COLON extends Acceptor {

    public COLON() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new CharSeq(':'),
new NotPredicate(new CharSeq(':')),
new Spacing()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public COLON create() {
        return new COLON();
    }

 


}


