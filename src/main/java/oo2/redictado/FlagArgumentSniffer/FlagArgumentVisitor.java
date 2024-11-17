package oo2.redictado.FlagArgumentSniffer;

import oo2.redictado.Aroma;
import org.antlr.v4.runtime.tree.TerminalNode;
import oo2.redictado.AromaReport;
import oo2.redictado.antlr4.BythonParser;
import oo2.redictado.antlr4.BythonParserBaseVisitor;
import java.util.List;
import java.util.ArrayList;


public class FlagArgumentVisitor extends BythonParserBaseVisitor<Void> {
    private AromaReport report;
    private String callerName;
    private List<TerminalNode> parametersList;
    private List<String> ExpressionVarList;
    

    public FlagArgumentVisitor(AromaReport report, String callerName) {
        super();
        this.report = report;
        this.callerName = callerName;
        this.parametersList = new ArrayList<TerminalNode>();
        this.ExpressionVarList = new ArrayList<String>();
    }
    
    /**
     * Obtiene las expresiones usadas en las cláusulas `if` y `elif`.
     * Agrega cada subexpresión encontrada a la lista `ExpressionVarList`.
     *
     * @param ifStmt Contexto de la cláusula `if` del parser.
     * @return Siempre devuelve el resultado de `visitChildren(ifStmt)`.
     */
    private Void getIfExpressions(BythonParser.IfStatementContext ifStmt) {
        if (ifStmt.valueExpression() != null) {
            processValueExpression(ifStmt.valueExpression());
        }

        ifStmt.elifClause().forEach(elif -> {
            if (elif.valueExpression() != null) {
                processValueExpression(elif.valueExpression());
            }
        });

        return visitChildren(ifStmt);
    }
    
    /**
     * Procesa una expresión de tipo `ValueExpressionContext` y todas sus subexpresiones.
     * Agrega cada subexpresión encontrada a la lista `ExpressionVarList`.
     *
     * @param valueExpr Contexto de la expresión del parser.
     */
    private void processValueExpression(BythonParser.ValueExpressionContext valueExpr) {
    	valueExpr.valueExpression().stream()
        	.forEach(subExpr -> processValueExpression(subExpr));
        String expression = valueExpr.getText();
        this.ExpressionVarList.add(expression);

    }
    
    /**
     * Verifica si alguna expresión en `ExpressionVarList` coincide con los parámetros definidos
     * en la lista `parametersList`.
     *
     * @param ifStmt Contexto de la cláusula `if` del parser.
     * @return `true` si alguna expresión coincide con un parámetro; `false` en caso contrario.
     */
    private boolean containsParameter(BythonParser.IfStatementContext ifStmt) {
        return this.ExpressionVarList.stream()
            .anyMatch(expression -> parametersList.stream()
                .anyMatch(param -> param.getText().equals(expression)));
    }
    
    
    /**
     * Agrega un objeto `Aroma` al reporte si la función analizada contiene
     * argumentos bandera en sus estructuras de control.
     *
     * @param containsParameter Resultado de la verificación de parámetros en `containsParameter`.
     */
    private void addAromaIfContainsParameter(boolean containsParameter) {
	    if (containsParameter) {
	    	report.addAroma(new Aroma(this.callerName, "The function " + this.callerName + " uses flag arguments.", true));
	    }
    }
    
    @Override
    public Void visitIfStatement(BythonParser.IfStatementContext ifStmt) {   
    	this.getIfExpressions(ifStmt);
        boolean containsParameter = containsParameter(ifStmt);
        addAromaIfContainsParameter(containsParameter);
   	
        return visitChildren(ifStmt);
    }

    
    @Override
    public Void visitMethodDecl(BythonParser.MethodDeclContext ctx) {
        parametersList.clear();
        visitChildren(ctx);
        parametersList.clear();
        
        return null;
    }

    
    @Override
    public Void visitIdentifierList(BythonParser.IdentifierListContext ctx) {
        ctx.ID().forEach(param -> this.parametersList.add(param));
        return visitChildren(ctx);
    }

   
}
