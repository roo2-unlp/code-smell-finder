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
            return null;  // Limitar a dos clases
        }
        // Obtener el nombre de la clase y registrar un mapa vacío para sus métodos
        currentClassName = ctx.ID().getText();
        classMethods.putIfAbsent(currentClassName, new HashMap<>());

        return visitChildren(ctx);  // Continuar con métodos y parámetros
    }

    @Override
    public Void visitMethodDecl(BythonParser.MethodDeclContext ctx) {
        if (currentClassName != null) {
            currentMethodName = ctx.ID().getText();

            // Registrar el método actual con una lista vacía de parámetros
            classMethods.get(currentClassName).putIfAbsent(currentMethodName, new ArrayList<String>());
        }

        return visitChildren(ctx);  // Procesar los parámetros del método
    }

    @Override
    public Void visitMethodParamList(BythonParser.MethodParamListContext ctx) {
        if (currentClassName != null && currentMethodName != null) {
            // Obtener lista de nombres de parámetros
            ArrayList<String> parameterNames = ctx.ID().stream()
                    .map(param -> param.getText())
                    .collect(Collectors.toCollection(ArrayList::new));

            // Actualizar la lista de parámetros del método actual solo si tiene parámetros
            // este es el que si modifico me da mal los test
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

            Map<String, ArrayList<String>> methodsA = classMethods.get(classA);
            Map<String, ArrayList<String>> methodsB = classMethods.get(classB);

            // Comparación detallada de métodos y parámetros
            boolean hasSameInterface = methodsA.size() == methodsB.size() &&
                    methodsA.entrySet().stream().allMatch(
                        entry -> {
                            // obtengo el nombre del metodo
                            String methodName = entry.getKey();
                            // comparo el valor del metodo
                            ArrayList<String> paramsA = (ArrayList<String>) entry.getValue();
                            ArrayList<String> paramsB = (ArrayList<String>) methodsB.get(methodName);
                            // Verificar que el método exista en ambas clases y que sus parámetros sean idénticos
                            // si la clase b no existe, retorno false
                            // si los nombres son distontos retorna false;
                            return paramsB != null && Objects.equals(paramsA, paramsB);
                    });
            // el problema de porque cuando analizo los test me da false en vez de false esta en la linea anterior => 
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
