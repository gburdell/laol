
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class KFILE extends Acceptor {

    public KFILE() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new CharSeq("__FILE__"),
new KTail()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public KFILE create() {
        return new KFILE();
    }

 


}


