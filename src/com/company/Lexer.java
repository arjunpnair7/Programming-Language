package com.company;
import java.util.ArrayList;
import java.util.List;

public class Lexer {
    String source;
    List<Token> tokens;
    int current;
    int currentLineNumber;

    public Lexer(String source) {
        this.source = source;
        tokens = new ArrayList<>();
        current = 0;
        currentLineNumber = 0;
    }

    List<Token> tokenizeSourceCode() throws Exception {
        while ( current < source.length()) {
            char curr = source.charAt(current);
            switch (curr) {
                case ' ':
                    break;
                case '\n':
                    currentLineNumber++;
                    continue;
                case '(':
                    tokens.add(new Token(TokenType.LPAREN, String.valueOf(curr)));
                    break;
                case ')':
                    tokens.add(new Token(TokenType.RPAREN, String.valueOf(curr)));
                    break;
                case '{':
                    tokens.add(new Token(TokenType.LB, String.valueOf(curr)));
                    break;
                case '}':
                    tokens.add(new Token(TokenType.RB, String.valueOf(curr)));
                    break;
                case '+':
                    tokens.add(new Token(TokenType.PLUS, String.valueOf(curr)));
                    break;
                case '-':
                    tokens.add(new Token(TokenType.MINUS, String.valueOf(curr)));
                    break;
                case '*':
                    tokens.add(new Token(TokenType.MULTIPLY, String.valueOf(curr)));
                    break;
                case '/':
                    tokens.add(new Token(TokenType.DIVIDE, String.valueOf(curr)));
                    break;
                case ';':
                    tokens.add(new Token(TokenType.SEMICOLON, String.valueOf(curr)));
                    break;
                case '=':
                    tokens.add(new Token(TokenType.ASSIGNMENT, String.valueOf(curr)));
                    break;

                default:
                    if (Character.isDigit(curr)) {
                        tokenizeNumber();
                        continue;
                    } else if (curr == '"') {
                        tokenizeString();
                    } else if (Character.isLetter(curr)) {
                        tokenizeLiteral();
                        continue;
                    } else {
                        System.err.println("Unrecognized token");
                    }
            } // var a = 5;
            current++;
        }
        tokens.add(new Token(TokenType.EOF, null));
        return tokens;
    }

    public void tokenizeLiteral() {
        StringBuilder result = new StringBuilder();
        while (current < source.length() && Character.isLetter(source.charAt(current))) {
            result.append(source.charAt(current));
            current++;
        }
        if (result.toString().equals("var")) {
            tokens.add(new Token(TokenType.VARIABLE, (String) result.toString()));
        } else if (result.toString().equals("print")) {
            tokens.add(new Token(TokenType.PRINT, (String) result.toString()));
        } else {
            tokens.add(new Token(TokenType.LITERAL, result.toString()));
        }
    }

    public void tokenizeString() throws Exception {
        StringBuilder runningString = new StringBuilder();
        boolean terminatedString = false;
        current++;
        while (current < source.length()) {
            if (source.charAt(current) == '"') {
                terminatedString = true;
                break;
            }
            runningString.append(source.charAt(current));
            current++;
        }
        if (!terminatedString) {
            throw new Exception("Unterminated string on line number: " + currentLineNumber);
        }
        tokens.add(new Token(TokenType.STRING, (String) runningString.toString()));
    }

    public void tokenizeNumber() throws Exception {
        StringBuilder runningString = new StringBuilder();
        boolean visitedDecimal = false;
        if (Character.isDigit(source.charAt(current))) {
            while (current < source.length() &&  ((Character.isDigit(source.charAt(current))) || (source.charAt(current) == '.'))) {
                if (source.charAt(current) == '.' && visitedDecimal) {
                    throw new Exception("Two or more decimals in a single number on line number " + currentLineNumber + " is invalid.");
                } else if (source.charAt(current) != '.') {
                    runningString.append(source.charAt(current));
                } else {
                    runningString.append(source.charAt(current));
                    visitedDecimal = true;
                }
                current++;
            }
            tokens.add(new Token(TokenType.NUMBER, Double.parseDouble(runningString.toString())));
        }
    }
}