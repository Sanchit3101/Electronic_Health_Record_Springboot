# Electronic_Health_Record_Springboot
🩺 Electronic Health Record (EHR) System
A full-stack EHR system built using Spring Boot, Kafka, and AWS with a microservices architecture. This project aims to streamline medical report management and patient-doctor interactions with secure, scalable, and cloud-native infrastructure.

🚀 Features
🔐 Secure Authentication for patients and doctors

📄 Upload & Retrieve Medical Reports to/from AWS S3

📬 Email Notifications to users using Amazon SES

📈 Kafka-based Asynchronous Communication between services

📊 Logging Microservice to track actions like report uploads, patient creation, etc.

🧠 AI Chatbot Microservice powered by Mistral/OpenAI API

🧑‍⚕️ Admin Dashboard to monitor logs and user activity

⚙️ Microservices communicating using Spring's Async + Kafka

🛠️ Tech Stack
Layer	Technology
Backend	Java, Spring Boot, Spring Security, Spring JPA
Frontend	HTML, CSS
Messaging	Apache Kafka
Cloud	AWS S3, SES
Chatbot API	Mistral / OpenAI
Database	MySQL
Build Tool	Maven
Deployment	Spring Boot Jar (Standalone Deployment)

📁 Project Structure
bash
Copy
Edit
ehr-backend/
<img width="613" height="921" alt="image" src="https://github.com/user-attachments/assets/f5101415-57d3-4f86-a372-559da6a88127" />


bash
Copy
Edit
git clone https://github.com/Sanchit3101/ehr-system
cd ehr-system
Start Kafka and Zookeeper (manually, not Docker-based)

Set AWS Credentials

bash
Copy
Edit
export AWS_ACCESS_KEY_ID=your_key
export AWS_SECRET_ACCESS_KEY=your_secret
Run Backend

bash
Copy
Edit
cd ehr-backend
mvn clean install
java -jar target/ehr-backend.jar
Run Kafka Logging Microservice

bash
Copy
Edit
cd kafka-logging-microservice
mvn spring-boot:run
Run Chatbot Microservice

bash
Copy
Edit
cd chatbot-microservice
mvn spring-boot:run
Access the Application

Report Upload Page: http://localhost:8080/upload

Admin Logs: http://localhost:8081/logs

📦 API Endpoints
Endpoint	Method	Description
/upload	POST	Upload medical report to S3
/logs	GET	Fetch activity logs from Kafka DB
/chatbot/ask	POST	Ask medical questions (AI Chatbot)
/notify/email	POST	Send email via SES

✍️ Author
Sanchit Sen
📧 sensanchita2212@gmail.com
🔗 GitHub
🔗 LinkedIn

📢 Let's Connect!
If you're interested in Java backend development, health tech, cloud-native design, or distributed systems — let’s chat!


📸 Demo Preview
<img width="1919" height="1079" alt="LOGIN_PAGE" src="https://github.com/user-attachments/assets/58905d22-9056-49bc-b9b6-849ac676b8c8" />

<img width="1914" height="1078" alt="index" src="https://github.com/user-attachments/assets/b43f0b6a-5e2f-4493-a7a8-e6545bbf396e" />
<img width="1919" height="1077" alt="UPLOAD_PAGE" src="https://github.com/user-attachments/assets/c2d868ef-ebb0-4cef-bf68-abe8bad1c25a" />
<img width="1919" height="1079" alt="PATIENT_SIGNUP" src="https://github.com/user-attachments/assets/38043cf2-35b7-49d5-8cc6-a8c5e710a41d" />
<img width="1914" height="1077" alt="doctor_signup" src="https://github.com/user-attachments/assets/24e9553b-f699-4e38-ae9f-0988b7432cf1" />


