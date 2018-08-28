#!/bin/bash
eval $(minishift oc-env); \
oc login -u=developer -p=developer; \
oc project developer; \
mvn fabric8:debug -Dfabric8.debug.suspend -Popenshift -Dimage-namespace=developer
