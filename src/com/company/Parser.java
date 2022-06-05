package com.company;


import java.util.List;

public class Parser {

    private List<Token> tokens;
    private int current;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        current = 0;
    }

    public Expr Expression() {
        Expr left = Product();
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
        while (tokens.get(current).type == TokenType.MULTIPLY || tokens.get(current).type == TokenType.DIVIDE) {
            Token operator = tokens.get(current);
            Expr right = Product();
            left = new Expr.Binary(left, right, operator);
            current++;
        }
        return left;
    }

    public Expr Unary()  {
        if (tokens.get(current).type == TokenType.MINUS) {
            Token operator = tokens.get(current);
            current++;
            Expr right = Unary();
            right = new Expr.Unary(right, operator);
            return right;
        }
        return Literal();
    }

    public Expr Literal() {
        if (tokens.get(current).type == TokenType.NUMBER) {
            current++;
            return new Expr.Literal(tokens.get(current - 1).tokenValue);
        } else  {
            current++;
            return new Expr.Literal(tokens.get(current - 1).tokenValue);
        }
    }





}
