Feature: Test booking page

 Scenario: Test hotels existing for required dates
   Given I am on main booking page
   When I select dates and search by Moscow
   Then I see there are hotels for required dates
   
 Scenario: Test that max rating more than 9 
   Given I am on main booking page
   When I select dates and search by Moscow and sort by rating and get first hotel
   Then I see that rating >=9   