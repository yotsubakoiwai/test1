@api
@odata
Feature: Demonstrate ability to test OData Service Documents
  The Open Data Protocol (OData) enables the creation of HTTP-based data 
  services, which allow resources identified using Uniform Resource 
  Identifiers (URIs) and defined in an abstract data model, to be published 
  and edited by Web clients using simple HTTP messages.

Scenario: Parse a canned OData Service document
  Given I have a simple service document
  When I search for available collections I find "3"