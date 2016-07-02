
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class STRING extends Acceptor {

    public STRING() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new PrioritizedChoice(new PrioritizedChoice.Choices() {
@Override
public Acceptor getChoice(int ix) {
Acceptor acc = null;
switch (ix) {
case 0:
acc = new Sequence(new CharSeq('"'),
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
acc = new Sequence(new NotPredicate(new CharSeq('"')),
new CharClass(ICharClass.IS_ANY)) ;
break;
}
return acc;
}
}), Repetition.ERepeat.eZeroOrMore),
new CharSeq('"'),
new Spacing()) ;
break;
case 1:
acc = new Sequence(new CharSeq('\''),
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
acc = new Sequence(new NotPredicate(new CharSeq('\'')),
new CharClass(ICharClass.IS_ANY)) ;
break;
}
return acc;
}
}), Repetition.ERepeat.eOptional),
new CharSeq('\''),
new Spacing()) ;
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
    public STRING create() {
        return new STRING();
    }

 


}


