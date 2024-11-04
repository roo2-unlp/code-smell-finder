package oo2.redictado.DontPassNullSniffer;

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
    
    public DontPassNullSnifferVisitor(AromaReport report, String callerName) {
        super();
        this.report = report;
        this.callerName = callerName;
    }
    @Override
    public Void visitArgumentList (BythonParser.ArgumentListContext ctx) {
    	//Obtengo la lista de argumentos enviados a la funcion y la convierto en un string
    	List<ExpressionContext> argumentVariable= ctx.expression();
    	argumentVariable.forEach((c)->{if (c.valueExpression().callableExpression().getChild(0).getText().equals("None")| this.variablesWithNone.contains(c.valueExpression().callableExpression().getText())){
    		report.addAroma(new Aroma(this.callerName, "The code Pass Null", true));
    		}});
    	return visitChildren(ctx);
    }
    @Override 
    
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
    }
}
