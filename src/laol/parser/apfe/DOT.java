
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class DOT extends Acceptor {

    public DOT() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new CharSeq('.'),
new NotPredicate(new CharSeq('.')),
new Spacing()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public DOT create() {
        return new DOT();
    }

 


}


