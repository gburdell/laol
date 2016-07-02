
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class KWHEN extends Acceptor {

    public KWHEN() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new CharSeq("when"),
new KTail()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public KWHEN create() {
        return new KWHEN();
    }

 


}


