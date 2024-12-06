package oo2.redictado.DontPassNullSniffer;

import oo2.redictado.Aroma;
import oo2.redictado.AromaReport;
import oo2.redictado.antlr4.BythonParser;
import oo2.redictado.antlr4.BythonParserBaseVisitor;

import java.util.List;

public class ArgumentListVisitor extends BythonParserBaseVisitor<Boolean> {
    private AromaReport report;
    private String callerName;
    private List<String> variablesWithNone;

    public ArgumentListVisitor(AromaReport report, String callerName, List<String> variablesWithNone) {
        this.report = report;
        this.callerName = callerName;
        this.variablesWithNone = variablesWithNone;
    }


    @Override
    public Boolean visitSimpleAssignment(BythonParser.SimpleAssignmentContext ctx) {
        if (ctx.expression().getText().equals("None")){
            report.addAroma(new Aroma(this.callerName, "El codigo envia None.", true));
        }
        return visitChildren(ctx);
    }

    @Override
    public Boolean visitIndexAssignment(BythonParser.IndexAssignmentContext ctx) {
        if (ctx.expression().getText().equals("None")){
            report.addAroma(new Aroma(this.callerName, "El codigo envia None.", true));
        }
        return visitChildren(ctx);
    }

    @Override
    public Boolean visitExpression(BythonParser.ExpressionContext ctx) {
        if (this.isPassingNone(ctx)){
            report.addAroma(new Aroma(this.callerName, "El codigo envia None.", true));
        }
        return visitChildren(ctx);
    }

    public boolean isPassingNone(BythonParser.ExpressionContext ctx) {
        return ctx.getText().equals("None") || this.variablesWithNone.contains(ctx.getText());
    }

    @Override
    public Boolean visitImplicitAssignment (BythonParser.ImplicitAssignmentContext ctx){
        return true;
    }

}
