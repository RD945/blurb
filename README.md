# Blurb - Social Media REST API

Blurb is a Java-based Spring Boot REST API application designed to provide backend functionality for a social media platform similar to Instagram. It supports posts, stories, comments, tags, user authentication, and role-based authorization.

## Why "Blurb"?

The name "Blurb" was chosen because it perfectly captures the essence of what this platform does. A blurb is a short, catchy piece of writing - a brief description or promotional paragraph. In the context of social media, every post, story, and comment is essentially a blurb: a quick snapshot of a thought, moment, or idea that users want to share with others. The name reflects the platform's focus on short-form content sharing and self-expression.

---

## Table of Contents

- [Overview](#overview)
- [Technology Stack](#technology-stack)
- [Project Structure](#project-structure)
- [Security Architecture](#security-architecture)
- [Data Models](#data-models)
- [API Reference](#api-reference)
- [Setup and Installation](#setup-and-installation)
- [Configuration](#configuration)

---

## Overview

Blurb provides a complete backend solution for social media applications with the following features:

- User registration and JWT-based authentication
- Role-based access control (USER, MODERATOR, ADMIN)
- CRUD operations for Posts and Stories
- Comment system with full CRUD support
- Like/Unlike toggle functionality for posts and stories
- Repost feature for content sharing
- Tag-based content filtering and discovery
- User management for administrators

---

## Technology Stack

| Technology | Version | Purpose |
|------------|---------|---------|
| Spring Boot | 3.0.1 | Main framework for building RESTful APIs |
| Spring Security | - | Authentication and authorization |
| Spring Data JPA | - | ORM for database interaction |
| PostgreSQL | - | Relational database |
| JWT (jjwt) | 0.11.5 | Token-based authentication |
| Lombok | - | Boilerplate code reduction |
| Java | 17 | Programming language |

---

## Project Structure

```
blurb/
├── src/main/java/com/had0uken/blurb/
│   ├── BlurbApplication.java             # Main entry point
│   │
│   ├── access/
│   │   └── Access.java                   # Access control utilities
│   │
│   ├── configuration/
│   │   ├── AppConfig.java                # Authentication providers and beans
│   │   ├── JwtAuthenticationFilter.java  # JWT request filter
│   │   └── SecurityConfig.java           # Security filter chain configuration
│   │
│   ├── controller/
│   │   ├── AuthenticationController.java # Login and registration endpoints
│   │   ├── CommentController.java        # Comment CRUD operations
│   │   ├── DemoController.java           # Demo/test endpoint
│   │   ├── PostController.java           # Post CRUD with like/repost
│   │   ├── StoriesController.java        # Stories CRUD with like/repost
│   │   ├── TagController.java            # Tag-based content retrieval
│   │   └── UserController.java           # User management (admin)
│   │
│   ├── model/
│   │   ├── Tag.java                      # Tag entity
│   │   ├── post/
│   │   │   ├── Comment.java              # Comment entity
│   │   │   ├── MediaFile.java            # Media attachment entity
│   │   │   ├── MediaType.java            # Media type enumeration
│   │   │   ├── Post.java                 # Post entity
│   │   │   ├── Stories.java              # Stories entity
│   │   │   └── UserOwned.java            # Interface for ownership checking
│   │   └── user/
│   │       ├── Role.java                 # Role enumeration
│   │       └── User.java                 # User entity (implements UserDetails)
│   │
│   ├── payload/
│   │   ├── requests/
│   │   │   ├── AuthRequest.java          # Login request DTO
│   │   │   └── RegisterRequest.java      # Registration request DTO
│   │   └── responses/
│   │       ├── ApiResponse.java          # Message-based response
│   │       ├── AuthResponse.java         # JWT token response
│   │       ├── ContentResponse.java      # Data list response
│   │       └── Response.java             # Abstract response base class
│   │
│   ├── repository/
│   │   ├── CommentRepository.java        # Comment data access
│   │   ├── MediaRepository.java          # Media file data access
│   │   ├── PostRepository.java           # Post data access with tag query
│   │   ├── StoriesRepository.java        # Stories data access
│   │   ├── TagRepository.java            # Tag data access
│   │   └── UserRepository.java           # User data access
│   │
│   └── service/
│       ├── AuthService.java              # Authentication service interface
│       ├── CommentService.java           # Comment service interface
│       ├── CustomUserDetailsService.java # User details service interface
│       ├── JwtService.java               # JWT service interface
│       ├── PostService.java              # Post service interface
│       ├── StoriesService.java           # Stories service interface
│       ├── TagService.java               # Tag service interface
│       └── implementation/
│           ├── AuthServiceImp.java       # Authentication implementation
│           ├── CommentServiceImpl.java   # Comment service implementation
│           ├── JwtServiceImpl.java       # JWT service implementation
│           ├── PostServiceImpl.java      # Post service implementation
│           ├── StoriesServiceImpl.java   # Stories service implementation
│           ├── TagServiceImpl.java       # Tag service implementation
│           └── UserServiceImpl.java      # User service implementation
│
└── src/main/resources/
    └── application.yml                    # Application configuration
```

---

## Security Architecture

### Authentication Flow

1. **Registration**: User sends a POST request to `/api/auth/register` with their details
2. **Login**: User sends credentials to `/api/auth/authenticate` and receives a JWT token
3. **Authorization**: All protected endpoints require a `Bearer <token>` in the `Authorization` header

### JWT Configuration

- **Algorithm**: HS256
- **Expiration**: 24 hours
- **Token Structure**: Standard JWT with username as subject

### Role-Based Access Control

| Role | Permissions |
|------|-------------|
| USER | Create, read, update, and delete own content; like and repost any content |
| MODERATOR | All USER permissions plus ability to delete or edit any user's content |
| ADMIN | All permissions including user management and role assignment |

### Access Control Rules

- **Content Deletion/Editing**: Allowed for content owner, administrators, or moderators
- **User Management**: Restricted to administrators only
- **Role Assignment**: Administrators only (cannot modify other administrators)

---

## Data Models

### User Entity

| Field | Type | Description |
|-------|------|-------------|
| id | Long | Primary key |
| firstname | String | User's first name |
| lastname | String | User's last name |
| email | String | Email address (used as username) |
| password | String | BCrypt encoded password |
| role | Role | USER, MODERATOR, or ADMIN |
| posts | List | One-to-Many relationship with Post |
| stories | List | One-to-Many relationship with Stories |
| comments | List | One-to-Many relationship with Comment |
| likedPosts | Set | Many-to-Many relationship with liked posts |
| repostedPosts | Set | Many-to-Many relationship with reposted posts |
| likedStories | Set | Many-to-Many relationship with liked stories |
| repostedStories | Set | Many-to-Many relationship with reposted stories |

### Post Entity

| Field | Type | Description |
|-------|------|-------------|
| id | Long | Primary key |
| title | String | Post title |
| body | String | Post content |
| user | User | Many-to-One relationship with author |
| created | LocalDate | Creation date |
| comments | List | One-to-Many relationship with Comment |
| mediaFiles | List | One-to-Many relationship with MediaFile |
| tags | List | Many-to-Many relationship with Tag |
| likedByUsers | Set | Many-to-Many relationship with users who liked |
| repostedByUsers | Set | Many-to-Many relationship with users who reposted |

### Stories Entity

| Field | Type | Description |
|-------|------|-------------|
| id | Long | Primary key |
| user | User | Many-to-One relationship with author |
| created | LocalDate | Creation date |
| mediaFile | MediaFile | One-to-One relationship with media |
| tags | List | Many-to-Many relationship with Tag |
| likedByUsers | Set | Many-to-Many relationship with users who liked |
| repostedByUsers | Set | Many-to-Many relationship with users who reposted |

### Comment Entity

| Field | Type | Description |
|-------|------|-------------|
| id | Long | Primary key |
| text | String | Comment content |
| user | User | Many-to-One relationship with author |
| post | Post | Many-to-One relationship with parent post |
| created | LocalDate | Creation date |

### MediaFile Entity

| Field | Type | Description |
|-------|------|-------------|
| id | Long | Primary key |
| fileName | String | Original file name |
| size | Double | File size |
| mediaType | MediaType | JPEG, PNG, SVG, WebP, MP4, or AAC |
| URL | String | URL to the media resource |

### Tag Entity

| Field | Type | Description |
|-------|------|-------------|
| id | Long | Primary key |
| name | String | Tag name |
| posts | List | Many-to-Many relationship with Post |
| stories | List | Many-to-Many relationship with Stories |

---

## API Reference

### Authentication Endpoints

| Method | Endpoint | Description | Request Body |
|--------|----------|-------------|--------------|
| POST | /api/auth/register | Register a new user | RegisterRequest |
| POST | /api/auth/authenticate | Login and get JWT token | AuthRequest |

#### Register Request Body
```json
{
  "firstname": "John",
  "lastname": "Doe",
  "email": "john.doe@example.com",
  "password": "securePassword123"
}
```

#### Authenticate Request Body
```json
{
  "email": "john.doe@example.com",
  "password": "securePassword123"
}
```

### Post Endpoints

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| GET | /api/posts/ | Get all posts | Yes |
| GET | /api/posts/{id} | Get post by ID | Yes |
| POST | /api/posts/ | Create new post | Yes (USER+) |
| PUT | /api/posts/{id} | Update post | Yes (Owner/Mod/Admin) |
| DELETE | /api/posts/{id} | Delete post | Yes (Owner/Mod/Admin) |
| POST | /api/posts/{id}/like | Like or unlike a post | Yes |
| POST | /api/posts/{id}/repost | Repost a post | Yes |

#### Create Post Request Body
```json
{
  "title": "My First Post",
  "body": "This is the content of my post.",
  "mediafiles": [
    {
      "URL": "https://example.com/image.jpg",
      "fileName": "image.jpg",
      "size": 2048.0,
      "mediatype": "JPEG"
    }
  ]
}
```

### Stories Endpoints

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| GET | /api/stories/ | Get all stories | Yes |
| GET | /api/stories/{id} | Get story by ID | Yes |
| POST | /api/stories/ | Create new story | Yes (USER+) |
| DELETE | /api/stories/{id} | Delete story | Yes (Owner/Mod/Admin) |
| POST | /api/stories/{id}/like | Like or unlike a story | Yes |
| POST | /api/stories/{id}/repost | Repost a story | Yes |

#### Create Story Request Body
```json
{
  "mediafile": {
    "URL": "https://example.com/story.jpg",
    "fileName": "story.jpg",
    "size": 1024.0,
    "mediatype": "JPEG"
  }
}
```

### Comment Endpoints

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| GET | /api/comments/{postId}/post | Get comments by post ID | Yes |
| GET | /api/comments/{id} | Get comment by ID | Yes |
| POST | /api/comments/{postId}/post | Add comment to post | Yes (USER+) |
| PUT | /api/comments/{id} | Update comment | Yes (Owner/Mod/Admin) |
| DELETE | /api/comments/{id} | Delete comment | Yes (Owner/Mod/Admin) |

#### Create Comment Request Body
```json
{
  "text": "This is a great post!"
}
```

### Tag Endpoints

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| GET | /api/tags/{tagName}/posts | Get posts with specific tag | Yes |
| GET | /api/tags/{tagName}/stories | Get stories with specific tag | Yes |

### User Management Endpoints (Admin Only)

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| GET | /api/users/{username}/posts | Get posts by username | Yes |
| GET | /api/users/{username}/stories | Get stories by username | Yes |
| POST | /api/users/ | Create new user | Yes (ADMIN) |
| PUT | /api/users/{username} | Update user | Yes (ADMIN) |
| DELETE | /api/users/{username} | Delete user | Yes (ADMIN) |
| PUT | /api/users/{username}/admin | Assign admin role | Yes (ADMIN) |
| PUT | /api/users/{username}/moderator | Assign moderator role | Yes (ADMIN) |
| PUT | /api/users/{username}/user | Assign user role | Yes (ADMIN) |

---

## Setup and Installation

### Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- PostgreSQL 12 or higher

### Installation Steps

1. Clone the repository:
   ```bash
   git clone https://github.com/RD945/blurb.git
   cd blurb
   ```

2. Create a PostgreSQL database:
   ```sql
   CREATE DATABASE blurb_db;
   ```

3. Configure the application properties:
   
   Edit `src/main/resources/application.yml` and update the database connection settings:
   ```yaml
   spring:
     datasource:
       url: jdbc:postgresql://localhost:5432/blurb_db
       username: your_username
       password: your_password
   ```

4. Build and run the application:
   ```bash
   mvn spring-boot:run
   ```

5. The API will be available at `http://localhost:8080`

---

## Configuration

### Application Properties (application.yml)

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/blurb_db
    username: your_username
    password: your_password
    driver-class-name: org.postgresql.Driver
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: false
    properties:
      hibernate:
        format_sql: true
    database: postgresql
    database-platform: org.hibernate.dialect.PostgreSQLDialect
```

---

## License

This project is licensed under the Apache License 2.0 - see the LICENSE file for details.
