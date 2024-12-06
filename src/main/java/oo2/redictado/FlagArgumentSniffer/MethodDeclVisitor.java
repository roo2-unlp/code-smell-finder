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
     * Obtiene los parámetros declarados en el método y los almacena en la lista parametersList.
     *
     * Este método se ejecuta al visitar una lista de identificadores (parámetros)
	 * y asegura que los parámetros declarados se registren para su posterior análisis.
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
     * Procesa una expresión y todas sus subexpresiones recursivamente.
     *
     * Este método agrega cada subexpresión encontrada al `ExpressionVarList`
     * para permitir su análisis posterior.
     *
     * @param valueExpr Contexto de una expresión del parser.
     */
    private void processValueExpression(BythonParser.ValueExpressionContext valueExpr) {
    	valueExpr.valueExpression().stream()
        	.forEach(subExpr -> processValueExpression(subExpr));
        String expression = valueExpr.getText();
        this.ExpressionVarList.add(expression);

    }
    
    /**
     * Procesa las expresiones dentro de las cláusulas `if` y `elif` del método.
     *
     * Este método utiliza `processValueExpression` para registrar todas las expresiones
     * en las cláusulas condicionales en la lista `ExpressionVarList`.
     *
     * @param ifStmt Contexto de una cláusula `if` del parser.
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
     * Verifica si alguna expresión registrada coincide con los parámetros del método.
     *
     * Este método utiliza las listas `ExpressionVarList` y `parametersList` para identificar
     * si alguna expresión depende directamente de un parámetro, lo que puede indicar
     * un posible uso de flag arguments.
     *
     * @return `true` si se detecta un parámetro como flag argument; `false` en caso contrario.
     */
    private boolean containsParameter() {
        return this.ExpressionVarList.stream()
            .anyMatch(expression -> parametersList.stream()
                .anyMatch(param -> param.getText().equals(expression)));
    }
    
    

    
    /**
     * Analiza las cláusulas `if` dentro del método.
     *
     * Este método extrae las expresiones de las cláusulas `if` y `elif` y verifica si
     * algún flag argument está siendo utilizado. Si se detecta un flag argument, registra
     * un bad smell en el reporte `AromaReport`.
     *
     * @param ifStmt Contexto de una cláusula `if` del parser.
     * @return Siempre devuelve `null`.
     */
    @Override
    public Void visitIfStatement(BythonParser.IfStatementContext ifStmt) {   
    	this.getIfExpressions(ifStmt);
        boolean containsParameter = containsParameter();
        if (containsParameter) {
	    	report.addAroma(new Aroma(this.methodName, "The function " + this.methodName + " in " + this.callerName + " uses flag arguments.", true));
	    }
        System.out.println(this.ExpressionVarList);
        return visitChildren(ifStmt);
    }
   
}

