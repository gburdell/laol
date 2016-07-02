
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class CaseStatement extends Acceptor {

    public CaseStatement() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new KCASE(),
new Expression(),
new LCURLY(),
new Repetition(new Sequence(new KWHEN(),
new Expression(),
new Repetition(new Sequence(new COMMA(),
new Expression()), Repetition.ERepeat.eZeroOrMore),
new COLON(),
new Statement()), Repetition.ERepeat.eZeroOrMore),
new Repetition(new Sequence(new KELSE(),
new Statement()), Repetition.ERepeat.eOptional),
new RCURLY()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public CaseStatement create() {
        return new CaseStatement();
    }

 


}


