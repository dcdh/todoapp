Feature: Todo

  Scenario Outline: nominal completed case
    Given the application noteapp-write is started
    Given the application noteapp-search is started
    When A new Todo item is created having this "<description>"
    When The todo item is completed
    Then Theses events are expected "<expected_events>"
    When A research is made using this word "<word>"
    Then The todo item is found and is completed

    Examples: 
      | description            | expected_events                             | word   |
      | Preparer pot de départ | ToDoItemCreatedEvent,ToDoItemCompletedEvent | depart |

  Scenario Outline: nominal completed case
    Given the application noteapp-write is started
    Given the application noteapp-search is started
    When A new Todo item is created having this "<description>"
    When The deadline is expired
    Then Theses events are expected "<expected_events>"
    When A research is made using this word "<word>"
    Then The todo item is found and is deadline is expired

    Examples: 
      | description          | expected_events                                   | word  |
      | Terminer cette tâche | ToDoItemCreatedEvent,ToDoItemDeadlineExpiredEvent | tache |
