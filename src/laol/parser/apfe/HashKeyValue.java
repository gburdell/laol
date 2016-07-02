
package laol.parser.apfe ;


import apfe.runtime.*;
import laol.parser.*;




public  class HashKeyValue extends Acceptor {

    public HashKeyValue() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new HashKey(),
new COLON(),
new Expression()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public HashKeyValue create() {
        return new HashKeyValue();
    }

 


}


