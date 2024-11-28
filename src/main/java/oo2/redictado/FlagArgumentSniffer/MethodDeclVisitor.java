package oo2.redictado.FlagArgumentSniffer;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.tree.TerminalNode;

import oo2.redictado.Aroma;
import oo2.redictado.AromaReport;
import oo2.redictado.antlr4.BythonParser;
import oo2.redictado.antlr4.BythonParserBaseVisitor;

public class MethodDeclVisitor extends BythonParserBaseVisitor<Void> {
	private AromaReport report;
    private String callerName;
    private String methodName;
    private List<TerminalNode> parametersList;
    private List<String> ExpressionVarList;
    

    public MethodDeclVisitor(AromaReport report, String methodName, String callerName) {
        super();
        this.report = report;
        this.callerName = callerName;
        this.methodName = methodName;
        this.parametersList = new ArrayList<TerminalNode>();
        this.ExpressionVarList = new ArrayList<String>();
    }
    
    /**
     * Obtiene y almacena los parámetros declarados en el método.
     *
     * @param ctx Contexto de la lista de identificadores del parser.
     * @return Siempre devuelve `null`.
     */
    @Override
    public Void visitIdentifierList(BythonParser.IdentifierListContext ctx) {
        ctx.ID().forEach(param -> this.parametersList.add(param));
        return visitChildren(ctx);
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
     * Procesa las expresiones dentro de las cláusulas `if` y `elif`.
     *
     * @param ifStmt Contexto de la cláusula `if`.
     */
    private void getIfExpressions(BythonParser.IfStatementContext ifStmt) {
        if (ifStmt.valueExpression() != null) {
            processValueExpression(ifStmt.valueExpression());
        }

        ifStmt.elifClause().forEach(elif -> {
            if (elif.valueExpression() != null) {
                processValueExpression(elif.valueExpression());
            }
        });
    }
    
    
    
    /**
     * Verifica si alguna expresión en `ExpressionVarList` coincide con los parámetros definidos
     * en la lista `parametersList`.
     *
     * @param ifStmt Contexto de la cláusula `if` del parser.
     * @return `true` si alguna expresión coincide con un parámetro; `false` en caso contrario.
     */
    private boolean containsParameter() {
        return this.ExpressionVarList.stream()
            .anyMatch(expression -> parametersList.stream()
                .anyMatch(param -> param.getText().equals(expression)));
    }
    
    

    
    /**
     * Analiza las declaraciones `if` y ´elif´ en el método y extrae expresiones.
     *
     * @param ifStmt Contexto del `if` en el árbol del parser.
     * @return Siempre devuelve `null`.
     */
    @Override
    public Void visitIfStatement(BythonParser.IfStatementContext ifStmt) {   
    	this.getIfExpressions(ifStmt);
        boolean containsParameter = containsParameter();
        if (containsParameter) {
	    	report.addAroma(new Aroma(this.methodName, "The function " + this.methodName + " in " + this.callerName + " uses flag arguments.", true));
	    }
        return visitChildren(ifStmt);
    }
   
}

