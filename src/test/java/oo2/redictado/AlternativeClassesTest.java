package oo2.redictado;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import oo2.redictado.AlternativeClassesSniffer.AlternativeClassesSniffer;

public class AlternativeClassesTest {
    AlternativeClassesSniffer codeSniffer;

    @BeforeEach
    public void setUp() {
        codeSniffer = new AlternativeClassesSniffer();
    }

    @Test
    public void testClasesSinMetodosSimilares() {
        String code = """
                class Perro {
                    def ladrar() {}
                }
                class Gato {
                    def maullar() {}
                }
                """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        assertFalse(report.stinks());
    }

    @Test
    public void testDosClasesConMetodosSimilares() {
        String code = """
                class Carro {
                    def arrancar() {}
                    def frenar() {}
                }
                class Bicicleta {
                    def arrancar() {}
                    def frenar() {}
                }
                """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        assertTrue(report.stinks());
    }

    @Test
    public void testClasesConUnMetodoSimilar() {
        String code = """
                class Computadora {
                    def iniciar() {}
                }
                class Tablet {
                    def iniciar() {}
                    def suspender() {}
                }
                """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        assertTrue(report.stinks());
    }

    @Test
    public void testClasesConMetodosRelacionados() {
        String code = """
                class Piloto {
                    def despegar() {}
                    def aterrizar() {}
                }
                class Avion {
                    def despegar() {}
                    def aterrizar() {}
                }
                """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        assertTrue(report.stinks());
    }

    @Test
    public void testTresClasesSimilares() {
        String code = """
                class Leon {
                    def comer() {}
                    def dormir() {}
                }
                class Tigre {
                    def comer() {}
                    def dormir() {}
                }
                class Oso {
                    def comer() {}
                    def dormir() {}
                }
                """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        assertTrue(report.stinks());
    }

    @Test
    public void testClasesSimilaresConInterfacesDiferentes() {
        String code = """
                class Television {
                    def encender() {}
                    def apagar() {}
                }
                class Radio {
                    def activar() {}
                    def desactivar() {}
                }
                """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        assertFalse(report.stinks());
    }

    @Test
    public void testClasesSimilaresConParametrosDiferentes() {
        String code = """
                class Camion {
                    def cargar(peso) {}
                    def descargar() {}
                }
                class Barco {
                    def cargar(cantidad) {}
                    def descargar() {}
                }
                """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        assertTrue(report.stinks());
    }

    @Test
    public void testDosClasesConParametrosDistintos() {
        String code = """
                class Restaurante {
                    def preparar(plato) {}
                }
                class ComidaRapida {
                    def preparar(menu) {}
                }
                """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        assertTrue(report.stinks());
    }

    @Disabled
    public void testMetodosIgualesConParametrosOpcionales() {
        String code = """
                class Servidor {
                    def iniciar(configuracion="predeterminada") {}
                    def detener() {}
                }
                class Cliente {
                    def iniciar(configuracion="predeterminada") {}
                    def detener() {}
                }
                """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        assertTrue(report.stinks());
    }

    @Test
    public void testVariasClasesConMetodosSimilares() {
        String code = """
                class Helado {
                    def derretir() {}
                }
                class Yogurt {
                    def derretir() {}
                }
                class Nieve {
                    def derretir() {}
                }
                """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        assertTrue(report.stinks());
    }
}
