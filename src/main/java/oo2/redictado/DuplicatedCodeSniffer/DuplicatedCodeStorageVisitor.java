package oo2.redictado.DuplicatedCodeSniffer;

import oo2.redictado.antlr4.BythonParser;
import oo2.redictado.antlr4.BythonParserBaseVisitor;

import java.util.ArrayList;
import java.util.List;


public class DuplicatedCodeStorageVisitor extends BythonParserBaseVisitor<Void> {
    private String elemento;
    private DuplicateCodeFinderVisitor duplicateCodeFinderVisitor;

    public DuplicatedCodeStorageVisitor() {
        super();
    }

    @Override
    public Void visitBlock(BythonParser.BlockContext ctx) {
        this.elemento = ctx.getText();
        System.out.print("elemento: " + "\n");
        System.out.println(elemento);
        duplicateCodeFinderVisitor = new DuplicateCodeFinderVisitor(elemento);
        duplicateCodeFinderVisitor.visitBlock(ctx);
        
        return visitChildren(ctx);
    }

   
    public Void visitProgram(BythonParser.StatementContext ctx) {
        return visitChildren(ctx);
    }
    

    public void getElementos() {
        System.out.print("metodo donde se para el visitor: " + "\n");
        System.out.println(elemento);
    }
    
}
