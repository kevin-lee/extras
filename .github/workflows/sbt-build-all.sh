#!/bin/bash -e

set -x

: ${CURRENT_BRANCH_NAME:?"CURRENT_BRANCH_NAME is missing."}

#  test_task="+test scalafix"
test_task="+test"
if [ "$1" == "report" ]
then
  # For now it does nothing
#  test_task="coverage test coverageReport coverageAggregate"
#  test_task="coverage test scalafix coverageReport coverageAggregate coveralls"
  echo "report build but it does nothing for now."
fi

#echo "sbt -J-Xmx2048m -v clean ${test_task}"
#sbt \
#  -v \
#  clean \
#  ${test_task}

export SOURCE_DATE_EPOCH=$(date +%s)
echo "SOURCE_DATE_EPOCH=$SOURCE_DATE_EPOCH"

if [[ "$CURRENT_BRANCH_NAME" == "main" || "$CURRENT_BRANCH_NAME" == "release" ]]
then
  sbt \
    -v \
    clean \
    ${test_task} \
    +packagedArtifacts
else
  sbt \
    -v \
    clean \
    ${test_task} \
    +package
fi


echo "============================================"
echo "Building projects: Done"
echo "============================================"
