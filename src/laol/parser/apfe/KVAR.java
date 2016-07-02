
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class KVAR extends Acceptor {

    public KVAR() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new CharSeq("var"),
new KTail()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public KVAR create() {
        return new KVAR();
    }

 


}


