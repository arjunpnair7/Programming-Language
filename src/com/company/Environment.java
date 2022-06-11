package com.company;

import java.util.HashMap;
import java.util.Map;

public class Environment {
    private Map<String, Object> envVariables;

    public Environment() {
        envVariables = new HashMap<>();
    }

    public void addVariable(String identifier, Object value) {
        envVariables.put(identifier, value);
    }

    public boolean checkIfVariableExists(String identifier) {
        return envVariables.containsKey(identifier);
    }


}
