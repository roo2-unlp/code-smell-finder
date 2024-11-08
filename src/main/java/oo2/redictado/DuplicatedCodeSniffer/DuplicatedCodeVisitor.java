package oo2.redictado.DuplicatedCodeSniffer;

import oo2.redictado.antlr4.BythonParser;
import oo2.redictado.antlr4.BythonParserBaseVisitor;

public class DuplicatedCodeVisitor extends BythonParserBaseVisitor<Boolean> {
    @Override
    public Boolean visitProgram(BythonParser.ProgramContext ctx) {
        return false;
    }
    
}
