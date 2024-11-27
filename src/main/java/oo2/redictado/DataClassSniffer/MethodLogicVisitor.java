package oo2.redictado.DataClassSniffer;

import oo2.redictado.antlr4.BythonParser;
import oo2.redictado.antlr4.BythonParserBaseVisitor;

public class MethodLogicVisitor extends BythonParserBaseVisitor<Void> {
    private boolean hasLogic = false; // Variable de instancia para almacenar el resultado

    public boolean hasLogic() {
        return hasLogic; // Método para obtener el resultado final
    }

    @Override
    public Void visitIfStatement(BythonParser.IfStatementContext ctx) {
        hasLogic = true; // Encontramos un `if`, consideramos que tiene lógica
        return null; // Cumple con el retorno `Void`
    }

    @Override
    public Void visitWhileStatement(BythonParser.WhileStatementContext ctx) {
        hasLogic = true; // Encontramos un `while`, consideramos que tiene lógica
        return null;
    }

    @Override
    public Void visitForStatement(BythonParser.ForStatementContext ctx) {
        hasLogic = true; // Encontramos un `for`, consideramos que tiene lógica
        return null;
    }

    @Override
    public Void visitReturnStatement(BythonParser.ReturnStatementContext ctx) {
        hasLogic = true; // Encontramos un `return`, lo consideramos lógica
        return null;
    }

    @Override
    public Void visitExpression(BythonParser.ExpressionContext ctx) {
        hasLogic = true; // Cualquier expresión cuenta como lógica
        return null;
    }

    @Override
    protected Void aggregateResult(Void aggregate, Void nextResult) {
        // No necesitamos combinar resultados, pero podemos interrumpir
        return hasLogic ? null : super.aggregateResult(aggregate, nextResult);
    }
}

