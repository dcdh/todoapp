# todoapp-write

This is the write side of the application. This application is dependent of the api *todoap-api*. It will only generate events from command. Saga and EventScheduler (SimpleEventScheduler) are used to ensure that there are working.

> maven

The *fabric8-maven-plugin* also known as *fmp* is used to deploy the application on OpenShift.
Scripts are provided to go easier and faster.

> building and running the app

The application depends on *todoapp-api*. So before executing the building sh script *openshift-run-developer.sh* you must execute the script *openshift-run-developer.sh* of the *todoapp-api* project. Next you can run *openshift-run-developer.sh* of this project.

> debugging the app

Prerequist:
The application must be running.

Just run *openshift-debug-developer.sh*. It will open a port (5005 or 8000) using a node port. Info on it will be displayed on the console. Next you can debug the application using the *Remote Java Application* on *Eclipse*.

Remark: you must have made a build before debugging (ie target directory must not be empty) because the plugin will used yml genreated in the build phase to target the good Pod.

> access using chrome or firefox

http://todoapp-write-developer.127.0.0.1.nip.io/swagger-ui/index.html?url=/api/swagger.json

# infrastructure

Just some documentation and useful command lines (avoid to seek for them on online documentation).

## Pod mongo

The pod for mongo is composed of two containers:
- centos/mongodb-36-centos7: mongo database
- linuxenko/mongo-rest: access mongo using rest api

### mongo container

Theses commands must be run from the container mongo in the Pod.

> connect to mongodb

mongo -u admin -p adminpassword --authenticationDatabase admin

> select eventstore database

use eventstoredb

show collections

> query it

db.domainevents.find()

db.domainevents.find({'events.payloadType':{$regex: /.*\.ToDoItemCompletedEvent/}})
db.domainevents.find({'events.payloadType':{$regex: /.*\.ToDoItemCreatedEvent/}})
db.domainevents.find({'events.payloadType':{$regex: /.*\.ToDoItemDeadlineExpiredEvent/}})

db.domainevents.drop()

db.trackingtokens.find()

db.trackingtokens.drop()

db.snapshotevents.find()

db.sagas.find()

db.sagas.drop()

> on line query example

mongo -u admin -p adminpassword --authenticationDatabase admin --eval="db = db.getSiblingDB('eventstoredb');db.domainevents.find()"

### mongo rest container

 I used this image *linuxenko/mongo-rest* which expose mongo using a rest API.

It's very usefull to use with firefox to see documents using json as output format. And it is very usefull for integration test too (cf dedicated paragraph in README.md present on *todoapp-it* project).

> domainevents

http://eventstore-database-rest-developer.127.0.0.1.nip.io/api/domainevents

http://eventstore-database-rest-developer.127.0.0.1.nip.io/api/domainevents?events.payloadType=com.damdamdeo.todoapp.api.event.ToDoItemCompletedEvent
http://eventstore-database-rest-developer.127.0.0.1.nip.io/api/domainevents?events.payloadType=com.damdamdeo.todoapp.api.event.ToDoItemCreatedEvent
http://eventstore-database-rest-developer.127.0.0.1.nip.io/api/domainevents?events.payloadType=com.damdamdeo.todoapp.api.event.ToDoItemDeadlineExpiredEvent

> trackingtokens

http://eventstore-database-rest-developer.127.0.0.1.nip.io/api/trackingtokens

> snapshotevents

http://eventstore-database-rest-developer.127.0.0.1.nip.io/api/snapshotevents

> sagas

http://eventstore-database-rest-developer.127.0.0.1.nip.io/api/sagas
