💳 Payment Service
The Payment Service handles payment processing for the e-commerce platform and is integrated with Razorpay for seamless online transactions. It manages payment gateway interactions and ensures secure, reliable payment operations across the platform.

🚀 Features
1. Payment Gateway Integration
Integrated with Razorpay to facilitate secure and efficient payment processing.

Supports different payment methods including credit/debit cards, net banking, wallets, and UPI.

2. Order Payment
Processes payments for user orders, ensuring that transactions are completed successfully.

Supports capturing, refunding, and verifying payment statuses.

3. Webhooks
Listens to Razorpay webhooks for payment status updates (e.g., success, failure, or refund).

Ensures synchronization between the payment service and other microservices like Order Service.

⚙️ Non-Functional Requirements
1. Scalability
Built to handle high traffic and large volumes of transactions efficiently.

2. Security
Implements secure communication via HTTPS.

Payment details are encrypted and stored securely.

3. Extensibility
Can be extended to integrate with additional payment gateways in the future.

🧩 Technologies Used
Java / Spring Boot

Razorpay API

MySQL for transaction data storage

RESTful APIs for communication with other services

🔄 Integration with Other Microservices
Integrated with Order Service to handle order payments.

Webhook listeners update the Order Service with payment status and transaction details.

🌐 API Gateway & Eureka Server
The service is registered with Eureka Server for service discovery, ensuring seamless communication between microservices.

API Gateway handles routing of payment requests and enforces authentication.

📌 Contributors
[Ridhim Singh Raizada]

