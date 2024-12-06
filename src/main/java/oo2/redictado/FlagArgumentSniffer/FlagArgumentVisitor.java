package oo2.redictado.FlagArgumentSniffer;

import oo2.redictado.AromaReport;
import oo2.redictado.antlr4.BythonParser;
import oo2.redictado.antlr4.BythonParserBaseVisitor;

public class FlagArgumentVisitor extends BythonParserBaseVisitor<Void> {
    private AromaReport report;
    private String callerName;

    public FlagArgumentVisitor(AromaReport report, String callerName) {
        super();
        this.report = report;
        this.callerName = callerName;
    }

    @Override
    /**
     * Visita cada declaración de función en el árbol del parser.
     * 
     * Este método se encarga de crear un visitor especializado (`MethodDeclVisitor`) 
     * para analizar los parámetros y las estructuras internas (If y Elif) del método.
     *
     * @param ctx Contexto de la declaración de la función del parser.
     * @return Siempre devuelve `null`.
     */
    public Void visitFunctionDecl(BythonParser.FunctionDeclContext ctx) {
        String methodName = ctx.ID().getText();

        MethodDeclVisitor methodVisitor = new MethodDeclVisitor(this.report, methodName, callerName);
        methodVisitor.visit(ctx);

        return null;
    }

}
