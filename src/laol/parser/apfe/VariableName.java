
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class VariableName extends Acceptor {

    public VariableName() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new IDENT(),
new Repetition(new Sequence(new COLON2(),
new IDENT()), Repetition.ERepeat.eZeroOrMore)) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public VariableName create() {
        return new VariableName();
    }

 


}


