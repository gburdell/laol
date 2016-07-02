
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class Comment extends Acceptor {

    public Comment() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new PrioritizedChoice(new PrioritizedChoice.Choices() {
@Override
public Acceptor getChoice(int ix) {
Acceptor acc = null;
switch (ix) {
case 0:
acc = new SLCOMMENT() ;
break;
case 1:
acc = new MLCOMMENT() ;
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
    public Comment create() {
        return new Comment();
    }

 


}


