
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class LPAREN extends Acceptor {

    public LPAREN() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new CharSeq('('),
new Spacing()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public LPAREN create() {
        return new LPAREN();
    }

 


}


