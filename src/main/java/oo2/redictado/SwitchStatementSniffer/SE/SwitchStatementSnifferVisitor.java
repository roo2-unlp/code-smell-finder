package oo2.redictado.SwitchStatementSniffer.SE;

import oo2.redictado.Aroma;
import oo2.redictado.AromaReport;
import oo2.redictado.antlr4.BythonParser;
import oo2.redictado.antlr4.BythonParserBaseVisitor;

public class SwitchStatementSnifferVisitor extends BythonParserBaseVisitor<Void> {
    private AromaReport report;
    private String callerName;

    public SwitchStatementSnifferVisitor(AromaReport report, String callerName) {
        super();
        this.report = report;
        this.callerName = callerName;
    }

    private int countElifClauses(BythonParser.IfStatementContext ctx) {
        // Verifica cuántas cláusulas elif hay
        return ctx.elifClause().size();
    }

    // Verifica si hay mal olor en una declaración if/else con múltiples elif
    private void detectBadSmell(BythonParser.IfStatementContext ctx) {
        int elifCount = countElifClauses(ctx);

        // Si hay más de un elif, puede considerarse un mal olor "switch"
        if (elifCount > 1) {
            report.addAroma(new Aroma(this.callerName, "The code has a Switch Statement smell.", true));
        }
    }

    @Override
    public Void visitIfStatement(BythonParser.IfStatementContext ctx) {
        int elifCount = countElifClauses(ctx);  // Cuenta el número de cláusulas elif

        // Detecta mal olor solo si hay más de una cláusula elif
        if (elifCount > 1) {
            detectBadSmell(ctx);
        }

        // Si el código solo tiene un if-else, no se marca como mal olor
        return visitChildren(ctx);
    }
}









