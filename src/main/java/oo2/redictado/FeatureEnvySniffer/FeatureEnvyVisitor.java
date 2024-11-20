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


    @Override
    public Void visitPropertyAccess(BythonParser.PropertyAccessContext ctx) {
        String fullAccess = ctx.getText(); // E.g., self.cliente.suscripcion
        System.out.println("visitPropertyAccess: " + fullAccess);

        if (fullAccess.contains(".")) {
            String[] parts = fullAccess.split("\\.");
            if (parts[0].equals("self") && parts.length > 1) {
                String firstLevelVar = parts[1];
                if (!instanceVariables.contains(firstLevelVar)) {
                    // Ignorar variables internas simples
                    return visitChildren(ctx);
                }
                contarAccesoExterno(firstLevelVar, null); // Registrar solo el nivel base
            } else if (parts.length > 0) {
                contarAccesoParametro(parts[0], parts);
            }

        }
        return visitChildren(ctx);
    }


    private void contarAccesoExterno(String instanceVar, String externalAttr) {
        // Registrar solo la variable de primer nivel
        attributeAccessCount.put(instanceVar, attributeAccessCount.getOrDefault(instanceVar, 0) + 1);
        System.out.println("contarAccesoExterno: " + instanceVar + " count: " + attributeAccessCount.get(instanceVar));
    
        // Detectar si excede el límite
        if (attributeAccessCount.get(instanceVar) > featureEnvyLimit) {
            report.addAroma(new Aroma(callerName, 
                "Feature envy detected on attribute: " + instanceVar, true));
        }

    }
    
    private void contarAccesoParametro(String paramName, String[] parts) {
        if (parts.length > 1) {
            String key = paramName + "." + parts[1]; // Propiedad específica, e.g., usuario.contactos
            attributeAccessCount.put(key, attributeAccessCount.getOrDefault(key, 0) + 1);
            System.out.println("contarAccesoParametro: " + key + " count: " + attributeAccessCount.get(key));
        
            // Detecta si el acceso a este atributo excede el límite
            if (attributeAccessCount.get(key) > featureEnvyLimit) { // Solo si EXCEDE el límite
                report.addAroma(new Aroma(callerName, 
                    "Feature envy detected on parameter: " + paramName + ", accessing: " + key, true));
            }
        }
    }
    
    @Override
    public Void visitMethodDecl(BythonParser.MethodDeclContext ctx) {
        String methodName = ctx.ID().getText();
        System.out.println("Visiting method: " + methodName);

        if (methodName.equals("_init_")) {
            System.out.println("Ignoring _init_ method.");
            return null; 
        }

        // Limpiamos los contadores antes de procesar cada nuevo método
        attributeAccessCount.clear();
        System.out.println("Resetting attribute access count for method: " + methodName);

        return visitChildren(ctx); // Continuamos con los hijos del método
    }







    @Override
    public Void visitClassDecl(BythonParser.ClassDeclContext ctx) {
        attributeAccessCount.clear();
        instanceVariables.clear();
        System.out.println("visitClassDecl: Reset attributeAccessCount and instanceVariables");
        return visitChildren(ctx);
    }


    public void crearAroma(int depth) {
        report.addAroma(new Aroma(this.callerName, "The code has feature envy with depth " + depth, true));
    }


    public  int calculateDepth(BythonParser.ChainedExpressionContext ctx) {
        return ctx.chainedMethodCall().size() + ctx.propertyAccess().size();
    }

}
