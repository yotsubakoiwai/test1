@blz
Feature: Exercise SOAP requests using thomas-bayer endpoint

@api
Scenario: Request a bank using its bank code
  Given I build a request to get bank codes from "http://www.thomas-bayer.com"
  And the namespace is "http://thomas-bayer.com/blz/"
  When I send the request to "/axis2/services/BLZService" to get bank code "10030400"
  Then Bezeichnung is "ABK-Kreditbank"
  And bic is "ABKBDEB1XXX"
  And ort is "Berlin" and plz is "10789"
  Then name space "ns1" is "http://thomas-bayer.com/blz/"