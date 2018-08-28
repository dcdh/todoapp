#!/bin/bash
eval $(minishift oc-env); \
# https://github.com/arquillian/arquillian-cube/issues/1040
# oc login -u developer -p developer; \
oc login -u system:admin; \
oc project developer; \
# forkCount=0 fix race condition. De temps en temps mes fichiers resources de tests sont copiés une deuxième fois et les placeholders ne sont pas remplacé.
mvnDebug -DforkCount=0 clean verify -Dimage-namespace=developer \
  -Dmaven.failsafe.debug="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=8000 -Xnoagent -Djava.compiler=NONE"
