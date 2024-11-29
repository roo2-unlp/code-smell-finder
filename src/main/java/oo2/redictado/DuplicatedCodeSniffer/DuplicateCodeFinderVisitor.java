package oo2.redictado.DuplicatedCodeSniffer;

import java.util.ArrayList;
import java.util.List;

import oo2.redictado.Aroma;
import oo2.redictado.AromaReport;
import oo2.redictado.antlr4.BythonParser;
import oo2.redictado.antlr4.BythonParser.BlockContext;
import oo2.redictado.antlr4.BythonParserBaseVisitor;
public class DuplicateCodeFinderVisitor extends BythonParserBaseVisitor<Void>{
    private BlockContext nodo;
    private AromaReport reports;

    public DuplicateCodeFinderVisitor(BlockContext nodo) {
        super();
        this.nodo = nodo;
        this.reports = new AromaReport("DuplicatedCodeSniffer");

    }

    @Override
    public Void visitBlock(BythonParser.BlockContext ctx) {
        if ((nodo != ctx) && (ctx.getText().equals(nodo.getText()))) {
            this.reports.addAroma(new Aroma("duplicated code", "The code has duplicated code.", true));
        }
        return visitChildren(ctx);
    }


    public AromaReport getReport() {
        return reports;
    }
   


}
