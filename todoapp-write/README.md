# Application

http://todoapp-write-developer.127.0.0.1.nip.io/swagger-ui/index.html?url=/api/swagger.json

# infrastructure

## mongo container

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

db.trackingtokens.find()

db.trackingtokens.drop()

db.snapshotevents.find()

db.sagas.find()

## mongo rest

http://eventstore-database-rest-developer.127.0.0.1.nip.io/api/domainevents?events.payloadType=com.damdamdeo.todoapp.api.event.ToDoItemCompletedEvent
http://eventstore-database-rest-developer.127.0.0.1.nip.io/api/domainevents?events.payloadType=com.damdamdeo.todoapp.api.event.ToDoItemCreatedEvent
http://eventstore-database-rest-developer.127.0.0.1.nip.io/api/domainevents?events.payloadType=com.damdamdeo.todoapp.api.event.ToDoItemDeadlineExpiredEvent

http://eventstore-database-rest-developer.127.0.0.1.nip.io/api/domainevents

http://eventstore-database-rest-developer.127.0.0.1.nip.io/api/trackingtokens

http://eventstore-database-rest-developer.127.0.0.1.nip.io/api/snapshotevents

http://eventstore-database-rest-developer.127.0.0.1.nip.io/api/sagas
