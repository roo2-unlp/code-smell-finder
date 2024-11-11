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
    public void testReturnNull() {
        String code = "return None;";
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);

        assertTrue(report.stinks());
        assertEquals(1, report.getAromas().size());
    }
    
    
    @Test
    public void testReturnNullOnFunc() {
        String code = """
            def divide(a, b):
                if b == 0:
                   return None;
                return a / b;
            """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);

        assertTrue(report.stinks());
        assertEquals(1, report.getAromas().size());
    }
    
    @Test
    public void testNoNulls() {
        String code = """
            def get_small_numbers(numbers):
                if numbers is None:
                    return [];
                small_numbers = [];
                for n in numbers:
                    if (n < 10):
                        small_numbers.append(n);
                return small_numbers;
            """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);

        assertFalse(report.stinks());
    }
    
    
    @Test
    public void testReturnVarWithNull() {
        String code = """
            def ejemplo5():
                a = None;
                return a;
            """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);

        assertTrue(report.stinks());
        assertEquals(1, report.getAromas().size());
    }


    @Test
    public void testReturnVarWithNoNull() {
    	// test de una var = none y luego se le asigna un valor valido
        String code = """
            def ejemplo5():
                a = None;
                a = 1;
                return a;
            """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);

        assertFalse(report.stinks());
    }

    @Test
    public void testReturnImplicitExpression() {
        // test de una var = none y luego se le asigna un valor valido
        String code = """
            def ejemplo5():
                a = None;
                return a + 10;
            """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);

        assertTrue(report.stinks());
    }
    //Test de una variable asignada con valor None que se devuelve en una expresion matematica


    @Test
    public void testReturnCompoundExpression() {
        // test de una var = none y luego se le asigna un valor valido
        String code = """
            def ejemplo5():
                return object.getFirstElement();
            """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);

        assertFalse(report.stinks());
    }
    // test de una var con valor desconocido. No deberia dar smell

}
        
