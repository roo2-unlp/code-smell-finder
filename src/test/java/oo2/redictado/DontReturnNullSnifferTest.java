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
        String code = "def divide(a, b) {\r\n"
        		+ "        if b == 0 {\r\n"
        		+ "               return None;\r\n"
        		+ "         }\r\n"
        		+ "\r\n"
        		+ "         return a / b;\r\n"
        		+ " }";
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);

        assertTrue(report.stinks());
        assertEquals(1, report.getAromas().size());
    }
    
    @Test
    public void testNoNulls() {
        String code = "def get_small_numbers(numbers) {\r\n"
        		+ "        if numbers is None {\r\n"
        		+ "              return [];\r\n"
        		+ "         }\r\n"
        		+ "         small_numbers = [];\r\n"
        		+ "         for n in numbers {\r\n"
        		+ "                   if (n < 10) {\r\n"
        		+ "                             small_numbers.append(n);\r\n"
        		+ "                   }\r\n"
        		+ "          }\r\n"
        		+ "          return small_numbers;\r\n"
        		+ "}";
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);

        assertFalse(report.stinks());
    }
    
    
    @Test
    public void testReturnVarWithNull() {
        String code = "def ejemplo5 (){\r\n"
        		+ "a = None;\r\n"
        		+ "return a;\r\n"
        		+ "}";
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);

        assertTrue(report.stinks());
        assertEquals(1, report.getAromas().size());
    }
    
    
    @Test
    public void testReturnVarWithNoNull() {
    	// test de una var = none y luego se le asigna un valor valido
        String code = "def ejemplo5 (){\r\n"
        		+ "a = None;\r\n"
        		+ "a=1;\r\n"
        		+ "return a;\r\n"
        		+ "}";
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);

        assertFalse(report.stinks());
    }
    
}
        
