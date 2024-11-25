package prj.library.database.DAO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import prj.library.models.Customer;

import static org.junit.jupiter.api.Assertions.*;

class CustomerDAOTest {

    CustomerDAO customerDAO;
    Customer customer;

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