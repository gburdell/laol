
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class ForVariableName extends Acceptor {

    public ForVariableName() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new IDENT() ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public ForVariableName create() {
        return new ForVariableName();
    }

 


}


