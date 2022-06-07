package com.company;


import java.util.List;

public class Parser {

    private List<Token> tokens;
    private int current;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        current = 0;
    }

    public TokenType getCurrentTokenType() {
        if (current <= tokens.size()) {
            return tokens.get(current).type;
        } else {
            return null;
            //CHANGE THIS!!!
        }
    }

    public Expr Statement() throws Exception {
        //if (current < tokens.size() && tokens.get(current).type == )
        Expr expression = Expression();
        if (current >= tokens.size() || tokens.get(current).type != TokenType.SEMICOLON) {
            throw new Exception("Unterminated Statement");
        }
        current++;
        return expression;
    }

    public Expr Expression() {
        Expr left = Product();
        if (current == tokens.size()) {
            return left;
        }
        while (tokens.get(current).type == TokenType.MINUS || tokens.get(current).type == TokenType.PLUS) {
            Token operator = tokens.get(current);
            current++;
            Expr right = Product();
            left = new Expr.Binary(left, right, operator);
            if (current == tokens.size()) {
                break;
            }
        }
        return left;
    }

    public Expr Product() {
        Expr left = Unary();
        if (current == tokens.size()) {
            return left;
        }
        while (current != tokens.size() && (tokens.get(current).type == TokenType.MULTIPLY || tokens.get(current).type == TokenType.DIVIDE)) {
            Token operator = tokens.get(current);
            current++;
            Expr right = Unary();
            left = new Expr.Binary(left, right, operator);
        }
        return left;
    }

    public Expr Unary()  {
        if (current != tokens.size() && tokens.get(current).type == TokenType.MINUS) {
            Token operator = tokens.get(current);
            current++;
            Expr right = Unary();
            right = new Expr.Unary(right, operator);
            return right;
        }
        return Literal();
    }

    public Expr Literal() {
        switch (tokens.get(current).type) {
            case NUMBER, STRING -> {
                current++;
                return new Expr.Literal(tokens.get(current - 1).tokenValue);
            }
            case LPAREN -> {
                current++;
                Expr paren =  Expression();
                if (tokens.get(current).type == TokenType.RPAREN) {
                    current++;
                    return paren;
                } else {
                    System.err.println("Unterminated parentheses");
                    return paren;
                }
            }
            default -> {
                current++;
                return new Expr.Literal(tokens.get(current - 1).tokenValue);
            }
        }
    }
}
