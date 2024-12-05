package oo2.redictado.FeatureEnvySniffer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.antlr.v4.runtime.tree.ParseTree;

import oo2.redictado.Aroma;
import oo2.redictado.AromaReport;
import oo2.redictado.antlr4.BythonParser;
import oo2.redictado.antlr4.BythonParser.MethodParamListContext;
import oo2.redictado.antlr4.BythonParserBaseVisitor;

public class ClassDeclVisitor extends BythonParserBaseVisitor<Void> {
    private AromaReport report;
    private String callerName;
    
    /**
     * Constructor de ClassDeclVisitor
     * @param report El reporte de aromas donde se registrarán los malos olores.
     * @param callerName El nombre del visitante.
     */
    public ClassDeclVisitor(AromaReport report, String callerName) {
        super();
        this.report = report;
        this.callerName = callerName;
      
    }

    /**
     * Visita la declaración de una clase.
     * @param ctx El contexto de la declaración de la clase.
     * @return null
     */
    @Override
    public Void visitClassDecl(BythonParser.ClassDeclContext ctx) {
       var visitor = new FeatureEnvyVisitor (report, "FeatureEnvySniffer");
       visitor.visit(ctx);
       return visitChildren(ctx);
    }

   
}