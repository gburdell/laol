
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class Space extends Acceptor {

    public Space() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new PrioritizedChoice(new PrioritizedChoice.Choices() {
@Override
public Acceptor getChoice(int ix) {
Acceptor acc = null;
switch (ix) {
case 0:
acc = new CharSeq(' ') ;
break;
case 1:
acc = new CharSeq('\t') ;
break;
case 2:
acc = new EOL() ;
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
    public Space create() {
        return new Space();
    }

 


}


