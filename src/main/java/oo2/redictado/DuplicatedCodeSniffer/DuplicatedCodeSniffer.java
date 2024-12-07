package oo2.redictado.DuplicatedCodeSniffer;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import oo2.redictado.Aroma;
import oo2.redictado.AromaReport;
import oo2.redictado.CodeSniffer;
import oo2.redictado.antlr4.BythonLexer;
import oo2.redictado.antlr4.BythonParser;

/**
 * Clase que implementa la interfaz {@link CodeSniffer} para detectar código duplicado
 * en un fragmento de código fuente escrito en el lenguaje Bython.
 * Utiliza ANTLR para analizar el código y busca bloques de código duplicado.
 */
public class DuplicatedCodeSniffer implements CodeSniffer {

    /**
     * Analiza el código fuente en busca de bloques, codigo plano y asignaciones duplicadas y genera un reporte de aromas.
     * Si se detecta código duplicado, se registra un aroma en el reporte.
     * Si no se encuentra duplicado, se añade un aroma indicando que el código no tiene problemas.
     *
     * @param code el código fuente a analizar, representado como una cadena de texto.
     * @param report el reporte donde se añadirán los aromas detectados.
     * @throws IllegalArgumentException si el código contiene errores de sintaxis.
     */
    @Override
    public void sniff(String code, AromaReport report) {
        CharStream stream = CharStreams.fromString(code);
        BythonLexer lexer = new BythonLexer(stream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        BythonParser parser = new BythonParser(tokens);

        ParseTree tree = parser.program();

        if (parser.getNumberOfSyntaxErrors() > 0) {
            throw new IllegalArgumentException("Syntax error");
        }

        DuplicatedCodeVisitor visitor = new DuplicatedCodeVisitor(report, "DuplicatedCodeSniffer");
        visitor.visit(tree);

        if (!report.stinks()) {
            report.addAroma(new Aroma("DuplicatedCodeSniffer", "El código está bien", false));
        }
    }
}
