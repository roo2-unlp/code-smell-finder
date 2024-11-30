package oo2.redictado.DuplicatedCodeSniffer;

import java.util.List;

import oo2.redictado.Aroma;
import oo2.redictado.AromaReport;
import oo2.redictado.antlr4.BythonParser;
import oo2.redictado.antlr4.BythonParserBaseVisitor;
public class DuplicateCodeFinderVisitor extends BythonParserBaseVisitor<Void>{
    
    private StringBuilder scripts;
    private List<StringBuilder> asignaciones;
    private BythonParser.BlockContext nodo;
    AromaReport report;

    public DuplicateCodeFinderVisitor(AromaReport report, StringBuilder scripts, List<StringBuilder> asignaciones, BythonParser.BlockContext nodo) {
        this.scripts = scripts;
        this.asignaciones = asignaciones;
        this.nodo = nodo;
        this.report = report;
    }

    // visito bloques de codigo para compararlos con asignaciones, scripts y otros bloques de codigo
    @Override
    public Void visitBlock(BythonParser.BlockContext ctx) {
        if ((nodo != ctx) && (ctx.getText().equals(nodo.getText()))) {
            //System.out.println("Duplicated code found");
            this.report.addAroma(new Aroma("duplicated code", "The code has duplicated code.", true));
        }
        if (ctx.getText().equals(scripts.toString())) {
            //System.out.println("Duplicated code found");
            this.report.addAroma(new Aroma("duplicated code", "The code has duplicated code.", true));
        }
        for (StringBuilder asignacion : asignaciones) {
            if (ctx.getText().equals(asignacion.toString())) {
                //System.out.println("Duplicated code found");
                this.report.addAroma(new Aroma("duplicated code", "The code has duplicated code.", true));
            }
        }
        return visitChildren(ctx);
    }



  
   


}
