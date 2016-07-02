
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class KCLASS extends Acceptor {

    public KCLASS() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new CharSeq("class"),
new KTail()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public KCLASS create() {
        return new KCLASS();
    }

 


}


