
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class SEMI extends Acceptor {

    public SEMI() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new CharSeq(';'),
new Spacing()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public SEMI create() {
        return new SEMI();
    }

 


}


