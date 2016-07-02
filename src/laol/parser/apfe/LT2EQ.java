
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class LT2EQ extends Acceptor {

    public LT2EQ() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new CharSeq("<<="),
new Spacing()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public LT2EQ create() {
        return new LT2EQ();
    }

 


}


