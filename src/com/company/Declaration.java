package com.company;

import java.util.List;

public abstract class Declaration {
    interface visitor {
        void visitVariableDeclaration(VariableDeclaration node);
        Object visitExpressionStatement(ExpressionStatement node);
        void visitPrintStatement(PrintStatement node);
        void visitBlockStatement(BlockStatement node);
    }
    abstract Object accept(visitor visitor);

    static class BlockStatement extends Declaration {
        List<Declaration> declarationList;
        public BlockStatement(List<Declaration> declarationList) {
            this.declarationList = declarationList;
        }

        @Override
        Object accept(visitor visitor) {
            visitor.visitBlockStatement(this);
            return null;
        }
    }

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
            return visitor.visitExpressionStatement(this);
            //return null;
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
