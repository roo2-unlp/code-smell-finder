package oo2.redictado.DuplicatedCodeSniffer;

import oo2.redictado.antlr4.BythonParser;
import oo2.redictado.antlr4.BythonParserBaseVisitor;

import java.util.ArrayList;
import java.util.List;


public class DuplicatedCodeVisitor extends BythonParserBaseVisitor<Void> {
    private List<String> elementos;
    private StringBuilder scripts = new StringBuilder();
    private int contadorScripts = 0;

    public DuplicatedCodeVisitor() {
        super();
        this.elementos = new ArrayList<>();
    }

    @Override
    public Void visitBlock(BythonParser.BlockContext ctx) {
        // guardo cuerpo de metodos

        this.elementos.add(ctx.getText());
        return visitChildren(ctx);
    }

   
    public Void visitStatement(BythonParser.StatementContext ctx) {
        // Si no es un script, no lo guardo y continuo recorrido
        if (!(ctx.getParent() instanceof BythonParser.ProgramContext)) {
            return visitChildren(ctx);
        }
        
        // Guardo las lineas
        scripts.append(ctx.getText()).append(" ");
        contadorScripts++;

        // Si ya tengo 4 lineas, guardo en la lista y reseteo el contador
        if (contadorScripts == 4) {
            elementos.add(scripts.toString().trim());
            scripts.setLength(0); // Resetea el acumulador
            contadorScripts = 0;
        }

        return visitChildren(ctx);
    }
    
    public Void visitMethodParamList(BythonParser.MethodParamListContext ctx){
        elementos.add(ctx.getText());
        return visitChildren(ctx);
    }

    public void getElementos() {
        System.out.print("Contenido de métodos y scripts: " + "\n");
        elementos.forEach(name -> System.out.print("Línea: " + name + "\n"));
    }
    
}
