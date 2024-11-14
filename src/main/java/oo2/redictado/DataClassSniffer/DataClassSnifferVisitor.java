package oo2.redictado.DataClassSniffer;

import org.antlr.v4.runtime.misc.NotNull;

import oo2.redictado.Aroma;
import oo2.redictado.AromaReport;
import oo2.redictado.antlr4.BythonParserBaseVisitor;
import oo2.redictado.antlr4.BythonParser;

public class DataClassSnifferVisitor extends BythonParserBaseVisitor<Void> {
    private final AromaReport report;
    private final String snifferName;

    public DataClassSnifferVisitor(AromaReport report, String snifferName) {
        this.report = report;
        this.snifferName = snifferName;
    }

   @Override public Void visitClassDecl(BythonParser.ClassDeclContext ctx) {
    ctx.classMember();
     return visitChildren(ctx); 
    }
  
}
