
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class LTEQ extends Acceptor {

    public LTEQ() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new CharSeq("<="),
new Spacing()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public LTEQ create() {
        return new LTEQ();
    }

 


}


