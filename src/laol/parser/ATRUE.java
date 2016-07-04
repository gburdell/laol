
package laol.parser;


import apfe.runtime.*;

public  class ATRUE extends Acceptor {

    public ATRUE() {
    }

    @Override
    protected boolean accepti() {
        return true;
    }

    @Override
    public ATRUE create() {
        return new ATRUE();
    }

 


}


