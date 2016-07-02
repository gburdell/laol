
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class LhsRef extends LeftRecursiveAcceptor {

    public LhsRef() {
    }

    private LhsRef(boolean isDRR) {
        super(isDRR);
    }

    @Override
    protected int getNonRecursiveChoiceIx() {
        return 1;
    }

    @Override
    public Acceptor create() {
        return new LhsRef();
    }

    @Override
    public Acceptor getChoice(int ix) {
        Acceptor matcher = null  ;
		switch(ix) {
		case 0:
matcher = new Sequence(this,
new LBRACK(),
new ArraySelectExpression(),
new RBRACK()) ;
break;
case 1:
matcher = new VariableName() ;
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


