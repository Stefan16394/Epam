package com.epam.patterns.lsp;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GenericUser {
    private Map<String, Boolean> accessRights = new HashMap<>();
    private Set<String> protectedRights;

    GenericUser(HashSet<String> protectedRights) {
        this.protectedRights = protectedRights;
    }

    void setupAccessRight(String right, boolean value) {
        if (!protectedRights.contains(right)) {
            accessRights.put(right, value);
        }
    }

    boolean getValueOfAccessRight(String right) {
        return accessRights.getOrDefault(right, false);
    }
}
