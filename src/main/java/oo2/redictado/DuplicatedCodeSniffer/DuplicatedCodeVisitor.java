package oo2.redictado.DuplicatedCodeSniffer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import oo2.redictado.Aroma;
import oo2.redictado.AromaReport;
import oo2.redictado.antlr4.BythonParser;
import oo2.redictado.antlr4.BythonParser.ClassMemberContext;
import oo2.redictado.antlr4.BythonParser.MethodDeclContext;
import oo2.redictado.antlr4.BythonParser.StatementContext;
import oo2.redictado.antlr4.BythonParserBaseVisitor;

/**
 * {@code DuplicatedCodeVisitor} es una clase que extiende {@link BythonParserBaseVisitor}
 * para recorrer el Árbol de Sintaxis Abstracta (AST) generado por {@link BythonParser}
 * e identificar bloques, cuerpos de métodos y funciones o asignaciones duplicadas en el código fuente analizado.
 */
public class DuplicatedCodeVisitor extends BythonParserBaseVisitor<Void> {

    private AromaReport report;
    private StringBuilder codigoPlano = new StringBuilder();
    private List<String> asignaciones = new ArrayList<>();
    private List<String> bloques = new ArrayList<>();
    private String callerName;

    /**
     * Constructor de la clase {@code DuplicatedCodeVisitor}.
     *
     * @param report     el objeto {@link AromaReport} donde se almacenarán los problemas detectados.
     * @param callerName el nombre de la entidad que inicia el análisis.
     */
    public DuplicatedCodeVisitor(AromaReport report, String callerName) {
        super();
        this.report = report;
        this.callerName = callerName;
    }

    /**
     * Visita el nodo "program" del árbol de sintaxis y analiza las declaraciones.
     *
     * @param ctx el contexto del programa a analizar.
     * @return el resultado de {@code visitChildren(ctx)}.
     */
    @Override
    public Void visitProgram(BythonParser.ProgramContext ctx) {
        this.codigoPlano.append(ctx.statement().stream().map(StatementContext::getText).reduce("{", (acc, stmt) -> acc + stmt) + "}");
        this.codigoPlano = this.codigoPlano.toString().equals("{}") ? new StringBuilder() : this.codigoPlano;
        Void result = visitChildren(ctx);

        for (String asignacion : this.asignaciones) {
            boolean match = this.bloques.stream().anyMatch(bloque -> bloque.equals(asignacion));
            if (match) {
                this.report.addAroma(new Aroma("duplicated code", "El código contiene duplicados.", true));
            }
        }

        return result;
    }
    
    /**
     * Visita el nodo "classDecl" del árbol de sintaxis y analiza sus miembros.
     *
     * @param ctx el contexto de la declaración de clase a analizar.
     * @return el resultado de {@code visitChildren(ctx)}.
     */
    @Override
    public Void visitClassDecl(BythonParser.ClassDeclContext ctx) {
        StringBuilder aux = this.generarAsignacion(ctx);
        if (aux != null) {
            this.comparar(aux.toString(), this.asignaciones);
        }
        return visitChildren(ctx);
    }

    /**
     * Visita una declaración de método y verifica si el bloque de código asociado es duplicado.
     *
     * @param ctx el contexto de la declaración del método.
     * @return el resultado de {@code visitChildren(ctx)}.
     */
    @Override
    public Void visitMethodDecl(MethodDeclContext ctx) {
        this.comparar(ctx.block().getText(), this.bloques);
        return visitChildren(ctx);
    }

    /**
     * Visita una declaración de función y verifica si el bloque de código asociado es duplicado.
     *
     * @param ctx el contexto de la declaración de la función.
     * @return el resultado de {@code visitChildren(ctx)}.
     */
    @Override
    public Void visitFunctionDecl(BythonParser.FunctionDeclContext ctx) {
        this.comparar(ctx.block().getText(), this.bloques);
        return visitChildren(ctx);
    }

    /**
     * Compara un bloque de código con una lista de bloques para detectar duplicados.
     *
     * @param comparado       el bloque de código a comparar.
     * @param comparaciones   la lista de bloques existentes para comparar.
     */
    private void comparar(String comparado, List<String> comparaciones) {
        boolean isDupled = comparaciones.stream().anyMatch(bloque -> comparado.equals(bloque.toString()));
        if (!isDupled && !comparado.equals("{}")) {
            comparaciones.add(comparado);
        }

        if (isDupled || comparado.equals(this.codigoPlano.toString())) {
            this.report.addAroma(new Aroma("duplicated code", "El código contiene duplicados.", true));
        }
    }

    /**
     * Genera un bloque de asignaciones a partir de los miembros de una clase.
     *
     * @param ctx el contexto de la declaración de clase a analizar.
     * @return un {@link StringBuilder} con las asignaciones, o {@code null} si no hay asignaciones.
     */
    private StringBuilder generarAsignacion(BythonParser.ClassDeclContext ctx) {
        String result = ctx.classMember().stream()
            .filter(children -> children.simpleAssignment() != null)
            .map(ClassMemberContext::getText)
            .collect(Collectors.joining("", "{", "}"));

        return !result.equals("{}") ? new StringBuilder(result) : null;
    }
}
