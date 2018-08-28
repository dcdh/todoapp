package com.damdamdeo.noteapp.it;

import org.junit.runner.RunWith;

import cucumber.runtime.arquillian.CukeSpace;
import cucumber.runtime.arquillian.api.Features;
import cucumber.runtime.arquillian.api.Glues;

@Features("com/damdamdeo/noteapp/it/Todo.feature")
@RunWith(CukeSpace.class)
@Glues(TodoFeatureITSteps.class)
public class TodoFeatureIT {

}
