package oo2.redictado.AlternativeClassesSniffer;

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
    private Map<String, Map<String, List<String>>> classMethods = new HashMap<>();
    private String currentClassName = null;
    private String currentMethodName = null;

    public AlternativeClassesVisitor(AromaReport report) {
        this.report = report;
    }

    @Override
    public Void visitClassDecl(BythonParser.ClassDeclContext ctx) {
        if (classMethods.size() >= 2) {
            return null;  // Limitar a dos clases
        }
        System.out.print("clase"); 

        // Obtener el nombre de la clase y registrar un mapa vacío para sus métodos
        currentClassName = ctx.ID().getText();
        classMethods.putIfAbsent(currentClassName, new HashMap<>());

        return visitChildren(ctx);  // Continuar con métodos y parámetros
    }

    @Override
    public Void visitMethodDecl(BythonParser.MethodDeclContext ctx) {
        System.out.print("Metodo"); 
        if (currentClassName != null) {
            currentMethodName = ctx.ID().getText();

            // Registrar el método actual con una lista vacía de parámetros
            classMethods.get(currentClassName).putIfAbsent(currentMethodName, List.of());
        }

        return visitChildren(ctx);  // Procesar los parámetros del método
    }

    @Override
    public Void visitMethodParamList(BythonParser.MethodParamListContext ctx) {
        System.out.print("Parametro"); 
        if (currentClassName != null && currentMethodName != null) {
            // Obtener lista de nombres de parámetros
            List<String> parameterNames = ctx.ID().stream()
                    .map(param -> param.getText())
                    .collect(Collectors.toList());

            // Actualizar la lista de parámetros del método actual solo si tiene parámetros
            if (!parameterNames.isEmpty()) {
                classMethods.get(currentClassName).put(currentMethodName, parameterNames);
            }
        }

        return null;  // No necesitamos recorrer más dentro de los parámetros
    }

    @Override
    public Void visitProgram(BythonParser.ProgramContext ctx) {
        super.visitProgram(ctx);

        // Asegurarse de que solo hay dos clases para comparar
        if (classMethods.size() == 2) {
            List<String> classNames = classMethods.keySet().stream().toList();
            String classA = classNames.get(0);
            String classB = classNames.get(1);

            Map<String, List<String>> methodsA = classMethods.get(classA);
            Map<String, List<String>> methodsB = classMethods.get(classB);

            // Comparación detallada de métodos y parámetros
            boolean hasSameInterface = methodsA.size() == methodsB.size() &&
                    methodsA.entrySet().stream().allMatch(entryA -> {
                        String methodNameA = entryA.getKey();
                        List<String> paramsA = entryA.getValue();
                        List<String> paramsB = methodsB.get(methodNameA);
                        // Verificar que el método exista en ambas clases y que sus parámetros sean idénticos
                        return paramsB != null && Objects.equals(paramsA, paramsB);
                    });

            // Reportar si las clases tienen una interfaz diferente
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
