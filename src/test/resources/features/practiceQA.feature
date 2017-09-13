@ui
Feature: Practice exercises at toolsQA

@timingalert
Scenario: Wait for timing alert
Given I am on the automation practice switch window
When I start the timing alert
Then I accept the timing alert

@chkradio
Scenario: Select Radio and checkboxes
Given I am on the automation-practice-form
When I select "other" for category Sex
And I select "male" for category Sex
And I select "female" for category Sex
And I select "3" years of experience
And I select as my profession "Automation Tester"
And I select automation tool "Selenium Webdriver"