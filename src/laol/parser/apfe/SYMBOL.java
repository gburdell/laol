
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class SYMBOL extends Acceptor {

    public SYMBOL() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new COLON(),
new IDENT(),
new Spacing()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public SYMBOL create() {
        return new SYMBOL();
    }

 


}


