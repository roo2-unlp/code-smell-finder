package oo2.redictado.LongMethodCodeSniffer;

import oo2.redictado.Aroma;
import oo2.redictado.AromaReport;
import oo2.redictado.antlr4.BythonParser;
import oo2.redictado.antlr4.BythonParserBaseVisitor;

public class LongMethodCodeVisitor extends BythonParserBaseVisitor<Void> {
    private static final int maxLineasPermitidas = 15;
    private static final int maxCaracteresPermitidos = 500;
    private int lineasValidas = 0;
    private int caracteresValidos = 0;
    private boolean esMetodoLlamado=false;
    private boolean esMetodo = false;

    private AromaReport report;
    private String callerName;

    public LongMethodCodeVisitor(AromaReport report, String callerName) {
        super();
        this.report = report;
        this.callerName = callerName;
    }

    @Override
    public Void visitStatement(BythonParser.StatementContext ctx) {

        if (esMetodo){
            String texto = ctx.getText().trim();
            if (esLineaValida(texto, ctx)) {
                lineasValidas++;
                caracteresValidos += texto.length();
                if (lineasValidas > maxLineasPermitidas || caracteresValidos > maxCaracteresPermitidos) {
                    report.addAroma(new Aroma(callerName,"el metodo excede la cantidad de lineas o caracteres por lo cual es un metodo largo",true));
                    return null; //corto la ejecucion en el nodo actual una vez que se que hay mal olor
                }
            }
            
        }   
        return visitChildren(ctx); 
    }

    public Void visitMethodDeclaration (BythonParser.MethodDeclContext ctx){
        esMetodo = true;
        visitChildren(ctx);
        esMetodo = false;
        return null;
    }



    private boolean esLlamadaAFuncion(BythonParser.StatementContext ctx) {
        esMetodoLlamado=false;
        if (ctx.getChildCount() > 0){
            this.visit(ctx.getChild(0));
        }
        return esMetodoLlamado;
    }

    public Void visitMethodCall (BythonParser.MethodCallContext ctx){
        esMetodoLlamado = true;
        return null;
    }

    private boolean esLineaValida(String texto, BythonParser.StatementContext ctx) {
        return !texto.startsWith("//") && !esLlamadaAFuncion(ctx) && !texto.startsWith("print");
    }
}
