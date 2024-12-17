package oo2.redictado.FeatureEnvySniffer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import oo2.redictado.Aroma;
import oo2.redictado.AromaReport;
import oo2.redictado.antlr4.BythonParser;
import oo2.redictado.antlr4.BythonParser.MethodParamListContext;
import oo2.redictado.antlr4.BythonParserBaseVisitor;

/**
 * Visitor que detecta el olor a "Feature Envy" en el código Bython.
 * Este olor ocurre cuando un método accede excesivamente a los atributos o
 * métodos de otra clase, mostrando un alto nivel de acoplamiento.
 */
public class FeatureEnvyVisitor extends BythonParserBaseVisitor<Void> {

    private final AromaReport report;
    private final String callerName;
    private final int featureEnvyLimit;
    private final Map<String, Integer> attributeAccessCount;
    private final Set<String> instanceVariables;

    /**
     * Constructor de FeatureEnvyVisitor.
     * @param report El reporte donde se registran los malos olores detectados.
     * @param callerName El nombre del visitante o del módulo que realiza el análisis.
     */
    public FeatureEnvyVisitor(AromaReport report, String callerName) {
        this.report = report;
        this.callerName = callerName;
        this.featureEnvyLimit = 2; // Límite para considerar un acceso como "Feature Envy".
        this.attributeAccessCount = new HashMap<>();
        this.instanceVariables = new HashSet<>();
    }

    /**
     * Visita la declaración de un método y registra sus parámetros.
     * @param ctx Contexto de la declaración del método.
     */
    @Override
    public Void visitMethodDecl(BythonParser.MethodDeclContext ctx) {
        if (ctx.methodParamList() != null) {
            MethodParamListContext paramListContext = ctx.methodParamList();

            paramListContext.ID().forEach(param -> {
                String paramName = param.getText();
                attributeAccessCount.put(paramName, 0);
            });
        }
        return super.visitMethodDecl(ctx);
    }

    /**
     * Visita un miembro de la clase y registra las variables de instancia.
     * @param ctx Contexto del miembro de la clase.
     */
    @Override
    public Void visitClassMember(BythonParser.ClassMemberContext ctx) {
        if (ctx.simpleAssignment() != null) {
            String varName = ctx.simpleAssignment().ID().getText();
            instanceVariables.add(varName);
        }
        return visitChildren(ctx);
    }

    /**
     * Visita una expresión encadenada y analiza los accesos a atributos o métodos.
     * @param ctx Contexto de la expresión encadenada.
     */
    @Override
    public Void visitChainedExpression(BythonParser.ChainedExpressionContext ctx) {
        StringBuilder fullAccess = new StringBuilder(ctx.chainStart().getText());

        ctx.children.forEach(child -> {
            if (child instanceof BythonParser.PropertyAccessContext || child instanceof BythonParser.ChainedMethodCallContext) {
                fullAccess.append(child.getText());
            }
        });

        contarAccesoProperty(fullAccess.toString());
        return null;
    }

    /**
     * Registra y analiza el acceso a una propiedad o método.
     * @param fullAccess Acceso completo a la propiedad o método.
     */
    private void contarAccesoProperty(String fullAccess) {
        String[] parts = fullAccess.split("\\.");

        // Caso: Acceso a una variable de instancia propia (self.variable)
        if (parts.length > 2 && parts[0].equals("self")) {
            String firstLevelVar = parts[1];
            incrementarConteo(firstLevelVar, fullAccess);

        // Caso: Acceso a parámetros de otros objetos (obj.variable)
        } else if (parts.length > 1 && !parts[0].equals("self")) {
            String paramName = parts[0];
            incrementarConteo(paramName, fullAccess);
        }
    }

    /**
     * Incrementa el conteo de accesos a un atributo y genera un aroma si excede el límite.
     * @param accessType Nombre del atributo o parámetro accedido.
     * @param fullAccess Acceso completo registrado.
     */
    private void incrementarConteo(String accessType, String fullAccess) {
        int newCount = attributeAccessCount.getOrDefault(accessType, 0) + 1;
        attributeAccessCount.put(accessType, newCount);

        if (newCount > featureEnvyLimit) {
            report.addAroma(new Aroma(
                this.callerName,
                "Feature envy detected on " + accessType + ": " + fullAccess,
                true
            ));
        }
    }

    /**
     * Calcula la profundidad total de una expresión encadenada.
     * @param ctx Contexto de la expresión encadenada.
     * @return Profundidad total de la cadena.
     */
    public int calculateDepth(BythonParser.ChainedExpressionContext ctx) {
        return ctx.chainedMethodCall().size() + ctx.propertyAccess().size();
    }
}
