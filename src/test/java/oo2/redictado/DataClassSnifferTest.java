package oo2.redictado;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import oo2.redictado.DataClassSniffer.DataClassSniffer;


public class DataClassSnifferTest {
    DataClassSniffer codeSniffer;

    @BeforeEach
    public void setUp() {
        codeSniffer = new DataClassSniffer();
    }
    
    @Test
    public void testDataClassWithOnlyAttributes() {
        String code = """
            class Persona{
                nombre = "";
                edad = 0;
            }
            """;
        
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);

        assertTrue(report.stinks()); // Verifica que el código "huele mal"
        assertEquals(1, report.getAromas().size()); // Verifica que se haya detectado un mal olor
    }

    @Test
    public void testDataClassWithAttributesAndConstructorOnly() {
        String code = """
            class Producto{
                def __init__(self, nombre, precio){
                    self.nombre = nombre;
                    self.precio = precio;
                }
            }
            """;
        
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);

        assertTrue(report.stinks()); // Verifica que el código "huele mal"
        assertEquals(1, report.getAromas().size()); // Verifica que se haya detectado un mal olor
    }

    @Test
    public void testNonDataClassWithLogicMethods() {
        String code = """
            class Cuenta {
                def __init__(self, balance){
                    self.balance = balance;
                }
                
                def depositar(cantidad){
                    if cantidad > 0 {
                        self.balance += cantidad;
                    }
                }
                
                def retirar(cantidad){
                    if cantidad > 0 and cantidad <= self.balance{
                        self.balance -= cantidad;
                    }
                }
            }
            """;
        
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);

        assertFalse(report.stinks()); // Verifica que el código no es detectado como "Data Class".
    }

    @Test
    public void testDataClassWithConstructorOnly() {
        String code = """
            class Direccion{
                def __init__(self, calle, ciudad){
                    self.calle = calle;
                    self.ciudad = ciudad;
                }
            }
            """;
        
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);

        assertTrue(report.stinks()); // Verifica que el código "huele mal"
        assertEquals(1, report.getAromas().size()); // Verifica que se haya detectado un mal olor
    }

    @Test
    public void testMixedClassWithSomeLogic() {
        String code = """
            class Usuario {
                def __init__(self, nombre, edad){
                    self.nombre = nombre;
                    self.edad = edad;
                }
                
                def es_mayor_de_edad(self){
                    return self.edad >= 18;
                }
            }
            """;
        
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);

        assertFalse(report.stinks()); // Verifica que el código no es detectado como "Data Class".

    }

    @Test
    public void testRandomCode() {
    // Código irrelevante o "cualquier cosa" para verificar que no sea marcado como Data Class
        String code = """
            print("Esto es solo texto sin relación con clases");
        
            def random_function(){
                return 42;
            }
        """;
    
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);

        assertFalse(report.stinks()); // Verifica que el código no "huele mal"
        //assertEquals(0, report.getAromas().size()); // Verifica que no se haya detectado un mal olor
    }

    @Test
    public void testEmptyClass() {
         // Código de una clase vacía
        String code = """
                    class ClaseVacia {
                  }
        """;

        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);

        // Verifica que no se haya detectado ningún mal olor
        assertFalse(report.stinks()); // La clase vacía no debe ser marcada como Data Class
        //assertEquals(0, report.getAromas().size()); // No se debe agregar ningún aroma (ningún mal olor)
    }
    
}