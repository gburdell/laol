
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class KBREAK extends Acceptor {

    public KBREAK() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new CharSeq("break"),
new KTail()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public KBREAK create() {
        return new KBREAK();
    }

 


}


