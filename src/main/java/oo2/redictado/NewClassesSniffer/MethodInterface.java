package oo2.redictado.NewClassesSniffer;

import java.util.List;
import java.util.Objects;

public class MethodInterface {
    private final String name;
    private final List<String> parameters;

    public MethodInterface(String name, List<String> parameters) {
        this.name = name;
        this.parameters = parameters;
    }

    public String getName() {
        return name;
    }

    public boolean hasSameSignatureAs(MethodInterface other) {
        return Objects.equals(parameters, other.parameters);
    }
}
