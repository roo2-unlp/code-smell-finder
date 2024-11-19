package oo2.redictado.FeatureEnvySniffer;

import oo2.redictado.Aroma;
import oo2.redictado.AromaReport;
import oo2.redictado.antlr4.BythonParser;
import oo2.redictado.antlr4.BythonParserBaseVisitor;

public class FeatureEnvyVisitor extends BythonParserBaseVisitor<Void> {
    private AromaReport report;
    private String callerName;
    private final int featureEnvyLimit;

    public FeatureEnvyVisitor(AromaReport report, String callerName) {
        super();
        this.report = report;
        this.callerName = callerName;
        this.featureEnvyLimit = 2;
    }

    @Override
    public Void visitChainedExpression(BythonParser.ChainedExpressionContext ctx) {
        int depth = calculateDepth(ctx);
        if (depth > this.featureEnvyLimit) {
            crearAroma(depth);
        }
        return visitChildren(ctx);
    }

    @Override
    public Void visitMethodCall(BythonParser.MethodCallContext ctx) {
        if (ctx.getText().contains("self")) {
            report.addAroma(new Aroma(this.callerName, "The code has feature envy due to passing self as a parameter", true));
        }
        return visitChildren(ctx);
    }

    @Override
    public Void visitClassDecl(BythonParser.ClassDeclContext ctx) {
        // Check for feature envy within class methods
        return visitChildren(ctx);
    }

    private void crearAroma(int depth) {
        report.addAroma(new Aroma(this.callerName, "The code has feature envy with depth " + depth, true));
    }

    private int calculateDepth(BythonParser.ChainedExpressionContext ctx) {
        return ctx.chainedMethodCall().size() + ctx.propertyAccess().size();
    }

}
