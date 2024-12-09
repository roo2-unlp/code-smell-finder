package oo2.redictado.NewClassesSniffer;

import java.util.ArrayList;
import java.util.stream.Collectors;

import oo2.redictado.antlr4.BythonParser;
import oo2.redictado.antlr4.BythonParserBaseVisitor;

public class ParameterVisitor extends BythonParserBaseVisitor<ArrayList<String>> {

    @Override
    public ArrayList<String> visitMethodParamList(BythonParser.MethodParamListContext ctx) {
        return ctx.ID().stream()
                .map(param -> param.getText())
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
