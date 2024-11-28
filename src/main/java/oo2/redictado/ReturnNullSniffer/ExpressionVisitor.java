package oo2.redictado.ReturnNullSniffer;

import oo2.redictado.Aroma;
import oo2.redictado.AromaReport;
import oo2.redictado.antlr4.BythonParser;
import oo2.redictado.antlr4.BythonParserBaseVisitor;

import java.util.List;

public class ExpressionVisitor extends BythonParserBaseVisitor<Void> {
    private AromaReport report;
    private String callerName;
    private List<String> variablesWithNone;
    private List<String> indexesWithNone;

    public ExpressionVisitor(AromaReport report, String callerName, List<String> variablesWithNone, List<String> indexesWithNone) {
            super();
            this.report = report;
            this.callerName = callerName;
            this.variablesWithNone = variablesWithNone;
            this.indexesWithNone = indexesWithNone;
    }

    @Override
    public Void visitExpression(BythonParser.ExpressionContext ctx){
        // Verifica si la expresión devuelve `None` o una variable/índice con `None`
        if (isReturningNone(ctx)) {
            report.addAroma(new Aroma(callerName, "El código devuelve None.", true));
        }
        return visitChildren(ctx);
    }

    public boolean isReturningNone(BythonParser.ExpressionContext ctx) {
        return ctx.getText().equals("None") || this.variablesWithNone.contains(ctx.getText()) || this.indexesWithNone.contains(ctx.getText());
    }


}
