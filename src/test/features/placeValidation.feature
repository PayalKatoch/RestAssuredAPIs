Feature: Validating Google Place API's

Scenario: Verify if place is successfully added or not
Given Add place playload
When User calls "AddPlaceAPI" with post http request
Then The Api call is success and verify the status code is 200
And "status" in response body is OK
