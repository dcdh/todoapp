package com.damdamdeo.todoapp.it;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.arquillian.cube.openshift.impl.enricher.AwaitRoute;
import org.arquillian.cube.openshift.impl.enricher.RouteURL;
import org.arquillian.cube.openshift.impl.requirement.RequiresOpenshift;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.experimental.categories.Category;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.response.ValidatableResponse;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@Category(RequiresOpenshift.class)
@RequiresOpenshift
public class TodoFeatureITSteps {

	private static final String PACKAGE_EVENEMENTS_METIERS = "com.damdamdeo.todoapp.api.event.";

	@RouteURL("eventstore-database-rest")
	private URL eventstoreDatabaseRestUrl;

	@RouteURL("solr")
	private URL solrUrl;

	@RouteURL("todoapp-write")
	@AwaitRoute
	private URL todoappWriteUrl;

	@RouteURL("todoapp-search")
	private URL todoappSearchUrl;

	@Given("^the application todoapp-write is started$")
	public void the_application_todoapp_write_is_started() throws Throwable {
		RestAssured.given(new RequestSpecBuilder().setBaseUri(todoappWriteUrl.toURI()).build())
			.log()
			.all()
			.when()
			.get("/api/service/ready")
			.then()
			.statusCode(Matchers.equalTo(200));
	}

	@Given("^the application todoapp-search is started$")
	public void the_application_todoapp_search_is_started() throws Throwable {
		RestAssured.given(new RequestSpecBuilder().setBaseUri(todoappSearchUrl.toURI()).build())
			.log()
			.all()
			.when()
			.get("/api/service/ready")
			.then()
			.statusCode(Matchers.equalTo(200));
	}

	@When("^A new Todo item is created having this todoId \"([^\"]*)\" and this description \"([^\"]*)\"$")
	public void a_new_Todo_item_is_created_having_this_todoId_and_this_description(final String todoId, final String description) throws Throwable {
		RestAssured.given(new RequestSpecBuilder().setBaseUri(todoappWriteUrl.toURI()).build())
			.given()
			.accept("application/json")
			.contentType("application/x-www-form-urlencoded; charset=utf-8")
			.formParam("todoId", todoId)
			.formParam("description", description)
			.log().all() 
			.when()
			.post("/api/v1/todoItem/createTodoItem")
			.then()
			.log().all()
			.and()
			.assertThat().statusCode(200)
			.body("todoId", CoreMatchers.equalTo(String.valueOf(todoId)))
			.body("description", CoreMatchers.equalTo(description))
			.body("status", CoreMatchers.equalTo("CREATED"));
	}

	@When("^The todo item with this todoId \"([^\"]*)\" and this description \"([^\"]*)\" is completed$")
	public void the_todo_item_with_this_todoId_and_this_description_is_completed(final String todoId, final String description) throws Throwable {
		RestAssured.given(new RequestSpecBuilder().setBaseUri(todoappWriteUrl.toURI()).build())
			.given()
			.accept("application/json")
			.contentType("application/x-www-form-urlencoded; charset=utf-8")
			.formParam("todoId", todoId)
			.log().all() 
			.when()
			.post("/api/v1/todoItem/" + todoId + "/completeTodoItem")
			.then()
			.log().all()
			.and()
			.assertThat().statusCode(200)
			.body("todoId", CoreMatchers.equalTo(String.valueOf(todoId)))
			.body("description", CoreMatchers.equalTo(description))
			.body("status", CoreMatchers.equalTo("COMPLETED"));
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Event {

		private String aggregateIdentifier;
		private String payloadType;
		public String getAggregateIdentifier() {
			return aggregateIdentifier;
		}
		public void setAggregateIdentifier(String aggregateIdentifier) {
			this.aggregateIdentifier = aggregateIdentifier;
		}
		public String getPayloadType() {
			return payloadType;
		}
		public void setPayloadType(String payloadType) {
			this.payloadType = payloadType;
		}
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Domainevents {

		private List<Event> events;
		public List<Event> getEvents() {
			return events;
		}
		public void setEvents(List<Event> events) {
			this.events = events;
		}

	}

	@Then("^Theses events are expected \"([^\"]*)\" for this todoId \"([^\"]*)\"$")
	public void theses_events_are_expected_for_this_todoId(final String events, final String todoId) throws Throwable {
		final Domainevents[] domainevents = RestAssured.given(new RequestSpecBuilder().setBaseUri(eventstoreDatabaseRestUrl.toURI()).build())
				.log()
				.all()
				.when()
				.get("/api/domainevents")
				.then()
				.log()
				.all()
				.statusCode(Matchers.equalTo(200))
				.extract()
				.as(Domainevents[].class);
		Arrays.asList(events.split(","))
			.stream()
			.map(String::trim)
			.forEach(eventAttendu -> {
				try {
					final String errorMsg = String.format("domainevents '%s' non trouvé pour le todoId '%s'", eventAttendu, todoId);
					Assert.assertTrue(errorMsg, Arrays.asList(domainevents)
						.stream()
						.flatMap(d -> d.getEvents().stream())
						.filter(event -> todoId.equals(event.aggregateIdentifier))
						.filter(event -> event.payloadType.equals(PACKAGE_EVENEMENTS_METIERS + eventAttendu))
						.count() > 0l);
				} catch (final Exception e) {
					throw new RuntimeException(e);
				}
			});
	}

	private ValidatableResponse reponse;

	@When("^A search is made using this word \"([^\"]*)\"$")
	public void a_search_is_made_using_this_word(final String word) throws Throwable {
		Thread.sleep(5000);// wait read asynchronous
		reponse = RestAssured.given(new RequestSpecBuilder().setBaseUri(todoappSearchUrl.toURI()).build())
				.given()
				.accept("application/json")
				.contentType("application/x-www-form-urlencoded; charset=utf-8")
				.formParam("description", word)
				.log().all()
				.when()
				.post("/api/v1/todoItem/search")
				.then()
				.log().all()
				.and()
				.assertThat().statusCode(200)
				.body("[0].todoId", CoreMatchers.notNullValue())
				.body("[0].description", CoreMatchers.notNullValue())
				.body("[0].status", CoreMatchers.notNullValue());
	}

	@When("^The deadline is expired$")
	public void the_deadline_is_expired() throws Throwable {
		Thread.sleep(10000);// deadline expire at 5 sec. Having 3 sec more ensure that search query will be updated
	}

	@Then("^The todo item with this todoId \"([^\"]*)\" and this description \"([^\"]*)\" is found and is completed$")
	public void the_todo_item_with_this_todoId_and_this_description_is_found_and_is_completed(final String todoId, final String description) throws Throwable {
		reponse
			.body("todoId", CoreMatchers.equalTo(todoId))
			.body("description", CoreMatchers.equalTo(description))
			.body("status", CoreMatchers.equalTo("COMPLETED"));
	}

	@Then("^The search return this todo item with this todoId \"([^\"]*)\" and this description \"([^\"]*)\" is found and is completed$")
	public void the_search_return_this_todo_item_with_this_todoId_and_this_description_is_found_and_is_completed(final String todoId, final String description) throws Throwable {
		reponse
			.body("[0].todoId", CoreMatchers.equalTo(todoId))
			.body("[0].description", CoreMatchers.equalTo(description))
			.body("[0].status", CoreMatchers.equalTo("COMPLETED"));
	}

	@When("^The todo item with this todoId \"([^\"]*)\" is retrieved$")
	public void the_todo_item_with_this_todoId_is_retrieved(final String todoId) throws Throwable {
		reponse = RestAssured.given(new RequestSpecBuilder().setBaseUri(todoappSearchUrl.toURI()).build())
				.given()
				.accept("application/json")
				.get("/api/v1/todoItem/" + todoId)
				.then()
				.log().all()
				.and()
				.assertThat().statusCode(200)
				.body("todoId", CoreMatchers.notNullValue())
				.body("description", CoreMatchers.notNullValue())
				.body("status", CoreMatchers.notNullValue());
	}

	@Then("^The todo item with this todoId \"([^\"]*)\" and this description \"([^\"]*)\" is found and his deadline is expired$")
	public void the_todo_item_with_this_todoId_and_this_description_is_found_and_his_deadline_is_expired(final String todoId, final String description) throws Throwable {
		reponse
			.body("todoId", CoreMatchers.equalTo(todoId))
			.body("description", CoreMatchers.equalTo(description))
			.body("status", CoreMatchers.equalTo("OVERDUE"));
	}

	@Then("^The search return this todo item with this todoId \"([^\"]*)\" and this description \"([^\"]*)\" is found and his deadline is expired$")
	public void the_search_return_this_todo_item_with_this_todoId_and_this_description_is_found_and_his_deadline_is_expired(final String todoId, final String description) throws Throwable {
		reponse
			.body("[0].todoId", CoreMatchers.equalTo(todoId))
			.body("[0].description", CoreMatchers.equalTo(description))
			.body("[0].status", CoreMatchers.equalTo("OVERDUE"));
	}

}
