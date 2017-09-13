@search
@ui
Feature: A simple Google search

Scenario: A simple Google search scenario
  Given I am on the main Google search webpage
  And I search for "qastuffs.blogspot.com"
  
  When I click the search button
  And I click the first result
  Then I should land on the qastuffs blog webpage