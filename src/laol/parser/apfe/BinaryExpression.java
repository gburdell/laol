
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class BinaryExpression extends LeftRecursiveAcceptor {

    public BinaryExpression() {
    }

    private BinaryExpression(boolean isDRR) {
        super(isDRR);
    }

    @Override
    protected int getNonRecursiveChoiceIx() {
        return 1;
    }

    @Override
    public Acceptor create() {
        return new BinaryExpression();
    }

    @Override
    public Acceptor getChoice(int ix) {
        Acceptor matcher = null  ;
		switch(ix) {
		case 0:
matcher = new Sequence(this,
new BinaryOp(),
new UnaryExpression()) ;
break;
case 1:
matcher = new UnaryExpression() ;
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


