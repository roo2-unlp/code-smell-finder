package oo2.redictado.PrintSniffer;

import oo2.redictado.Aroma;
import oo2.redictado.AromaReport;
import oo2.redictado.antlr4.BythonParser;
import oo2.redictado.antlr4.BythonParserBaseVisitor;

public class PrintSnifferVisitor extends BythonParserBaseVisitor<Void> {
    private AromaReport report;
    private String callerName;

    public PrintSnifferVisitor(AromaReport report, String callerName) {
        super();
        this.report = report;
        this.callerName = callerName;
    }

    @Override
    public Void visitMethodCall(BythonParser.MethodCallContext ctx) {
        if (ctx.ID().getText().equals("print")) {
            report.addAroma(new Aroma(this.callerName, "The code has print calls.", true));
        }
        return visitChildren(ctx);
    }
}
