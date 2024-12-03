package oo2.redictado.LongMethodCodeSniffer;

import oo2.redictado.Aroma;
import oo2.redictado.AromaReport;
import oo2.redictado.antlr4.BythonParser;
import oo2.redictado.antlr4.BythonParserBaseVisitor;

public class LongMethodCodeVisitor extends BythonParserBaseVisitor<Void> {
    private static final int maxLineasPermitidas = 10;
    private static final int maxCaracteresPermitidos = 250;
    private int lineasValidas = 0;
    private int caracteresValidos = 0;


    private AromaReport report;
    private String callerName;

    public LongMethodCodeVisitor(AromaReport report, String callerName) {
        super();
        this.report = report;
        this.callerName = callerName;
    }

    @Override
    public Void visitFunctionDecl(BythonParser.FunctionDeclContext ctx) { 
    // Reiniciar contadores para cada método
    lineasValidas = 0;
    caracteresValidos = 0;

    // Visitar el contenido del método
    visitChildren(ctx);

    // Después de visitar, verificar si excede límites
    if (lineasValidas > maxLineasPermitidas || caracteresValidos > maxCaracteresPermitidos) {
        report.addAroma(new Aroma(
            callerName, 
            "El método excede la cantidad de líneas o caracteres permitidos.",
            true
        ));
    }

    return null; // Terminar esta visita
}

@Override
public Void visitStatement(BythonParser.StatementContext ctx) { 
    String texto = ctx.getText().trim();

    // Contar líneas y caracteres
    lineasValidas++;
    caracteresValidos += texto.length();

    return visitChildren(ctx); 
}
}