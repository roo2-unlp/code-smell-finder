package oo2.redictado;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import oo2.redictado.ReturnNullSniffer.DontReturnNullSniffer;

public class DontReturnNullSnifferTest {
	
	
	DontReturnNullSniffer codeSniffer;

    @BeforeEach
    public void setUp() {
        codeSniffer = new DontReturnNullSniffer();
    }
    
    
    @Test
    public void testReturnNullOnFunc() {
        String code = """
            def divide(a, b){
                if (b == 0) {
                   return None;
                }
                return a / b;
            }
            """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);

        assertTrue(report.stinks());
    }
    
    @Test
    public void testNoNulls() {
        String code = """
            def get_small_numbers(numbers){
                if numbers is None{
                    return [];
                }
                small_numbers = [];
                for n in numbers{
                    if (n < 10){
                        small_numbers.append(n);
                    }
                }
                return small_numbers;
            }
            """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);

        assertFalse(report.stinks());
    }
    
    
    @Test
    public void testReturnVarWithNull() {
        String code = """
            def ejemplo5(){
                a = None;
                return a;
            }
            """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);

        assertTrue(report.stinks());
    }


    @Test
    public void testReturnVarWithNoNull() {
    	// test de una var = none y luego se le asigna un valor valido
        String code = """
            def ejemplo5(){
                a = None;
                a = 1;
                return a;
            }
            """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);

        assertFalse(report.stinks());
    }

    @Test
    public void testReturnImplicitExpression() {
        String code = """
            def ejemplo5(){
                a = None;
                return a + 10;
            }
            """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);

        assertFalse(report.stinks());
    }
    //Test de una variable asignada con valor None que se devuelve en una expresion matematica.
    // No devuelve mal olor ya que no hay forma de determinar el resultado


    @Test
    public void testReturnCompoundExpression() {
        String code = """
            def ejemplo5(){
                return object.getFirstElement();
            }
            """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);

        assertFalse(report.stinks());
    }
    // test de una propiedad de un objeto con valor desconocido.
    // No devuelve mal olor ya que no hay forma de determinar el resultado

    @Test
    public void testReturnIndexValueWithNotNone() {
        String code = """
            def ejemplo5(){
                a[1] = None;
                a[1] = 1;
                return a[1];
            }
            """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);

        assertFalse(report.stinks());
    }
    // test de un indice que se le asigna None y luego un valor valido

    @Test
    public void testReturnIndexValueWithNone() {
        String code = """
            def ejemplo5(){
                a[1] = None;
                return a[1];
            }
            """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);

        assertTrue(report.stinks());
    }
    // test de un indice que se le asigna None y nunca se le asigna un valor valido


    @Test
    public void testReturnIndexValueWithIf() {
        String code = """
            def ejemplo5(){
                a[1] = None;
                if (True){
                    return a[1];
                }
                a[1] = 1;
                return a[1];
            }
            """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);

        assertTrue(report.stinks());
    }
    // funcion con 2 sentencias return, donde una devuelve un indice que no fue modificado

    @Test
    public void testWithoutReturn() {
        String code = """
            def ejemplo5(){
                a = 1;
                a + 10;
            }
            """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);

        assertFalse(report.stinks());
    }
    // test de una funcion sin return. No devuelve mal olor ya que no hay nada que devolver

    @Test
    public void testReturnImplicitAssignment() {
        String code = """
            def ejemplo5(){
                a = 500;
                return a += None;
            }
            """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);

        assertFalse(report.stinks());
    }
}
        
