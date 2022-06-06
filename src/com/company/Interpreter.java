package com.company;

public class Interpreter implements Expr.vistor  {
    Expr AST;
    public Interpreter(Expr AST) {
        this.AST = AST;
    }

    public Object interpretTree() {
        Object temp =  evaluate(AST);
        System.out.println(temp);
        return temp;
    }

    public Object evaluate(Expr node) {
        return node.accept(this);
    }

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
                    System.err.println("Cannot add " + left + " with " + right);
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

    @Override
    public Object visitLiteral(Expr.Literal node) {
        return node.value;
    }
}
