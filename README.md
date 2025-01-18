![logo](https://github.com/user-attachments/assets/dfce9c5e-e89b-410f-b1e1-e0b39bfacc42)

# Library Management System
This project is a Library Management System built using Java/JavaFX and Maven. It provides functionalities to manage books, customers and lends in a library database.
Grade: 30 (5cfu)

## Features
- Manage books by title, author, genre, and year.
- Manage customers by name, phone number, email, and address.
- Manage lends by date, returned property.
- Send email alert notifications
- System usage stats.
- Establish and close connections to the database.

## Technologies Used
- Java
- Maven
- MySQL
- Java Mail
- Google Gmail

## Getting Started

### Prerequisites
- Java 11 or higher
- Maven 3.6.0 or higher
- MySQL 8
- JavaFX

### Installation
1. Clone the repository:
   ```sh
   git clone https://github.com/Vinello28/library.git
   ```
2. Navigate to the project directory:
   ```sh
   cd library
   ```
3. Update database and system connections details in `config.properties`:
   ```properties
   database.url=jdbc:mysql://urDB
   database.user=user
   database.password=password
   ecc
   ```
4. Build the project using Maven:
   ```sh
   mvn clean install
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
