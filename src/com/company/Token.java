package com.company;

public class Token {

    TokenType type;
    Object tokenValue;

    public Token(TokenType type, Object tokenValue) {
        this.type = type;
        this.tokenValue = tokenValue;
    }

    @Override
    public String toString() {
        return "Type: " + type + ", " + "Value: " + tokenValue;
    }
}
