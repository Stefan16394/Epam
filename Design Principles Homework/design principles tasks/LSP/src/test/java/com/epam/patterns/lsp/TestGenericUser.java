package com.epam.patterns.lsp;

import org.junit.Test;
import java.util.HashSet;
import static com.epam.patterns.lsp.DatabaseGateway.WRITE_TO_DATABASE;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class TestGenericUser {

    @Test
    public void shouldNotGetValueOfAccessRightWhenRightIsProtected() {
        HashSet<String> protectedRights = new HashSet<>();
        protectedRights.add(WRITE_TO_DATABASE);

        GenericUser genericUser = new GenericUser(protectedRights);
        genericUser.setupAccessRight(WRITE_TO_DATABASE, true);

        assertFalse(genericUser.getValueOfAccessRight(WRITE_TO_DATABASE));
    }

    @Test
    public void shouldGetValueOfAccessRightWhenRightIsNotProtected() {
        final String CHANGE_IN_DATABASE = "change in database";

        GenericUser genericUser = new GenericUser(new HashSet<String>());
        genericUser.setupAccessRight(CHANGE_IN_DATABASE, true);

        assertTrue(genericUser.getValueOfAccessRight(CHANGE_IN_DATABASE));
    }

}
