Feature: Test mail.ru

Scenario: Test presentation of exit link after login
   Given I am logged in mail.ru  
   Then I see that exit link is presented   
 
Scenario: I see expected message after sending emails 
   Given I am logged in mail.ru
   When I send emails
   Then I see message that mail was send

Scenario: If I flag 3 checkboxes then 3 checkboxes are flagged 
   Given I am logged in mail.ru
   When I flagged 3 checkboxes
   Then I see 3 flagged checkboxes   
   
Scenario: If I flag 3 checkboxes and then unflag checkboxes then all flags are deselected 
   Given I am logged in mail.ru
   When I flagged 3 checkboxes
   And I remove all flags
   Then I see that all flags are deselected   
   
Scenario: If I move mail to spam then I see alert 
   Given I am logged in mail.ru
   When I move mail to spam
   Then I see alert   
   
Scenario: If I return mail from spam then I see alert 
   Given I am logged in mail.ru
   When I go to spam
   And I return mail from spam
   Then Alert 'Move from Spam' presented
   
Scenario: Test presentation of WriteEmail button
   Given I am logged in mail.ru  
   Then I see that of WriteEmail button is presented 