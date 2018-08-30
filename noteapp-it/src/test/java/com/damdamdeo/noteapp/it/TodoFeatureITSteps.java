package com.damdamdeo.noteapp.it;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.arquillian.cube.openshift.impl.enricher.AwaitRoute;
import org.arquillian.cube.openshift.impl.enricher.RouteURL;
import org.arquillian.cube.openshift.impl.requirement.RequiresOpenshift;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.experimental.categories.Category;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.ValidatableResponse;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@Category(RequiresOpenshift.class)
@RequiresOpenshift
public class TodoFeatureITSteps {

	private static final String PACKAGE_EVENEMENTS_METIERS = "com.damdamdeo.noteapp.api.event.";

	@RouteURL("eventstore-database-rest")
	private URL eventstoreDatabaseRestUrl;

	@RouteURL("solr")
	private URL solrUrl;

	@RouteURL("noteapp-write")
	@AwaitRoute
	private URL noteappWriteUrl;

	@RouteURL("noteapp-search")
	private URL noteappSearchUrl;

	@Given("^the application noteapp-write is started$")
	public void the_application_noteapp_write_is_started() throws Throwable {
		RestAssured.given(new RequestSpecBuilder().setBaseUri(noteappWriteUrl.toURI()).build())
			.log()
			.all()
			.when()
			.get("/api/service/ready")
			.then()
			.statusCode(Matchers.equalTo(200));
	}

	@Given("^the application noteapp-search is started$")
	public void the_application_noteapp_search_is_started() throws Throwable {
		RestAssured.given(new RequestSpecBuilder().setBaseUri(noteappSearchUrl.toURI()).build())
			.log()
			.all()
			.when()
			.get("/api/service/ready")
			.then()
			.statusCode(Matchers.equalTo(200));
	}

	@When("^A new Todo item is created having this todoId \"([^\"]*)\" and this description \"([^\"]*)\"$")
	public void a_new_Todo_item_is_created_having_this_todoId_and_this_description(final String todoId, final String description) throws Throwable {
		RestAssured.given(new RequestSpecBuilder().setBaseUri(noteappWriteUrl.toURI()).build())
			.given()
			.accept(ContentType.JSON)
			.contentType(ContentType.URLENC)
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
		RestAssured.given(new RequestSpecBuilder().setBaseUri(noteappWriteUrl.toURI()).build())
			.given()
			.accept(ContentType.JSON)
			.contentType(ContentType.URLENC)
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

	private class Event {

		private String aggregateIdentifier;
		private Integer sequenceNumber;
		private String payloadType;
		private String serializedPayload;

	}

	private class Domainevents {

		private List<Event> events;

	}

	@Then("^Theses events are expected \"([^\"]*)\" for this todoId \"([^\"]*)\"$")
	public void theses_events_are_expected_for_this_todoId(final String events, final String todoId) throws Throwable {
//		Arrays.asList(events.split(";"))
//			.stream()
//			.forEach(event -> {
//				try {
//					final Domainevents domainevents = RestAssured.given(new RequestSpecBuilder().setBaseUri(eventstoreDatabaseRestUrl.toURI()).build())
//						.log()
//						.all()
//						.when()
//						.get("/api/domainevents?events.payloadType=" + PACKAGE_EVENEMENTS_METIERS + event)
//						.then()
//						.log()
//						.all()
//						.statusCode(Matchers.equalTo(200))
//						.extract()
//						.as(Domainevents.class);
//					System.out.println(domainevents);
//				} catch (final Exception e) {
//					throw new RuntimeException(e);
//				}
//			});
//		TODO
//		recuperer la liste NEGATIF faire une requete pour chaque me retournant une liste, extraire puis filtrer dessus :)
//		generer l'event via xstream
//		utiliser l'id
	}

	private ValidatableResponse reponse;

	@When("^A search is made using this word \"([^\"]*)\"$")
	public void a_search_is_made_using_this_word(final String word) throws Throwable {
		Thread.sleep(5000);// wait read asynchronous
		reponse = RestAssured.given(new RequestSpecBuilder().setBaseUri(noteappSearchUrl.toURI()).build())
				.given()
				.accept(ContentType.JSON)
				.contentType(ContentType.URLENC)
				.formParam("description", word)
				.log().all()
				.when()
				.post("/v1/todoItem/search")
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
		Thread.sleep(5000);// deadline expire at 2 sec. Having 3 sec more ensure that search query will be updated
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
		reponse = RestAssured.given(new RequestSpecBuilder().setBaseUri(noteappSearchUrl.toURI()).build())
				.given()
				.accept(ContentType.JSON)
				.get("/v1/todoItem/" + todoId)
				.then()
				.log().all()
				.and()
				.assertThat().statusCode(200)
				.body("[0].todoId", CoreMatchers.notNullValue())
				.body("[0].description", CoreMatchers.notNullValue())
				.body("[0].status", CoreMatchers.notNullValue());
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
