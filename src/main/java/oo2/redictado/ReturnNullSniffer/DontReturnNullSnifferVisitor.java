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
    	// obtengo la variable o la expresion que se devuelve, puede ser 'None' directamente o una variable 'a'
    	String returnVariable = ctx.expression().getText();
    	
    	// si la expresion es 'None' o la variable esta en la lista de vars = None entonces hay mal olor
        if (returnVariable.equals("None") | this.variablesWithNone.contains(returnVariable) ) {
            report.addAroma(new Aroma(this.callerName, "The code returns None.", true));
        }
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
