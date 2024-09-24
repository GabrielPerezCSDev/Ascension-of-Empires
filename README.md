
# Backend Application

This backend application is built using Spring Boot and supports a multiplayer strategy game with features like player management, game sessions, real-time WebSocket communication, and in-game purchases. The backend handles the core game logic, including boards, players, resources, servers, and sessions.

## Table of Contents
- [Features](#features)
- [Project Structure](#project-structure)
- [Running the Application](#running-the-application)
- [API Documentation](#api-documentation)
- [Contributors](#contributors)

## Features

### Player and Country Management
- **User Registration/Login**: Users can register and log in, managing their own player profile.
- **Player Actions**: Players can select countries, manage resources, and interact with the game board.
- **Real-Time Game Interaction**: Players interact with the game in real-time, including purchases and board expansions.

### Server and Game Settings
- **Server Creation**: Allows the creation of multiplayer game servers, each with specific settings (like max players and turn limits).
- **Dynamic Game Board**: The board size and configuration adjust based on the number of players, allowing for strategic gameplay.

### In-Game Resources and Shop
- **Resource Management**: The game dynamically allocates resources such as Iron and Water on the game board.
- **Item Shop**: Players can purchase items that enhance their performance in the game.

### WebSocket Support
- **Real-Time Updates**: The game supports real-time updates using WebSocket for in-game chat and board updates.
- **Server-Specific Chat**: Players can chat within their specific game server using WebSockets.

## Project Structure

### `API`
Defines the Swagger configuration for API documentation using `SpringFoxConfig`.

### `Board`
Contains the logic for generating, initializing, and managing the game board. Includes multiple board types (small, medium, large) that scale with the number of players.

### `Configuration`
Handles Jackson object mappers and JSON serialization configurations.

### `Country`
Defines countries in the game with attributes like `gpt` (gold per turn) and `grwpt` (growth per turn). Includes `CountryService` for country-related operations.

### `Exceptions`
Defines custom exceptions such as `UserAlreadyLoggedInException` to handle error scenarios.

### `Game`
Manages the core game logic, including initializing the game, processing player turns, and determining the winner. The `GameController` exposes REST endpoints for interacting with the game.

### `Player`
Manages players within the game, including their attributes like gold, tiles owned, and items purchased. Provides REST API endpoints through `PlayerController`.

### `Resources`
Handles resource management for game tiles, including assigning resource types and generating resource clusters.

### `Rules`
Defines the rules of the game, including processing player turns and calculating winners based on game outcomes.

### `Server`
Handles the creation and management of game servers. The `ServerService` provides methods to add players to servers, manage server settings, and update player states.

### `ServerSetting`
Manages server settings like max player size and turn limits. Used during server creation to configure game settings.

### `Session`
Manages user sessions, allowing inspection and modification of session variables.

### `Shop`
Manages in-game purchases, allowing players to buy items from the shop. The `ItemsController` provides REST endpoints for interacting with the shop.

### `Tile`
Defines tiles on the game board, with attributes like owner and resources.

### `User`
Handles user management, including registration, login, and session handling. The `UserController` exposes REST APIs for user operations.

### `WebSockets`
Implements WebSocket functionality for real-time communication. Includes a chat system that is server-specific, as well as game updates sent to the front end.

### `BackendapplicationApplication`
The main entry point for the Spring Boot application.

## Running the Application

To run this application, follow these steps:

1. **Clone the repository**:
   ```bash
   git clone https://github.com/GabrielPerezCSDev/PokemonASCII.git
   ```

2. **Navigate to the project directory**:
   ```bash
   cd backend-application
   ```

3. **Build and run the application**:
   You can build the application using Maven or Gradle, then run the application using:
   ```bash
   mvn spring-boot:run
   ```

   or

   ```bash
   java -jar target/backendapplication-0.0.1-SNAPSHOT.jar
   ```

4. **Access the API**:
   Once the application is running, you can access the API at:
   ```
   http://localhost:8080
   ```

5. **View API Documentation**:
   The application uses Swagger for API documentation. You can view it at:
   ```
   http://localhost:8080/swagger-ui.html
   ```

## API Documentation

The application includes Swagger for documenting the API endpoints. The Swagger UI is available at `http://localhost:8080/swagger-ui.html`, where you can interact with and test the API directly.

## Contributors

- Gabriel Perez
