
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class KMIXIN extends Acceptor {

    public KMIXIN() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new CharSeq("mixin"),
new KTail()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public KMIXIN create() {
        return new KMIXIN();
    }

 


}


