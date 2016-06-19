## Synopsis

A simple example of  how to run Gatling test scenarios as a gradle task 

### How to run IT

Execute all test scenarios and setting 10 as the number of concurrent users
`gradle clean build gatling -Pusers=10`

Execute one specific scenario and setting 10 as the number of concurrent users
`gradle clean build gatling-io.sandbox.gatling.MySimulation -Pusers=10`
