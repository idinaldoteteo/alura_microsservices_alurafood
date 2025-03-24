# Project about microsservices with Java and Spring

# 🛠️ Project

1) route Eureka Server: http://localhost:8762/
2) route Gateway: http://localhost:8090
3) route order microsservice to get all orders: http://localhost:8090/order-ms/order
4) route payment microsservice to get all payment: http://localhost:8090/payments-ms/payment
4.1) queue payment.confirmation with biding exchange fanout.payment.confirmation 
5) route rating microsservice to get all payment confirmed: http://localhost:8090/rating-ms/rating
5.1) queue rating.payment.confirmation with biding exchange fanout.payment.confirmation 


# Microsservices:
* EUREKA SERVER: http://localhost:8762/
* GATEWAY: http://localhost:8090
* ORDER: http://localhost:8090/order-ms
* PAYMENT: http://localhost:8090/payments-ms


# Queues:
* RABBITMQ SERVER: http://localhost:15672/


# :hammer: Project Features
* Database MySql
* Database PostgreSql
* lib lombok
* lib modelmapper
* lib flyway to import the script sql
* Service Registration and Discovery
* Load Balance with Gateway
* Spring Cloud
* Communication Sync with Open Feign
* lib resilience4j
*   Circuit breaker
*   Retry
*   Fallback
* Swagger 
* RabbitMQ

# :hammer: AWS Project Features
* VPC
* ClUSTER
* ECS (service)
* EC2
* AWS Fargate
* RDS with my docker image of my project Order
* ECR
* Cloudformation
* Cloudwatch
* Scale Memory and CPU



