package oo2.redictado.MessageChainsSniffer;

import oo2.redictado.Aroma;
import oo2.redictado.AromaReport;
import oo2.redictado.antlr4.BythonParser;
import oo2.redictado.antlr4.BythonParserBaseVisitor;

public class MessageChainsSnifferVisitor extends BythonParserBaseVisitor<Void> {
    private final AromaReport report;
    private final String callerName;
    private final int chainThreshold;

    public MessageChainsSnifferVisitor(AromaReport report, String callerName) {
        super();
        this.report = report;
        this.callerName = callerName;
        this.chainThreshold = 3; //El limite que establecemos para detectar el bad smell
    }

    @Override
    public Void visitChainedExpression(BythonParser.ChainedExpressionContext ctx) {
        int profundidad = calcularProfundidadDeCadena(ctx);
        if (profundidad >= chainThreshold) {
            crearAroma(profundidad);
        }
        return visitChildren(ctx);
    }

    private int calcularProfundidadDeCadena(BythonParser.ChainedExpressionContext ctx) {
        return ctx.chainedMethodCall().size() + ctx.propertyAccess().size();
    }

    private void crearAroma(int profundidad) {
        String descripcion = "Detectada una expresión encadenada de longitud " + profundidad;
        report.addAroma(new Aroma(callerName, descripcion, true));
    }
}
