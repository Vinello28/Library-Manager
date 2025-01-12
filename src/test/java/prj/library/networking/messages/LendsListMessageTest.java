package prj.library.networking.messages;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import prj.library.models.Lends;
import java.time.LocalDate;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the LendsListMessage class.
 * 
 * This test class verifies the functionality of the LendsListMessage class,
 * which is responsible for handling messages related to a list of lends.
 * 
 * The following test methods are included:
 * 
 * - setUp(): Sets up the test environment before each test method is executed.
 *   Initializes a list of lends with sample data and creates a LendsListMessage
 *   with the specified operation and lends list.
 * 
 * - getLends(): Tests the getLends() method to ensure it returns the correct
 *   list of lends.
 * 
 * - setLends(): Tests the setLends() method to ensure it correctly updates the
 *   list of lends.
 */
class LendsListMessageTest {

    LendsListMessage lendsListMessage;

    /**
     * Sets up the test environment before each test method is executed.
     * Initializes a list of lends with sample data and creates a LendsListMessage
     * with the specified operation and lends list.
     */
    @BeforeEach
    void setUp() {
        ArrayList<Lends> lends = new ArrayList<>();
        lends.add(new Lends(1, 1, LocalDate.now(), false));
        lends.add(new Lends(2, 2, LocalDate.now(), false));
        lendsListMessage = new LendsListMessage(Operation.RESULT_LENDS, lends);
    }

    @Test
    void getLends() {
        assertEquals(2, lendsListMessage.getLends().size());
        assertEquals(1, lendsListMessage.getLends().get(0).getBookId());
        assertEquals(2, lendsListMessage.getLends().get(1).getBookId());
    }

    @Test
    void setLends() {
        ArrayList<Lends> lends = new ArrayList<>();
        lends.add(new Lends(3, 3, LocalDate.now(), false));
        lends.add(new Lends(4, 4, LocalDate.now(), false));
        lendsListMessage.setLends(lends);
        assertEquals(2, lendsListMessage.getLends().size());
        assertEquals(3, lendsListMessage.getLends().get(0).getBookId());
        assertEquals(4, lendsListMessage.getLends().get(1).getBookId());
    }
}