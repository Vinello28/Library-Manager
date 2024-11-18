package prj.library.networking.messages;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageTest {

    Message message;

    @BeforeEach
    void setUp() {
        message.setMessage("test");
        message.setOperation(Operation.ADD_BOOK);
    }

    @Test
    void getOperation() {
        assertEquals(Operation.ADD_BOOK, message.getOperation());
    }

    @Test
    void getMessage() {
        assertEquals("test", message.getMessage());
    }

    @Test
    void setOperation() {
        message.setOperation(Operation.UPDATE_BOOK);
        assertEquals(Operation.UPDATE_BOOK, message.getOperation());
    }

    @Test
    void setMessage() {
        message.setMessage("test2");
        assertEquals("test2", message.getMessage());
    }
}