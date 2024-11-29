package oo2.redictado.DuplicatedCodeSniffer;

import oo2.redictado.AromaReport;
import oo2.redictado.antlr4.BythonParser;
import oo2.redictado.antlr4.BythonParser.MethodDeclContext;
import oo2.redictado.antlr4.BythonParserBaseVisitor;

public class DuplicatedCodeStorageVisitor extends BythonParserBaseVisitor<Void> {
    private DuplicateCodeFinderVisitor duplicateCodeFinderVisitor;
    private BythonParser.ProgramContext rootNode;
    private AromaReport report;

    public DuplicatedCodeStorageVisitor(AromaReport report) {
        super();
        this.report = report;
    }

    @Override
    public Void visitBlock(BythonParser.BlockContext ctx) {
        if ((ctx.depth() < 4) || (ctx.getParent() instanceof MethodDeclContext)) {
            this.duplicateCodeFinderVisitor = new DuplicateCodeFinderVisitor(ctx);
            this.duplicateCodeFinderVisitor.visit(this.rootNode);
            if (this.duplicateCodeFinderVisitor.getReport().stinks()) {
                this.duplicateCodeFinderVisitor.getReport().getAromas().forEach(aroma -> {
                    this.report.addAroma(aroma);
                });
            }
        }
        return visitChildren(ctx);
    }

    public AromaReport getReport() {
        return report;
    }

    public Void visitProgram(BythonParser.ProgramContext ctx) {
        this.rootNode = ctx;
        return visitChildren(ctx);
    }
    
}
