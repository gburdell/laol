
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class MINUS extends Acceptor {

    public MINUS() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new CharSeq('-'),
new NotPredicate(new PrioritizedChoice(new PrioritizedChoice.Choices() {
@Override
public Acceptor getChoice(int ix) {
Acceptor acc = null;
switch (ix) {
case 0:
acc = new CharSeq('-') ;
break;
case 1:
acc = new CharSeq('=') ;
break;
case 2:
acc = new CharSeq('>') ;
break;
}
return acc;
}
})),
new Spacing()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public MINUS create() {
        return new MINUS();
    }

 


}


