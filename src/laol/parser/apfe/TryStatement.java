
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class TryStatement extends Acceptor {

    public TryStatement() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new KTRY(),
new Statement(),
new Repetition(new CatchStatement(), Repetition.ERepeat.eZeroOrMore),
new Repetition(new FinallyStatement(), Repetition.ERepeat.eOptional)) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public TryStatement create() {
        return new TryStatement();
    }

 


}


