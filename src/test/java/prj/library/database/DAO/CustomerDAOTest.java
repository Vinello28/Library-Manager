package prj.library.database.DAO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import prj.library.models.Customer;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the CustomerDAO class.<p>
 * <p>
 * This class contains test methods to verify the functionality of the CustomerDAO class,<p>
 * which is responsible for performing CRUD operations on Customer objects in the database.<p>
 * <p>
 * The following test methods are included:<p>
 * <p>
 * - setUp(): Sets up the test environment before each test method. Initializes a new CustomerDAO instance and creates a sample Customer with test data.<p>
 * - createCustomer(): Tests the creation of a new customer and verifies that the customer is correctly added to the database.<p>
 * - updateCustomer(): Tests the update functionality of a customer and verifies that the customer's details are correctly updated in the database.<p>
 * - deleteCustomer(): Tests the deletion of a customer and verifies that the customer is correctly removed from the database.<p>
 * - readCustomer(): Tests the retrieval of a customer by ID and verifies that the correct customer details are returned.<p>
 * - searchCustomerByName(): Tests the search functionality by customer name and verifies that the correct customer is returned.<p>
 * - searchCustomerByPhoneNumber(): Tests the search functionality by customer phone number and verifies that the correct customer is returned.<p>
 * - searchCustomerByEmail(): Tests the search functionality by customer email and verifies that the correct customer is returned.<p>
 * - searchCustomerByAddress(): Tests the search functionality by customer address and verifies that the correct customer is returned.<p>
 * - searchCustomerByNameAndPhoneNumber(): Tests the search functionality by customer name and phone number and verifies that the correct customer is returned.<p>
 * - searchCustomerByNameAndEmail(): Tests the search functionality by customer name and email and verifies that the correct customer is returned.<p>
 * - searchCustomerByNameAndAddress(): Tests the search functionality by customer name and address and verifies that the correct customer is returned.<p>
 * - searchCustomerByPhoneNumberAndEmail(): Tests the search functionality by customer phone number and email and verifies that the correct customer is returned.<p>
 * - searchCustomerByPhoneNumberAndAddress(): Tests the search functionality by customer phone number and address and verifies that the correct customer is returned.<p>
 * - searchCustomerByEmailAndAddress(): Tests the search functionality by customer email and address and verifies that the correct customer is returned.<p>
 * - searchCustomerByNameAndPhoneNumberAndEmail(): Tests the search functionality by customer name, phone number, and email and verifies that the correct customer is returned.<p>
 * - searchCustomerByNameAndPhoneNumberAndAddress(): Tests the search functionality by customer name, phone number, and address and verifies that the correct customer is returned.<p>
 * - searchCustomerByNameAndEmailAndAddress(): Tests the search functionality by customer name, email, and address and verifies that the correct customer is returned.<p>
 * - searchCustomerByPhoneNumberAndEmailAndAddress(): Tests the search functionality by customer phone number, email, and address and verifies that the correct customer is returned.<p>
 * - searchCustomerByAll(): Tests the search functionality by all customer details (name, phone number, email, and address) and verifies that the correct customer is returned.<p>
 */
class CustomerDAOTest {

    CustomerDAO customerDAO;
    Customer customer;

    /**
     * Sets up the test environment before each test method.<p>
     * Initializes a new CustomerDAO instance and creates a sample Customer<p>
     * with test data (name: "John Doe", email: "a@a.it", phone: "1234567890", address: "via1").<p>
     */
    @BeforeEach
    void setUp() {
        customerDAO = new CustomerDAO();
        customer = new Customer("John Doe", "a@a.it", "1234567890", "via1");
    }

    @Test
    void createCustomer() {
        customerDAO.createCustomer(customer);
        Customer customer1 = customerDAO.searchCustomerByName("John Doe").get(0);
        assertEquals(customer.getEmail(), customer1.getEmail());

        customerDAO.deleteCustomer(customer1);
    }

    @Test
    void updateCustomer() {
        customerDAO.createCustomer(customer);
        Customer customer1 = customerDAO.searchCustomerByName("John Doe").get(0);
        customer1.setName("Jane Doe");
        customerDAO.updateCustomer(customer1);
        Customer customer2 = customerDAO.searchCustomerByName("Jane Doe").get(0);
        assertEquals(customer1.getName(), customer2.getName());

        customerDAO.deleteCustomer(customer2);
    }

    @Test
    void deleteCustomer() {
        customerDAO.createCustomer(customer);
        Customer customer1 = customerDAO.searchCustomerByName("John Doe").get(0);

        customerDAO.deleteCustomer(customer1);

        assertEquals(0, customerDAO.searchCustomerByName("John Doe").size());
    }

    @Test
    void readCustomer() {
        customerDAO.createCustomer(customer);
        Customer customer1 = customerDAO.searchCustomerByName("John Doe").get(0);
        Customer customer2 = customerDAO.readCustomer(customer1.getId());
        assertEquals(customer1.getName(), customer2.getName());

        customerDAO.deleteCustomer(customer1);
    }

    @Test
    void searchCustomerByName() {
        customerDAO.createCustomer(customer);
        Customer customer1 = customerDAO.searchCustomerByName("John Doe").get(0);
        assertEquals(customer.getName(), customer1.getName());

        customerDAO.deleteCustomer(customer1);
    }

    @Test
    void searchCustomerByPhoneNumber() {
        customerDAO.createCustomer(customer);
        Customer customer1 = customerDAO.searchCustomerByPhoneNumber("1234567890").get(0);
        assertEquals(customer.getPhone(), customer1.getPhone());

        customerDAO.deleteCustomer(customer1);
    }

    @Test
    void searchCustomerByEmail() {
        customerDAO.createCustomer(customer);
        Customer customer1 = customerDAO.searchCustomerByEmail("a@a.it").get(0);
        assertEquals(customer.getEmail(), customer1.getEmail());

        customerDAO.deleteCustomer(customer1);
    }

    @Test
    void searchCustomerByAddress() {
        customerDAO.createCustomer(customer);
        Customer customer1 = customerDAO.searchCustomerByAddress("via1").get(0);
        assertEquals(customer.getAddress(), customer1.getAddress());

        customerDAO.deleteCustomer(customer1);
    }

    @Test
    void searchCustomerByNameAndPhoneNumber() {
        customerDAO.createCustomer(customer);
        Customer customer1 = customerDAO.searchCustomerByNameAndPhoneNumber("John Doe", "1234567890").get(0);
        assertEquals(customer.getName(), customer1.getName());
        assertEquals(customer.getPhone(), customer1.getPhone());

        customerDAO.deleteCustomer(customer1);
    }

    @Test
    void searchCustomerByNameAndEmail() {
        customerDAO.createCustomer(customer);
        Customer customer1 = customerDAO.searchCustomerByNameAndEmail("John Doe", "a@a.it").get(0);
        assertEquals(customer.getName(), customer1.getName());
        assertEquals(customer.getEmail(), customer1.getEmail());

        customerDAO.deleteCustomer(customer1);
    }

    @Test
    void searchCustomerByNameAndAddress() {
        customerDAO.createCustomer(customer);
        Customer customer1 = customerDAO.searchCustomerByNameAndAddress("John Doe", "via1").get(0);
        assertEquals(customer.getName(), customer1.getName());
        assertEquals(customer.getAddress(), customer1.getAddress());

        customerDAO.deleteCustomer(customer1);
    }

    @Test
    void searchCustomerByPhoneNumberAndEmail() {
        customerDAO.createCustomer(customer);
        Customer customer1 = customerDAO.searchCustomerByPhoneNumberAndEmail("1234567890", "a@a.it").get(0);
        assertEquals(customer.getPhone(), customer1.getPhone());
        assertEquals(customer.getEmail(), customer1.getEmail());

        customerDAO.deleteCustomer(customer1);
    }

    @Test
    void searchCustomerByPhoneNumberAndAddress() {
        customerDAO.createCustomer(customer);
        Customer customer1 = customerDAO.searchCustomerByPhoneNumberAndAddress("1234567890", "via1").get(0);
        assertEquals(customer.getPhone(), customer1.getPhone());
        assertEquals(customer.getAddress(), customer1.getAddress());

        customerDAO.deleteCustomer(customer1);
    }

    @Test
    void searchCustomerByEmailAndAddress() {
        customerDAO.createCustomer(customer);
        Customer customer1 = customerDAO.searchCustomerByEmailAndAddress("a@a.it", "via1").get(0);
        assertEquals(customer.getEmail(), customer1.getEmail());
        assertEquals(customer.getAddress(), customer1.getAddress());

        customerDAO.deleteCustomer(customer1);
    }

    @Test
    void searchCustomerByNameAndPhoneNumberAndEmail() {
        customerDAO.createCustomer(customer);
        Customer customer1 = customerDAO.searchCustomerByNameAndPhoneNumberAndEmail("John Doe", "1234567890", "a@a.it").get(0);
        assertEquals(customer.getName(), customer1.getName());
        assertEquals(customer.getPhone(), customer1.getPhone());
        assertEquals(customer.getEmail(), customer1.getEmail());

        customerDAO.deleteCustomer(customer1);
    }

    @Test
    void searchCustomerByNameAndPhoneNumberAndAddress() {
        customerDAO.createCustomer(customer);
        Customer customer1 = customerDAO.searchCustomerByNameAndPhoneNumberAndAddress("John Doe", "1234567890", "via1").get(0);
        assertEquals(customer.getName(), customer1.getName());
        assertEquals(customer.getPhone(), customer1.getPhone());
        assertEquals(customer.getAddress(), customer1.getAddress());

        customerDAO.deleteCustomer(customer1);
    }

    @Test
    void searchCustomerByNameAndEmailAndAddress() {
        customerDAO.createCustomer(customer);
        Customer customer1 = customerDAO.searchCustomerByNameAndEmailAndAddress("John Doe", "a@a.it", "via1").get(0);
        assertEquals(customer.getName(), customer1.getName());
        assertEquals(customer.getEmail(), customer1.getEmail());
        assertEquals(customer.getAddress(), customer1.getAddress());

        customerDAO.deleteCustomer(customer1);
    }

    @Test
    void searchCustomerByPhoneNumberAndEmailAndAddress() {
        customerDAO.createCustomer(customer);
        Customer customer1 = customerDAO.searchCustomerByPhoneNumberAndEmailAndAddress("1234567890", "a@a.it", "via1").get(0);
        assertEquals(customer.getPhone(), customer1.getPhone());
        assertEquals(customer.getEmail(), customer1.getEmail());
        assertEquals(customer.getAddress(), customer1.getAddress());

        customerDAO.deleteCustomer(customer1);
    }

    @Test
    void searchCustomerByAll() {
        customerDAO.createCustomer(customer);
        Customer customer1 = customerDAO.searchCustomerByAll("John Doe", "1234567890", "a@a.it", "via1").get(0);
        assertEquals(customer.getName(), customer1.getName());
        assertEquals(customer.getPhone(), customer1.getPhone());
        assertEquals(customer.getEmail(), customer1.getEmail());
        assertEquals(customer.getAddress(), customer1.getAddress());

        customerDAO.deleteCustomer(customer1);
    }
}