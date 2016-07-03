FLEX ?= jflex
TARGET1 ?= LaolScanner.java

.PHONY: all
all: ${TARGET1} ;

${TARGET1}: laol.flex
	${FLEX} --nobak ${<}

.PHONY: clean
clean:
	-rm -f ${TARGET1}
