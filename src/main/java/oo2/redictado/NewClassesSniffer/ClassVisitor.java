package oo2.redictado.NewClassesSniffer;

import java.util.ArrayList;
import java.util.List;

import oo2.redictado.Aroma;
import oo2.redictado.AromaReport;
import oo2.redictado.antlr4.BythonParser;
import oo2.redictado.antlr4.BythonParserBaseVisitor;

public class ClassVisitor extends BythonParserBaseVisitor<Boolean> {
    private final AromaReport report;
    private final List<ClassInterface> classInterfaces = new ArrayList<>();

    public ClassVisitor(AromaReport report) {
        this.report = report;
    }

    @Override
    public Boolean visitClassDecl(BythonParser.ClassDeclContext ctx) {
        MethodVisitor methodVisitor = new MethodVisitor();
        String className = ctx.ID().getText();
        ClassInterface classInterface = new ClassInterface(className);

        boolean valid = ctx.classMember().stream().allMatch(methodDecl -> {
            MethodInterface methodInterface = methodVisitor.visit(methodDecl);
            return methodInterface != null && classInterface.addMethod(methodInterface);
        });

        if (!valid) {
            report.addAroma(new Aroma(
                "AlternativeClassesSniffer",
                "Discrepancy detected in class: " + className,
                true
            ));
            return false;
        }

        // Compara la nueva clase con las existentes
        for (ClassInterface otherClass : classInterfaces) {
            if (!classInterface.hasSameInterfaceAs(otherClass)) {
                report.addAroma(new Aroma(
                    "AlternativeClassesSniffer",
                    "Alternative classes with different interfaces detected between "
                        + className + " and " + otherClass.getName(),
                    true
                ));
                return false;
            }
        }

        classInterfaces.add(classInterface);
        return true;
    }
}
