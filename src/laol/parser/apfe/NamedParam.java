
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class NamedParam extends Acceptor {

    public NamedParam() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new NamedParamEle(),
new Repetition(new Sequence(new COMMA(),
new NamedParamEle()), Repetition.ERepeat.eZeroOrMore),
new Repetition(new Sequence(new COMMA(),
new Expression()), Repetition.ERepeat.eZeroOrMore)) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public NamedParam create() {
        return new NamedParam();
    }

 


}


