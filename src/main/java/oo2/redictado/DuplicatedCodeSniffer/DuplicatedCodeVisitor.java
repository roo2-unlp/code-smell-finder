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

public class DuplicatedCodeVisitor extends BythonParserBaseVisitor<Void> { 
    private AromaReport report;
    private StringBuilder scripts = new StringBuilder();
    private List<StringBuilder> asignaciones = new ArrayList<>();
    private List<BythonParser.BlockContext> bloques = new ArrayList<>();

    public DuplicatedCodeVisitor(AromaReport report) {
        super();
        this.report = report;
    }
 
    @Override
    public Void visitBlock(BythonParser.BlockContext ctx) {
        if ((ctx.depth() < 4) || (ctx.getParent() instanceof MethodDeclContext)) {//bloque valido a comparar
           /*  if (bloques.isEmpty()) { //no hay nadie ne la lista me agrego
               bloques.add(ctx);
            }else{ */  
            Boolean isDuped= bloques.stream().anyMatch(bloque -> ctx.getText().equals(bloque.getText()));
            
            if (!isDuped) {
               bloques.add(ctx);
            }
                       
            if ((isDuped)||(ctx.getText().equals(scripts.toString()))) {
                System.out.println("hay duplicados");
               this.report.addAroma(new Aroma("duplicated code", "The code has duplicated code.", true));
            }
        }
        
        return visitChildren(ctx);
    }
 
    public Void visitProgram(BythonParser.ProgramContext ctx) { 
        //recolecto bloque de codigo plano hijo de program
        scripts.append(ctx.statement().stream().map(StatementContext::getText).reduce("{", (acc, stmt) -> acc + stmt) + "}");
        scripts= scripts.toString().equals("{}") ? new StringBuilder(): scripts; 
        Void result= visitChildren(ctx);
        System.out.println("tamaño de bloques: "+ bloques.size());
        System.out.println("tam de asig: "+ asignaciones.size());
        //comparo las asignaciones con los bloques
        System.out.println("tam reportes antes : "+ report.getAromas().size() );
        for (StringBuilder asignacion : asignaciones) {
            Boolean match= bloques.stream().anyMatch(bloque ->bloque.getText().equals(asignacion.toString()));
            if (match) {
               this.report.addAroma(new Aroma("duplicated code", "The code has duplicated code.", true));
            }
        }
        System.out.println("tam reportes desp: "+ report.getAromas().size() );
        return result;
    }
    
    public Void visitClassDecl(BythonParser.ClassDeclContext ctx) {
        //genero la asignacion
        StringBuilder aux=generarAsignacion(ctx);
        //evaluo las asignaciones 
        if (aux != null) { 
            Boolean isDuped= asignaciones.stream().anyMatch(asignacion ->aux.toString().equals(asignacion.toString()));
            
            if (!isDuped) {
            asignaciones.add(aux);
            }
            if ((isDuped)||(aux.toString().equals(scripts.toString()))) {
            this.report.addAroma(new Aroma("duplicated code", "The code has duplicated code.", true));
            }   
        }
        return visitChildren(ctx);
    }

    private StringBuilder generarAsignacion(BythonParser.ClassDeclContext ctx){
        StringBuilder aux = new StringBuilder();  
        aux.append("{");
        for (ClassMemberContext children : ctx.classMember()) {
            if (!(children.getChild(0) instanceof MethodDeclContext)) {
                aux.append(children.getText());    
            }
        }
        aux.append("}");
        return !aux.toString().equals("{}") ? aux : null;
    }
    
}
