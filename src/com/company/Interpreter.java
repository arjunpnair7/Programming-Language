package com.company;

public class Interpreter implements Expr.vistor  {
    Expr AST;
    public Interpreter(Expr AST) {
        this.AST = AST;
    }

    public Object interpretTree() {
        return evaluate(AST);
    }

    public Object evaluate(Expr node) {
        return node.accept(this);
    }

    @Override
    public Object visitBinary(Expr.Binary node) {
        Object left = evaluate(node.left);
        Object right = evaluate(node.right);

//        if (node.operator.type == TokenType.PLUS) {
//            System.out.println((double) left + (double) right);
//            return (double) left + (double) right;
//        } else {
//            return 4;
//        }
        switch (node.operator.type) {
            case PLUS -> {
                System.out.println((double) left + (double) right);
                return (double) left + (double) right;
            }
            case MINUS -> {
                System.out.println((double) left - (double) right);
                return (double) left - (double) right;
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
