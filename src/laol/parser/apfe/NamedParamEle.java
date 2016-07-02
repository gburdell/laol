
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class NamedParamEle extends Acceptor {

    public NamedParamEle() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new IDENT(),
new COLON(),
new Expression()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public NamedParamEle create() {
        return new NamedParamEle();
    }

 


}


