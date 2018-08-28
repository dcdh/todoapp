# Openshift

> installation

TODO installation

> start

oc cluster up --host-data-dir=/mydata_axon --use-existing-config  --service-catalog=false

> stop

oc cluster down

# Fails :(

## QuartzEventScheduler;

import org.axonframework.eventhandling.scheduling.quartz.QuartzEventScheduler;

Use @PostConstruct and setters for defining dependencies by injection (like event bus). Too linked on Spring... :(

This kind of code will not work actually:

	@Inject
	private transient EventScheduler eventScheduler;

It is required not to Produce an QuartzEventScheduler actually.

