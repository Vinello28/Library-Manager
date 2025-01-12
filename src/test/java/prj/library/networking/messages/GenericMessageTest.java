package prj.library.networking.messages;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the GenericMessage class.
 * 
 * This test class verifies the functionality of the GenericMessage class,
 * which is responsible for handling generic messages.
 * 
 * The following test methods are included:
 * 
 * - setUp(): Sets up the test environment before each test method is executed.
 *   Initializes a GenericMessage instance with a GENERIC_RESPONSE operation and a true flag.
 * 
 * - getResponse(): Tests the getResponse() method to ensure it returns the correct
 *   response value.
 */
class GenericMessageTest {

    GenericMessage genericMessage;

    /**
     * Sets up the test environment before each test method execution.
     * Initializes a GenericMessage instance with a GENERIC_RESPONSE operation and a true flag.
     */
    @BeforeEach
    void setUp() {
        genericMessage = new GenericMessage(Operation.GENERIC_RESPONSE, true);
    }

    @Test
    void getResponse() {
        assertTrue(genericMessage.getResponseBoolean());
    }
}