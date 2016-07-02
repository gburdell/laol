
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class Integer extends Acceptor {

    public Integer() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new Digits(),
new NotPredicate(new PrioritizedChoice(new PrioritizedChoice.Choices() {
@Override
public Acceptor getChoice(int ix) {
Acceptor acc = null;
switch (ix) {
case 0:
acc = new CharSeq('.') ;
break;
case 1:
acc = new CharClass(CharClass.matchOneOf('e'),
CharClass.matchOneOf('E')) ;
break;
}
return acc;
}
}))) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public Integer create() {
        return new Integer();
    }

 


}


