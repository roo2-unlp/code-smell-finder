package oo2.redictado.DontPassNullSniffer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import oo2.redictado.Aroma;
import oo2.redictado.AromaReport;
import oo2.redictado.antlr4.BythonParser;
import oo2.redictado.antlr4.BythonParser.ExpressionContext;
import oo2.redictado.antlr4.BythonParserBaseVisitor;

public class DontPassNullSnifferVisitor extends BythonParserBaseVisitor<Void> {
    private AromaReport report;
    private String callerName;
    private List<String> variablesWithNone;
    private List<String> indexesWithNone;

    public DontPassNullSnifferVisitor(AromaReport report, String callerName) {
        super();
        this.report = report;
        this.callerName = callerName;
        this.variablesWithNone = new ArrayList<>();
        this.indexesWithNone = new ArrayList<>();
    }

    @Override
    public Void visitArgumentList(BythonParser.ArgumentListContext ctx) {
        //Obtengo la lista de argumentos enviados a la funcion y la convierto en un string
        List<ExpressionContext> argumentVariable = ctx.expression();
        for (ExpressionContext expression:argumentVariable) {
            if (isPassNone(expression)) {
                report.addAroma(new Aroma(this.callerName, "El codigo envia None.", true));
            }
        }
        return visitChildren(ctx);
    }
    private boolean isPassNone(ExpressionContext exprCtx) {
            if (exprCtx.assignment() != null) {
                return checkAssignment(exprCtx.assignment());
            } else {
                if (exprCtx.valueExpression().getText().equals("None")) {
                    return true;
                } else if (exprCtx.valueExpression().getText().equals("None")) {
                    return true;
            }
            }
        return false;
    }

    // Modificación en checkValueExpression para manejar variables y accesos a indices
    private boolean checkAssignment(BythonParser.AssignmentContext ctx) {
        if (ctx.simpleAssignment() != null) {
            visitSimpleAssignment(ctx.simpleAssignment());
          return this.variablesWithNone.contains(ctx.simpleAssignment().ID().getText());
        }
        if (ctx.implicitAssignment() != null) {
            visitImplicitAssignment(ctx.implicitAssignment());
            if (ctx.implicitAssignment().ID().getText()!=null) {
                return this.variablesWithNone.contains(ctx.implicitAssignment().ID().getText());
            }else{
                return this.variablesWithNone.contains(ctx.implicitAssignment().objectProperty().getText());
            }
        }
        if (ctx.indexAssignment() != null) {
            visitIndexAssignment(ctx.indexAssignment());
            return this.variablesWithNone.contains(ctx.indexAssignment().indexAccess().getText());
        }
        if (ctx.compoundAssignment() != null) {
            visitCompoundAssignment(ctx.compoundAssignment());
            return this.variablesWithNone.contains(ctx.compoundAssignment().objectProperty().getText());
        }
        return false;
    }



    //| this.variablesWithNone.contains(c.getText())
    /*@Override
    //c.valueExpression().callableExpression().getChild(0).getText().equals("None")| this.variablesWithNone.contains(c.valueExpression().callableExpression().getText())
    public Void visitAssignment(BythonParser.AssignmentContext ctx) {
        // Check if the assignment is to None
        // Preguntar si hay que chequear por cada tipo de assignment

        if (ctx.simpleAssignment() != null) { // Verifica que sea un simpleAssignment
            String variableName = ctx.simpleAssignment().ID().getText(); // Obtengo el nombre de la variable
            String assignedValue = ctx.simpleAssignment().expression().getText(); // Obtengo la expresión asignada

            if ("None".equals(assignedValue)) {
                // Si el valor asignado es "None", agrega la variable a la lista en el caso de que no este
                if (!variablesWithNone.contains(variableName)) {
                    variablesWithNone.add(variableName);
                }
            } else {
                // Si el valor no es "None" y la variable ya estaba en la lista, la elimino
                variablesWithNone.remove(variableName);
            }
        }
        return visitChildren(ctx);
    }*/
    @Override
    public Void visitSimpleAssignment(BythonParser.SimpleAssignmentContext ctx) {
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
    public Void visitImplicitAssignment(BythonParser.ImplicitAssignmentContext ctx) {
        String variableName;
        if (ctx.objectProperty() != null) {
            variableName = ctx.objectProperty().ID().getText();
        }else {
            variableName = ctx.ID().getText();
        }
        String assignedValue = ctx.expression().getText();
        if ("None".equals(assignedValue)) {
            if (!variablesWithNone.contains(variableName)) {
                variablesWithNone.add(variableName);
            }
        } else {
            variablesWithNone.remove(variableName);
        }
        return visitChildren(ctx);
    }
    public Void visitCompoundAssignment(BythonParser.CompoundAssignmentContext ctx) {
        String variableName=ctx.objectProperty().getText();
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
    @Override
    public Void visitIndexAssignment(BythonParser.IndexAssignmentContext ctx) {
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

