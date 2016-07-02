
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class MethodParamDecl extends Acceptor {

    public MethodParamDecl() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new LPAREN(),
new Repetition(new MethodParamDeclList(), Repetition.ERepeat.eOptional),
new RPAREN()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public MethodParamDecl create() {
        return new MethodParamDecl();
    }

 


}


