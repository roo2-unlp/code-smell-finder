package oo2.redictado.DuplicatedCodeSniffer;

import oo2.redictado.antlr4.BythonParser;
import oo2.redictado.antlr4.BythonParserBaseVisitor;

public class DuplicateCodeFinderVisitor extends BythonParserBaseVisitor<Void>{
    private String elemento;

    public DuplicateCodeFinderVisitor(String elemento) {
        super();
        this.elemento = elemento;
    }

    public Void visitBlock(BythonParser.BlockContext ctx) {
        if (ctx.getText().equals(elemento)) {
            System.out.println("Duplicated code found: " + elemento);
        }
        return visitChildren(ctx);
    }

}
