package com.company;


import java.util.ArrayList;
import java.util.List;

public class Parser {

    private List<Token> tokens;
    private int current;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        current = 0;
    }

    public TokenType getCurrentTokenType() {
        if (current < tokens.size()) {
           // System.out.println(tokens.size());
           // System.out.println(current);
            return tokens.get(current).type;
        } else {
            return null;
            //CHANGE THIS!!!
        }
    }



    public Declaration Program() throws Exception {
//        Expr expression;
//        if (tokens.get(current).type == TokenType.VARIABLE) {
//            current++;
//            //expression = VariableStatement();
//        } else if (tokens.get(current).type == TokenType.PRINT) {
//            current++;
//            //expression = PrintStatement();
//        } else {
//            expression = Expression();
//        }
//        return expression;
        if (getCurrentTokenType() != TokenType.LB) {
            //System.out.println("MISSING '{': CODE MUST BE WRAPPED IN A BLOCK USING '{' AND '}'");
            throw new Exception("MISSING '{': CODE MUST BE WRAPPED IN A BLOCK USING '{' AND '}'");
        }
        return Declaration();
    }

    public Declaration BlockStatement() throws Exception {
        Declaration declaration = null;
        List<Declaration> declarationList = new ArrayList<>();
        if (getCurrentTokenType() != TokenType.LB) {
            System.out.println("MISSING STARTING '{' OF BLOCK");
        } else {
            current++;
//            if (getCurrentTokenType() == TokenType.LB) {
//                return Declaration();
//            }

            while (getCurrentTokenType() != TokenType.RB && current < tokens.size()) {
                declarationList.add(Declaration());
            }
            System.out.println("CURRENT: " + getCurrentTokenType());
            if (getCurrentTokenType() != TokenType.RB) {
                throw new Exception("Expected '}'");
            }
            declaration = new Declaration.BlockStatement(declarationList);
            current++;
        }
        return declaration;
    }

    public Declaration Declaration() throws Exception {
        Declaration declaration = null;
        if (getCurrentTokenType() == TokenType.VARIABLE) {
            current++;
            declaration = VariableDeclaration();
            //current++;
            if (getCurrentTokenType() == TokenType.SEMICOLON) {
                current++;
            return declaration;
            } else {
                System.out.println("UNTERMINATED STATEMENT: MISSING SEMICOLON");
            }
        } else if(getCurrentTokenType() == TokenType.LB) {
            declaration = BlockStatement();
            System.out.println(getCurrentTokenType());
        } else {
//            declaration = Expression();
            declaration =  ExpressionStatement();
            if (getCurrentTokenType() == TokenType.SEMICOLON) {
                current++;
                return declaration;
            } else {
                System.out.println("UNTERMINATED STATEMENT: MISSING SEMICOLON");
            }
        }
        return declaration;
    }

//    public Declaration BlockStatement() {
//
//    }

    public Declaration ExpressionStatement() {
        Expr expr = Expression();
        return new Declaration.ExpressionStatement(expr);
    }

    public Declaration VariableDeclaration() {
        System.out.println(getCurrentTokenType().toString());
        if (getCurrentTokenType() == TokenType.LITERAL) {
            Expr initializer = null;
            String identifier = (String) tokens.get(current).tokenValue;
            current++;
            if (getCurrentTokenType() == TokenType.ASSIGNMENT) {
                current++;
                initializer = Expression();
            }
            return new Declaration.VariableDeclaration(identifier, initializer);
        } else {
            System.out.println("ERROR WITH VARIABLE DECLARATION");
            return null;
        }
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
            case LITERAL -> {
                current++;
                //return new Expr.Literal(tokens.get(current - 1).tokenValue);
                return new Expr.Variable((String) tokens.get(current - 1).tokenValue);
            }
            case ASSIGNMENT -> {
                current++;
                return Expression();
            }
//            case LB -> {
//                current++;
//                return Block();
//            }
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