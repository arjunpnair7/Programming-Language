package com.company;

import java.util.HashMap;
import java.util.Map;

public class Environment {
    private Map<String, Object> envVariables;
    private Environment enclosing;

    public Environment(Environment enclosing) {
        this.enclosing = enclosing;
        envVariables = new HashMap<>();
    }

    public Environment() {
        envVariables = new HashMap<>();
        enclosing = null;
    }

    public void addVariable(String identifier, Object value) {
        envVariables.put(identifier, value);
    }

    public boolean checkIfVariableExists(String identifier) {
        return envVariables.containsKey(identifier);
    }

    public Object accessVariable(String identifier) {
        if (checkIfVariableExists(identifier)) {
            return envVariables.get(identifier);
        }
        if (enclosing != null) {
            return enclosing.accessVariable(identifier);
        }
        throw new Error("Undefined variable '" + identifier + "'.");
    }




}
