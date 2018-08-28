package com.damdamdeo.noteapp.it;

import org.arquillian.cube.openshift.impl.requirement.RequiresOpenshift;
import org.junit.experimental.categories.Category;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@Category(RequiresOpenshift.class)
@RequiresOpenshift
public class TodoFeatureITSteps {

	@Given("^the application noteapp-write is started$")
	public void the_application_noteapp_write_is_started() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
//		throw new PendingException();
	}

	@Given("^the application noteapp-search is started$")
	public void the_application_noteapp_search_is_started() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
//		throw new PendingException();
	}

	@When("^A new Todo item is created having this \"([^\"]*)\"$")
	public void a_new_Todo_item_is_created_having_this(String arg1) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
//		throw new PendingException();
	}

	@When("^The todo item is completed$")
	public void the_todo_item_is_completed() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
//		throw new PendingException();
	}

	@Then("^Theses events are expected \"([^\"]*)\"$")
	public void theses_events_are_expected(String arg1) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
//		throw new PendingException();
	}

	@When("^A research is made using this word \"([^\"]*)\"$")
	public void a_research_is_made_using_this_word(String arg1) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
//		throw new PendingException();
	}

	@Then("^The todo item is found and is completed$")
	public void the_todo_item_is_found_and_is_completed() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
//		throw new PendingException();
	}

	@When("^The deadline is expired$")
	public void the_deadline_is_expired() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
//		throw new PendingException();
	}

	@Then("^The todo item is found and is deadline is expired$")
	public void the_todo_item_is_found_and_is_deadline_is_expired() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
//		throw new PendingException();
		Thread.sleep(60000);
	}


}
