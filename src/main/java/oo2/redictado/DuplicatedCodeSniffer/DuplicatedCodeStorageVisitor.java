package oo2.redictado.DuplicatedCodeSniffer;

import java.beans.Statement;

import oo2.redictado.AromaReport;
import oo2.redictado.antlr4.BythonParser;
import oo2.redictado.antlr4.BythonParser.MethodDeclContext;
import oo2.redictado.antlr4.BythonParser.StatementContext;
import oo2.redictado.antlr4.BythonParserBaseVisitor;

public class DuplicatedCodeStorageVisitor extends BythonParserBaseVisitor<Void> {
    private DuplicateCodeFinderVisitor duplicateCodeFinderVisitor;
    private BythonParser.ProgramContext rootNode;
    private AromaReport report;
    private StringBuilder scripts = new StringBuilder();
    private StringBuilder asignaciones = new StringBuilder();

    public DuplicatedCodeStorageVisitor(AromaReport report) {
        super();
        this.report = report;
    }

    @Override
    public Void visitBlock(BythonParser.BlockContext ctx) {
        if ((ctx.depth() < 4) || (ctx.getParent() instanceof MethodDeclContext)) {
            System.out.println("Block con toString: ");
            System.out.println(ctx.toString());
            System.out.println("Block con getText: ");
            System.out.println(ctx.getText());
            this.duplicateCodeFinderVisitor = new DuplicateCodeFinderVisitor(ctx, scripts);
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
        scripts.append("{");
        for (StatementContext children : ctx.statement()) {
            scripts.append(children.getText());
        }
        scripts.append("}");
        System.err.println("Scripts: ");
        System.out.println(scripts.toString());
        return visitChildren(ctx);
    }

    
}
