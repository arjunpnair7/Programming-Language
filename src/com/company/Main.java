package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
	// write your code here
        if (args.length == 1) {
            readFile(args[0]);
        } else {
            startRepl();
        }
    }

    public static void readFile(String path) throws Exception {
        String srcCode = generateSrcString(path);

        Lexer lexer = new Lexer(srcCode);
        List<Token> list = lexer.tokenizeSourceCode();
        List<Declaration> expressionList = getProgramExpressions(list);
        Interpreter interpreter = new Interpreter();

        for (Declaration curr: expressionList) {
            interpreter.interpretAST(curr);
        }
    }

    public static String generateSrcString(String path) throws IOException {
        File file = new File(path);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        StringBuilder srcCode = new StringBuilder();
        while ((st = br.readLine()) != null) {
            srcCode.append(st);
        }
        return srcCode.toString();
    }

    public static List<Declaration> getProgramExpressions(List<Token> tokenList) throws Exception {
        Parser parser = new Parser(tokenList);
        List<Declaration> expressionList = new ArrayList<>();
        while (parser.getCurrentTokenType() != TokenType.EOF) {
            Declaration AST = parser.Program();
            expressionList.add(AST);
        }
        return expressionList;
    }

    public static void startRepl() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            runInterpreter(scanner.nextLine());
        }
    }

    public static void runInterpreter(String userInput) {
        Lexer lexer = new Lexer(userInput);
        try {
            List<Token> list = lexer.tokenizeSourceCode();
            Parser p = new Parser(list);
            Declaration AST = p.Program();
            Interpreter interpreter = new Interpreter();
            interpreter.interpretTree(AST);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public static void printTokens(List<Token> tokens) {
        for (Token curr: tokens) {
            System.out.println(curr);
        }
    }
}
