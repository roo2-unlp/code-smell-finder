package oo2.redictado.ReturnNullSniffer;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import oo2.redictado.Aroma;
import oo2.redictado.AromaReport;
import oo2.redictado.CodeSniffer;
import oo2.redictado.PrintSniffer.PrintSnifferVisitor;
import oo2.redictado.antlr4.BythonLexer;
import oo2.redictado.antlr4.BythonParser;

import java.util.List;

public class DontReturnNullSniffer implements CodeSniffer {
	
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

        DontReturnNullSnifferVisitor visitor = new DontReturnNullSnifferVisitor(report, "DontReturnNullSniffer");

        visitor.visit(tree);

        if (!report.stinks()) {
            report.addAroma(new Aroma("DontReturnNullSniffer", "The code smells good", false));
        }
    }
}
