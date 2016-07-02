
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class ArrayPrimary extends Acceptor {

    public ArrayPrimary() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new PrioritizedChoice(new PrioritizedChoice.Choices() {
@Override
public Acceptor getChoice(int ix) {
Acceptor acc = null;
switch (ix) {
case 0:
acc = new Sequence(new LBRACK(),
new Expression(),
new Sequence(new COMMA(),
new Expression()),
new RBRACK()) ;
break;
case 1:
acc = new Sequence(new CharSeq('%'),
new CharClass(CharClass.matchOneOf('w'),
CharClass.matchOneOf('i')),
new CharSeq('{'),
new Repetition(new PrioritizedChoice(new PrioritizedChoice.Choices() {
@Override
public Acceptor getChoice(int ix) {
Acceptor acc = null;
switch (ix) {
case 0:
acc = new Sequence(new CharSeq('\\'),
new CharClass(ICharClass.IS_ANY)) ;
break;
case 1:
acc = new NotPredicate(new CharSeq('}')) ;
break;
}
return acc;
}
}), Repetition.ERepeat.eZeroOrMore),
new CharSeq('}')) ;
break;
}
return acc;
}
}) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public ArrayPrimary create() {
        return new ArrayPrimary();
    }

 


}


