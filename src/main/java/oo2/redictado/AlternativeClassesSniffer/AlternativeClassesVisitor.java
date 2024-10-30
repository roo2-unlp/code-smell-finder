package oo2.redictado.AlternativeClassesSniffer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oo2.redictado.Aroma;
import oo2.redictado.AromaReport;
import oo2.redictado.antlr4.BythonParser;
import oo2.redictado.antlr4.BythonParserBaseVisitor;

public class AlternativeClassesVisitor extends BythonParserBaseVisitor<Void> {
    private AromaReport report;
    private Map<String, List<String>> classMethods = new HashMap<>();

    public AlternativeClassesVisitor(AromaReport report) {
        this.report = report;
    }

    @Override
    public Void visitClassDecl(BythonParser.ClassDeclContext ctx) {
        String className = ctx.ID().getText();
        List<String> methods = ctx.classMember().stream()
                .filter(member -> member.methodDecl() != null)
                .map(member -> member.methodDecl().ID().getText())
                .toList();
        classMethods.put(className, methods);
        return visitChildren(ctx);
    }

    @Override
    public Void visitProgram(BythonParser.ProgramContext ctx) {
        super.visitProgram(ctx);

        // Comparar métodos entre clases para detectar métodos similares
        for (String classA : classMethods.keySet()) {
            for (String classB : classMethods.keySet()) {
                if (!classA.equals(classB)) {
                    List<String> methodsA = classMethods.get(classA);
                    List<String> methodsB = classMethods.get(classB);
                    
                    long similarMethodCount = methodsA.stream()
                            .filter(methodsB::contains)
                            .count();
                    
                    if (similarMethodCount >= 1) {  // Umbral de similitud
                        report.addAroma(new Aroma(
                                "AlternativeClassesSniffer",
                                "Similar methods detected in classes " + classA + " and " + classB,
                                true
                        ));
                    }
                }
            }
        }
        return null;
    }
}
