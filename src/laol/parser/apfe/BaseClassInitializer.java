
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class BaseClassInitializer extends Acceptor {

    public BaseClassInitializer() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new KSUPER(),
new Repetition(new MethodParamDecl(), Repetition.ERepeat.eOptional),
new SEMI()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public BaseClassInitializer create() {
        return new BaseClassInitializer();
    }

 


}


