package oo2.redictado.SwitchStatementSniffer.SE;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import oo2.redictado.Aroma;
import oo2.redictado.AromaReport;
import oo2.redictado.CodeSniffer;
import oo2.redictado.antlr4.BythonLexer;
import oo2.redictado.antlr4.BythonParser;

public class SwitchStatementSniffer implements CodeSniffer {
    public void sniff(String code, AromaReport report) {
        // Crea el lexer y parser
        CharStream stream = CharStreams.fromString(code);
        BythonLexer lexer = new BythonLexer(stream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        BythonParser parser = new BythonParser(tokens);

        // Verifica errores de sintaxis
        ParseTree tree = parser.program();
        if (parser.getNumberOfSyntaxErrors() > 0) {
            throw new IllegalArgumentException("Syntax error");
        }

        // Ejecuta el visitante que detecta el Switch Statement
        SwitchStatementSnifferVisitor visitor = new SwitchStatementSnifferVisitor(report, "SwitchStatementSniffer");
        visitor.visit(tree);

        // Si no hay malos olores, añade un mensaje indicando que el código está limpio
        //if (!report.stinks()) {
         //   report.addAroma(new Aroma("SwitchStatementSniffer", "The code smells good.", false));
       // }
    }
}


