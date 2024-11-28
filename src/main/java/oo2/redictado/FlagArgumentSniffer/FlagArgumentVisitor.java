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

    /**
     * Visita cada declaración de método en el árbol y utiliza `visitFunctionDecl`
     * para analizar los parámetros e instrucciones `if` y ´elif´ dentro del método.
     *
     * @param ctx Contexto del método en el árbol del parser.
     * @return Siempre devuelve `null`.
     */
    @Override
    public Void visitFunctionDecl(BythonParser.FunctionDeclContext ctx) {
        String methodName = ctx.ID().getText();

        MethodDeclVisitor methodVisitor = new MethodDeclVisitor(this.report, methodName, callerName);
        methodVisitor.visit(ctx);

        return null;
    }

}
