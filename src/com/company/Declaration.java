package com.company;

public abstract class Declaration {
    interface visitor {
        void visitVariableDeclaration(VariableDeclaration node);
        void visitExpressionStatement(ExpressionStatement node);
        void visitPrintStatement(PrintStatement node);
    }
    abstract Object accept(visitor visitor);

    static class VariableDeclaration extends Declaration {
        String identifier;
        Expr expr;
        public VariableDeclaration(String identifier, Expr expr) {
            this.identifier = identifier;
            this.expr = expr;
        }

        @Override
        Object accept(visitor visitor) {
            visitor.visitVariableDeclaration(this);
            return null;
        }
    }

    static class ExpressionStatement extends Declaration {
        Expr expr;
        public ExpressionStatement(Expr expr) {
            this.expr = expr;
        }

        @Override
        Object accept(visitor visitor) {
            visitor.visitExpressionStatement(this);
            return null;
        }
    }

    static class PrintStatement extends Declaration {
        Expr expr;
        public PrintStatement(Expr expr) {
            this.expr = expr;
        }

        @Override
        Object accept(visitor visitor) {
            visitor.visitPrintStatement(this);
            return null;
        }
    }
}
