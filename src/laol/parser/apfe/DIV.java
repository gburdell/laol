
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class DIV extends Acceptor {

    public DIV() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new CharSeq('/'),
new NotPredicate(new CharSeq('=')),
new Spacing()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public DIV create() {
        return new DIV();
    }

 


}


