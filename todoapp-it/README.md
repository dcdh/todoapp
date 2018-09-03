# todoapp-it

The application is only intended to run integration tests on OpenShift.
The main objective is to run them using the expected production infrastructure: no more H2 (for JPA), Fongo (for Mongo), Embedded Solr (for Solr)... only the production infrastructure: Mongo, Solr, PostgreSQL (not used here), NEO4J (not used here), Oracle (forget about it...) ...;

BDD is used (instead of simple Junit). Please have a look to *Todo.feature* to see what kind of tests are made.

## setup

IT will be run on a dedicated random project called *IT-xxxxxxxx* to allow running multiple IT on different projects. We need to use a user having the capability to create project on a cluster node.

This setup will create an admin having all capabilities on cluster node.
Warning: be careful it is dangerous.
At this time, I didn't find a way for a developer user to launch it on new project (without seeing all projects).

Using the *admin* user you will be able to access to the generated project (using the webconsole for example) to ensure that pods are well executing (so no one is on *No Deployment* for example).

### admin

Theses following lines will create and setup an admin user.

> create admin user

oc create user admin

> create a password defined to *admin*

oc create identity anypassword:admin

oc create useridentitymapping anypassword:admin admin

> add the *admin cluster user* to the admin user to allow him to list and create projects

oc adm policy add-cluster-role-to-user cluster-admin admin

## Arquillian

Tests are using Arquillian Cube.

Arquillian is a framework to run a Java application test on a host. It support a lot of application server (like Tomcat, Jetty...).
The sub project *Arquillian Cube* have an OpenShift module which is able to communicate with an OpenShift instance using a rest API.
SpaceCuke is used to run Cucumber (BDD) tests (easier to read than normal JUnit test).

### pods communication

Because tests are run outside OpenShift, all applications (like Wildfly Swarm, SpringBoot...) and infrastructure (like databases as Mongo, Solr...) must be accessible using a Rest API. I did not find a way to define a NodePort easily using Arquillian so you can't use in your test file client like MongoClient or other.

Hopefully nice projects exist to expose databases using rest API.
Here are some examples:
- postgrest (TODO docker hub) for PostgreSQL,
- linuxenko/mongo-rest (TODO docker hub) for Mongo...
- ...

In this project theses Arquillian projects are not used (but it could be): Drone (== Selenium) for Web IHM, Arquillian Algeron Pact for Consumer Driven Contracts ...

### openshift.yml

This file is essential. It correspond to the template used to run Arquillian tests.

I have put some comments on it to split applications definitions.

When running the tests, some placeholders are replaces using maven resource filter:
- ${image-namespace} : correspond to the source namespace (aka project) where the image is present (generaly developement project)
- ${project.version} : correspond to the image version to use from the source project.

**Tips:** when defining the image, I'll run it in a temp project to ensure that every application are well defined.
**Tips:** I used init containers to wait for dependent pods to be ready (like todoapp-write must wait that mongo is ready)

## running

You've got two sh scripts *run* and *debug*.

*run* will just run all tested.

*debug* will debug tests but NOT your applications running inside pods.
If you want to debug your pods you need to run the debug script of your application. But be aware about the timing...

## known issue

A race condition is present when running the tests about *openshift.yml* rewrited **twice**. When it occurs, placeholders defining the expected image to use are not replaced.

A workaround exist consisting to run test with a *forkCount* defined to 0.
Please used provided script *it-openshift-run-developer.sh* and *it-openshift-debug-developer.sh* which handle this issue to run tests.
