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

    public void visitFeatureEnvy(BythonParser.ChainedExpressionContext ctx) {
        int depth = calculateDepth(ctx);
        if (depth > this.featureEnvyLimit) {
            crearAroma(depth);
        }
    }

    private void crearAroma(int depth) {
        report.addAroma(new Aroma(this.callerName, "The code has feature envy with depth " + depth, true));
    }

    private int calculateDepth(BythonParser.ChainedExpressionContext  ctx) {
        return ctx.chainedMethodCall().size() + ctx.propertyAccess().size();
    }

}
