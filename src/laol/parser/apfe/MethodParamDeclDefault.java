
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class MethodParamDeclDefault extends Acceptor {

    public MethodParamDeclDefault() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new EQ(),
new Expression()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public MethodParamDeclDefault create() {
        return new MethodParamDeclDefault();
    }

 


}


