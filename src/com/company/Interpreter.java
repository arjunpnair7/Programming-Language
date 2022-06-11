package com.company;

public class Interpreter implements Expr.visitor, Declaration.visitor  {

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
    public Object visitVariable(Expr.Variable node) {
//        if (programEnv.checkIfVariableExists(node.identifier)) {
//            return programEnv.accessVariable(node.identifier);
//        } else {
//            throw new Exception("UNDEFINED VARIABLE: " + node.identifier);
//        }
        try {
            return programEnv.accessVariable(node.identifier);
        } catch (Exception e) {
            System.err.println("UNDEFINED VARIABLE: " + node.identifier);
        }
        return null;
    }


    @Override
    public void visitVariableDeclaration(Declaration.VariableDeclaration node) {
        Object variableValue = evaluate(node.expr);
        programEnv.addVariable(node.identifier, variableValue);
    }

    @Override
    public Object visitExpressionStatement(Declaration.ExpressionStatement node) {
        return evaluate(node.expr);
    }

    @Override
    public void visitPrintStatement(Declaration.PrintStatement node) {
        System.out.println(evaluate(node).toString());
    }
}
