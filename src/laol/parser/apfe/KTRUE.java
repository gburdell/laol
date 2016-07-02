
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class KTRUE extends Acceptor {

    public KTRUE() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new CharSeq("true"),
new KTail()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public KTRUE create() {
        return new KTRUE();
    }

 


}


