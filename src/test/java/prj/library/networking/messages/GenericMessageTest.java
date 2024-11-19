package prj.library.networking.messages;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GenericMessageTest {

    GenericMessage genericMessage;

    @BeforeEach
    void setUp() {
        genericMessage = new GenericMessage(true);
    }

    @Test
    void getResponse() {
        assertTrue(genericMessage.getResponse());
    }
}