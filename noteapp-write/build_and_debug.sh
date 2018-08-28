#!/bin/bash
mvn clean install -Dmaven.test.skip=true && \
    java -jar -Xdebug -Xrunjdwp:transport=dt_socket,address=8000,server=y,suspend=y target/noteapp-write-swarm.jar -Djava.net.preferIPv4Stack=true -Deventstore.username=devuser -Deventstore.password=devpassword -Deventstore.database=eventstoredb -Deventstore.remote-host=eventstore-database.$(KUBERNETES_NAMESPACE).svc -Deventstore.remote-port=27017

