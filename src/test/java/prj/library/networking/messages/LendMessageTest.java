package prj.library.networking.messages;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import prj.library.models.Lends;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class LendMessageTest {

    LendMessage lendMessage;

    @BeforeEach
    void setUp() {
        Lends lends = new Lends(1, 1, LocalDate.now());
        lendMessage = new LendMessage(Operation.ADD_LEND, lends);
    }

    @Test
    void getLend() {
        assertEquals(LocalDate.now(), lendMessage.getLend().getReturnDate());
    }
}