# todoapp-search

This is a query side of the application. This application is dependent of the api *todoap-api*. It use a Solr database to index TodoItem and give the hability to search them using proximity search. It use french language.

> maven

The *fabric8-maven-plugin* also known as *fmp* is used to deploy the application on OpenShift.
Scripts are provided to go easier and faster.

> building and running the app

The application depends on *todoapp-api*. So before executing the building sh script *openshift-run-developer.sh* you must execute the script *openshift-run-developer.sh* of the *todoapp-api* project. Next you can run *openshift-run-developer.sh* of this project.

> debugging the app

Prerequist:
The application *todoapp-search* must be **running**.

Just run *openshift-debug-developer.sh*. It will open a port (5005 or 8000) using a node port. Info on it will be displayed on the console. Next you can debug the application using the *Remote Java Application* on *Eclipse*.

Remark: you must have made a build before debugging (ie target directory must not be empty) because the plugin will used yml generated in the build phase to target the good Pod.

> access using chrome or firefox

http://todoapp-search-developer.127.0.0.1.nip.io/swagger-ui/index.html?url=/api/swagger.json

# infrastructure

Just some useful links (avoid to seek for them on online documentation).

## solr

http://solr-developer.127.0.0.1.nip.io/solr/#/

### delete query

http://solr-developer.127.0.0.1.nip.io/solr/#/mycores/documents

/update

xml

<delete><query>*:*</query></delete> 
