package oo2.redictado.SwitchStatementSniffer.SE;

import oo2.redictado.Aroma;
import oo2.redictado.AromaReport;
import oo2.redictado.antlr4.BythonParser;
import oo2.redictado.antlr4.BythonParserBaseVisitor;
import java.util.HashSet;
import java.util.Set;


public class SwitchStatementSnifferVisitor extends BythonParserBaseVisitor<Void> {

    private AromaReport report;
    private String callerName;
    private Set<String> conditionSet; // Para almacenar las condiciones

    public SwitchStatementSnifferVisitor(AromaReport report, String callerName) {
        super();
        this.report = report;
        this.callerName = callerName;
        this.conditionSet = new HashSet<>();
    }

    private int countElifClauses(BythonParser.IfStatementContext ctx) {
        // Verifica cuántas cláusulas elif hay
        return ctx.elifClause().size();
    }

    private void detectBadSmell(BythonParser.IfStatementContext ctx) {

        // Si hay más de un elif, puede considerarse un mal olor "switch"
        if (countElifClauses(ctx) > 1) {
            report.addAroma(new Aroma(this.callerName, "The code has a Switch Statement smell.", true));
        }

        // Verificar condiciones duplicadas en elif
        for (BythonParser.ElifClauseContext elif : ctx.elifClause()) {
            // Aquí intentamos acceder a la condición del elif.
            // Asegúrate de que esto corresponda a la forma en que la gramática está definida.
            String condition = elif.getText();  // Extraemos la condición como texto completo

            if (conditionSet.contains(condition)) {
                report.addAroma(new Aroma(this.callerName, "Duplicate elif condition detected, possible bad smell.", true));
            } else {
                conditionSet.add(condition); // Añadimos la condición al set
            }
        }
    }

    @Override
    public Void visitIfStatement(BythonParser.IfStatementContext ctx) {
        // Cuenta el número de cláusulas elif
        if (countElifClauses(ctx) > 1) {
            detectBadSmell(ctx);  // Detecta "bad smell" si hay más de una cláusula elif
        }
        return visitChildren(ctx);
    }
}











