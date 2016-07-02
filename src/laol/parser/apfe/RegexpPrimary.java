
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class RegexpPrimary extends Acceptor {

    public RegexpPrimary() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new PrioritizedChoice(new PrioritizedChoice.Choices() {
@Override
public Acceptor getChoice(int ix) {
Acceptor acc = null;
switch (ix) {
case 0:
acc = new Sequence(new CharSeq('/'),
new NotPredicate(new PrioritizedChoice(new PrioritizedChoice.Choices() {
@Override
public Acceptor getChoice(int ix) {
Acceptor acc = null;
switch (ix) {
case 0:
acc = new CharSeq('/') ;
break;
case 1:
acc = new CharSeq('*') ;
break;
}
return acc;
}
})),
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
acc = new Sequence(new NotPredicate(new CharSeq('/')),
new CharClass(ICharClass.IS_ANY)) ;
break;
}
return acc;
}
}), Repetition.ERepeat.eOneOrMore),
new CharSeq('/')) ;
break;
case 1:
acc = new Sequence(new CharSeq("%r{"),
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
acc = new Sequence(new NotPredicate(new CharSeq('}')),
new CharClass(ICharClass.IS_ANY)) ;
break;
}
return acc;
}
}), Repetition.ERepeat.eOneOrMore),
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
    public RegexpPrimary create() {
        return new RegexpPrimary();
    }

 


}


