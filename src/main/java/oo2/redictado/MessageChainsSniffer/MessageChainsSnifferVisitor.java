package oo2.redictado.MessageChainsSniffer;

import oo2.redictado.Aroma;
import oo2.redictado.AromaReport;
import oo2.redictado.antlr4.BythonParser;
import oo2.redictado.antlr4.BythonParserBaseVisitor;

public class MessageChainsSnifferVisitor extends BythonParserBaseVisitor<Void> {
    private AromaReport report;
    private String callerName;
    private int chainThreshold;

    public MessageChainsSnifferVisitor(AromaReport report, String callerName) {
        super();
        this.report = report;
        this.callerName = callerName;
        this.chainThreshold = 3; //El limite que establecemos para detectar el bad smell
    }

    @Override
    public Void visitMethodCall(BythonParser.MethodCallContext ctx) {
        int depth = 1;
        BythonParser.MethodCallContext current = ctx;
        
        while (current.parent instanceof BythonParser.MethodCallContext) {
            depth++;
            current = (BythonParser.MethodCallContext) current.parent;
        }

        if (depth >= chainThreshold) {
            report.addAroma(new Aroma(this.callerName, "Detectó una cadena de mensajes de longitud " + depth, true));
        }

        return visitChildren(ctx);
    }
}
