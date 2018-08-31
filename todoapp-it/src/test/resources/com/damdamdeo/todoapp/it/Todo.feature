Feature: Todo

  Scenario Outline: nominal completed case
    Given the application todoapp-write is started
    Given the application todoapp-search is started
    When A new Todo item is created having this todoId "<todoId>" and this description "<description>"
    When The todo item with this todoId "<todoId>" and this description "<description>" is completed
    Then Theses events are expected "<expected_events>" for this todoId "<todoId>"
    When A search is made using this word "<word>"
    Then The search return this todo item with this todoId "<todoId>" and this description "<description>" is found and is completed
    When The todo item with this todoId "<todoId>" is retrieved
    Then The todo item with this todoId "<todoId>" and this description "<description>" is found and is completed

    Examples: 
      | todoId | description            | expected_events                             | word   |
      |      1 | Preparer pot de départ | ToDoItemCreatedEvent,ToDoItemCompletedEvent | depart |

  Scenario Outline: nominal completed case
    Given the application todoapp-write is started
    Given the application todoapp-search is started
    When A new Todo item is created having this todoId "<todoId>" and this description "<description>"
    When The deadline is expired
    Then Theses events are expected "<expected_events>" for this todoId "<todoId>"
    When A search is made using this word "<word>"
    Then The search return this todo item with this todoId "<todoId>" and this description "<description>" is found and his deadline is expired
    When The todo item with this todoId "<todoId>" is retrieved
    Then The todo item with this todoId "<todoId>" and this description "<description>" is found and his deadline is expired

    Examples: 
      | todoId | description          | expected_events                                   | word     |
      |      2 | Terminer cette tâche | ToDoItemCreatedEvent,ToDoItemDeadlineExpiredEvent | terminer |
