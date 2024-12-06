package oo2.redictado.ReturnNullSniffer;

import oo2.redictado.Aroma;
import oo2.redictado.AromaReport;
import oo2.redictado.antlr4.BythonParser;
import oo2.redictado.antlr4.BythonParserBaseVisitor;

import java.util.List;

public class ExpressionVisitor extends BythonParserBaseVisitor<Boolean> {
    private AromaReport report;
    private String callerName;
    private List<String> variablesWithNone;
    private List<String> indexesWithNone;

    public ExpressionVisitor(AromaReport report, String callerName, List<String> variablesWithNone) {
            super();
            this.report = report;
            this.callerName = callerName;
            this.variablesWithNone = variablesWithNone;
    }

    @Override
    public Boolean visitExpression(BythonParser.ExpressionContext ctx){
        // Verifica si la expresión devuelve `None` o una variable/índice con `None`
        if (isReturningNone(ctx)) {
            report.addAroma(new Aroma(callerName, "El código devuelve None.", true));
        }
        return visitChildren(ctx);
    }

    public boolean isReturningNone(BythonParser.ExpressionContext ctx) {
        return ctx.getText().equals("None") || this.variablesWithNone.contains(ctx.getText());
    }

    @Override
    public Boolean visitSimpleAssignment(BythonParser.SimpleAssignmentContext ctx) {
        if (ctx.expression().getText().equals("None")) {
            report.addAroma(new Aroma(callerName, "El código devuelve None.", true));
        }
        return visitChildren(ctx);
    }

    @Override
    public Boolean visitIndexAssignment(BythonParser.IndexAssignmentContext ctx) {
        if (ctx.expression().getText().equals("None")) {
            report.addAroma(new Aroma(callerName, "El código devuelve None.", true));
        }
        return visitChildren(ctx);
    }

    @Override
    public Boolean visitImplicitAssignment (BythonParser.ImplicitAssignmentContext ctx) {
        return true;
    }

}
