
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class AssignmentLhs extends Acceptor {

    public AssignmentLhs() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new LhsDecl(),
new Repetition(new Sequence(new COMMA(),
new LhsDecl()), Repetition.ERepeat.eZeroOrMore)) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public AssignmentLhs create() {
        return new AssignmentLhs();
    }

 


}


