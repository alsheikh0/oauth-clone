Feature : User Registration

  Scenario :  User registers with valid details
    Given the user provides a valid email "test@example.com"
    And the user provides a valid username "testUser"
    And the user provides a valid password "securePassword"
    Then the user should be successfully registered