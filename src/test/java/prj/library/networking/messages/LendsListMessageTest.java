package prj.library.networking.messages;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import prj.library.models.Lends;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class LendsListMessageTest {

    LendsListMessage lendsListMessage;

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