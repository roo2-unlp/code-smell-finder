package oo2.redictado.FeatureEnvySniffer;

import java.util.HashMap;
import java.util.Map;

import oo2.redictado.Aroma;
import oo2.redictado.AromaReport;
import oo2.redictado.antlr4.BythonParser;
import oo2.redictado.antlr4.BythonParserBaseVisitor;

public class FeatureEnvyVisitor extends BythonParserBaseVisitor<Void> {
    private AromaReport report;
    private String callerName;
    private final int featureEnvyLimit;
    private Map<String, Integer> attributeAccessCount;

    /**
     * Constructor de FeatureEnvyVisitor.
     * @param report El reporte de aromas donde se registrarán los malos olores.
     * @param callerName El nombre del visitante.
     */
    public FeatureEnvyVisitor(AromaReport report, String callerName) {
        super();
        this.report = report;
        this.callerName = callerName;
        this.featureEnvyLimit = 2;
        this.attributeAccessCount = new HashMap<>();
    }

    /**
     * Visita una expresión encadenada y calcula su profundidad.
     * Si la profundidad excede el límite, se registra un mal olor.
     * @param ctx El contexto de la expresión encadenada.
     * @return null
     */
    @Override
    public Void visitChainedExpression(BythonParser.ChainedExpressionContext ctx) {
        int depth = calculateDepth(ctx);
        if (depth > this.featureEnvyLimit) {
            crearAroma(depth);
        }
        return visitChildren(ctx);
    }

  @Override
    public Void visitPropertyAccess(BythonParser.PropertyAccessContext ctx) {
        String propertyAccess = ctx.getText();
        // Verifica si el acceso es a un atributo de otro objeto
        if (propertyAccess.contains(".")) {
            String objectName = propertyAccess.substring(0, propertyAccess.indexOf('.'));
            if (objectName.equals("self")) {
                // Acceso a una variable de instancia
                String instanceVariable = propertyAccess.substring(propertyAccess.indexOf('.') + 1, propertyAccess.lastIndexOf('.'));
                attributeAccessCount.put(instanceVariable, attributeAccessCount.getOrDefault(instanceVariable, 0) + 1);
                if (attributeAccessCount.get(instanceVariable) > featureEnvyLimit) {
                    report.addAroma(new Aroma(this.callerName, "The code has feature envy due to excessive access to attributes of instance variable " + instanceVariable, true));
                }
            } else {
                // Acceso a un parámetro
                attributeAccessCount.put(objectName, attributeAccessCount.getOrDefault(objectName, 0) + 1);
                if (attributeAccessCount.get(objectName) > featureEnvyLimit) {
                    report.addAroma(new Aroma(this.callerName, "The code has feature envy due to excessive access to attributes of parameter " + objectName, true));
                }
            }
        }
        return visitChildren(ctx);
    }

    /**
     * Obtiene el nombre del objeto al que se llama el método.
     * @param methodCall La cadena de texto de la llamada a método.
     * @return El nombre del objeto.
     

    private String getObjectName(String methodCall) {
        // Obtiene el nombre del objeto antes del primer punto en la cadena de texto de la llamada al método
        int firstDotIndex = methodCall.indexOf('.');
        if (firstDotIndex != -1) {
            return methodCall.substring(0, firstDotIndex);
        }
        return methodCall;
    }*/

    /**
     * Visita una expresión y cuenta los accesos a métodos y variables de los parámetros.
     * @param ctx El contexto de la expresión.
     * @return null
     */
    @Override
    public Void visitExpression(BythonParser.ExpressionContext ctx) {
        String expression = ctx.getText();
        // Verifica si el acceso es a un método o variable del parámetro
        if (expression.contains(".")) {
            String parameterName = expression.substring(0, expression.indexOf('.'));
            methodAccessCount.put(parameterName, methodAccessCount.getOrDefault(parameterName, 0) + 1);
            if (methodAccessCount.get(parameterName) > featureEnvyLimit) {
                report.addAroma(new Aroma(this.callerName, "The code has feature envy due to excessive access to parameter " + parameterName, true));
            }
        }
        return visitChildren(ctx);
    }


    /**
     * Visita una declaración de clase y resetea el contador de accesos a métodos.
     * @param ctx El contexto de la declaración de clase.
     * @return null
     */
    @Override
    public Void visitClassDecl(BythonParser.ClassDeclContext ctx) {
        methodAccessCount.clear();
        return visitChildren(ctx);
    }

    /**
     * Crea un aroma de mal olor y lo agrega al reporte.
     * @param depth La profundidad de la expresión encadenada.
     */
    private void crearAroma(int depth) {
        report.addAroma(new Aroma(this.callerName, "The code has feature envy with depth " + depth, true));
    }

    /**
     * Calcula la profundidad de una expresión encadenada.
     * @param ctx El contexto de la expresión encadenada.
     * @return La profundidad de la expresión.
     */
    private int calculateDepth(BythonParser.ChainedExpressionContext ctx) {
        return ctx.chainedMethodCall().size() + ctx.propertyAccess().size();
    }



}
