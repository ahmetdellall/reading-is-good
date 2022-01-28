# Reading Is Good

ReadingIsGood is an online books retail firm which operates only on the Internet. Main
target of ReadingIsGood is to deliver books from its one centralized warehouse to their
customers within the same day. That is why stock consistency is the first priority for their
vision operations.

You can use the link below to access the swagger document.

http://localhost:8080/swagger-ui/index.html#/
![](swagger_screenshot.png)

RUN

The project could be executed via docker-compose.

If you want to debug this code, you can use docker-compose -f docker-compose.yml up command

General Info

The system performs a spring security auth check. therefore, the user will be able to access the endpoints with the 
token that comes first after registering and logging in.

Example Curl :

User Created: 

curl --location --request POST 'http://localhost:8080/api/auth/signup' \
--header 'accept: */*' \
--header 'Content-Type: application/json' \
--data-raw '{
"username": "ahmetdellal",
"email": "cedellalahmet@gmail.com",
"role": [
"ROLE_USER","ROLE_ADMIN"
],
"password": "Aa123456"
}'


Get Token By User: 

curl --location --request POST 'http://localhost:8080/api/auth/signin' \
--header 'accept: */*' \
--header 'Content-Type: application/json' \
--data-raw '{
"username": "ahmetdellal",
"password": "Aa123456"
}'


To access the endpoints, access to the endpoints is provided with the token returned to us from the above member login.
Example curls are given in order.

PS :The token in the header is changing. Do not forget to change the token here. 
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhaG1ldGRlbGxhbCIsImlhdCI6MTY0MzM2ODU5OSwiZXhwIjoxNjQzND
U0OTk5fQ.wwi2ki4YXYhNZKcPVhiQ4QjJZKVd94uZP1W_kyk9JQA' \

Create Customer :

curl --location --request POST 'http://localhost:8080/api/customer' \
--header 'accept: */*' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhaG1ldGRlbGxhbCIsImlhdCI6MTY0MzM2ODU5OSwiZXhwIjoxNjQzNDU0OTk5fQ.wwi2ki4YXYhNZKcPVhiQ4QjJZKVd94uZP1W_kyk9JQA' \
--data-raw '{
"id": 0,
"name": "ahmetdellal",
"email": "cedellalahmet@gmail.com",
"phone": "+905326713502",
"address": "kadıköy",
"createDateTime": "2022-01-27T20:40:59.853Z"
}'

Get Customer :

curl --location --request GET 'http://localhost:8080/api/customer/1' \
--header 'accept: */*' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhaG1ldGRlbGxhbCIsImlhdCI6MTY0MzM2ODU5OSwiZXhwIjoxNjQzNDU0OTk5fQ.wwi2ki4YXYhNZKcPVhiQ4QjJZKVd94uZP1W_kyk9JQA'


Paginate Customer :

curl --location --request GET 'http://localhost:8080/api/customer/get-all-customer?pageIndex=0&pageSize=10' \
--header 'accept: */*' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhaG1ldGRlbGxhbCIsImlhdCI6MTY0MzM2ODU5OSwiZXhwIjoxNjQzNDU0OTk5fQ.wwi2ki4YXYhNZKcPVhiQ4QjJZKVd94uZP1W_kyk9JQA'


Create A Book :

curl --location --request POST 'http://localhost:8080/api/book' \
--header 'accept: */*' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhaG1ldGRlbGxhbCIsImlhdCI6MTY0MzM2ODU5OSwiZXhwIjoxNjQzNDU0OTk5fQ.wwi2ki4YXYhNZKcPVhiQ4QjJZKVd94uZP1W_kyk9JQA' \
--data-raw '{
"id": 0,
"name": "Lord Of The Ring",
"quantity": 5,
"createDateTime": "2022-01-28T07:47:13.496Z",
"price": 60
}'

Get All Book:
curl --location --request GET 'http://localhost:8080/api/book/get-all-book?pageIndex=0&pageSize=10' \
--header 'accept: */*' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhaG1ldGRlbGxhbCIsImlhdCI6MTY0MzM2ODU5OSwiZXhwIjoxNjQzNDU0OTk5fQ.wwi2ki4YXYhNZKcPVhiQ4QjJZKVd94uZP1W_kyk9JQA'


Created A Order :

curl --location --request POST 'http://localhost:8080/api/order/new-order' \
--header 'accept: */*' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhaG1ldGRlbGxhbCIsImlhdCI6MTY0MzM2ODU5OSwiZXhwIjoxNjQzNDU0OTk5fQ.wwi2ki4YXYhNZKcPVhiQ4QjJZKVd94uZP1W_kyk9JQA' \
--data-raw '{
"books": [
{
"bookId": 2,
"quantity": 2
}
],
"customerId": 1
}'

Get A order by Customer 

curl --location --request GET 'http://localhost:8080/api/order?customerId=1' \
--header 'accept: */*' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhaG1ldGRlbGxhbCIsImlhdCI6MTY0MzM2ODU5OSwiZXhwIjoxNjQzNDU0OTk5fQ.wwi2ki4YXYhNZKcPVhiQ4QjJZKVd94uZP1W_kyk9JQA'


List orders by date interval ( startDate - endDate ): 

curl --location --request GET 'http://localhost:8080/api/order/get-by-interval/2022-01-27/2022-01-29' \
--header 'accept: */*' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhaG1ldGRlbGxhbCIsImlhdCI6MTY0MzM2ODU5OSwiZXhwIjoxNjQzNDU0OTk5fQ.wwi2ki4YXYhNZKcPVhiQ4QjJZKVd94uZP1W_kyk9JQA'

Will serve customer’s monthly order statistics : 

curl --location --request GET 'http://localhost:8080/api/statistics/1' \
--header 'accept: */*' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhaG1ldGRlbGxhbCIsImlhdCI6MTY0MzM3MDEyNiwiZXhwIjoxNjQzNDU2NTI2fQ.Xiq7fBZyakCQOWVbIsl6G0DTTbLx9jwoc1NkFHr7c2M'