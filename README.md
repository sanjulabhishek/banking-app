Spring Boot, Spring Security, PostgreSQL: JWT Authentication & Authorization example
User Registration, User Login and Authorization process.
The diagram shows flow of how we implement User Registration, User Login and Authorization process.

Dependency
<dependency>
  <groupId>org.postgresql</groupId>
  <artifactId>postgresql</artifactId>
  <scope>runtime</scope>
</dependency>
Configure Spring Datasource, JPA, App properties
Open src/main/resources/application.properties

spring.datasource.url= jdbc:postgresql://localhost:5432/test2
spring.datasource.username= postgres
spring.datasource.password= 123

spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation= true
spring.jpa.properties.hibernate.dialect= org.hibernate.dialect.PostgreSQLDialect

# Hibernate ddl auto (create, create-drop, validate, update)
spring.jpa.hibernate.ddl-auto= update

# App Properties
bezkoder.app.jwtSecret= bezKoderSecretKey
bezkoder.app.jwtExpirationMs= 86400000

Run Spring Boot application
mvn spring-boot:run

Run following SQL insert statements
INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');



---------------------

Admin role with features:

signup :

curl -X POST \
  http://localhost:8080/api/auth/signup \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: 0e28d42e-cfa2-f9ba-c8f7-196d399056b5' \
  -d '{
	"username": "test1",
	"email": "test@test1.com",
	"password": "123456",
	"role": ["admin","user"]
} '

Sign in as admin.

curl -X POST \
  http://localhost:8080/api/auth/signin \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: 87044423-c3b0-7488-7966-c5e2f06fb35e' \
  -d '{
	"username": "test1",
	"password": "123456"
}'

sign out as Admin.

curl -X POST \
  http://localhost:8080/api/auth/signout \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: 707f554e-fee8-6f66-0bf4-43d0b69e4970'


Add bank employees.

curl -X POST \
  http://localhost:8080/api/auth/signup \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: 0e28d42e-cfa2-f9ba-c8f7-196d399056b5' \
  -d '{
	"username": "test1",
	"email": "test@test1.com",
	"password": "123456",
	"role": [ "user"]
} '


Delete employees.

curl -X POST \
  http://localhost:8080/api/auth/delete \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: 1f7adb4c-8421-fb21-d6c7-e429b3e83f99' \
  -d '{
	"username": "test6"
}'


Employee role with feature:

Sign in/out as an employee. 

curl -X POST \
  http://localhost:8080/api/auth/signin \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: 87044423-c3b0-7488-7966-c5e2f06fb35e' \
  -d '{
	"username": "test1",
	"password": "123456"
}'

curl -X POST \
  http://localhost:8080/api/auth/signout \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: 707f554e-fee8-6f66-0bf4-43d0b69e4970'

Create a customer.

curl -X POST \
  http://localhost:8080/api/customers/create \
  -H 'authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0MSIsImlhdCI6MTYxMTcyNTI5NSwiZXhwIjoxNjExNzI1Mzk1fQ.yCe09oht9zkL3E2cqN6tmhcmxe6NNEEGOEg0R6NamiHsrAne_2xynLLBNiOkfxJ_nCZ9ndVziBXkThPtbcO7qg' \
  -H 'cache-control: no-cache' \
  -H 'postman-token: 6e40160c-17df-052a-9908-58499efb5f26' \
  -d '{"username":"user1","customerFirstName":"AA","getCustomerLastName":"CC","address":"add","mobileNumber":123456,"emailId":"test@test1.com","bankName":null,"ifscCode":null,"identificationType":[{"name":"Passport","number":"A-111","expiry":"2029-07-02","country":"India","address":"address","id":null,"valid":true}],"accountList":[{"id":null,"number":12345,"type":"SALARY","balance":20000.0,"active":true,"customer_id":null}]}
'

Create accounts like savings, salary, loan, current account etc.
    SAVINGS("SAVINGS"),
    LOAN("LOAN"),
    SALARY("SALARY"),
    CURRENT("CURRENT");

Link customers with accounts.
one to many relation with accounts.
one customer can have list of accounts

Update KYC for a customer.

curl -X PUT \
  http://localhost:8080/api/customers/update \
  -H 'authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0MSIsImlhdCI6MTYxMTcyNTI5NSwiZXhwIjoxNjExNzI1Mzk1fQ.yCe09oht9zkL3E2cqN6tmhcmxe6NNEEGOEg0R6NamiHsrAne_2xynLLBNiOkfxJ_nCZ9ndVziBXkThPtbcO7qg' \
  -H 'cache-control: no-cache' \
  -H 'postman-token: 6e40160c-17df-052a-9908-58499efb5f26' \
  -d '{"address":"add","mobileNumber":123456,"emailId":"test@test1.com"}'
'

Get details of a customer.

curl -X GET \
  http://localhost:8080/api/customers/get \
  -H 'authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0MSIsImlhdCI6MTYxMTcyNTI5NSwiZXhwIjoxNjExNzI1Mzk1fQ.yCe09oht9zkL3E2cqN6tmhcmxe6NNEEGOEg0R6NamiHsrAne_2xynLLBNiOkfxJ_nCZ9ndVziBXkThPtbcO7qg' \
  -H 'cache-control: no-cache' \
  -H 'postman-token: 6e40160c-17df-052a-9908-58499efb5f26' \
  -d '{"username":"user1"}'
'

Delete customer.

curl -X DELETE \
  http://localhost:8080/api/customers/delete \
  -H 'authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0MSIsImlhdCI6MTYxMTcyNTI5NSwiZXhwIjoxNjExNzI1Mzk1fQ.yCe09oht9zkL3E2cqN6tmhcmxe6NNEEGOEg0R6NamiHsrAne_2xynLLBNiOkfxJ_nCZ9ndVziBXkThPtbcO7qg' \
  -H 'cache-control: no-cache' \
  -H 'postman-token: 6e40160c-17df-052a-9908-58499efb5f26' \
  -d '{"username":"user1"}'

Get account balance for an account.

curl -X GET \
  http://localhost:8080/api/customers/balance \
  -H 'authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0MSIsImlhdCI6MTYxMTcyNTI5NSwiZXhwIjoxNjExNzI1Mzk1fQ.yCe09oht9zkL3E2cqN6tmhcmxe6NNEEGOEg0R6NamiHsrAne_2xynLLBNiOkfxJ_nCZ9ndVziBXkThPtbcO7qg' \
  -H 'cache-control: no-cache' \
  -H 'postman-token: 6e40160c-17df-052a-9908-58499efb5f26' \
  -d '{"username":"user1","accountList":[{"number":12345,"type":"SALARY","balance":20000.0,"active":true}]}'

