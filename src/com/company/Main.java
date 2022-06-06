package com.company;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	// write your code here
        startRepl();
    }


    public static void startRepl() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("> ");
            String userInput = scanner.nextLine();
            Lexer lexer = new Lexer(userInput);
            try {
                List<Token> list = lexer.tokenizeSourceCode();
                Parser p = new Parser(list);
                Expr AST = p.Expression();
                Interpreter interpreter = new Interpreter(AST);
                interpreter.interpretTree();
                //printTokens(list);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public static void printTokens(List<Token> tokens) {
        for (Token curr: tokens) {
            System.out.println(curr);
        }
    }
}
