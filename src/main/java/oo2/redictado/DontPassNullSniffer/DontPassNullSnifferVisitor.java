package oo2.redictado.DontPassNullSniffer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import oo2.redictado.Aroma;
import oo2.redictado.AromaReport;
import oo2.redictado.antlr4.BythonParser;
import oo2.redictado.antlr4.BythonParser.ExpressionContext;
import oo2.redictado.antlr4.BythonParserBaseVisitor;

public class DontPassNullSnifferVisitor extends BythonParserBaseVisitor<Boolean> {
    private AromaReport report;
    private String callerName;
    private List<String> variablesWithNone;
    private ArgumentListVisitor argumentListVisitor;

    public DontPassNullSnifferVisitor(AromaReport report, String callerName) {
        super();
        this.report = report;
        this.callerName = callerName;
        this.variablesWithNone = new ArrayList<>();
        this.argumentListVisitor = new ArgumentListVisitor(report, callerName, variablesWithNone);
    }

    @Override
    public Boolean visitArgumentList(BythonParser.ArgumentListContext ctx) {
        //Obtengo la lista de argumentos enviados a la funcion y la convierto en un string

        return this.argumentListVisitor.visit(ctx);
    }

    @Override
    public Boolean visitSimpleAssignment(BythonParser.SimpleAssignmentContext ctx) {
        String variableName = ctx.ID().getText(); // Obtengo el nombre de la variable
        String assignedValue = ctx.expression().getText(); // Obtengo la expresión asignada
        if ("None".equals(assignedValue)) {
            if (!variablesWithNone.contains(variableName)) {
                variablesWithNone.add(variableName);
            }
        } else {
            variablesWithNone.remove(variableName);
        }
        return visitChildren(ctx);
    }

    @Override
    public Boolean visitIndexAssignment(BythonParser.IndexAssignmentContext ctx) {
        String variableName=ctx.indexAccess().getText();
        String assignedValue=ctx.expression().getText();
        if ("None".equals(assignedValue)) {
            if (!variablesWithNone.contains(variableName)) {
                variablesWithNone.add(variableName);
            }
        }else{
            variablesWithNone.remove(variableName);
        }
        return visitChildren(ctx);

    }
}
