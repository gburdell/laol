ANTLR := antlr4
ANTLR_OPTS :=

.PHONY: goal clean

goal: LaolParser.java ;

LaolParser.java: Laol.g4
	${ANTLR} ${ANTLR_OPTS} $<

clean:
	rm -f Laol{BaseListener,Lexer,Listener,Parser,}.{java,tokens}
