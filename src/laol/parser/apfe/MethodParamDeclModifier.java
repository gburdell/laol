
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class MethodParamDeclModifier extends Acceptor {

    public MethodParamDeclModifier() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new Repetition(new AccessModifier(), Repetition.ERepeat.eOptional),
new Repetition(new Mutability(), Repetition.ERepeat.eOptional)) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public MethodParamDeclModifier create() {
        return new MethodParamDeclModifier();
    }

 


}


