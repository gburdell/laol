
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class Block extends Acceptor {

    public Block() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new AnonymousFunction() ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public Block create() {
        return new Block();
    }

 


}


