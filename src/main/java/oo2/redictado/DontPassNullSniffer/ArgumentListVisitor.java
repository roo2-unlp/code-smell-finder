package oo2.redictado.DontPassNullSniffer;

import com.sun.jdi.VoidType;
import oo2.redictado.Aroma;
import oo2.redictado.AromaReport;
import oo2.redictado.antlr4.BythonParser;
import oo2.redictado.antlr4.BythonParserBaseVisitor;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ArgumentListVisitor extends BythonParserBaseVisitor<Void> {
    private AromaReport report;
    private String callerName;
    private List<String> variablesWithNone;

    public ArgumentListVisitor(AromaReport report, String callerName, List<String> variablesWithNone) {
        this.report = report;
        this.callerName = callerName;
        this.variablesWithNone = variablesWithNone;
    }


    @Override
    public Void visitSimpleAssignment(BythonParser.SimpleAssignmentContext ctx) {
        if (ctx.expression().getText().equals("None")){
            report.addAroma(new Aroma(this.callerName, "El codigo envia None.", true));
        }
        return visitChildren(ctx);
    }

    @Override
    public Void visitIndexAssignment(BythonParser.IndexAssignmentContext ctx) {
        if (ctx.expression().getText().equals("None")){
            report.addAroma(new Aroma(this.callerName, "El codigo envia None.", true));
        }
        return visitChildren(ctx);
    }

    @Override
    public Void visitExpression(BythonParser.ExpressionContext ctx) {
        System.out.println("CONTEXTOOO"+ctx.getText());
        if (this.isReturningNone(ctx)){
            report.addAroma(new Aroma(this.callerName, "El codigo envia None.", true));
        }
        return visitChildren(ctx);
    }

    public boolean isReturningNone(BythonParser.ExpressionContext ctx) {
        return ctx.getText().equals("None") || this.variablesWithNone.contains(ctx.getText());
    }

}
