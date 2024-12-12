package oo2.redictado.DuplicatedCodeSniffer;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.misc.NotNull;

import oo2.redictado.Aroma;
import oo2.redictado.AromaReport;
import oo2.redictado.antlr4.BythonParser;
import oo2.redictado.antlr4.BythonParser.ClassMemberContext;
import oo2.redictado.antlr4.BythonParser.MethodDeclContext;
import oo2.redictado.antlr4.BythonParser.StatementContext;
import oo2.redictado.antlr4.BythonParserBaseVisitor;

/**
 * DuplicatedCodeVisitor es una clase que extiende BythonParserBaseVisitor<Void>
 * recorre el Árbol de Sintaxis Abstracta (AST) generado por BythonParser
 * para identificar bloques, clases o asignaciones duplicadas.
 */
public class DuplicatedCodeVisitor extends BythonParserBaseVisitor<Void> {

    private AromaReport report;
    private StringBuilder codigoPlano = new StringBuilder();
    private List<String> asignaciones = new ArrayList<>();
    private List<String> bloques = new ArrayList<>();
    private String callerName;

    /**
     * Constructor de la clase DuplicatedCodeVisitor.
     *
     * @param report     objeto reporte de aromas donde se almacenarán los problemas detectados.
     * @param callerName el nombre de la entidad que inicia el análisis.
    */
    public DuplicatedCodeVisitor(AromaReport report, String callerName) {
        super();
        this.report = report;
        this.callerName = callerName;
    }

    @Override
    public Void visitMethodDecl(MethodDeclContext ctx) {
        comparar(ctx.block().getText(),bloques);
        return visitChildren(ctx);
    }

    @Override
    public Void visitFunctionDecl(BythonParser.FunctionDeclContext ctx){
        comparar(ctx.block().getText(), this.bloques);
        return visitChildren(ctx);
    }

    private void comparar(String comparado, List<String> comparaciones) {
        boolean isDupled = comparaciones.stream().anyMatch(bloque -> comparado.equals(bloque.toString()));
        if (!(isDupled)&&!(comparado.equals("{}"))) {
            comparaciones.add(comparado);
        }
    
        if (isDupled || comparado.equals(codigoPlano.toString())) {
            this.report.addAroma(new Aroma("duplicated code", "El código contiene duplicados.", true));
        }
    }

    /**
     * Visita el nodo "program" del árbol de sintaxis.
     * Convierte las lineas de codigo plano en un bloque y las analiza con asignaciones previamente almacenadas.
     *
     * @param ctx el contexto del programa a analizar.
     * @return el resultado de {@code visitChildren(ctx)}, que continúa el recorrido del árbol de sintaxis.
     */
    @Override
    public Void visitProgram(BythonParser.ProgramContext ctx) {
        codigoPlano.append(ctx.statement().stream().map(StatementContext::getText).reduce("{", (acc, stmt) -> acc + stmt) + "}");
        codigoPlano = codigoPlano.toString().equals("{}") ? new StringBuilder() : codigoPlano;
        Void result = visitChildren(ctx);

        for (String asignacion : asignaciones) {
            boolean match = bloques.stream().anyMatch(bloque -> bloque.equals(asignacion));
            if (match) {
                this.report.addAroma(new Aroma("duplicated code", "El código contiene duplicados.", true));
            }
        }
        
        return result;
    }

    /**
     * Visita el nodo "classDecl" del árbol de sintaxis.
     * Analiza los nodos hijos "classMember" para detectar asignaciones duplicadas.
     *
     * @param ctx el contexto de la declaración de clase a analizar.
     * @return el resultado de {@code visitChildren(ctx)}, que continúa el recorrido del árbol de sintaxis.
     */
    @Override
    public Void visitClassDecl(BythonParser.ClassDeclContext ctx) {
        StringBuilder aux = generarAsignacion(ctx);

        if (aux != null) {
            comparar(aux.toString(), asignaciones);
        }

        return visitChildren(ctx);
    }


    /**
     * Almacena las asignaciones de una clase en un StringBuilder.
     *
     * @param ctx el contexto de la declaración de clase a analizar.
     * @return un StringBuilder con las asignaciones, o null si no hay asignaciones.
     */
    private StringBuilder generarAsignacion(BythonParser.ClassDeclContext ctx) {
        StringBuilder aux = new StringBuilder();
        aux.append("{");
        for (ClassMemberContext children : ctx.classMember()) {
            if (children.simpleAssignment() != null) {
                aux.append(children.getText());
            }
        }
        aux.append("}");

        return !aux.toString().equals("{}") ? aux : null;
    }
}
