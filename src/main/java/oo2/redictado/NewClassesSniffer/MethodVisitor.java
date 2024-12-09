package oo2.redictado.NewClassesSniffer;

import java.util.ArrayList;

import oo2.redictado.antlr4.BythonParser;
import oo2.redictado.antlr4.BythonParserBaseVisitor;

public class MethodVisitor extends BythonParserBaseVisitor<MethodInterface> {

    @Override
    public MethodInterface visitMethodDecl(BythonParser.MethodDeclContext ctx) {
        if (ctx == null) {
            return null;
        }

        String methodName = ctx.ID().getText();

        ParameterVisitor parameterVisitor = new ParameterVisitor();
        ArrayList<String> parameters = ctx.methodParamList() != null
                ? parameterVisitor.visit(ctx.methodParamList())
                : new ArrayList<>(); // Si no hay parámetros, usa una lista vacía.

        if (parameters == null) {
            return null;
        }

        return new MethodInterface(methodName, parameters);
    }
}
