package oo2.redictado.PrintSniffer;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import oo2.redictado.Aroma;
import oo2.redictado.AromaReport;
import oo2.redictado.CodeSniffer;
import oo2.redictado.antlr4.BythonLexer;
import oo2.redictado.antlr4.BythonParser;

public class PrintSniffer implements CodeSniffer {
    public void sniff(String code, AromaReport report) {
        // Creates Bython Parser
        CharStream stream = CharStreams.fromString(code);
        BythonLexer lexer = new BythonLexer(stream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        BythonParser parser = new BythonParser(tokens);

        // Parses the code and checks for syntax errors
        ParseTree tree = parser.program();
        System.out.println(parser.getNumberOfSyntaxErrors());
        if (parser.getNumberOfSyntaxErrors() > 0) {
            throw new IllegalArgumentException("Syntax error");
        }

        // Visits the parse tree to check for bad smells
        PrintSnifferVisitor visitor = new PrintSnifferVisitor(report, "PrintSniffer");
        visitor.visit(tree);

        if (!report.stinks()) {
            report.addAroma(new Aroma("PrintSniffer", "The code smells good", false));
        }
    }
}
