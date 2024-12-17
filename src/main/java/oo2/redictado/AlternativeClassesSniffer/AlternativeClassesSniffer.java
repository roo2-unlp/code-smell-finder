package oo2.redictado.AlternativeClassesSniffer;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import oo2.redictado.Aroma;
import oo2.redictado.AromaReport;
import oo2.redictado.CodeSniffer;
import oo2.redictado.antlr4.BythonLexer;
import oo2.redictado.antlr4.BythonParser;

public class AlternativeClassesSniffer implements CodeSniffer {

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

        
        AlternativeClassesVisitor visitor = new AlternativeClassesVisitor(report);
        visitor.visit(tree);

        if (!report.stinks()) {
            report.addAroma(new Aroma("AlternativeClassesSniffer", "No alternative classes with different interfaces detected.", false));
        }
    }
}
