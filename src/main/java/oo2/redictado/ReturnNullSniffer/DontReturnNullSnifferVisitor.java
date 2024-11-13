package oo2.redictado.ReturnNullSniffer;

import java.util.ArrayList;
import java.util.List;

import oo2.redictado.Aroma;
import oo2.redictado.AromaReport;
import oo2.redictado.antlr4.BythonParser;
import oo2.redictado.antlr4.BythonParserBaseVisitor;

public class DontReturnNullSnifferVisitor extends BythonParserBaseVisitor<Void> {
	private AromaReport report;
    private String callerName;
    private List<String> variablesWithNone;
    private List<String> indexesWithNone;

    public DontReturnNullSnifferVisitor(AromaReport report, String callerName) {
        super();
        this.report = report;
        this.callerName = callerName;
        this.variablesWithNone = new ArrayList<>();
        this.indexesWithNone = new ArrayList<>();

    }

    @Override
    public Void visitReturnStatement(BythonParser.ReturnStatementContext ctx) {
        // Verificamos si la expresión en el return contiene una variable que tiene None
        if (isReturningNone(ctx.expression())) {
            report.addAroma(new Aroma(this.callerName, "El codigo devuelve None.", true));
        }

        return visitChildren(ctx);  // Continuamos con la visita a los hijos
    }


    // Verifica si la expresión de retorno contiene None explícitamente o implícitamente
    private boolean isReturningNone(BythonParser.ExpressionContext exprCtx) {
        if (exprCtx.valueExpression() != null) {
            return checkValueExpression(exprCtx.valueExpression());
        }
        return false;
    }

    // Modificación en checkValueExpression para manejar variables y accesos a indices
    private boolean checkValueExpression(BythonParser.ValueExpressionContext ctx) {
        if (ctx == null) return false;

        // Si la expresión es "None"
        if (ctx.getText().equals("None")) {
            return true;
        }

        // Si es una variable, verifica si fue asignada a None y nunca fue modificada
        if (ctx.callableExpression() != null) {
            String variableName = ctx.callableExpression().getText();
            return this.variablesWithNone.contains(variableName);
        }

        // Si es un acceso por indice, verifica si fue asignado a None y nunca fue modificado
        if(ctx.indexAccess() != null){
            String indexName = ctx.indexAccess().getText();
            return this.indexesWithNone.contains(indexName);
        }

        return false;
    }

    // Obtenemos variables asignadas con None
    @Override
    public Void visitSimpleAssignment(BythonParser.SimpleAssignmentContext ctx) {
        String variableName = ctx.ID().getText();
        String assignedValue = ctx.expression().getText();

        // si el valor de la variable es None, la agrega a la lista
        if ("None".equals(assignedValue)) {
             if (!variablesWithNone.contains(variableName)) {
                  variablesWithNone.add(variableName);
             }
        } else {
            // Si se asigna un valor distinto a None, remueve la variable de la lista
            variablesWithNone.remove(variableName);
        }

        return visitChildren(ctx);
    }


    // Obtenemos indices asignados con None
    @Override
    public Void visitIndexAssignment(BythonParser.IndexAssignmentContext ctx) {
        String indexName = ctx.indexAccess().getText();
        String assignedValue = ctx.expression().getText();

        // si se asigna None a la posicion de un indice, se agrega a la lista el indice
        if ("None".equals(assignedValue)) {
            if (!indexesWithNone.contains(indexName)) {
                indexesWithNone.add(indexName);
            }
        } else {
            // Si se asigna un valor distinto a None, remueve el indice de la lista
            indexesWithNone.remove(indexName);
        }
        return visitChildren(ctx);
    }

}
