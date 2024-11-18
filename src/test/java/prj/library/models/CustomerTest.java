package prj.library.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    Customer customer = new Customer();

    @BeforeEach
    void setUp() {
        customer = new Customer(1, "Paolo", "paolo@gmail.com", "09123456789", "Manila");
    }

    @Test
    void getId() {
        assertEquals(1, customer.getId());
    }

    @Test
    void getName() {
        assertEquals("Paolo", customer.getName());
    }

    @Test
    void getEmail() {
        assertEquals("paolo@gmail.com", customer.getEmail());
    }

    @Test
    void getPhone() {
        assertEquals("09123456789", customer.getPhone());
    }

    @Test
    void setId() {
        customer.setId(2);
        assertEquals(2, customer.getId());
    }

    @Test
    void setName() {
        customer.setName("Juan");
        assertEquals("Juan", customer.getName());
    }

    @Test
    void setEmail() {
        customer.setEmail("paolino@gmail.com");
        assertEquals("paolino@gmail.com", customer.getEmail());
    }

    @Test
    void setPhone() {
        customer.setPhone("09123456788");
        assertEquals("09123456788", customer.getPhone());
    }

    @Test
    void getAddress() {
        assertEquals("Manila", customer.getAddress());
    }

    @Test
    void setAddress() {
        customer.setAddress("Ancona");
        assertEquals("Ancona", customer.getAddress());
    }
}