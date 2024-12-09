package oo2.redictado.NewClassesSniffer;

import java.util.HashMap;
import java.util.Map;

public class ClassInterface {
    private final String name;
    private final Map<String, MethodInterface> methods = new HashMap<>();

    public ClassInterface(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean addMethod(MethodInterface method) {
        if (methods.containsKey(method.getName())) {
            return false; // Método duplicado
        }
        methods.put(method.getName(), method);
        return true;
    }

    public boolean hasSameInterfaceAs(ClassInterface other) {
        if (methods.size() != other.methods.size()) {
            return false;
        }
        return methods.entrySet().stream().allMatch(entry -> {
            MethodInterface otherMethod = other.methods.get(entry.getKey());
            return otherMethod != null && entry.getValue().hasSameSignatureAs(otherMethod);
        });
    }
}
