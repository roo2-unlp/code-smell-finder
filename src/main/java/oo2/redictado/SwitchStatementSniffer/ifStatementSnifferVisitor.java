package oo2.redictado.SwitchStatementSniffer;

import oo2.redictado.Aroma;
import oo2.redictado.AromaReport;
import oo2.redictado.antlr4.BythonParser;
import oo2.redictado.antlr4.BythonParser.IfStatementContext;
import oo2.redictado.antlr4.BythonParserBaseVisitor;

public class ifStatementSnifferVisitor extends BythonParserBaseVisitor<Void> {
    private AromaReport report;
    private String callerName;

    public ifStatementSnifferVisitor(AromaReport report, String callerName) {
        super();
        this.report = report;
        this.callerName = callerName;
    }

    private int howMuchElifClause(BythonParser.IfStatementContext ctx){
        return ctx.elifClause().size();
    }

    private void hasBadSmell(BythonParser.ElifClauseContext elCtx, BythonParser.IfStatementContext ctx){
        int sameID = (int) ctx.elifClause().stream().filter(elif -> elif.getChild(0).getText().equals(elCtx.getChild(0).getText())).count(); //Count the amount of elifClause who compare the same parameter/variable.
        if (sameID > 2){
            report.addAroma(new Aroma(this.callerName, "The code has Switch Statements", true));
        }
    }

    @Override
    public Void visitIfStatement(BythonParser.IfStatementContext ctx) {
        //if (ctx.ID().getText().equals("print")) {
        //    report.addAroma(new Aroma(this.callerName, "The code has print calls.", true));
        //}

        //return visitChildren(ctx);
        //Buscar la presencia de elif dentro del ifStatement.
        //Buscar la presencia de if dentro del ifStatement

        if (this.howMuchElifClause(ctx) > 0){
           BythonParser.ElifClauseContext elCtx = ctx.elifClause().getFirst(); //Save the first elifClause
           this.hasBadSmell(elCtx, ctx);
        }

        return visitChildren(ctx);
    }
}
