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

    public DontReturnNullSnifferVisitor(AromaReport report, String callerName) {
        super();
        this.report = report;
        this.callerName = callerName;
        this.variablesWithNone = new ArrayList<>();
    }

    @Override
    public Void visitReturnStatement(BythonParser.ReturnStatementContext ctx) {
        // Verificamos si la expresión en el return contiene una variable que tiene None
        if (isReturningNone(ctx.expression())) {
            report.addAroma(new Aroma(this.callerName, "The code returns None.", true));
        }

        return visitChildren(ctx);  // Continuamos con la visita a los hijos
    }

//    // Verifica si la expresión de retorno es a None o una variable con None asignada
//    private boolean isReturningNone(BythonParser.ExpressionContext exprCtx) {
//        if (exprCtx.valueExpression() != null) {
//            return checkValueExpression(exprCtx.valueExpression());
//        } else if (exprCtx.assignment() != null) {
//            return checkAssignment(exprCtx.assignment());
//        }
//        return false;
//    }
//
//    // Verifica si una ValueExpression contiene una variable que ha sido asignada a None
//    private boolean checkValueExpression(BythonParser.ValueExpressionContext ctx) {
//        if (ctx == null) return false;
//
//        // Caso explícito: la expresión es literalmente "None"
//        if (ctx.getText().equals("None")) {
//            return true;
//        }
//
//        // Revisa si es una llamada a una expresión
//        if (ctx.callableExpression() != null) {
//            String variableName = ctx.callableExpression().getText();
//            return this.variablesWithNone.contains(variableName);
//        }
//        // Verifica expresion con operadores
//        // valueExpression(id) te da la valueExpression de esa pos, se fija en la expresion de la izq y derecha
//        if (ctx.valueExpression(0) != null && ctx.valueExpression(1) != null) {
//            return checkValueExpression(ctx.valueExpression(0)) ||
//                    checkValueExpression(ctx.valueExpression(1));
//        }
//        // Si es un literal numérico no puede ser None
//        else if (ctx.NUMBER_LITERAL() != null) {
//            return false;
//        }
//
//        return false;
//    }
//
//    private boolean checkAssignment(BythonParser.AssignmentContext ctx) {
//        // Verificamos si es una asignación simple
//        if (ctx.simpleAssignment() != null) {
//            return checkSimpleAssignment(ctx.simpleAssignment());
//        }
//        // Verificamos si es una asignación implícita
//        else if (ctx.implicitAssignment() != null) {
//            return checkImplicitAssignment(ctx.implicitAssignment());
//        }
//        return false;
//    }
//
//    private boolean checkSimpleAssignment(BythonParser.SimpleAssignmentContext ctx) {
//        String variableName = ctx.ID().getText();
//        String assignedValue = ctx.expression().getText();
//
//        if ("None".equals(assignedValue)) {
//            if (!variablesWithNone.contains(variableName)) {
//                variablesWithNone.add(variableName);
//            }
//            return true;
//        } else {
//            variablesWithNone.remove(variableName);
//        }
//        return false;
//    }
//
//    private boolean checkImplicitAssignment(BythonParser.ImplicitAssignmentContext ctx) {
//        String variableName = ctx.ID().getText();
//        BythonParser.ExpressionContext assignedExpr = ctx.expression();
//
//        // Si la variable ya tiene None asignado, devuelve true
//        if (this.variablesWithNone.contains(variableName)) {
//            return true;
//        }
//
//        // Verifica si hay una expresión en el lado derecho
//        assignedExpr = ctx.expression();
//
//        // Si no hay una expresión derecha, no hay nada que chequear
//        if (assignedExpr == null) {
//            return false;
//        }
//
//        // Chequea si la expresión contiene None con metodo checkValueExpression
//        return checkValueExpression(assignedExpr.valueExpression());
//    }


    // Verifica si la expresión de retorno contiene None explícitamente o implícitamente
    private boolean isReturningNone(BythonParser.ExpressionContext exprCtx) {
        if (exprCtx.valueExpression() != null) {
            return checkValueExpression(exprCtx.valueExpression());
        }
        return false;
    }

    // Modificación en checkValueExpression para manejar variables y expresiones complejas
    // aca directamente revisa si la expresion es None o si es una variable que tiene None asignado
    // o si es una implicitExpression (a + 10) y a es None
    private boolean checkValueExpression(BythonParser.ValueExpressionContext ctx) {
        if (ctx == null) return false;

        // Si la expresión es literalmente "None"
        if (ctx.getText().equals("None")) {
            return true;
        }

        // Si es una variable, verifica si fue asignada a None
        if (ctx.callableExpression() != null) {
            String variableName = ctx.callableExpression().getText();
            return this.variablesWithNone.contains(variableName);
        }

        // Si la expresión contiene operaciones aritméticas u otras expresiones complejas
        if (ctx.valueExpression(0) != null && ctx.valueExpression(1) != null) {
            return checkValueExpression(ctx.valueExpression(0)) || checkValueExpression(ctx.valueExpression(1));
        }

        return false;
    }

    // Mejora para rastrear correctamente variables asignadas a None
    @Override
    public Void visitAssignment(BythonParser.AssignmentContext ctx) {
        if (ctx.simpleAssignment() != null) {
            String variableName = ctx.simpleAssignment().ID().getText();
            String assignedValue = ctx.simpleAssignment().expression().getText();

            if ("None".equals(assignedValue)) {
                if (!variablesWithNone.contains(variableName)) {
                    variablesWithNone.add(variableName);
                }
            } else {
                // Si se asigna un valor distinto a None, remueve la variable de la lista
                variablesWithNone.remove(variableName);
            }
        }
        return visitChildren(ctx);
    }

}
