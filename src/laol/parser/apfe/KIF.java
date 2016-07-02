
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class KIF extends Acceptor {

    public KIF() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new CharSeq("if"),
new KTail()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public KIF create() {
        return new KIF();
    }

 


}


