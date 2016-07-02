
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class RBRACK extends Acceptor {

    public RBRACK() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new CharSeq(']'),
new Spacing()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public RBRACK create() {
        return new RBRACK();
    }

 


}


