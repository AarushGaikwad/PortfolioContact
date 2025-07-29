# Portfolio Contact Service - Backend

A Spring Boot REST API service that handles contact form submissions from a portfolio website and sends emails using JavaMail.

## 🚀 Features

- **Contact Form API**: RESTful endpoint to handle contact form submissions
- **Email Service**: Automated email sending using JavaMail
- **Input Validation**: Server-side validation using Bean Validation annotations
- **CORS Configuration**: Configured for frontend integration
- **Error Handling**: Comprehensive error handling with proper HTTP status codes

## 🛠️ Tech Stack

- **Java 17+**
- **Spring Boot 3.x**
- **Spring Web** - REST API development
- **Spring Mail** - Email functionality with Brevo SMTP
- **Jakarta Validation** - Input validation
- **Lombok** - Reducing boilerplate code
- **Maven** - Dependency management
- **Brevo (Sendinblue)** - Email service provider

## 📋 Prerequisites

Before running this application, make sure you have:

- Java 17 or higher installed
- Maven 3.6+ installed
- Brevo account with verified sender domain/email
- SMTP credentials from Brevo dashboard

## ⚙️ Configuration

### 1. Application Properties

Create `application.properties` or `application.yml` file in `src/main/resources/`:

```properties
# Server Configuration
server.port=8080

# Brevo Email Configuration
spring.mail.host=smtp-relay.brevo.com
spring.mail.port=587
spring.mail.username=your-brevo-email@example.com
spring.mail.password=your-brevo-smtp-key
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
spring.mail.properties.mail.smtp.starttls.required=true

# Custom Email Properties
app.mail.from=your-verified-sender@example.com
app.mail.to=recipient@example.com

# Logging
logging.level.com.example.portfolioContact=DEBUG
```

### 2. Brevo (formerly Sendinblue) Setup

1. **Create a Brevo Account**:
   - Sign up at [brevo.com](https://www.brevo.com)
   - Verify your email address

2. **Generate SMTP Credentials**:
   - Go to Brevo Dashboard
   - Navigate to **Settings** → **SMTP & API**
   - Click on **SMTP** tab
   - Your SMTP settings will be displayed:
     - Server: `smtp-relay.brevo.com`
     - Port: `587` (recommended) or `25`, `2525`
     - Login: Your Brevo account email
     - Password: Your SMTP key (generate if needed)

3. **Verify Sender Domain/Email**:
   - Go to **Settings** → **Senders & IP**
   - Add and verify your sender email address
   - This email must be used in `app.mail.from` property

4. **Generate SMTP Key**:
   - In **SMTP & API** section
   - Click **Generate a new SMTP key**
   - Copy the generated key for `spring.mail.password`

## 🚀 Installation & Setup

### 1. Clone the Repository

```bash
git clone <repository-url>
cd repository-name
```

### 2. Configure Environment Variables

```bash
# Set environment variables for Brevo
export MAIL_USERNAME=your-brevo-email@example.com
export MAIL_PASSWORD=your-brevo-smtp-key
export MAIL_TO=recipient@example.com
export MAIL_FROM=your-verified-sender@example.com
```

### 3. Build the Project

```bash
mvn clean compile
```

### 4. Run the Application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## 📡 API Endpoints

### Send Contact Message

**POST** `/api/contact`

Sends a contact form message via email.

#### Request Body

```json
{
  "name": "John Doe",
  "email": "john.doe@example.com",
  "message": "Hello, I would like to discuss a project opportunity."
}
```

#### Request Headers

```
Content-Type: application/json
```

#### Validation Rules

- `name`: Required, cannot be blank
- `email`: Required, must be valid email format
- `message`: Required, cannot be blank

#### Response

**Success (200 OK)**
```json
"Message sent Successfully!"
```

**Validation Error (400 Bad Request)**
```json
{
  "timestamp": "2024-01-15T10:30:00.000+00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "path": "/api/contact"
}
```

**Server Error (500 Internal Server Error)**
```json
"Failed to sent message: <error details>"
```

## 🏗️ Project Structure

```
src/main/java/com/example/portfolioContact/
├── configuration/
│   └── CorsConfig.java           # CORS configuration
├── controller/
│   └── ContactController.java    # REST controller
├── dto/
│   └── ContactRequest.java       # Data transfer object
├── service/
│   └── EmailService.java         # Email service logic
└── PortfolioContactApplication.java  # Main application class
```

## 🔧 Key Components

### CorsConfig
- Configures CORS to allow requests from the frontend
- Currently allows all origins from `https://portfoliocontact-1.onrender.com`

### ContactController
- Handles POST requests to `/api/contact`
- Validates request body using `@Valid`
- Returns appropriate HTTP status codes

### EmailService
- Uses JavaMailSender to send HTML emails
- Formats contact form data into HTML email
- Handles email sending errors

### ContactRequest DTO
- Data transfer object with validation annotations
- Ensures required fields are present and email format is valid

## 🧪 Testing

### Manual Testing with cURL

```bash
curl -X POST http://localhost:8080/api/contact \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Test User",
    "email": "test@example.com",
    "message": "This is a test message"
  }'
```

### Unit Testing

Add test dependencies to `pom.xml`:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
```

## 🚀 Deployment

### Environment Variables for Production

```bash
SPRING_PROFILES_ACTIVE=prod
MAIL_USERNAME=your-brevo-email@example.com
MAIL_PASSWORD=your-brevo-smtp-key
MAIL_TO=recipient@example.com
MAIL_FROM=your-verified-sender@example.com
```

### Docker Deployment

Create `Dockerfile`:

```dockerfile
FROM openjdk:17-jdk-slim
COPY target/portfolio-contact-*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

Build and run:

```bash
mvn clean package
docker build -t portfolio-contact-backend .
docker run -p 8080:8080 \
  -e MAIL_USERNAME=your-brevo-email@example.com \
  -e MAIL_PASSWORD=your-brevo-smtp-key \
  -e MAIL_FROM=your-verified-sender@example.com \
  -e MAIL_TO=recipient@example.com \
  portfolio-contact-backend
```

## 🔒 Security Considerations

- Never commit Brevo SMTP credentials to version control
- Use environment variables or secure vaults for sensitive data
- Verify sender domains in Brevo to prevent spoofing
- Consider rate limiting for the contact endpoint
- Validate and sanitize all input data
- Use HTTPS in production
- Monitor email sending limits and quotas in Brevo dashboard

## 📝 Logging

The application uses SLF4J with Logback for logging:

- Email sending success/failure is logged
- Error messages include stack traces for debugging
- Configure log levels in `application.properties`

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Submit a pull request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 📞 Support

For support or questions, please contact aarushgaikwad789@gmail.com
