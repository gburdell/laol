
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class KPUBLIC extends Acceptor {

    public KPUBLIC() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new CharSeq("public"),
new KTail()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public KPUBLIC create() {
        return new KPUBLIC();
    }

 


}


