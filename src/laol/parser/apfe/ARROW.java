
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class ARROW extends Acceptor {

    public ARROW() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new CharSeq("->"),
new Spacing()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public ARROW create() {
        return new ARROW();
    }

 


}


