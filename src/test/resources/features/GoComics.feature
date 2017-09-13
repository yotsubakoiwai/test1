@hobbes
@ui
Feature: Open Calvin and Hobbes

Scenario: Display today's Calvin and Hobbes comic strip
  Given I am on the GOCOMICS comics page
  When I select "Calvin and Hobbes"
  Then the "Calvin and Hobbes" comic strip is displayed