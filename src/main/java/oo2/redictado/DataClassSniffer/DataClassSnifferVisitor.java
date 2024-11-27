package oo2.redictado.DataClassSniffer;

import oo2.redictado.Aroma;
import oo2.redictado.AromaReport;
import oo2.redictado.antlr4.BythonParserBaseVisitor;
import oo2.redictado.antlr4.BythonParser;

/**
 * Sniffer que detecta Data Classes en el código.
 */
public class DataClassSnifferVisitor extends BythonParserBaseVisitor<Void> {
    private final AromaReport report;
    private final String snifferName;

    // Declarar las variables de instancia
    private boolean hasAttributes = false;
    private boolean hasConstructor = false;
    private boolean hasLogicMethods = false;

    public DataClassSnifferVisitor(AromaReport report, String snifferName) {
        super();
        this.report = report;
        this.snifferName = snifferName;
    }

    @Override
    public Void visitClassDecl(BythonParser.ClassDeclContext ctx) {
        // Verifica que la clase y sus miembros sean válidos
        if (ctx == null || ctx.classMember() == null || ctx.ID() == null) {
            return null; // Ignora clases no válidas
        }

        // Reiniciar variables para cada clase
        hasAttributes = false;
        hasConstructor = false;
        hasLogicMethods = false;

        // Visita recursivamente los hijos de classDecl, incluyendo los miembros de la clase
        visitChildren(ctx);

        // En este punto, visitChildren(ctx) ya habrá recorrido los miembros de la clase

        System.out.println("Visiting class: " + ctx.ID().getText());
        System.out.println("Attributes: " + hasAttributes + ", Constructor: " + hasConstructor + ", Logic Methods: " + hasLogicMethods);

        // Detecta si es una Data Class
        // Si no tiene lógica y tiene atributos o un constructor, lo marca como Data Class
        if (!hasLogicMethods && (hasAttributes || hasConstructor)) {
            report.addAroma(new Aroma(
                snifferName,
                "Data Class detected in class " + ctx.ID().getText(),
                true
            ));
        }

        return null; // No hace falta llamar a visitChildren(ctx) aquí, ya que ya lo hemos hecho antes
    }

    /**
     * Verifica si un método contiene lógica (declaraciones como if, while, operaciones, etc.).
     */
    @Override
    public Void visitMethodDecl(BythonParser.MethodDeclContext ctx) {
        // Verifica si se debe continuar recorriendo nodos
        if (!shouldContinue()) {
            return null; // No es necesario seguir analizando los métodos
        }

        // Lógica para detectar métodos de inicialización o que contengan lógica
        String methodName = ctx.ID().getText();

        if (methodName.equals("__init__")) {
            hasConstructor = true;
        } else {
            if (hasMethodLogic(ctx)) {
                hasLogicMethods = true;
                return null; // Salir temprano, no es necesario seguir analizando el método
            }
        }

        return null; // Finaliza la visita al método
    }

    @Override
    public Void visitSimpleAssignment(BythonParser.SimpleAssignmentContext ctx) {
        // Verifica si se debe continuar recorriendo nodos
        if (!shouldContinue()) {
            return null; // No es necesario seguir analizando las asignaciones
        }

        // Detecta asignaciones simples (atributos de clase)
        hasAttributes = true;
        return null; // Finaliza la visita a la asignación simple
    }

    /**
     * Verifica si un método contiene lógica (declaraciones como if, while, operaciones, etc.).
     */
    // private boolean hasMethodLogic(BythonParser.MethodDeclContext methodDecl) {
    //     if (methodDecl.block() != null) {
    //         for (BythonParser.StatementContext statement : methodDecl.block().statement()) {
    //             if (statement.ifStatement() != null ||
    //                 statement.whileStatement() != null ||
    //                 statement.forStatement() != null ||
    //                 statement.returnStatement() != null ||
    //                 statement.expression() != null) {
    //                 return true; // El método contiene lógica
    //             }
    //         }
    //     }
    //     return false; // No tiene lógica
    // }

    private boolean hasMethodLogic(BythonParser.MethodDeclContext methodDecl) {
        MethodLogicVisitor logicVisitor = new MethodLogicVisitor();
        methodDecl.accept(logicVisitor); // Recorre todo el método
        return logicVisitor.hasLogic();  // Devuelve si encontró lógica
    }
    

    /**
        * Verifica si se debe continuar recorriendo nodos.
    */
    private boolean shouldContinue() {
           return !hasLogicMethods;
    }

}
