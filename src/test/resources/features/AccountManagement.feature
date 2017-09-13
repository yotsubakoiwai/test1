@register
@ui
Feature: Manage account on Mecury Tours web site

Scenario: Register a new account
  Given I am on the Mecury Tours web site
  When I request to register a new account
  Then I am asked for and enter Contact Information:
    |firstName   | lastName | phone       | email                         |
    |Guinea      | Piglette   | 888-555-1234| guinea.piglette3@somedomain.com |
  And I am asked for and enter Mailing Information:
    | address1        | address2                 | city       | state              | postalCode | country       |
    | The White House | 1600 Pennsylvania Ave NW | Washington | DC                 | 20500      | United States |
  And I am asked for and enter User Information:
    | userName    | password    | confirmPassword |
    | piglette3   | Go#piggies1 | Go#piggies1     |
  When I submit the new registration request
  Then the registered page is displayed with salutations to "Guinea Piglette" and with user name "piglette3"
  
@api
@registerapi
Scenario: Repeat register a new using using API testing
  Given I create a request for URL "http://newtours.demoaut.com"
  When I send the registration request with this user input:
    |address1        | The White House          |
    |address2        | 1600 Pennsylvania Ave NW |
    |city            | Washington               |
    |confirmPassword | Go#piggies1              |
    |country         | 215                      |
    |email           | piglette3                |
    |firstName       | Guinea                   |
    |lastName        | Piglette                 |
    |mercury         | process                  |
    |password        | Go#piggies1              |
    |phone           | 555-555-5555             |
    |postalCode      | 20500                    |
    |register.x      | 21                       |
    |register.y      | 12                       |
    |state           | ND                       |
    |userName        | freddie@domain.com       |
    
  Then the registration status is "200"