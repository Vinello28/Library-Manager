package prj.library.database.DAO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import prj.library.models.Lends;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class LendsDAOTest {

    LendsDAO lendsDAO;
    Lends lend;

    //TODO: Implement tests for LendsDAO

    @BeforeEach
    void setUp() {
        lendsDAO = new LendsDAO();
        lend = new Lends(1, 1, LocalDate.now(), false);
    }

    @Test
    void addLend() {

    }
}