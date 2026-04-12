# Musician Portfolio Page

A web application for managing a musician's portfolio. Provides a public-facing website for visitors and an admin panel for content management.

## Tech Stack

- **Java 21**, Spring Boot 4.0.3
- **Thymeleaf** – server-side templates
- **Spring Security** – authentication and authorization
- **MySQL** + **Liquibase** – database and schema migrations
- **Spring Data JPA** (Hibernate)
- **MapStruct** – entity-to-DTO mapping
- **Lombok**
- **TinyMCE** – WYSIWYG editor (bundled locally)
- **fslightbox** – photo lightbox
- **Docker / Docker Compose**

## Features

**Public website**
- Home page
- About Me (editable rich-text content)
- Photo gallery (albums with lightbox)
- Videos (YouTube embeds)
- Projects (with cover images)
- Concerts
- Contacts

**Admin panel** (`/admin`, requires `ADMIN` role)
- Full CRUD for all sections above
- User profile and password management
- Forgotten password reset via email

## Running the Application

### Prerequisites

- Java 21
- Maven
- MySQL (local or via Docker)

### Local

```bash
# Build
mvn clean package -DskipTests

# Run
DB_URL=jdbc:mysql://localhost:3306/musician DB_PASSWORD=secret mvn spring-boot:run
```

### Docker Compose

Create a `.env` file in the project root:

```env
DB_URL=jdbc:mysql://musician-db:3306/musician
DB_PASSWORD=secret
DB_NAME=musician
```

Then run:

```bash
docker compose up --build
```

The application will be available at `http://localhost:8080`.

## Configuration

Main configuration is in `src/main/resources/application.yaml`. Sensitive values are loaded from environment variables:

| Variable      | Description                              |
|---------------|------------------------------------------|
| `DB_URL`      | JDBC URL to the MySQL database           |
| `DB_PASSWORD` | Database password (root user)            |
| `DB_NAME`     | Database name (Docker Compose only)      |

## Database Migrations

Schema is managed exclusively by Liquibase (`ddl-auto: none`). Changelog files are in:

```
src/main/resources/db/changelog/
├── db.changelog-master.yaml
└── 001-create-tables.yaml
```

## File Storage

Uploaded photos and project images are stored **outside the JAR** on the filesystem:

| Directory         | Contents                          | URL prefix           |
|-------------------|-----------------------------------|----------------------|
| `photos/`         | Photos organized into albums      | `/photos/**`         |
| `project-photos/` | Project cover images              | `/project-photos/**` |
| `about-me/`       | JSON file with About Me content   | –                    |
| `contacts/`       | JSON file with contact details    | –                    |

When deployed via Docker, these directories are mounted as volumes (see `docker-compose.yml`).

## Project Structure

```
src/main/java/cz/kavka/
├── configuration/      # SecurityConfig, MvcConfig, ApplicationConfig
├── constant/           # ConstantNameResolver – URL paths and template names
├── controller/         # MVC controllers (public + admin)
├── dto/                # DTO records + MapStruct mappers
├── entity/             # JPA entities + Spring Data repositories
└── service/            # Business logic (interface + impl)

src/main/resources/
├── application.yaml
├── db/changelog/       # Liquibase migrations
├── static/             # CSS, JS, fonts, icons
└── templates/
    ├── fragments/      # Shared layouts (public + admin)
    ├── public/pages/   # Public pages
    └── admin/          # Admin panel pages
```
