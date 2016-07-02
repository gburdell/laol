
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class GT2 extends Acceptor {

    public GT2() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new CharSeq(">>"),
new NotPredicate(new CharSeq('=')),
new Spacing()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public GT2 create() {
        return new GT2();
    }

 


}


