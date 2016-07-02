
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class MethodParamDeclList extends Acceptor {

    public MethodParamDeclList() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new MethodParamDeclEle(),
new Repetition(new Sequence(new COMMA(),
new MethodParamDeclEle()), Repetition.ERepeat.eZeroOrMore)) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public MethodParamDeclList create() {
        return new MethodParamDeclList();
    }

 


}


