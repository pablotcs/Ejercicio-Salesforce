
Feature: Login into application

Background: Positive test validating login
Given Initialize the browser with chrome 
And Navigate to "https://login.salesforce.com/?locale=es" site
When User enters username and password and logs in
Then Verify that user is succesfully logged in
And Click on waffle, and services

Scenario: Click on each new and cancel of all tabs
Given Get all tabs in a list
Then Click on each tab, and press new and cancel if it has
And Close Driver

Scenario: Create account
Given Enter accounts tab
And Click on new
And Get all the elements on a list
When User fills all the elements
Then Submit the creation 
And Close Driver

Scenario: Create empty Account
Given Enter accounts tab
And Click on new
When Submit the creation
Then Verify if the error is correct
And Close Driver

Scenario: Create new Contact
Given Enter Contacts tab in a new browsers tab
And Click on new
Then Get all the elements on a list
When User fills all the contact elements
Then Submit the creation
And Go back to previous tab
And Close Driver


Scenario: Edit Contact
Given Enter accounts tab
And Click on the record to edit
And Click on the arrow for more options
And Click on Edit button
Then Get labels and elements to edit in lists
And Edit those elements
Then Compare previous and new selected values
And Submit the creation
Then Close Driver

Scenario: Failing account edit
Given Enter accounts tab
And Click on the record to edit
And Click on the arrow for more options
And Click on Edit button
Then Get labels and elements to edit in lists
And Edit Empleados textbox
Then Submit the creation
And Check the error
Then Close Driver

Scenario: Creating accounts
Given Enter accounts tab
And Reach the Excel document
Then Create the different accounts
Then Close Driver





