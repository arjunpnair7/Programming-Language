package com.company;

public abstract class Expr {

    abstract Object accept(visitor visitor);

    interface visitor {
        Object visitBinary(Binary node);
        Object visitUnary(Unary node);
        Object visitLiteral(Literal node);
        Object visitVariable(Variable node);
    }
    public static class Binary extends Expr {
        Expr left;
        Expr right;
        Token operator;
        public Object accept(visitor visitor) {
            return visitor.visitBinary(this);
        }
        public Binary(Expr left, Expr right, Token operator) {
            this.left = left;
            this.right = right;
            this.operator = operator;
        }
    }

    public static class Variable extends Expr {
        String identifier;
        public Variable(String identifier) {
            this.identifier = identifier;
        }

        @Override
        Object accept(visitor visitor) {
            return visitor.visitVariable(this);
        }
    }

    public static class Unary extends Expr {
        Expr right;
        Token operator;
        public Object accept(visitor visitor) {
            return visitor.visitUnary(this);
        }

        public Unary(Expr right, Token operator) {
            this.right = right;
            this.operator = operator;
        }
    }

    public static class Literal extends Expr {
        Object value;
        public Object accept(visitor visitor) {
            return visitor.visitLiteral(this);
        }
        public Literal(Object value) {
            this.value = value;
        }
    }
}
