
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class GT2EQ extends Acceptor {

    public GT2EQ() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new CharSeq(">>="),
new Spacing()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public GT2EQ create() {
        return new GT2EQ();
    }

 


}


