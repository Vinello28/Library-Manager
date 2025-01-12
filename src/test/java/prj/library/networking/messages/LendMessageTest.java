package prj.library.networking.messages;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import prj.library.models.Lends;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the LendMessage class.
 * 
 * This test class verifies the functionality of the LendMessage class,
 * which is responsible for handling messages related to a lend.
 * 
 * The following test methods are included:
 * 
 * - setUp(): Sets up the test environment before each test method is executed.
 *   Initializes a Lends object with sample data and assigns it to a new LendMessage
 *   with the operation type set to ADD_LEND.
 * 
 * - getLend(): Tests the getLend() method to ensure it returns the correct
 *   lend object.
 */
class LendMessageTest {

    LendMessage lendMessage;

    /**
     * Sets up the test environment before each test method is executed.
     * Initializes a Lends object with sample data and assigns it to a new LendMessage
     * with the operation type set to ADD_LEND.
     */
    @BeforeEach
    void setUp() {
        Lends lends = new Lends(1, 1, LocalDate.now(), false);
        lendMessage = new LendMessage(Operation.ADD_LEND, lends);
    }

    /**
     * Tests the getLend method of the LendMessage class.
     * This test verifies that the return date of the lend message
     * is equal to the current date.
     */
    @Test
    void getLend() {
        assertEquals(LocalDate.now(), lendMessage.getLend().getReturnDate());
    }
}