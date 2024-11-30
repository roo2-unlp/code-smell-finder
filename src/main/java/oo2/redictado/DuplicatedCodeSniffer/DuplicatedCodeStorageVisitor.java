package oo2.redictado.DuplicatedCodeSniffer;

import java.util.ArrayList;
import java.util.List;

import oo2.redictado.Aroma;
import oo2.redictado.AromaReport;
import oo2.redictado.antlr4.BythonParser;
import oo2.redictado.antlr4.BythonParser.ClassMemberContext;
import oo2.redictado.antlr4.BythonParser.MethodDeclContext;
import oo2.redictado.antlr4.BythonParser.StatementContext;
import oo2.redictado.antlr4.BythonParserBaseVisitor;

public class DuplicatedCodeStorageVisitor extends BythonParserBaseVisitor<Void> {
    private DuplicateCodeFinderVisitor finderVisitor;
    private BythonParser.ProgramContext rootNode;
    private AromaReport report;
    private StringBuilder scripts = new StringBuilder();
    private List<StringBuilder> asignaciones = new ArrayList<>();
    private List<BythonParser.BlockContext> bloques = new ArrayList<>();
    private String callerName;

    public DuplicatedCodeStorageVisitor(AromaReport report, String callerName) {
        super();
        this.report = report;
        this.callerName = callerName;
    }

    // recolecto todos los bloques de codigo pertenecientes a metodos
    @Override
    public Void visitBlock(BythonParser.BlockContext ctx) {
        if ((ctx.depth() < 4) || (ctx.getParent() instanceof MethodDeclContext)) {
            bloques.add(ctx);
        }
        return visitChildren(ctx);
    }

    // recolecto todos las lineas de codigo que no pertenezcan a una clase o metodo
    public Void visitProgram(BythonParser.ProgramContext ctx) {
        this.rootNode = ctx;
        scripts.append(ctx.statement().stream().map(StatementContext::getText).reduce("{", (acc, stmt) -> acc + stmt) + "}");
        return visitChildren(ctx);
    }
    
    // recolecto todas las lineas de codigo que pertenezcan a una clase
    public Void visitClassDecl(BythonParser.ClassDeclContext ctx) {
        StringBuilder aux = new StringBuilder();  
        aux.append("{");
        for (ClassMemberContext children : ctx.classMember()) {
            if (!(children.getChild(0) instanceof MethodDeclContext)) {
                aux.append(children.getText());    
            }
        }
        aux.append("}");
        this.asignaciones.add(aux);
        return visitChildren(ctx);
    }

    // le digo al finder que compare mis bloques de codigo recolectados con otros bloques de codigo, asignaciones y scripts
    public void analyze() {
        this.compareAssignmentsWithScripts();
        for (BythonParser.BlockContext bloque : bloques) {
            this.finderVisitor = new DuplicateCodeFinderVisitor(this.report, scripts, asignaciones, bloque);
            this.finderVisitor.visit(this.rootNode);
        }
    }

    // comparo asignaciones con scripts
    private void compareAssignmentsWithScripts() {
        for (StringBuilder asignacion : asignaciones) {
            if (scripts.toString().contains(asignacion.toString()) && !asignacion.toString().equals("{}")) {
                //System.out.println("Duplicated code found between assignments and scripts");
                this.report.addAroma(new Aroma("duplicated code", "The code has duplicated code.", true));
            }
        }
    }
    
}
