package oo2.redictado.FlagArgumentSniffer;

import oo2.redictado.Aroma;
import org.antlr.v4.runtime.tree.TerminalNode;
import oo2.redictado.AromaReport;
import oo2.redictado.antlr4.BythonParser;
import oo2.redictado.antlr4.BythonParserBaseVisitor;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class FlagArgumentVisitor extends BythonParserBaseVisitor<Void> {
    private AromaReport report;
    private String callerName;
    private List<TerminalNode> parametersList;

    public FlagArgumentVisitor(AromaReport report, String callerName) {
        super();
        this.report = report;
        this.callerName = callerName;
        this.parametersList = new ArrayList<TerminalNode>();
    }

    
    
    private boolean containsParameterIf (BythonParser.IfStatementContext ifStmt) {
    	// Verificar si la variable usada en el if no está en la lista de parámetros
        String expressionText = ifStmt.valueExpression().getChild(0).getText();//momentaneo
        return parametersList.stream()
            .anyMatch(param -> param.getText().equals(expressionText));
    }
    
    private boolean containsParameterElif(BythonParser.IfStatementContext ifStmt) {
        // Obtenemos el texto de cada expresión en elifClause
        List<String> expressionsText = ifStmt.elifClause().stream()
        	.map(expression -> expression.getChild(1).getChild(0).getText()) //momentaneo
            .collect(Collectors.toList());

        // Verificamos que ninguno de los textos en expressionsText esté en parametersList
        return expressionsText.stream()
            .anyMatch(expression -> parametersList.stream()
                .anyMatch(param -> param.getText().equals(expression)));
    }
    
    
    
    private void addAromaIfContainsParameter(boolean containsParameterIf, boolean containsParameterElif) {
	    if ((!containsParameterIf) && (!containsParameterElif)) {
	        System.out.println("La expresión no contiene ninguno de los parámetros de la lista.");
	    } else {
	    	report.addAroma(new Aroma(this.callerName, "The function uses flag arguments.", true));
	        System.out.println("La expresión contiene un parámetro de la lista.");
	    }
    }
    
    @Override
    public Void visitIfStatement(BythonParser.IfStatementContext ifStmt) {
  
        boolean containsParameterIf = containsParameterIf(ifStmt);
        
        boolean containsParameterElif = containsParameterElif(ifStmt);

        addAromaIfContainsParameter(containsParameterIf, containsParameterElif);
    	
        return visitChildren(ifStmt);
    }

    
    @Override
  //Obtenemos todos los parametros de las funciones. La funcion retorna los parametros solo cuando existen, por lo tanto no debemos verificar su existencia
    public Void visitMethodDecl(BythonParser.MethodDeclContext ctx) {
        // Reiniciamos la lista de parámetros para la nueva función
        parametersList.clear();

        // Visitamos los parámetros de la función
        visitChildren(ctx);

        // Limpiamos la lista al finalizar el análisis del método
        parametersList.clear();
        return null;
    }

    @Override
    public Void visitIdentifierList(BythonParser.IdentifierListContext ctx) {
        ctx.ID().forEach(param -> this.parametersList.add(param));
        return visitChildren(ctx);
    }

   
}
