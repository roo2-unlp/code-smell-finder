package oo2.redictado.LongMethodCodeSniffer;

import oo2.redictado.Aroma;
import oo2.redictado.AromaReport;
import oo2.redictado.antlr4.BythonParser;
import oo2.redictado.antlr4.BythonParserBaseVisitor;

public class LongMethodCodeVisitor extends BythonParserBaseVisitor<Void> {
    private static final int maxLineasPermitidas = 10;
    private static final int maxCaracteresPermitidos = 100;
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
    public Void visitStatement(BythonParser.StatementContext ctx) { 
        String texto = ctx.getText().trim();
        if (!texto.matches("^\\s*def\\s+[a-zA-Z_][a-zA-Z0-9_]*\\s*\\(.*\\)\\s*:\\s*$")){
            lineasValidas++;
            caracteresValidos += texto.length();
            if (lineasValidas > maxLineasPermitidas || caracteresValidos > maxCaracteresPermitidos) {
                report.addAroma(new Aroma(callerName,"el metodo excede la cantidad de lineas o caracteres por lo cual es un metodo largo",true));
                return null; //corto la ejecucion en el nodo actual una vez que se que hay mal olor
            }    
        }
        else{
            return visitStatement(ctx) //nose que parametro pasarle para que se empiece a recorrer el metodo que esta adentro del primer metodo 
        }
        return visitChildren(ctx); 
    }



}
