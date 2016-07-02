
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class UnnamedParam extends Acceptor {

    public UnnamedParam() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new Expression(),
new Repetition(new Sequence(new COMMA(),
new Expression()), Repetition.ERepeat.eZeroOrMore)) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public UnnamedParam create() {
        return new UnnamedParam();
    }

 


}


