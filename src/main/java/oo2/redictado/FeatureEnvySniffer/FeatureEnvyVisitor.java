package oo2.redictado.FeatureEnvySniffer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import oo2.redictado.Aroma;
import oo2.redictado.AromaReport;
import oo2.redictado.antlr4.BythonParser;
import oo2.redictado.antlr4.BythonParserBaseVisitor;

public class FeatureEnvyVisitor extends BythonParserBaseVisitor<Void> {
    private AromaReport report;
    private String callerName;
    private final int featureEnvyLimit;
    private Map<String, Integer> attributeAccessCount;
    private Set<String> instanceVariables;

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
        this.instanceVariables = new HashSet<>();
    }

    /**
     * Visita una declaración de método.
     * Ignora el método __init__.
     * @param ctx El contexto de la declaración de método.
     * @return null
     */
    @Override
    public Void visitMethodDecl(BythonParser.MethodDeclContext ctx) {
        String methodName = ctx.ID().getText();
        if (methodName.equals("__init__")) {
            // Guarda las variables de instancia inicializadas en __init__
            ctx.block().statement().forEach(statement -> {
                if (statement instanceof BythonParser.AssignmentContext) {
                    BythonParser.AssignmentContext assignment = (BythonParser.AssignmentContext) statement;
                    if (assignment.left.getText().startsWith("self.")) {
                        String variableName = assignment.left.getText().substring(5);
                        instanceVariables.add(variableName);
                    }
                }
            });
            return null; // Ignora el método __init__
        }
        return visitChildren(ctx);
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

    /**
     * Visita un acceso a propiedad.
     * Cuenta los accesos a atributos de otras clases.
     * @param ctx El contexto del acceso a propiedad.
     * @return null
     */
    @Override
    public Void visitPropertyAccess(BythonParser.PropertyAccessContext ctx) {
        String propertyAccess = ctx.getText();
        // Verifica si el acceso es a un atributo de otro objeto
        if (propertyAccess.contains(".")) {
            String objectName = propertyAccess.substring(0, propertyAccess.indexOf('.'));
            if (objectName.equals("self")) {
                // Acceso a una variable de instancia
                String instanceVariable = propertyAccess.substring(propertyAccess.indexOf('.') + 1, propertyAccess.lastIndexOf('.'));
                if (instanceVariables.contains(instanceVariable)) {
                    attributeAccessCount.put(instanceVariable, attributeAccessCount.getOrDefault(instanceVariable, 0) + 1);
                    if (attributeAccessCount.get(instanceVariable) > featureEnvyLimit) {
                        report.addAroma(new Aroma(this.callerName, "The code has feature envy due to excessive access to attributes of instance variable " + instanceVariable, true));
                    }
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
     * Visita una declaración de clase y resetea el contador de accesos a atributos.
     * @param ctx El contexto de la declaración de clase.
     * @return null
     */
    @Override
    public Void visitClassDecl(BythonParser.ClassDeclContext ctx) {
        attributeAccessCount.clear();
        instanceVariables.clear();
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
