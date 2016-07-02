
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class Expression extends LeftRecursiveAcceptor {

    public Expression() {
    }

    private Expression(boolean isDRR) {
        super(isDRR);
    }

    @Override
    protected int getNonRecursiveChoiceIx() {
        return 1;
    }

    @Override
    public Acceptor create() {
        return new Expression();
    }

    @Override
    public Acceptor getChoice(int ix) {
        Acceptor matcher = null  ;
		switch(ix) {
		case 0:
matcher = new Sequence(this,
new QMARK(),
new Expression(),
new COLON(),
new Expression()) ;
break;
case 1:
matcher = new BinaryExpression() ;
break;

		}
        //Keep track so if we growSeed()
        m_lastIx = ix;
        m_lastChoice = matcher;
        return matcher;
    }

    @Override
    protected boolean accepti() {
		boolean match = super.accepti();

        return match;
    }

 


}


