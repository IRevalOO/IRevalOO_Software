#!/bin/bash

if [ "$1" = "compile" ]
then
	ant jar_inst
	exit 1
fi


numOfDocs="-1"
onlyJudged="no"
allTopics="no"

metrics="official"
runpath="none"
qrelspath="none"
PJPATH=$PWD

relevance="binary"

resultview="overall"
resultexport="none"

helper="no"

while getopts ':M: :c :J :h :m: :Q: :R: :v: :e: :r:' opt; do
    case $opt in
        M)  numOfDocs="$OPTARG" ;;
        J)  onlyJudged="yes"    ;;
	c)  allTopics="yes"    ;;
	m)  metrics="$OPTARG"  ;;
	h)  helper="yes" 	;;
	Q) qrelspath="$OPTARG" ;;
	R) runpath="$OPTARG" ;;
	r) relevance="$OPTARG" ;;
	v) resultview="$OPTARG" ;;
	e) resultexport="$OPTARG" ;;
	"?")
        echo "Unknown option $OPTARG"
        exit 1;;
      	":")
        echo "No argument value for option $OPTARG"
        exit 1;;
      	*)
      	# Should not occur
        echo "Unknown error while processing options"
	exit 1;;
    esac
done

cd out/jar

java -jar TrecEvalOO_inst.jar ${numOfDocs} ${onlyJudged} ${allTopics} ${metrics} ${helper} $PJPATH/${qrelspath} $PJPATH/${runpath} ${resultview} $PJPATH/${resultexport} ${relevance}
