package oo2.redictado;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import oo2.redictado.DuplicatedCodeSniffer.DuplicatedCodeSniffer;

import static org.junit.jupiter.api.Assertions.*;

public class DuplicatedCodeSnifferTest {
    DuplicatedCodeSniffer codeSniffer;


    @BeforeEach
    public void setUp() {
        codeSniffer = new DuplicatedCodeSniffer();
    }

    @Test
    public void testTresAtributosRepetidos() {
        String code = """     
            class Coche() {
                def __init__(self, marca, modelo, color, asientos) {
                    self.marca = marca;
                    self.modelo = modelo;
                    self.color = color;
                    self.encendido = False;
                    self.asientos = asientos;
                    self.velocidad = 0;
                }
            }

            class Moto() {
                def __init__(self, marca, modelo, color) {
                    self.marca = marca;
                    self.modelo = modelo;
                    self.color = color;
                }
            }                       
            """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        assertTrue(report.stinks());
        assertEquals(1, report.getAromas().size());
    }

    public void testAtributosSinRepetir(){
        String code = """     
            class Persona() {
                def __init__(self, nombre, apellido) {
                    self.nombre = nombre;
                    self.apellido = apellido;
                }
            }

            class Moto() {
                def __init__(self, marca, modelo, color) {
                    self.marca = marca;
                    self.modelo = modelo;
                    self.color = color;
                }
            }                      
            """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        assertFalse(report.stinks());
    }

    public void testUnAtributoRepetido() {
        String code = """     
            class Persona() {
                def __init__(self, nombre, apellido) {
                    self.nombre = nombre;
                    self.apellido = apellido;
                }
            }

            class Empresa() {
                def __init__(self, nombre, empleados) {
                    self.nombre = nombre;
                    self.empleados = empleados;
                }
            }                      
            """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        assertFalse(report.stinks());
    }

    public void testMetodosRepetidos(){
        String code= """
            def suma(a, y) {
                return a + y;
            }

            def add(a, y) {
                return a + y;
            }
            """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        assertTrue(report.stinks());
        assertEquals(1, report.getAromas().size());
    }

}