#!/bin/bash
eval $(minishift oc-env); \
oc login -u=developer -p=developer; \
oc project developer; \
mvn clean fabric8:deploy -Dfabric8.debug.enabled=true -Popenshift -Dimage-namespace=developer
