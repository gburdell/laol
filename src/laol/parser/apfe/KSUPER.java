
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class KSUPER extends Acceptor {

    public KSUPER() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new CharSeq("super"),
new KTail()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public KSUPER create() {
        return new KSUPER();
    }

 


}


