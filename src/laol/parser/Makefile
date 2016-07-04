GRAMMAR ?= laol
PEG  ?= ${GRAMMAR}.peg
TOOL ?= ${HOME}/projects/apfe/bin/generate
DEPS ?= base.java.tmpl dlrbase.java.tmpl generate.props.txt ${PEG}

.PHONY: goal clean
goal: generate.log

generate.log: ${PEG}
	${MAKE} clean
	${TOOL} ${PEG} generate.props.txt > generate.log
	tail generate.log

clean:
	-rm -rf src/${GRAMMAR}/apfe/generated
	-rm -f generate.log
