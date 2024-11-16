package prj.library.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import prj.library.models.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO implements CustomerDAOInterface {
    private String DB_URL;
    private String DB_USER;
    private String DB_PASSWORD;

    public CustomerDAO(String DB_URL, String DB_USER, String DB_PASSWORD) {
        this.DB_URL = DB_URL;
        this.DB_USER = DB_USER;
        this.DB_PASSWORD = DB_PASSWORD;
    }

    public void createCustomer(Customer customer) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "INSERT INTO customers (name, email, phone, address) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, customer.getName());
            statement.setString(2, customer.getEmail());
            statement.setString(3, customer.getPhone());
            statement.setString(4, customer.getAddress());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SERVER | CustomerDAO | createCustomer: " + e.getMessage());
        }
    }

    public void updateCustomer(Customer customer) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "UPDATE customers SET name = ?, email = ?, phone = ?, address = ? WHERE id_c = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, customer.getName());
            statement.setString(2, customer.getEmail());
            statement.setString(3, customer.getPhone());
            statement.setString(4, customer.getAddress());
            statement.setInt(5, customer.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SERVER | CustomerDAO | updateCustomer: " + e.getMessage());
        }
    }

    public void deleteCustomer(Customer customer) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "DELETE FROM customers WHERE id_c = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, customer.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SERVER | CustomerDAO | deleteCustomer: " + e.getMessage());
        }
    }

    public synchronized Customer readCustomer(int id) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM customers WHERE id_c = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet rst = statement.executeQuery();
            if (rst.next()) return CustomerExtractor(rst);

        } catch (SQLException e) {
            System.out.println("SERVER | CustomerDAO | readCustomer: " + e.getMessage());
        }
        return null;
    }

    public synchronized List<Customer> searchCustomerByName(String name) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM customers WHERE name = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, name);
            return getCustomers(statement);
        } catch (SQLException e) {
            System.out.println("SERVER | CustomerDAO | searchCustomerByName: " + e.getMessage());
        }
        return null;
    }

    public synchronized List<Customer> searchCustomerByPhoneNumber(String phoneNumber) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM customers WHERE phone = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, phoneNumber);
            return getCustomers(statement);
        } catch (SQLException e) {
            System.out.println("SERVER | CustomerDAO | searchCustomerByPhoneNumber: " + e.getMessage());
        }
        return null;
    }

    public synchronized List<Customer> searchCustomerByEmail(String email) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM customers WHERE email = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, email);
            return getCustomers(statement);
        } catch (SQLException e) {
            System.out.println("SERVER | CustomerDAO | searchCustomerByEmail: " + e.getMessage());
        }
        return null;
    }

    public synchronized List<Customer> readAllCustomers() {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM customers";
            PreparedStatement statement = conn.prepareStatement(query);
            return getCustomers(statement);
        } catch (SQLException e) {
            System.out.println("SERVER | CustomerDAO | readAllCustomers: " + e.getMessage());
            return new ArrayList<Customer>();
        }
    }

    public synchronized List<Customer> searchCustomerByAddress(String address) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM customers WHERE address = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, address);
            return getCustomers(statement);
        } catch (SQLException e) {
            System.out.println("SERVER | CustomerDAO | searchCustomerByAddress: " + e.getMessage());
            return new ArrayList<Customer>();
        }
    }

    public synchronized List<Customer> searchCustomerByNameAndPhoneNumber(String name, String phoneNumber) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM customers WHERE name = ? AND phone = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, phoneNumber);
            return getCustomers(statement);
        } catch (SQLException e) {
            System.out.println("SERVER | CustomerDAO | searchCustomerByNameAndPhoneNumber: " + e.getMessage());
        }
        return null;
    }

    public synchronized List<Customer> searchCustomerByNameAndEmail(String name, String email) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM customers WHERE name = ? AND email = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, email);
            return getCustomers(statement);
        } catch (SQLException e) {
            System.out.println("SERVER | CustomerDAO | searchCustomerByNameAndEmail: " + e.getMessage());
        }
        return null;
    }

    public synchronized List<Customer> searchCustomerByNameAndAddress(String name, String address) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM customers WHERE name = ? AND address = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, address);
            return getCustomers(statement);
        } catch (SQLException e) {
            System.out.println("SERVER | CustomerDAO | searchCustomerByNameAndAddress: " + e.getMessage());
        }
        return null;
    }

    public synchronized List<Customer> searchCustomerByPhoneNumberAndEmail(String phoneNumber, String email) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM customers WHERE phone = ? AND email = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, phoneNumber);
            statement.setString(2, email);
            return getCustomers(statement);
        } catch (SQLException e) {
            System.out.println("SERVER | CustomerDAO | searchCustomerByPhoneNumberAndEmail: " + e.getMessage());
        }
        return null;
    }

    public synchronized List<Customer> searchCustomerByPhoneNumberAndAddress(String phoneNumber, String address) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM customers WHERE phone = ? AND address = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, phoneNumber);
            statement.setString(2, address);
            return getCustomers(statement);
        } catch (SQLException e) {
            System.out.println("SERVER | CustomerDAO | searchCustomerByPhoneNumberAndAddress: " + e.getMessage());
        }
        return null;
    }

    public synchronized List<Customer> searchCustomerByEmailAndAddress(String email, String address) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM customers WHERE email = ? AND address = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, email);
            statement.setString(2, address);
            return getCustomers(statement);
        } catch (SQLException e) {
            System.out.println("SERVER | CustomerDAO | searchCustomerByEmailAndAddress: " + e.getMessage());
        }
        return null;
    }

    public synchronized List<Customer> searchCustomerByNameAndPhoneNumberAndEmail(String name, String phoneNumber, String email) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM customers WHERE name = ? AND phone = ? AND email = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, phoneNumber);
            statement.setString(3, email);
            return getCustomers(statement);
        } catch (SQLException e) {
            System.out.println("SERVER | CustomerDAO | searchCustomerByNameAndPhoneNumberAndEmail: " + e.getMessage());
        }
        return null;
    }

    public synchronized List<Customer> searchCustomerByNameAndPhoneNumberAndAddress(String name, String phoneNumber, String address) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM customers WHERE name = ? AND phone = ? AND address = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, phoneNumber);
            statement.setString(3, address);
            return getCustomers(statement);
        } catch (SQLException e) {
            System.out.println("SERVER | CustomerDAO | searchCustomerByNameAndPhoneNumberAndAddress: " + e.getMessage());
        }
        return null;
    }

    public synchronized List<Customer> searchCustomerByNameAndEmailAndAddress(String name, String email, String address) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM customers WHERE name = ? AND email = ? AND address = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, address);
            return getCustomers(statement);
        } catch (SQLException e) {
            System.out.println("SERVER | CustomerDAO | searchCustomerByNameAndEmailAndAddress: " + e.getMessage());
        }
        return null;
    }

    public synchronized List<Customer> searchCustomerByPhoneNumberAndEmailAndAddress(String phoneNumber, String email, String address) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM customers WHERE phone = ? AND email = ? AND address = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, phoneNumber);
            statement.setString(2, email);
            statement.setString(3, address);
            return getCustomers(statement);
        } catch (SQLException e) {
            System.out.println("SERVER | CustomerDAO | searchCustomerByPhoneNumberAndEmailAndAddress: " + e.getMessage());
        }
        return null;
    }

    public synchronized List<Customer> searchCustomerByAll(String name, String phoneNumber, String email, String address) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM customers WHERE name = ? AND phone = ? AND email = ? AND address = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, phoneNumber);
            statement.setString(3, email);
            statement.setString(4, address);
            return getCustomers(statement);
        } catch (SQLException e) {
            System.out.println("SERVER | CustomerDAO | searchCustomerByNameAndPhoneNumberAndEmailAndAddress: " + e.getMessage());
            return new ArrayList<Customer>();
        }
    }

    private synchronized List<Customer> getCustomers(PreparedStatement pstmt) throws SQLException {
        ResultSet resultSet = pstmt.executeQuery();
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        try {
            while (resultSet.next()) {
                customers.add(CustomerExtractor(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("SERVER | CustomerDAO | getCustomers: " + e.getMessage());
        }
        return customers;
    }

    private Customer CustomerExtractor(ResultSet rst) throws SQLException {
        Customer customer = new Customer();
        customer.setId(rst.getInt("id_c"));
        customer.setName(rst.getString("name"));
        customer.setEmail(rst.getString("email"));
        customer.setPhone(rst.getString("phone"));
        customer.setAddress(rst.getString("address"));
        return customer;
    }
}
