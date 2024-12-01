package prj.library.networking.messages;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GenericMessageTest {

    GenericMessage genericMessage;

    @BeforeEach
    void setUp() {
        genericMessage = new GenericMessage(Operation.GENERIC_RESPONSE, true);
    }

    @Test
    void getResponse() {
        assertTrue(genericMessage.getResponseBoolean());
    }
}