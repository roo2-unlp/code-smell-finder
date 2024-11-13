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

    public void testAtributosConValorDiferente(){
        String code= """
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
                    self.marca = "Yamaha";
                    self.modelo = "Scooter";
                    self.color = "Rojo";
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

    public void testMetodosSinRepetir(){
        String code= """
            def suma(x, y) {
                return x + y;
            }

            def resta(x, y) {
                return x - y;
            }
            """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        assertFalse(report.stinks());
    }

    public void testMetodoRepetidoEnOtro(){
        String code= """
            class Ejemplo() {

                def metodoA() { 
                    valor1 = 20.5;
                    print("Inicio"); 
                    resultado = 5 * 4;
                    print("Fin"); 
                }
            
                def metodoB() {
                    print("Inicio"); 
                    resultado = 5 * 4;
                    print("Fin");
                }
            
            }
            """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        assertTrue(report.stinks());
        assertEquals(1, report.getAromas().size());
    }

    public void testMetodoRepetidoEnScript(){
        String code= """
            def inicio() {
                print("1")
                print("2")
                print("3")
            }

            print("1")
            print("2")
            print("3")
            suma = 1 + 3
            print("suma")
            resta = 4 - 2
            print("resta")
            """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        assertTrue(report.stinks());
        assertEquals(1, report.getAromas().size());
    }

    public void testMetodoSinRepetirEnScript(){
        String code= """
            def inicio() {
                lista.add(x);
                print(x); 
            }

            y= 5
            x= 2
            elem = x + y
            print("suma" + elem)
            resta = 4 - 2
            print("resta")
            """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        assertFalse(report.stinks());
    }

    public void testScriptRepetido(){
        String code= """
           x = elem1 + elem2;
           lista.add(x);
           print(x);
           print(lista);
           x = elem1 + elem2;
           lista.add(x);
           print(x);
           print(lista);
            """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        assertTrue(report.stinks());
        assertEquals(1, report.getAromas().size());
    }

    public void testScriptSinRepetir(){
        String code= """
           x = elem1 + elem2;
           lista.add(x);
           print("algo");
           print(x);
           print(lista);
           x = elem1 + elem2;
           lista.add(x);
           print(x);
           print(lista);
            """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report); 
        assertFalse(report.stinks());
    }

    public void testCodgioNoBython(){
        String code= """
           SELECT columna1, columna2
           WHERE columna3 = 'valor';
            """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report); 
        assertFalse(report.stinks());
    }

    public void testMasDeUnCaso(){
        String code= """
            class Mujer(){
                def __init__(self, nombre, apellido, edad) {
                    self.nombre = nombre;
                    self.apellido = apellido;
                    self.edad = edad;
                }
                
                def cumpleanios(self){
                    self.edad += 1;
                }
            }

            class Hombre() {
                def __init__(self, nombre, apellido, edad) {
                    self.nombre = nombre;
                    self.apellido = apellido;
                    self.edad = edad;
                }
                
                def birthday(self){
                    self.edad += 1;
                }
            }
            """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        assertTrue(report.stinks());
        assertEquals(2, report.getAromas().size());
    }




}