#
# Generated Makefile - do not edit!
#
# Edit the Makefile in the project folder instead (../Makefile). Each target
# has a -pre and a -post target defined where you can add customized code.
#
# This makefile implements configuration specific macros and targets.


# Environment
MKDIR=mkdir
CP=cp
GREP=grep
NM=nm
CCADMIN=CCadmin
RANLIB=ranlib
CC=gcc
CCC=g++
CXX=g++
FC=gfortran
AS=as

# Macros
CND_PLATFORM=gcc-4.7.4-MacOSX
CND_DLIB_EXT=dylib
CND_CONF=Release
CND_DISTDIR=dist
CND_BUILDDIR=build

# Include project Makefile
include Makefile

# Object Directory
OBJECTDIR=${CND_BUILDDIR}/${CND_CONF}/${CND_PLATFORM}

# Object Files
OBJECTFILES= \
	${OBJECTDIR}/laol/rt/array.o \
	${OBJECTDIR}/laol/rt/exception.o \
	${OBJECTDIR}/laol/rt/iterator.o \
	${OBJECTDIR}/laol/rt/laol.o \
	${OBJECTDIR}/laol/rt/map.o \
	${OBJECTDIR}/laol/rt/ostream.o \
	${OBJECTDIR}/laol/rt/range.o \
	${OBJECTDIR}/laol/rt/string.o

# Test Directory
TESTDIR=${CND_BUILDDIR}/${CND_CONF}/${CND_PLATFORM}/tests

# Test Files
TESTFILES= \
	${TESTDIR}/TestFiles/f1

# Test Object Files
TESTOBJECTFILES= \
	${TESTDIR}/laol/rt/tests/number1.o

# C Compiler Flags
CFLAGS=

# CC Compiler Flags
CCFLAGS=-std=c++11
CXXFLAGS=-std=c++11

# Fortran Compiler Flags
FFLAGS=

# Assembler Flags
ASFLAGS=

# Link Libraries and Options
LDLIBSOPTIONS=

# Build Targets
.build-conf: ${BUILD_SUBPROJECTS}
	"${MAKE}"  -f nbproject/Makefile-${CND_CONF}.mk ${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}/liblaolrt.a

${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}/liblaolrt.a: ${OBJECTFILES}
	${MKDIR} -p ${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}
	${RM} ${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}/liblaolrt.a
	${AR} -rv ${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}/liblaolrt.a ${OBJECTFILES} 
	$(RANLIB) ${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}/liblaolrt.a

${OBJECTDIR}/laol/rt/array.o: laol/rt/array.cxx
	${MKDIR} -p ${OBJECTDIR}/laol/rt
	${RM} "$@.d"
	$(COMPILE.cc) -O2 -I../../../xyzzy/src -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/laol/rt/array.o laol/rt/array.cxx

${OBJECTDIR}/laol/rt/exception.o: laol/rt/exception.cxx
	${MKDIR} -p ${OBJECTDIR}/laol/rt
	${RM} "$@.d"
	$(COMPILE.cc) -O2 -I../../../xyzzy/src -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/laol/rt/exception.o laol/rt/exception.cxx

${OBJECTDIR}/laol/rt/iterator.o: laol/rt/iterator.cxx
	${MKDIR} -p ${OBJECTDIR}/laol/rt
	${RM} "$@.d"
	$(COMPILE.cc) -O2 -I../../../xyzzy/src -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/laol/rt/iterator.o laol/rt/iterator.cxx

${OBJECTDIR}/laol/rt/laol.o: laol/rt/laol.cxx
	${MKDIR} -p ${OBJECTDIR}/laol/rt
	${RM} "$@.d"
	$(COMPILE.cc) -O2 -I../../../xyzzy/src -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/laol/rt/laol.o laol/rt/laol.cxx

${OBJECTDIR}/laol/rt/map.o: laol/rt/map.cxx
	${MKDIR} -p ${OBJECTDIR}/laol/rt
	${RM} "$@.d"
	$(COMPILE.cc) -O2 -I../../../xyzzy/src -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/laol/rt/map.o laol/rt/map.cxx

${OBJECTDIR}/laol/rt/ostream.o: laol/rt/ostream.cxx
	${MKDIR} -p ${OBJECTDIR}/laol/rt
	${RM} "$@.d"
	$(COMPILE.cc) -O2 -I../../../xyzzy/src -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/laol/rt/ostream.o laol/rt/ostream.cxx

${OBJECTDIR}/laol/rt/range.o: laol/rt/range.cxx
	${MKDIR} -p ${OBJECTDIR}/laol/rt
	${RM} "$@.d"
	$(COMPILE.cc) -O2 -I../../../xyzzy/src -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/laol/rt/range.o laol/rt/range.cxx

${OBJECTDIR}/laol/rt/string.o: laol/rt/string.cxx
	${MKDIR} -p ${OBJECTDIR}/laol/rt
	${RM} "$@.d"
	$(COMPILE.cc) -O2 -I../../../xyzzy/src -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/laol/rt/string.o laol/rt/string.cxx

# Subprojects
.build-subprojects:

# Build Test Targets
.build-tests-conf: .build-tests-subprojects .build-conf ${TESTFILES}
.build-tests-subprojects:

${TESTDIR}/TestFiles/f1: ${TESTDIR}/laol/rt/tests/number1.o ${OBJECTFILES:%.o=%_nomain.o}
	${MKDIR} -p ${TESTDIR}/TestFiles
	${LINK.cc} -o ${TESTDIR}/TestFiles/f1 $^ ${LDLIBSOPTIONS}   


${TESTDIR}/laol/rt/tests/number1.o: laol/rt/tests/number1.cpp 
	${MKDIR} -p ${TESTDIR}/laol/rt/tests
	${RM} "$@.d"
	$(COMPILE.cc) -O2 -I../../../xyzzy/src -I. -MMD -MP -MF "$@.d" -o ${TESTDIR}/laol/rt/tests/number1.o laol/rt/tests/number1.cpp


${OBJECTDIR}/laol/rt/array_nomain.o: ${OBJECTDIR}/laol/rt/array.o laol/rt/array.cxx 
	${MKDIR} -p ${OBJECTDIR}/laol/rt
	@NMOUTPUT=`${NM} ${OBJECTDIR}/laol/rt/array.o`; \
	if (echo "$$NMOUTPUT" | ${GREP} '|main$$') || \
	   (echo "$$NMOUTPUT" | ${GREP} 'T main$$') || \
	   (echo "$$NMOUTPUT" | ${GREP} 'T _main$$'); \
	then  \
	    ${RM} "$@.d";\
	    $(COMPILE.cc) -O2 -I../../../xyzzy/src -Dmain=__nomain -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/laol/rt/array_nomain.o laol/rt/array.cxx;\
	else  \
	    ${CP} ${OBJECTDIR}/laol/rt/array.o ${OBJECTDIR}/laol/rt/array_nomain.o;\
	fi

${OBJECTDIR}/laol/rt/exception_nomain.o: ${OBJECTDIR}/laol/rt/exception.o laol/rt/exception.cxx 
	${MKDIR} -p ${OBJECTDIR}/laol/rt
	@NMOUTPUT=`${NM} ${OBJECTDIR}/laol/rt/exception.o`; \
	if (echo "$$NMOUTPUT" | ${GREP} '|main$$') || \
	   (echo "$$NMOUTPUT" | ${GREP} 'T main$$') || \
	   (echo "$$NMOUTPUT" | ${GREP} 'T _main$$'); \
	then  \
	    ${RM} "$@.d";\
	    $(COMPILE.cc) -O2 -I../../../xyzzy/src -Dmain=__nomain -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/laol/rt/exception_nomain.o laol/rt/exception.cxx;\
	else  \
	    ${CP} ${OBJECTDIR}/laol/rt/exception.o ${OBJECTDIR}/laol/rt/exception_nomain.o;\
	fi

${OBJECTDIR}/laol/rt/iterator_nomain.o: ${OBJECTDIR}/laol/rt/iterator.o laol/rt/iterator.cxx 
	${MKDIR} -p ${OBJECTDIR}/laol/rt
	@NMOUTPUT=`${NM} ${OBJECTDIR}/laol/rt/iterator.o`; \
	if (echo "$$NMOUTPUT" | ${GREP} '|main$$') || \
	   (echo "$$NMOUTPUT" | ${GREP} 'T main$$') || \
	   (echo "$$NMOUTPUT" | ${GREP} 'T _main$$'); \
	then  \
	    ${RM} "$@.d";\
	    $(COMPILE.cc) -O2 -I../../../xyzzy/src -Dmain=__nomain -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/laol/rt/iterator_nomain.o laol/rt/iterator.cxx;\
	else  \
	    ${CP} ${OBJECTDIR}/laol/rt/iterator.o ${OBJECTDIR}/laol/rt/iterator_nomain.o;\
	fi

${OBJECTDIR}/laol/rt/laol_nomain.o: ${OBJECTDIR}/laol/rt/laol.o laol/rt/laol.cxx 
	${MKDIR} -p ${OBJECTDIR}/laol/rt
	@NMOUTPUT=`${NM} ${OBJECTDIR}/laol/rt/laol.o`; \
	if (echo "$$NMOUTPUT" | ${GREP} '|main$$') || \
	   (echo "$$NMOUTPUT" | ${GREP} 'T main$$') || \
	   (echo "$$NMOUTPUT" | ${GREP} 'T _main$$'); \
	then  \
	    ${RM} "$@.d";\
	    $(COMPILE.cc) -O2 -I../../../xyzzy/src -Dmain=__nomain -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/laol/rt/laol_nomain.o laol/rt/laol.cxx;\
	else  \
	    ${CP} ${OBJECTDIR}/laol/rt/laol.o ${OBJECTDIR}/laol/rt/laol_nomain.o;\
	fi

${OBJECTDIR}/laol/rt/map_nomain.o: ${OBJECTDIR}/laol/rt/map.o laol/rt/map.cxx 
	${MKDIR} -p ${OBJECTDIR}/laol/rt
	@NMOUTPUT=`${NM} ${OBJECTDIR}/laol/rt/map.o`; \
	if (echo "$$NMOUTPUT" | ${GREP} '|main$$') || \
	   (echo "$$NMOUTPUT" | ${GREP} 'T main$$') || \
	   (echo "$$NMOUTPUT" | ${GREP} 'T _main$$'); \
	then  \
	    ${RM} "$@.d";\
	    $(COMPILE.cc) -O2 -I../../../xyzzy/src -Dmain=__nomain -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/laol/rt/map_nomain.o laol/rt/map.cxx;\
	else  \
	    ${CP} ${OBJECTDIR}/laol/rt/map.o ${OBJECTDIR}/laol/rt/map_nomain.o;\
	fi

${OBJECTDIR}/laol/rt/ostream_nomain.o: ${OBJECTDIR}/laol/rt/ostream.o laol/rt/ostream.cxx 
	${MKDIR} -p ${OBJECTDIR}/laol/rt
	@NMOUTPUT=`${NM} ${OBJECTDIR}/laol/rt/ostream.o`; \
	if (echo "$$NMOUTPUT" | ${GREP} '|main$$') || \
	   (echo "$$NMOUTPUT" | ${GREP} 'T main$$') || \
	   (echo "$$NMOUTPUT" | ${GREP} 'T _main$$'); \
	then  \
	    ${RM} "$@.d";\
	    $(COMPILE.cc) -O2 -I../../../xyzzy/src -Dmain=__nomain -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/laol/rt/ostream_nomain.o laol/rt/ostream.cxx;\
	else  \
	    ${CP} ${OBJECTDIR}/laol/rt/ostream.o ${OBJECTDIR}/laol/rt/ostream_nomain.o;\
	fi

${OBJECTDIR}/laol/rt/range_nomain.o: ${OBJECTDIR}/laol/rt/range.o laol/rt/range.cxx 
	${MKDIR} -p ${OBJECTDIR}/laol/rt
	@NMOUTPUT=`${NM} ${OBJECTDIR}/laol/rt/range.o`; \
	if (echo "$$NMOUTPUT" | ${GREP} '|main$$') || \
	   (echo "$$NMOUTPUT" | ${GREP} 'T main$$') || \
	   (echo "$$NMOUTPUT" | ${GREP} 'T _main$$'); \
	then  \
	    ${RM} "$@.d";\
	    $(COMPILE.cc) -O2 -I../../../xyzzy/src -Dmain=__nomain -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/laol/rt/range_nomain.o laol/rt/range.cxx;\
	else  \
	    ${CP} ${OBJECTDIR}/laol/rt/range.o ${OBJECTDIR}/laol/rt/range_nomain.o;\
	fi

${OBJECTDIR}/laol/rt/string_nomain.o: ${OBJECTDIR}/laol/rt/string.o laol/rt/string.cxx 
	${MKDIR} -p ${OBJECTDIR}/laol/rt
	@NMOUTPUT=`${NM} ${OBJECTDIR}/laol/rt/string.o`; \
	if (echo "$$NMOUTPUT" | ${GREP} '|main$$') || \
	   (echo "$$NMOUTPUT" | ${GREP} 'T main$$') || \
	   (echo "$$NMOUTPUT" | ${GREP} 'T _main$$'); \
	then  \
	    ${RM} "$@.d";\
	    $(COMPILE.cc) -O2 -I../../../xyzzy/src -Dmain=__nomain -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/laol/rt/string_nomain.o laol/rt/string.cxx;\
	else  \
	    ${CP} ${OBJECTDIR}/laol/rt/string.o ${OBJECTDIR}/laol/rt/string_nomain.o;\
	fi

# Run Test Targets
.test-conf:
	@if [ "${TEST}" = "" ]; \
	then  \
	    ${TESTDIR}/TestFiles/f1 || true; \
	else  \
	    ./${TEST} || true; \
	fi

# Clean Targets
.clean-conf: ${CLEAN_SUBPROJECTS}
	${RM} -r ${CND_BUILDDIR}/${CND_CONF}

# Subprojects
.clean-subprojects:

# Enable dependency checking
.dep.inc: .depcheck-impl

include .dep.inc
