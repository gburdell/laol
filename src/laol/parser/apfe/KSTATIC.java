
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class KSTATIC extends Acceptor {

    public KSTATIC() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new CharSeq("static"),
new KTail()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public KSTATIC create() {
        return new KSTATIC();
    }

 


}


