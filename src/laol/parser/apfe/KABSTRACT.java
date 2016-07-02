
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class KABSTRACT extends Acceptor {

    public KABSTRACT() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new CharSeq("abstract"),
new KTail()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public KABSTRACT create() {
        return new KABSTRACT();
    }

 


}


