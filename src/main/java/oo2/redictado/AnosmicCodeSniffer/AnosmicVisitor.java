package oo2.redictado.AnosmicCodeSniffer;

import oo2.redictado.antlr4.BythonParserBaseVisitor;
import oo2.redictado.antlr4.BythonParser;

public class AnosmicVisitor extends BythonParserBaseVisitor<Boolean> {
    @Override
    public Boolean visitProgram(BythonParser.ProgramContext ctx) {
        return false;
    }
}
