GRAMMAR ?= laol
PEG  ?= ${GRAMMAR}.peg
TOOL ?= ${HOME}/projects/apfe/bin/generate
DEPS ?= base.java.tmpl dlrbase.java.tmpl generate.props.txt ${PEG}
FLEX ?= jflex
TARGET1 ?= LaolScanner.java
TARGET2 ?= goal

.PHONY: all
all: ${TARGET1} ${TARGET2} ;

${TARGET1}: laol.flex
	${FLEX} --nobak ${<}

.PHONY: clean clean2
clean: clean2
	-rm -f ${TARGET1}

clean2:
	-rm -rf apfe
	-rm -f generate.log

goal: generate.log

generate.log: ${DEPS}
	${MAKE} clean2
	${TOOL} ${PEG} generate.props.txt > generate.log
	tail generate.log
