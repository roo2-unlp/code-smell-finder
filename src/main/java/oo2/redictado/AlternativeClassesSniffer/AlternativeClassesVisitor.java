package oo2.redictado.AlternativeClassesSniffer;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import oo2.redictado.Aroma;
import oo2.redictado.AromaReport;
import oo2.redictado.antlr4.BythonParser;
import oo2.redictado.antlr4.BythonParserBaseVisitor;

public class AlternativeClassesVisitor extends BythonParserBaseVisitor<Void> {
    private AromaReport report;
    private Map<String, Map<String, ArrayList<String>>> classMethods = new HashMap<>();
    private String currentClassName = null;
    private String currentMethodName = null;

    public AlternativeClassesVisitor(AromaReport report) {
        this.report = report;
    }

    @Override
    public Void visitClassDecl(BythonParser.ClassDeclContext ctx) {
        if (classMethods.size() >= 2) {
            return null; 
        }
        
        currentClassName = ctx.ID().getText();
        classMethods.putIfAbsent(currentClassName, new HashMap<>());

        return visitChildren(ctx); 
    }

    @Override
    public Void visitMethodDecl(BythonParser.MethodDeclContext ctx) {
        if (currentClassName != null) {
            currentMethodName = ctx.ID().getText();

            classMethods.get(currentClassName).putIfAbsent(currentMethodName, new ArrayList<String>());
        }

        return visitChildren(ctx);
    }

    @Override
    public Void visitMethodParamList(BythonParser.MethodParamListContext ctx) {
        if (currentClassName != null && currentMethodName != null) {
        
            ArrayList<String> parameterNames = ctx.ID().stream()
                    .map(param -> param.getText())
                    .collect(Collectors.toCollection(ArrayList::new));

            if (!parameterNames.isEmpty()) {
                classMethods.get(currentClassName).put(currentMethodName, parameterNames);
            }
        }

        return null;  
    }


    @Override
    public Void visitProgram(BythonParser.ProgramContext ctx) {
        super.visitProgram(ctx);

        if (classMethods.size() == 2) {
            List<String> classNames = classMethods.keySet().stream().toList();
            String classA = classNames.get(0);
            String classB = classNames.get(1);

            Map<String, ArrayList<String>> methodsA = classMethods.get(classA);
            Map<String, ArrayList<String>> methodsB = classMethods.get(classB);

            boolean hasSameInterface = methodsA.size() == methodsB.size() &&
                    methodsA.entrySet().stream().allMatch(
                        entry -> {
                            
                            String methodName = entry.getKey();
                            
                            ArrayList<String> paramsA = (ArrayList<String>) entry.getValue();
                            ArrayList<String> paramsB = (ArrayList<String>) methodsB.get(methodName);
                            
                            return paramsB != null && Objects.equals(paramsA, paramsB);
                    });
            
            if (!hasSameInterface) {
                report.addAroma(new Aroma(
                        "AlternativeClassesSniffer",
                        "Alternative classes with different interfaces detected between " + classA + " and " + classB,
                        true
                ));
            }
        }

        return null;
    }
}
