package prj.library.database.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import prj.library.models.Customer;
import prj.library.utils.CLIUtils;
import static prj.library.database.DatabaseController.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Customer Data Access Object
 */
public class CustomerDAO implements CustomerDAOInterface {

    public CustomerDAO() {
    }

    public synchronized void createCustomer(Customer customer) {
        Connection conn = getConnection();
        String query = "INSERT INTO customers (name, email, phone, address) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, customer.getName());
            statement.setString(2, customer.getEmail());
            statement.setString(3, customer.getPhone());
            statement.setString(4, customer.getAddress());
            statement.executeUpdate();
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        }
        closeConnection(conn);
    }

    public synchronized void updateCustomer(Customer customer) {
        Connection conn = getConnection();
        String query = "UPDATE customers SET name = ?, email = ?, phone = ?, address = ? WHERE id_c = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, customer.getName());
            statement.setString(2, customer.getEmail());
            statement.setString(3, customer.getPhone());
            statement.setString(4, customer.getAddress());
            statement.setInt(5, customer.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        }
        closeConnection(conn);
    }

    public synchronized void deleteCustomer(Customer customer) {
        Connection conn = getConnection();
        String query = "DELETE FROM customers WHERE id_c = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, customer.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        }
        closeConnection(conn);
    }

    public synchronized Customer readCustomer(int id) {
        Connection conn = getConnection();
        String query = "SELECT * FROM customers WHERE id_c = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet rst = statement.executeQuery();
            if (rst.next()) return CustomerExtractor(rst);

        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        }
        closeConnection(conn);
        return null;
    }

    public synchronized List<Customer> searchCustomerByName(String name) {
        Connection conn = getConnection();
        String query = "SELECT * FROM customers WHERE name = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, name);
            return getCustomers(statement);
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        }
        closeConnection(conn);
        return null;
    }

    public synchronized List<Customer> searchCustomerByPhoneNumber(String phoneNumber) {
        Connection conn = getConnection();
        String query = "SELECT * FROM customers WHERE phone = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, phoneNumber);
            return getCustomers(statement);
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        }
        closeConnection(conn);
        return null;
    }

    public synchronized List<Customer> searchCustomerByEmail(String email) {
        Connection conn = getConnection();
        String query = "SELECT * FROM customers WHERE email = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, email);
            return getCustomers(statement);
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        }
        return null;
    }

    public synchronized List<Customer> readAllCustomers() {
        Connection conn = getConnection();
        String query = "SELECT * FROM customers";
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            List<Customer> res = getCustomers(statement);
            closeConnection(conn);
            return res;
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public synchronized List<Customer> searchCustomerByAddress(String address) {
        Connection conn = getConnection();
        String query = "SELECT * FROM customers WHERE address = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, address);
            List<Customer> res = getCustomers(statement);
            closeConnection(conn);
            return res;
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
            return new ArrayList<>();
        }

    }

    public synchronized List<Customer> searchCustomerByNameAndPhoneNumber(String name, String phoneNumber) {
        Connection conn = getConnection();
        String query = "SELECT * FROM customers WHERE name = ? AND phone = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, phoneNumber);
            return getCustomers(statement);
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        }
        closeConnection(conn);
        return null;
    }

    public synchronized List<Customer> searchCustomerByNameAndEmail(String name, String email) {
        Connection conn = getConnection();
        String query = "SELECT * FROM customers WHERE name = ? AND email = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, email);
            return getCustomers(statement);
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        }
        closeConnection(conn);
        return null;
    }

    public synchronized List<Customer> searchCustomerByNameAndAddress(String name, String address) {
        Connection conn = getConnection();
        String query = "SELECT * FROM customers WHERE name = ? AND address = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, address);
            return getCustomers(statement);
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        }
        closeConnection(conn);
        return null;
    }

    public synchronized List<Customer> searchCustomerByPhoneNumberAndEmail(String phoneNumber, String email) {
        Connection conn = getConnection();
        String query = "SELECT * FROM customers WHERE phone = ? AND email = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, phoneNumber);
            statement.setString(2, email);
            return getCustomers(statement);
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        }
        closeConnection(conn);
        return null;
    }

    public synchronized List<Customer> searchCustomerByPhoneNumberAndAddress(String phoneNumber, String address) {
        Connection conn = getConnection();
        String query = "SELECT * FROM customers WHERE phone = ? AND address = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, phoneNumber);
            statement.setString(2, address);
            return getCustomers(statement);
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        }
        closeConnection(conn);
        return null;
    }

    public synchronized List<Customer> searchCustomerByEmailAndAddress(String email, String address) {
        Connection conn = getConnection();
        String query = "SELECT * FROM customers WHERE email = ? AND address = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, email);
            statement.setString(2, address);
            return getCustomers(statement);
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        }
        closeConnection(conn);
        return null;
    }

    public synchronized List<Customer> searchCustomerByNameAndPhoneNumberAndEmail(String name, String phoneNumber, String email) {
        Connection conn = getConnection();
        String query = "SELECT * FROM customers WHERE name = ? AND phone = ? AND email = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, phoneNumber);
            statement.setString(3, email);
            return getCustomers(statement);
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        }
        closeConnection(conn);
        return null;
    }

    public synchronized List<Customer> searchCustomerByNameAndPhoneNumberAndAddress(String name, String phoneNumber, String address) {
        Connection conn = getConnection();
        String query = "SELECT * FROM customers WHERE name = ? AND phone = ? AND address = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, phoneNumber);
            statement.setString(3, address);
            return getCustomers(statement);
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        }
        closeConnection(conn);
        return null;
    }

    public synchronized List<Customer> searchCustomerByNameAndEmailAndAddress(String name, String email, String address) {
        Connection conn = getConnection();
        String query = "SELECT * FROM customers WHERE name = ? AND email = ? AND address = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, address);
            return getCustomers(statement);
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        }
        closeConnection(conn);
        return null;
    }

    public synchronized List<Customer> searchCustomerByPhoneNumberAndEmailAndAddress(String phoneNumber, String email, String address) {
        Connection conn = getConnection();
        String query = "SELECT * FROM customers WHERE phone = ? AND email = ? AND address = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, phoneNumber);
            statement.setString(2, email);
            statement.setString(3, address);
            return getCustomers(statement);
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        }
        closeConnection(conn);
        return null;
    }

    public synchronized List<Customer> searchCustomerByAll(String name, String phoneNumber, String email, String address) {
        Connection conn = getConnection();
        String query = "SELECT * FROM customers WHERE name = ? AND phone = ? AND email = ? AND address = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, phoneNumber);
            statement.setString(3, email);
            statement.setString(4, address);
            List<Customer> res = getCustomers(statement);
            closeConnection(conn);
            return res;
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
            return new ArrayList<>();
        }

    }

    /**
     * Gets a list of customers from a PreparedStatement
     *
     * @param pstmt PreparedStatement
     * @return List of customers
     * @throws SQLException if a database error occurs
     */
    private synchronized List<Customer> getCustomers(PreparedStatement pstmt) throws SQLException {
        ResultSet resultSet = pstmt.executeQuery();
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        try {
            while (resultSet.next()) {
                customers.add(CustomerExtractor(resultSet));
            }
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        }
        return customers;
    }

    /**
     * Extracts a customer from a ResultSet
     *
     * @param rst ResultSet
     * @return Customer
     * @throws SQLException if a database error occurs
     */
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
