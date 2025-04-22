# Gym Management System

## Project Description

The **Gym Management System** is a web application designed to manage gym activities, allowing both gym trainees and trainers to create and update their profiles. Trainees can select one or more trainers, log their activities, and view their training sessions. Trainers can manage their schedules and see the activities of their trainees. The system also allows users to modify their profile information and activate or deactivate profiles.

To access the application (except the Registration page), login credentials are required for both trainers and trainees.

### Key Features:
- **User Registration**: Allows both trainees and trainers to register.
- **User Profiles**: Users have editable profiles that can be activated or deactivated.
- **Login Authentication**: Both trainees and trainers are required to log in.
- **Training Management**: Trainees can track their activities, and trainers can view their trainees' activities.
- **Reports**: The system generates weekly reports summarizing training activity in CSV format.
- **AWS Deployment**: The application is deployed on AWS, utilizing various services like EC2, S3, and DynamoDB.

---

## Project Structure

### 1. Frontend:
- Web pages for registration, profile updates, and viewing training information.

### 2. Backend:
- REST API to handle user registration, profile updates, and training logs.

### 3. Database:
- Uses both relational (RDNMS) and NoSQL databases (MongoDB and DynamoDB).

### 4. Lambda Functions:
- AWS Lambda is used to generate and upload reports about training activity.

---

## Technologies Used

- **Java**: The primary language used for server-side logic.
- **Spring Boot**: Framework used for rapid development of Java applications.
- **AWS**: Used for deploying the app, as well as utilizing services like EC2, S3, DynamoDB, and Lambda.
- **MongoDB / DynamoDB**: Databases used for storing user, training, and report data.
- **HTML/CSS**: Used for building the user interface.
- **REST API**: Provides a way for users to interact with the system via HTTP requests.

---

## Database Structure

### 1. **User Table**: 
- Stores general information about users (both trainers and trainees).

### 2. **Trainee Table**:
- Stores additional information about trainees, such as their date of birth and address.

### 3. **Trainer Table**:
- Stores information about trainers and their specializations.

### 4. **Training Table**:
- Stores information about each training session, including duration and type.

### 5. **Training Type Table**:
- Stores available training types (e.g., fitness, yoga, Zumba).

---

## Key API Endpoints

- **/register/trainee** — Register a trainee.
- **/register/trainer** — Register a trainer.
- **/login** — Login to the system.
- **/profile/trainee** — Retrieve a trainee's profile.
- **/profile/trainer** — Retrieve a trainer's profile.
- **/trainings/trainee** — Get a list of trainings for a trainee.
- **/trainings/trainer** — Get a list of trainings for a trainer.
- **/training/add** — Add a new training session for a trainee.
- **/training/update** — Update training session information.

---

## Lambda Logic (AWS)

### 1. **Lambda Function**:
- Generates weekly reports on trainers' training activity.

### 2. **CloudWatch Trigger**:
- AWS CloudWatch triggers the Lambda function weekly.

### 3. **DynamoDB**:
- Lambda reads data from DynamoDB to calculate the total training duration for each trainer.

### 4. **S3**:
- After generating the report, it is uploaded to an S3 bucket.

### 5. **Report Format**:
  - Trainer’s first name
  - Trainer’s last name
  - Current month’s training duration summary
  - Inactive trainers with zero training duration are excluded from the report.


AWS Deployment Steps
EC2: Set up a virtual machine to host the application.

S3: Use S3 for storing reports and files.

DynamoDB: Store data related to users and training sessions.

CloudWatch: Set up triggers for Lambda functions.
