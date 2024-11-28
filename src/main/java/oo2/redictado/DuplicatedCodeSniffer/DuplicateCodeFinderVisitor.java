package oo2.redictado.DuplicatedCodeSniffer;

import oo2.redictado.antlr4.BythonParser;
import oo2.redictado.antlr4.BythonParser.BlockContext;
import oo2.redictado.antlr4.BythonParserBaseVisitor;
public class DuplicateCodeFinderVisitor extends BythonParserBaseVisitor<Void>{
    private BlockContext elemento;

    public DuplicateCodeFinderVisitor(BlockContext elemento) {
        super();
        this.elemento = elemento;
    }
    @Override
    public Void visitBlock(BythonParser.BlockContext ctx) {
        if ((ctx != elemento ) && (ctx.getText().equals(elemento.getText()))){
            System.out.println("Duplicated code found: " + elemento);
        }
        return visitChildren(ctx);
    }

}
