package oo2.redictado.FeatureEnvySniffer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.antlr.v4.runtime.tree.ParseTree;

import oo2.redictado.Aroma;
import oo2.redictado.AromaReport;
import oo2.redictado.antlr4.BythonParser;
import oo2.redictado.antlr4.BythonParser.MethodParamListContext;
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
        this.featureEnvyLimit = 2;  // Limite de "feature envy"
        this.attributeAccessCount = new HashMap<>();
        this.instanceVariables = new HashSet<>();
    }

    /**
     * Visita la declaración de un método.
     * @param ctx El contexto de la declaración del método.
     * @return null
     */
    @Override
    public Void visitMethodDecl(BythonParser.MethodDeclContext ctx) {
        if (ctx.methodParamList() != null) {
            MethodParamListContext paramListContext = ctx.methodParamList();

            for (int i = 0; i < paramListContext.ID().size(); i++) {
                String paramName = paramListContext.ID(i).getText();  
                System.out.println("Parámetro encontrado: " + paramName);

                attributeAccessCount.put(paramName, 0);  
            }
        }
        return super.visitMethodDecl(ctx);
    }

    /**
     * Visita un miembro de clase.
     * @param ctx El contexto del miembro de clase.
     * @return null
     */
    @Override
    public Void visitClassMember(BythonParser.ClassMemberContext ctx) {
        if (ctx.simpleAssignment() != null) {
            String varName = ctx.simpleAssignment().ID().getText(); 
            instanceVariables.add(varName);
            System.out.println("Variable de instancia registrada: " + varName);
        }
        return visitChildren(ctx);
    }

    /**
     * Visita una expresión encadenada.
     * @param ctx El contexto de la expresión encadenada.
     * @return null
     */
    @Override
    public Void visitChainedExpression(BythonParser.ChainedExpressionContext ctx) {
        StringBuilder fullAccess = new StringBuilder(ctx.chainStart().getText());
        System.out.println("Acceso completo construido: " + fullAccess);

        for (ParseTree child : ctx.children) {
            if (child instanceof BythonParser.PropertyAccessContext || child instanceof BythonParser.ChainedMethodCallContext) {
                fullAccess.append(child.getText()); 
            }
        }

        System.out.println("Acceso completo construido: " + fullAccess);
        contarAccesoProperty(fullAccess.toString()); 
        return null; 
    }

    
    /**
     * Cuenta el acceso a una propiedad.
     * @param fullAccess El acceso completo a la propiedad.
     */
    private void contarAccesoProperty(String fullAccess) {
        String[] parts = fullAccess.split("\\."); 
    
        if (parts.length > 2 && parts[0].equals("self")) {
            String firstLevelVar = parts[1];  
    
            int newCount = attributeAccessCount.getOrDefault(firstLevelVar, 0) + 1;
            attributeAccessCount.put(firstLevelVar, newCount);
    
            if (newCount > featureEnvyLimit) {
                crearAroma(firstLevelVar, fullAccess);
            }
        } 

       

        else if (parts.length > 1 && !parts[0].equals("self")) {
            String paramName = parts[0];  
    
            int newCount = attributeAccessCount.getOrDefault(paramName, 0) + 1;
            attributeAccessCount.put(paramName, newCount);
    
            if (newCount > featureEnvyLimit) {
                crearAroma(paramName, fullAccess);
            }
        }
    }

    /**
     * Crea un aroma si el acceso excede el límite.
     * @param accessType El tipo de acceso.
     * @param fullAccess El acceso completo.
     */
    private void crearAroma(String accessType, String fullAccess) {
        report.addAroma(new Aroma(this.callerName, 
            "Feature envy detected on " + accessType + ": " + fullAccess, true));
    }

    /**
     * Calcula la profundidad total de una expresión encadenada.
     * @param ctx El contexto de la expresión encadenada.
     * @return La profundidad total.
     */
    public int calculateDepth(BythonParser.ChainedExpressionContext ctx) {
        return ctx.chainedMethodCall().size() + ctx.propertyAccess().size();
    }
}