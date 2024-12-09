package oo2.redictado.NewClassesSniffer;

import oo2.redictado.Aroma;
import oo2.redictado.AromaReport;
import oo2.redictado.antlr4.BythonParser;
import oo2.redictado.antlr4.BythonParserBaseVisitor;

public class NewAlternativeClassesVisitor extends BythonParserBaseVisitor<Boolean> {
    private final AromaReport report;

    public NewAlternativeClassesVisitor(AromaReport report) {
        this.report = report;
    }

    @Override
    public Boolean visitProgram(BythonParser.ProgramContext ctx) {
        ClassVisitor classVisitor = new ClassVisitor(report);
        boolean valid = ctx.classDecl().stream().allMatch(classDecl -> classVisitor.visit(classDecl));

        if (valid) {
            report.addAroma(new Aroma(
                "AlternativeClassesSniffer",
                "No alternative classes with different interfaces detected.",
                false
            ));
        }

        return valid;
    }
}
