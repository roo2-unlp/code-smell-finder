package oo2.redictado;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import oo2.redictado.AlternativeClassesSniffer.AlternativeClassesSniffer;

public class AlternativeClassesTest {
    AlternativeClassesSniffer codeSniffer;

    @BeforeEach
    public void setUp() {
        codeSniffer = new AlternativeClassesSniffer();
    }

    // true = detecta mal olor. Cumple con el mal olor. Diferente interfaz 
    // false = no cumple. Es deci, misma interfaz 

    // Caso 1: Métodos con el mismo nombre, parámetros y contenido igual (no hay mal olor)
    @Test
    public void testMetodosIgualesConParametrosYContenidoIgual() {
        String code = """
                class Perro {
                    def ladrar() {
                        print("Ladrando");
                    }
                }
                class Gato {
                    def ladrar() {
                        print("Ladrando");
                    }
                }
                """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);

        assertFalse(report.stinks()); // No debe detectar mal olor
    }

    // Caso 2: Métodos con el mismo nombre, parámetros iguales, pero el contenido es diferente (mal olor)
    @Test
    public void testMetodosIgualesConParametrosIgualesPeroContenidoDiferente() {
        String code = """
                class Perro {
                    def ladrar() {
                        print("Ladrando fuerte");
                    }
                }
                class Gato {
                    def ladrar() {
                        print("Ladrando suave");
                    }
                }
                """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);

        assertTrue(report.stinks()); // Debe detectar mal olor
    }

    // Caso 3: Métodos con el mismo nombre, parámetros diferentes, pero el contenido es igual (mal olor)
    @Test
    public void testMetodosIgualesConParametrosDiferentesYContenidoIgual() {
        String code = """
                class Perro {
                    def ladrar(volume) {
                        print("Ladrando");
                    }
                }
                class Gato {
                    def ladrar(int volumen) {
                        print("Ladrando");
                    }
                }
                """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);

        assertTrue(report.stinks()); // Debe detectar mal olor
    }

    // Caso 4: Métodos con el mismo nombre y parámetros diferentes, contenido diferente (no hay mal olor)
    @Test
    public void testMetodosIgualesConParametrosYContenidoDiferentes() {
        String code = """
                class Perro {
                    def ladrar(volume) {
                        print("Ladrando fuerte");
                    }
                }
                class Gato {
                    def ladrar(int volumen) {
                        print("Ladrando suave");
                    }
                }
                """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);

        assertFalse(report.stinks()); // No debe detectar mal olor
    }

    // Caso 5: Métodos con nombres diferentes, pero parámetros y contenido iguales (mal olor)
    @Test
    public void testMetodosConNombresDiferentesPeroParametrosYContenidoIguales() {
        String code = """
                class Perro {
                    def ladrar() {
                        print("Ladrando");
                    }
                }
                class Gato {
                    def maullar() {
                        print("Ladrando");
                    }
                }
                """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);

        assertTrue(report.stinks()); // Debe detectar mal olor
    }

    // Caso 6: Métodos con nombres diferentes, parámetros iguales, contenido diferente (no hay mal olor)
    @Test
    public void testMetodosConNombresDiferentesParametrosIgualesYContenidoDiferente() {
        String code = """
                class Perro {
                    def ladrar(volume) {
                        print("Ladrando fuerte");
                    }
                }
                class Gato {
                    def maullar(volume) {
                        print("Ladrando suave");
                    }
                }
                """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);

        assertFalse(report.stinks()); // No debe detectar mal olor
    }

    // Caso 7: Métodos con nombres diferentes, parámetros diferentes, pero contenido igual (mal olor)
    @Test
    public void testMetodosConNombresDiferentesParametrosDiferentesYContenidoIgual() {
        String code = """
                class Perro {
                    def ladrar(volume) {
                        print("Ladrando");
                    }
                }
                class Gato {
                    def maullar(int volumen) {
                        print("Ladrando");
                    }
                }
                """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);

        assertTrue(report.stinks()); // Debe detectar mal olor
    }

    // Caso 8: Métodos con nombres diferentes, parámetros diferentes, contenido diferente (no hay mal olor)
    @Test
    public void testMetodosConNombresDiferentesParametrosDiferentesYContenidoDiferente() {
        String code = """
                class Perro {
                    def ladrar(volume) {
                        print("Ladrando fuerte");
                    }
                }
                class Gato {
                    def maullar(volume) {
                        print("Maullando suave");
                    }
                }
                """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);

        assertFalse(report.stinks()); // No debe detectar mal olor
    }

    // Caso 9: La clase tiene más de un método, pero solo uno de ellos es similar (mal olor)
    @Test
    public void testVariasMetodosConUnoSimilar() {
        String code = """
                class Carro {
                    def arrancar() {
                        print("Arrancando");
                    }
                    def frenar() {
                        print("Frenando");
                    }
                }
                class Bicicleta {
                    def arrancar() {
                        print("Arrancando");
                    }
                    def detener() {
                        print("Deteniendo");
                    }
                }
                """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);

        assertTrue(report.stinks()); // Debe detectar mal olor solo por el método similar 'arrancar'
    }
}
