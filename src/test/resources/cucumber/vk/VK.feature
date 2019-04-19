Feature: Test VK 
 
Scenario: Add post to wall and check that presented    
   When Add post to wall
   Then Wall contains post with message
   
Scenario: Edit post on wall and check that result presented    
   When Edit post on wall
   Then Wall contains edited post  
   
Scenario: Delete post and check that post was deleted    
   When Delete Post on the wall
   Then Wall does not contain post      