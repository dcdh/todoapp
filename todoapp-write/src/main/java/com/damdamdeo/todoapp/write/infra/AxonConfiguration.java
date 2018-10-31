package com.damdamdeo.todoapp.write.infra;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import org.axonframework.eventhandling.tokenstore.TokenStore;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.messaging.correlation.CorrelationDataProvider;
import org.axonframework.messaging.correlation.SimpleCorrelationDataProvider;
import org.axonframework.mongo.MongoTemplate;
import org.axonframework.mongo.eventsourcing.eventstore.MongoEventStorageEngine;
import org.axonframework.mongo.eventsourcing.eventstore.documentpercommit.DocumentPerCommitStorageStrategy;
import org.axonframework.mongo.eventsourcing.tokenstore.MongoTokenStore;
import org.axonframework.serialization.xml.XStreamSerializer;
import org.bson.Document;

import com.arjuna.ats.internal.arjuna.recovery.PeriodicRecovery.Status;
import com.damdamdeo.todoapp.api.event.ToDoItemCompletedEvent;
import com.damdamdeo.todoapp.api.event.ToDoItemCreatedEvent;
import com.damdamdeo.todoapp.api.event.ToDoItemDeadlineExpiredEvent;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@ApplicationScoped
public class AxonConfiguration {

	public static final String DOMAIN_EVENTS = "domainevents";
	public static final String TRACKING_TOKENS = "trackingtokens";
	public static final String SNAPSHOT_EVENTS = "snapshotevents";
	public static final String SAGAS = "sagas";

	@Inject
	@Named("mongodbwriteeventstore")
	private MongoDatabase databaseWriteEventStore;

	@Produces
	@ApplicationScoped
	public EmbeddedEventStore produceEmbeddedEventStore() {
		final EventStorageEngine eventStorageEngine = new MongoEventStorageEngine(mongoTemplate());
		return new EmbeddedEventStore(eventStorageEngine);
	}

	@Produces
	@ApplicationScoped
	public TokenStore produceTokenStore() {
		return new MongoTokenStore(mongoTemplate(), new XStreamSerializer());
	}

	@Produces
	@ApplicationScoped
	public EventStorageEngine eventStorageEngine() {
		final XStreamSerializer xStreamSerializer = new XStreamSerializer();
		xStreamSerializer.addAlias("Status", Status.class);
		xStreamSerializer.addAlias("ToDoItemCompletedEvent", ToDoItemCompletedEvent.class);
		xStreamSerializer.addAlias("ToDoItemCreatedEvent", ToDoItemCreatedEvent.class);
		xStreamSerializer.addAlias("ToDoItemDeadlineExpiredEvent", ToDoItemDeadlineExpiredEvent.class);
		return new MongoEventStorageEngine(xStreamSerializer, null, mongoTemplate(), new DocumentPerCommitStorageStrategy());
	}

	@Produces
	@ApplicationScoped
	public CorrelationDataProvider correlationDataProvider() {
		return new SimpleCorrelationDataProvider("todoItem");
	}

	private MongoTemplate mongoTemplate() {
		return new MongoTemplate() {

			@Override
			public MongoCollection<Document> trackingTokensCollection() {
				return databaseWriteEventStore.getCollection(TRACKING_TOKENS);
			}

			@Override
			public MongoCollection<Document> snapshotCollection() {
				return databaseWriteEventStore.getCollection(SNAPSHOT_EVENTS);
			}

			@Override
			public MongoCollection<Document> sagaCollection() {
				return databaseWriteEventStore.getCollection(SAGAS);
			}

			@Override
			public MongoCollection<Document> eventCollection() {
				return databaseWriteEventStore.getCollection(DOMAIN_EVENTS);
			}

		};
	}

}
