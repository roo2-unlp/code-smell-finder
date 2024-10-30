package oo2.redictado.DataClassSniffer;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import oo2.redictado.Aroma;
import oo2.redictado.AromaReport;
import oo2.redictado.CodeSniffer;
import oo2.redictado.antlr4.BythonLexer;
import oo2.redictado.antlr4.BythonParser;

public class DataClassSniffer implements CodeSniffer {
    @Override
    public void sniff(String code, AromaReport report) {
        // Crea el parser para Bython
        CharStream stream = CharStreams.fromString(code);
        BythonLexer lexer = new BythonLexer(stream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        BythonParser parser = new BythonParser(tokens);

        // Verifica errores de sintaxis
        ParseTree tree = parser.program();
        if (parser.getNumberOfSyntaxErrors() > 0) {
            throw new IllegalArgumentException("Error de sintaxis");
        }

        // Visita el árbol de análisis para verificar el patrón Data Class
        DataClassSnifferVisitor visitor = new DataClassSnifferVisitor(report, "DataClassSniffer");
        visitor.visit(tree);

        // Agrega un aroma si la clase no tiene mal olor
        if (!report.stinks()) {
            report.addAroma(new Aroma("DataClassSniffer", "No se detectaron Data Classes", false));
        }
    }
}
