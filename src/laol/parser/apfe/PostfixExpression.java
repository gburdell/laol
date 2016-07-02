
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class PostfixExpression extends LeftRecursiveAcceptor {

    public PostfixExpression() {
    }

    private PostfixExpression(boolean isDRR) {
        super(isDRR);
    }

    @Override
    protected int getNonRecursiveChoiceIx() {
        return 4;
    }

    @Override
    public Acceptor create() {
        return new PostfixExpression();
    }

    @Override
    public Acceptor getChoice(int ix) {
        Acceptor matcher = null  ;
		switch(ix) {
		case 0:
matcher = new Sequence(this,
new LBRACK(),
new ArraySelectExpression(),
new RBRACK(),
new Repetition(new Block(), Repetition.ERepeat.eOptional)) ;
break;
case 1:
matcher = new Sequence(this,
new LPAREN(),
new Repetition(new ParamExpressionList(), Repetition.ERepeat.eOptional),
new RPAREN(),
new Repetition(new Block(), Repetition.ERepeat.eOptional)) ;
break;
case 2:
matcher = new Sequence(this,
new DotSuffix(),
new Repetition(new Block(), Repetition.ERepeat.eOptional)) ;
break;
case 3:
matcher = new Sequence(this,
new PrioritizedChoice(new PrioritizedChoice.Choices() {
@Override
public Acceptor getChoice(int ix) {
Acceptor acc = null;
switch (ix) {
case 0:
acc = new PLUS2() ;
break;
case 1:
acc = new MINUS2() ;
break;
}
return acc;
}
})) ;
break;
case 4:
matcher = new Sequence(new PrimaryExpression(),
new Repetition(new Block(), Repetition.ERepeat.eOptional)) ;
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


