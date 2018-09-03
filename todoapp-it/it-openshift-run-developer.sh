#!/bin/bash
eval $(minishift oc-env); \
# https://github.com/arquillian/arquillian-cube/issues/1040
# oc login -u developer -p developer; \
oc login -u system:admin; \
oc project developer; \
# forkCount=0 fix a race condition. Sometimes, resources file like openshift.yml are copied twice in the target repository
# and placeholders are not replaces :(
mvn -DforkCount=0 clean verify -Dimage-namespace=developer \
  -Dmaven.failsafe.debug="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=8000 -Xnoagent -Djava.compiler=NONE"
