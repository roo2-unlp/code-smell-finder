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
    public void testDataClassWithOnlyGettersAndSetters() {
        String code = """
            class Persona {
                private String nombre;
                private int edad;
                
                public String getNombre() { return nombre; }
                public void setNombre(String nombre) { this.nombre = nombre; }
                
                public int getEdad() { return edad; }
                public void setEdad(int edad) { this.edad = edad; }
            }
            """;
        
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);

        assertTrue(report.stinks()); //Verifica que el código "huele mal"
        assertEquals(1, report.getAromas().size()); //Verifica que se haya detectado un mal olor
    }

    @Test
    public void testDataClassWithPublicFieldsOnly() {
        String code = """
            class Producto {
                public String nombre;
                public double precio;
            }
            """;
        
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);

        assertTrue(report.stinks()); //Verifica que el código "huele mal"
        assertEquals(1, report.getAromas().size()); //Verifica que se haya detectado un mal olor
    }

    @Test
    public void testNonDataClassWithLogic() {
        String code = """
            class Cuenta {
                private double balance;
                
                public void depositar(double cantidad) {
                    if (cantidad > 0) {
                        balance += cantidad;
                    }
                }
                
                public void retirar(double cantidad) {
                    if (cantidad > 0 && cantidad <= balance) {
                        balance -= cantidad;
                    }
                }
            }
            """;
        
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);

        assertFalse(report.stinks()); //Verifica que el código no es detectado como "Data Class".
    }

    @Test
    public void testDataClassWithConstructorsOnly() {
        String code = """
            class Direccion {
                private String calle;
                private String ciudad;
                
                public Direccion(String calle, String ciudad) {
                    this.calle = calle;
                    this.ciudad = ciudad;
                }
                
                public String getCalle() { return calle; }
                public String getCiudad() { return ciudad; }
            }
            """;
        
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);

        assertTrue(report.stinks()); //Verifica que el código "huele mal"
    }

    @Test
    public void testMixedClassWithSomeLogic() {
        String code = """
            class Usuario {
                private String nombre;
                private int edad;
                
                public String getNombre() { return nombre; }
                public void setNombre(String nombre) { this.nombre = nombre; }
                
                public boolean esMayorDeEdad() { return edad >= 18; }
            }
            """;
        
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);

        assertFalse(report.stinks()); //Verifica que el código no es detectado como "Data Class".
    }
}
