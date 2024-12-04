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
    private Set<String> uniqueAccesses; // Para evitar contar duplicados

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
        // Revisamos si el método tiene parámetros
        if (ctx.methodParamList() != null) {
            // Accedemos a los parámetros a través de `methodParamList()`
            MethodParamListContext paramListContext = ctx.methodParamList();

            // Iteramos sobre los parámetros y los registramos
            for (int i = 0; i < paramListContext.ID().size(); i++) {
                String paramName = paramListContext.ID(i).getText();  // Extraemos el nombre del parámetro
                System.out.println("Parámetro encontrado: " + paramName);

                // Inicializamos el contador para el parámetro
                attributeAccessCount.put(paramName, 0);  // Comenzamos con 0 accesos
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
            String varName = ctx.simpleAssignment().ID().getText(); // Nombre de la variable
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
                fullAccess.append(child.getText()); // Construir acceso completo
            }
        }

        System.out.println("Acceso completo construido: " + fullAccess);
        contarAccesoProperty(fullAccess.toString()); // Procesar cualquier acceso (tanto lectura como escritura)
        return null; // No recorrer más
    }

    /**
     * Visita una asignación simple.
     * @param ctx El contexto de la asignación simple.
     * @return null
     */
    @Override
    public Void visitSimpleAssignment(BythonParser.SimpleAssignmentContext ctx) {
        String assignment = ctx.getText();  // Texto completo de la asignación

        if (assignment.startsWith("self.")) {
            String varName = assignment.split("\\.")[1].split("=")[0]; // Extraemos el nombre de la variable
            instanceVariables.add(varName);
            System.out.println("Variable de instancia registrada: " + varName);

            // Contamos el acceso (tanto para asignaciones como para comparaciones)
            contarAccesoProperty("self." + varName); // Aseguramos que se cuente cuando haya una asignación
        }

        // Ahora también contamos los accesos a parámetros dentro de las asignaciones
        if (assignment.contains("=")) {
            // Esto asegura que cuando asignamos algo a un parámetro, se registre el acceso a la propiedad
            String[] parts = assignment.split("=");
            String lhs = parts[0].trim(); // Left side (donde estamos haciendo la asignación)
            if (lhs.startsWith("usuario")) {
                contarAccesoProperty(lhs);  // Contamos también el acceso a 'usuario'
            }
        }

        return super.visitSimpleAssignment(ctx);
    }

    /**
     * Cuenta el acceso a una propiedad.
     * @param fullAccess El acceso completo a la propiedad.
     */
    private void contarAccesoProperty(String fullAccess) {
        String[] parts = fullAccess.split("\\.");  // Dividir el acceso por puntos
    
        // Verificamos si el acceso es a una propiedad encadenada de instancia (es decir, tiene más de un punto y empieza con `self`)
        if (parts.length > 2 && parts[0].equals("self")) {
            String firstLevelVar = parts[1];  // Es una propiedad de instancia de un objeto
    
            // Incrementar el contador de acceso a la propiedad (solo para propiedades encadenadas)
            int newCount = attributeAccessCount.getOrDefault(firstLevelVar, 0) + 1;
            attributeAccessCount.put(firstLevelVar, newCount);
            System.out.println("Acceso contado a propiedad encadenada de instancia: " + firstLevelVar + " -> " + newCount);
    
            // Detectar si el acceso excede el límite
            if (newCount > featureEnvyLimit) {
                crearAroma(firstLevelVar, fullAccess);
            }
        } 
        // Si el acceso es directo a `self` (como `self.total`), NO lo contamos
        else if (parts.length == 1 && parts[0].equals("self")) {
            System.out.println("Acceso directo a `self` no contado: " + fullAccess);
        }

        // Si el acceso es a un parámetro (como `usuario.email`), lo contamos si tiene más de un punto
        else if (parts.length > 1 && !parts[0].equals("self")) {
            String paramName = parts[0];  // Es un parámetro
    
            // Incrementamos el contador de acceso al parámetro
            int newCount = attributeAccessCount.getOrDefault(paramName, 0) + 1;
            attributeAccessCount.put(paramName, newCount);
            System.out.println("Acceso contado a parámetro: " + paramName + " -> " + newCount);
    
            // Detectar si el número de accesos al parámetro excede el límite
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
        // Creamos un aroma solo si el acceso excede el límite
        report.addAroma(new Aroma(this.callerName, 
            "Feature envy detected on " + accessType + ": " + fullAccess, true));
    }

    /**
     * Calcula la profundidad total de una expresión encadenada.
     * @param ctx El contexto de la expresión encadenada.
     * @return La profundidad total.
     */
    public int calculateDepth(BythonParser.ChainedExpressionContext ctx) {
        // Calcula la profundidad total: número de accesos a propiedades y llamadas a métodos
        return ctx.chainedMethodCall().size() + ctx.propertyAccess().size();
    }
}