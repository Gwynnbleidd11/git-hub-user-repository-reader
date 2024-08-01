# GitHub Repository and Branch Viewer

## Introduction
GitHub-repo-reader is a Spring Boot application that allows users to fetch and view repositories and their branches from GitHub. The application provides endpoints to get the list of repositories by a GitHub username, along with branch details such as the name and the last commit SHA.

## Features
- Fetch repositories for a given GitHub username.
- Display branch details for each repository.
- Error handling with appropriate JSON responses.

## Requirements
- Java 21 or higher
- Gradle 8.8 or higher

## Setup
1. **Clone the repository:**
   ```bash
   git clone https://github.com/your-username/github-repo-branch-viewer.git
   cd github-repo-branch-viewer

2. Build the project

## Configuration
Configure the application properties:

Edit the src/main/resources/application.properties file to include any necessary configurations:

    spring.application.name=GitHub-repo-reader
    github.api.host=https://api.github.com

## Endpoints
Get Repositories and Branches:
    GET /github/{username}

    Path Variable "username" is user GitHub login/username

## Example response:

[
    {
        "name": "repo-name",
        "ownerName": "owner-login",
        "branchList": [
            {
                "name": "branch-name",
                "sha": "last-commit-sha"
            }
        ]
    }
]

## Exception Handling
In case of errors (e.g., user not found), the application returns a JSON response:

{
    "status": 404,
    "message": "User not found. Provided user does not exist. Please ensure you are using the correct username."
}

## Technologies Used
Java 21
Spring Boot 3.3.2
Gradle
REST API
GitHub API
