package prj.library.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
class LendsTest {

    Lends lend;
    @BeforeEach
    void setUp() {
        lend = new Lends(1, 1, LocalDate.now());
    }

    @Test
    void isLate() {
        assertFalse(lend.isLate());
    }

    @Test
    void getId() {
        assertEquals(0, lend.getId());
    }

    @Test
    void getBookId() {
        assertEquals(1, lend.getBookId());
    }

    @Test
    void getReturnDate() {
        assertEquals(LocalDate.now(), lend.getReturnDate());
    }

    @Test
    void setId() {
        lend.setId(1);
        assertEquals(1, lend.getId());
    }

    @Test
    void setBookId() {
        lend.setBookId(2);
        assertEquals(2, lend.getBookId());
    }

    @Test
    void setReturnDate() {
        lend.setReturnDate(LocalDate.now().plusDays(1));
        assertEquals(LocalDate.now().plusDays(1), lend.getReturnDate());
    }

    @Test
    void getCustomerId() {
        assertEquals(1, lend.getCustomerId());
    }

    @Test
    void setCustomerId() {
        lend.setCustomerId(2);
        assertEquals(2, lend.getCustomerId());
    }
}