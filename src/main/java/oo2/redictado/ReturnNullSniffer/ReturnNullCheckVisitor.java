package oo2.redictado.ReturnNullSniffer;

import oo2.redictado.antlr4.BythonParser;
import oo2.redictado.antlr4.BythonParserBaseVisitor;

import java.util.List;

public class ReturnNullCheckVisitor extends BythonParserBaseVisitor<Boolean> {

    public ReturnNullCheckVisitor() {
        super();
    }

   // @Override
    public Boolean visitExpression(BythonParser.ExpressionContext ctx, List<String> variablesWithNone, List<String> indexesWithNone) {
        if (ctx.valueExpression() != null) {
            String expr = ctx.valueExpression().getText();
            // Verificamos si la expresión es una variable o índice con None no modificado
            return "None".equals(expr) ||
                    variablesWithNone.contains(expr) || indexesWithNone.contains(expr);
        }
        return false;
    }


}
