package com.company;

public abstract class Expr {

    abstract Object accept(vistor vistor);

    interface vistor {
        Object visitBinary(Binary node);
        Object visitUnary(Unary node);
        Object visitLiteral(Literal node);
    }
    public static class Binary extends Expr {
        Expr left;
        Expr right;
        Token operator;
        public Object accept(vistor visitor) {
            return visitor.visitBinary(this);
        }
        public Binary(Expr left, Expr right, Token operator) {
            this.left = left;
            this.right = right;
            this.operator = operator;
        }
    }

    public static class Unary extends Expr {
        Expr right;
        Token operator;
        public Object accept(vistor visitor) {
            return visitor.visitUnary(this);
        }

        public Unary(Expr right, Token operator) {
            this.right = right;
            this.operator = operator;
        }
    }

    public static class Literal extends Expr {
        Object value;
        public Object accept(vistor visitor) {
            return visitor.visitLiteral(this);
        }
        public Literal(Object value) {
            this.value = value;
        }
    }
}
