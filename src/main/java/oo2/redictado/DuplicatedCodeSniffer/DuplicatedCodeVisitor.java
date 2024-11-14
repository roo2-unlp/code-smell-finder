package oo2.redictado.DuplicatedCodeSniffer;

import oo2.redictado.antlr4.BythonParser;
import oo2.redictado.antlr4.BythonParserBaseVisitor;

import java.util.ArrayList;
import java.util.List;


public class DuplicatedCodeVisitor extends BythonParserBaseVisitor<Void> {
    private List<String> methodNames;

    public DuplicatedCodeVisitor() {
        super();
        this.methodNames = new ArrayList<>();
    }

    @Override
    public Void visitMethodDecl(BythonParser.MethodDeclContext ctx) {
        // Capturamos el nombre del método
        

        // Capturamos el cuerpo del método como un ParseTree
        this.methodNames.add(ctx.ID().getText());

        return visitChildren(ctx);
    }

    public void getMethodNames() {
        System.out.println("Method names:");
        methodNames.forEach(name -> System.out.print(name));
    }
    
}
