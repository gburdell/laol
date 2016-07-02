
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class KTARGET extends Acceptor {

    public KTARGET() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new CharSeq("__TARGET__"),
new KTail()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public KTARGET create() {
        return new KTARGET();
    }

 


}


