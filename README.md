![logo](https://github.com/user-attachments/assets/dfce9c5e-e89b-410f-b1e1-e0b39bfacc42)

# Library Management System

This project is a Library Management System built using Java and Maven. It provides functionalities to manage books and customers in a library database.

## Features

- Manage books by title, author, genre, and year.
- Manage customers by name, phone number, email, and address.
- Establish and close connections to the database.

## Technologies Used

- Java
- Maven
- MySQL

## Getting Started

### Prerequisites

- Java 11 or higher
- Maven 3.6.0 or higher
- MySQL

### Installation

1. Clone the repository:
   ```sh
   git clone https://github.com/yourusername/library-management-system.git
   ```
2. Navigate to the project directory:
   ```sh
   cd library-management-system
   ```
3. Update the database connection details in `DatabaseController.java`:
   ```java
   private static String DB_URL = "jdbc:mysql://urDB";
   private static String DB_USER = "user";
   private static String DB_PASSWORD = "password";
   ```
4. Build the project using Maven:
   ```sh
   mvn clean install
   ```

### Usage

1. Run the application:
   ```sh
   mvn exec:java -Dexec.mainClass="prj.library.Main"
   ```

## Contributing

1. Fork the repository.
2. Create a new branch (`git checkout -b feature-branch`).
3. Make your changes.
4. Commit your changes (`git commit -m 'Add some feature'`).
5. Push to the branch (`git push origin feature-branch`).
6. Open a pull request.

## License

This project is licensed under the MIT License. See the `LICENSE` file for details.

## Contact

Gabriele Vianello - [vianello.tech@gmail.com](mailto:vianello.tech@gmail.com)

Project Link: [https://github.com/Vinello28/Library](https://github.com/Vinello28/Library)
