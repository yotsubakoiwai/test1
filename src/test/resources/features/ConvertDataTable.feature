@datatab
@api
Feature: Convert a data table to an object

Scenario: Convert a one column list of integers into a List object of ints
    Given a list of numbers:
      |100 |
      | 99 |
      | 98 |
      
    When I sum them
    Then I should get "297"
    
Scenario: Convert a two column table to a map
    Given a price per item list:
      | coke   | 1 |
      | cookie | 2 |
      
    When I order "1" "coke" and "1" "cookie"
    Then my total owed is "3"
    
@datatab3
Scenario: An international coffee shop must handle currencies
    Given the price list for an international coffee shop
      | product | currency | price | happy |
      | coffee  | EUR      | 1     | true  |
      | donut   | SEK      | 18    | false |
    When I buy "1" "coffee" and "1" "donut"
    Then I should pay "1" EUR be "happy" and "18" SEK be "unhappy"