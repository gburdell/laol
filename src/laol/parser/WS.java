
package laol.parser;


import apfe.runtime.*;




public  class WS extends Acceptor {

    public WS() {
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
}
return acc;
}
}) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public WS create() {
        return new WS();
    }

 


}


