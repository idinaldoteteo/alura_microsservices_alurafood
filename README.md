# Project about microsservices with Java and Spring

# 🛠️ Project

1) route Eureka Server: http://localhost:8762/
2) route Gateway: http://localhost:8090
3) route order microsservice handling requests: http://localhost:8090/order-ms/order
4) route payment microsservice handling requests: http://localhost:8090/payments-ms/payment
5) route rating microsservice handling requests payment confirmed: http://localhost:8090/rating-ms/rating
6) payment microsservice creating an exchange fanout.payment.confirmation 
7) order microsservice creating a queue payment.confirmation (binding exchange)
8) rating microsservice creating a queue rating.payment.confirmation (binding exchange)


# Microsservices:
* EUREKA SERVER: http://localhost:8762/
* GATEWAY: http://localhost:8090
* ORDER: http://localhost:8090/order-ms
* PAYMENT: http://localhost:8090/payments-ms
* RATING: http://localhost:8090/rating-ms


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



