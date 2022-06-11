package com.company;

import javax.crypto.EncryptedPrivateKeyInfo;

public class Interpreter implements Expr.vistor, Declaration.visitor  {

    private Environment programEnv;

    public Interpreter() {
        programEnv = new Environment();
    }

    public Object interpretAST(Declaration AST) {
        return interpretTree(AST);
    }


    public Object interpretTree(Declaration AST) {
        Object temp =  evaluate(AST);
        System.out.println(temp);
        return temp;
    }

    public Object evaluate(Expr node) {
        return node.accept(this);
    }
    public Object evaluate(Declaration node) { return node.accept(this);}

    @Override
    public Object visitBinary(Expr.Binary node) {
        Object left = evaluate(node.left);
        Object right = evaluate(node.right);

        switch (node.operator.type) {
            case PLUS -> {
                if (left instanceof Double && right instanceof Double) {
                    return (double) left + (double) right;
                } else if (left instanceof String && right instanceof String) {
                    return (String) left + (String) right;
                } else {
                    System.err.println("Incompatible types: Cannot add " + left + " with " + right);
                }
            }
            case MINUS -> {
                return (double) left - (double) right;
            }
            case MULTIPLY -> {
                return (double) left * (double) right;
            }
            case DIVIDE -> {
                return (double) left / (double) right;
            }
        }
        return null;
    }

    @Override
    public Object visitUnary(Expr.Unary node) {
        double value = (double) evaluate(node.right);
        return -1 * value;
    }
    //var a = 5;
    //a + 5
    // 10
    @Override
    public Object visitLiteral(Expr.Literal node) {
        if (node.value instanceof Expr.Literal) {
            //System.out.println("Entered recursion on literal");
            return evaluate((Expr) node.value);
        }
        return node.value;
        //return evaluate(node.value);
    }


    @Override
    public void visitVariableDeclaration(Declaration.VariableDeclaration node) {
        Object variableValue = evaluate(node.expr);
        programEnv.addVariable(node.identifier, variableValue);
    }

    @Override
    public void visitExpressionStatement(Declaration.ExpressionStatement node) {
        evaluate(node);
    }

    @Override
    public void visitPrintStatement(Declaration.PrintStatement node) {
        System.out.println(evaluate(node).toString());
    }
}
