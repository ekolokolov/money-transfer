#MoneyTransfer

Application for transfer  money between user accounts

#Installation

Use Maven 3.3.9+ for make moneyTransfer.jar

```bash
mvn clean package
``` 
#Run

App run with nested jetty-server
```bash
java -jar moneyTransfer.jar
```
Server start on http://localhost:8080

#Get resources

 - information about all users (for presentation only)

http://localhost:8080/users

 - for information about one user
 
http://localhost:8080/users/{login} 

 - for information about user accounts

http://localhost:8080/users/{login}/accounts 

 - for information about one account

http://localhost:8080/users/{login}/accounts/{accountNumber} 

 - for information account money-transactions

http://localhost:8080/users/{login}/accounts/{accountNumber}/transactions

 - for info about one transaction

http://localhost:8080/users/{login}/accounts/{accountNumber}/transactions/{transactionId}

#Create resource

For money transfer between two accounts need create http POST with new Transaction

```json
{
  "from": 778999007,
  "to": 869866773,
  "count": 10.01
}
```