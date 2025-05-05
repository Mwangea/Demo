# Google OAuth2 Authentication Demo (Spring Boot)

## Project Overview
This project demonstrates Google OAuth2 authentication in a Spring Boot application with two main endpoints:
1. `/signup` - Authenticates users via Google OAuth2 and logs their information
2. `/create` - Stores non-personal authentication data in a MySQL database

## Features
* Google OAuth2 authentication flow
* Secure session management
* Database storage of provider information (without personal data)
* Proper credential management

## Prerequisites
* Java 17+
* MySQL 8+
* Google Cloud Platform account
* Gradle

## Setup Instructions

### 1. Google OAuth2 Configuration
1. Go to Google Cloud Console
2. Create a new project
3. Navigate to "APIs & Services" → "Credentials"
4. Create OAuth 2.0 Client ID for Web Application
5. Add authorized redirect URI: `http://localhost:8080/login/oauth2/code/google`

### 2. Application Configuration
1. Clone the repository
2. Create `application-secrets.yml` file in `src/main/resources` with:
```yaml
spring:
  security:
    oauth2:
      client:
        registration:
          google:
            client-id: your-client-id
            client-secret: your-client-secret
```

### 3. Database Setup
1. Create MySQL database:
```sql
CREATE DATABASE auth_demo;
```

## API Endpoints

### GET `/signup`
* Google-authenticated endpoint
* Logs user's full name and email (not stored)
* Returns confirmation message

### POST `/create`
* Creates database record with:
  * Authentication provider ("google")
  * Provider ID (Google's unique user identifier)
  * Creation timestamp

## Security Practices
* Personal information (email, name) is never stored
* Credentials are kept in separate config file (excluded from Git)
* CSRF protection properly configured
* Session-based authentication

## Running the Application
```bash
./gradlew bootRun
```

## Testing
1. Access `http://localhost:8080/login` in browser
2. Authenticate with Google
3. Verify logs show authentication info
4. Test `/create` endpoint with Postman:
```bash
POST http://localhost:8080/create
Cookie: JSESSIONID=<your-session-id>
```

## Important Notes
* The provided Google credentials in example files are placeholders
* Personal information is only logged, never stored
* Database only stores provider information

## Project Structure
```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── authentication/
│   │           └── demo/
│   │               ├── config/
│   │               │   └── SecurityConfig.java
│   │               ├── controller/
│   │               │   └── AuthController.java
│   │               ├── entity/
│   │               │   └── User.java
│   │               └── repository/
│   │                   └── UserRepository.java
│   └── resources/
│       ├── application.yml
│       └── application-secrets.yml
```

## License
This project is for demonstration purposes only.
