
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class KCATCH extends Acceptor {

    public KCATCH() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new CharSeq("catch"),
new KTail()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public KCATCH create() {
        return new KCATCH();
    }

 


}


