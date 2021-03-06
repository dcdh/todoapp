# Presentation

This is a sample project used to demonstrate how to use axon in JEE applications.

This project allow to create todo items and search them.

This project is compound of two main applications:
- **todoapp-write**: a write application dedicated to create domain events;
- **todoapp-search**: a search application used to project domain event in a solr database.

Each applications are accessible using a rest api.

Applications are hosted on an Openshift Instance.

A project called **todoapp-it** is used to run integration test. Arquillian cube is used to run them on openshift.
The main objectif is to be able to run integration test using the real infrastructure: no more fongo (for mongo), no more h2 (for sql database), no more embedded solr (for solr)...

On each project a README.md is provided as documentation to explain how each of them work.

On each project sh scripts are provided to build and debug application containing maven command and other to go easy and faster.

## Openshift

We are in a microservice project: write side and a read side (but there can be many read side using different technologies as projections).
A microservices architecture implies a lot of applications. It can be hard to deploy (no matter the target environment: dev, UAT, preproduction, production), manage, setup configuration links between each of them.

So, to be more efficient and simplify the run the use of an orchestrator is necessary.

I have chosen OpenShift because it had a lot of features out of the box (containerisation, authentification, authorization, nice webconsole, project partitionment, CI/CD tools ...)

With theses tools we can be able to use the principe of 'Golden Image': an application is an image and this image must not change between project phases from the development to the production.

> version

There are two versions of OpenShift: local one (using minishift) and server one.

I personnaly used the server one on my laptop because minishift use a Virtual Box and it consumes a lot of resources on it (CPU and Disk IO) making my system hanging when a build is running and it makes my CPU burns :(

> installation **v3.9.0 arch linux**

The following instructions are for **arch** linux with version 3.9.0 of OpenShift.

Warning: OpenShift v3.9.0 support only max docker version of 1:17.09.0-1

> docker setup

sudo vim /usr/lib/systemd/system/docker.service

add this **--insecure-registry 172.30.0.0/16**
to obtain this

```
ExecStart=/usr/bin/dockerd -H fd:// \
  --insecure-registry 172.30.0.0/16
```

sudo systemctl daemon-reload
sudo systemctl restart docker

> openshift installation

```
git clone https://aur.archlinux.org/openshift-origin-server-bin.git
cd openshift-origin-server-bin
makepkg
sudo pacman -U openshift-origin-server-bin-3.9.0-1-x86_64.pkg.tar

export KUBECONFIG=/var/lib/origin/openshift.local.config/master/admin.kubeconfig
export CURL_CA_BUNDLE=/var/lib/origin/openshift.local.config/master/ca.crt
sudo chmod +r /var/lib/origin/openshift.local.config/master/admin.kubeconfig
```

You can used an installer using *ansible*. I have not used it.

To avoid pulling images when first deployment of applications like mongo, solr... (which can be long and make your first deployment failing) I recommand to execute this command line:

```
docker pull openshift/origin-docker-registry:v3.9.0 \
  && docker pull openshift/origin-haproxy-router:v3.9.0 \
  && docker pull openshift/origin-web-console:v3.9.0 \
  && docker pull openshift/origin-docker-builder:v3.9.0 \
  && docker pull centos/mongodb-36-centos7 \
  && docker pull fabric8/java-jboss-openjdk8-jdk:1.2 \
  && docker pull linuxenko/mongo-rest \
  && docker pull giantswarm/tiny-tools
```

> start

 Start a persistent server cluster using this command line

oc cluster up --host-data-dir=/mydata_axon --use-existing-config  --service-catalog=false

To ensure that OpenShift is started and running: a message should be displayed giving the url of the webconsole *https://127.0.0.1:8443*
Also executing *docker ps* should display a lot of containers where names begin with *k8s*

> stop

 If you want to stop OpenShift just run this command line:

oc cluster down
