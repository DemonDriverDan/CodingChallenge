# CodingChallenge

### Running
To run the application first build with `mvn clean install` then either launch through the `Launcher` class 
or from the command line with `java -jar target\landbay-1.0-SNAPSHOT.jar`.

### Assumptions

1. Investment diversity is a percentage of total investment split across all available loans
2. Loan interest rate is set on the end point, there should be a class to calculate that based on 
customer risk etc.
3. Account IDs are passed to represent specific customers, this should be a session token or some
other authentication method
4. Interest is paid monthly
5. Test coverage is minimal, only testing the `InvestmentManagerImpl` class
