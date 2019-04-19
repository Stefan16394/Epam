package com.epam.patterns.lsp;

import org.junit.Test;
import static org.junit.Assert.*;
import static com.epam.patterns.lsp.DatabaseGateway.*;

public class TestDBAccess {

    @Test
    public void testPowerUser() {

        PowerUser powerUser = new PowerUser();

        writeToDBForce(powerUser, "payload");
        assertEquals(readFromDB(), "payload");
    }

}
