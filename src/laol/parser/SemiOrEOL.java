
package laol.parser;


import apfe.runtime.*;

public  class SemiOrEOL extends Acceptor {

    public SemiOrEOL() {
    }

    @Override
    protected boolean accepti() {
        return SpacingWithSemi.lastWasSemiOrEOL();
    }

    @Override
    public SemiOrEOL create() {
        return new SemiOrEOL();
    }

 


}


