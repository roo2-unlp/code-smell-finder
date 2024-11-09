package oo2.redictado;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import oo2.redictado.FlagArgumentSniffer.FlagArgumentSniffer;

public class FlagArgumentSnifferTest {
    FlagArgumentSniffer codeSniffer;

    @BeforeEach
    public void setUp() {
        codeSniffer = new FlagArgumentSniffer();
    }

    @Test
    public void testOneParameter() {
        String code = """
       	def Mensajero(nombre){
		    print("Bienvenido al sistema {nombre}");
		}
        """;
        
	AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);

        assertFalse(report.stinks());
        assertEquals(0, report.getAromas().size()); 

    }
    @Test
    public void testParameters() {
        String code = """
			def Sumador(x, y, z){
			    sum = x + y + z;
			    print("La suma es {sum}");
			}
		""";
        
	AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);

        assertFalse(report.stinks());
        assertEquals(0, report.getAromas().size()); 

    }

    @Test
    public void testNoDefinition() {
        String code = """
       		print("Hello, World!");
               """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);

        assertFalse(report.stinks());
        assertEquals(0, report.getAromas().size());
    }

    @Test
    public void testNoParameters() {
        String code = """
       	def Mensajero(){
		    print("Bienvenido al sistema");
		}
        """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);

        assertFalse(report.stinks());
        assertEquals(0, report.getAromas().size());
    }


    @Test
    public void testWithFlags() {
        String code = """
           def set_equipo(tipo){
           if tipo == "Futbol 5"{
                   self._equipo = 5;}
           elif tipo == "Futbol 7"{
                   self._equipo = 7;}
           elif tipo == "Futbol 11"{
                   self._equipo = 11;
        }
        }
        """;
        
	AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);

        assertTrue(report.stinks());
        assertEquals(1, report.getAromas().size()); 
    }

    @Test
    public void testWithTwoFlags() {
        String code = """
           def set_tiempo(tipo){
           if tipo == "Futbol 5"{
                   self._tiempo = 60;}
           elif tipo == "Futbol 7"{
                   self._tiempo = 80;}
           elif tipo == "Futbol 11"{
                   self._tiempo = 90;
            }
        }

        def set_equipo(tipo){
               if tipo == "Futbol 5"{
                       self._equipo = 5;}
               elif tipo == "Futbol 7"{
                       self._equipo = 7;}
               elif tipo == "Futbol 11"{
                       self._equipo = 11;
            }
        }
        """;

	AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);

        assertTrue(report.stinks());
        assertEquals(2, report.getAromas().size()); 
    }
	
  

    @Test
    public void testSyntaxError() {
        String code = "for (int i = 0; i < 10; i++) {}";
        AromaReport report = new AromaReport(code);
        
        assertThrows(IllegalArgumentException.class, () -> {
            codeSniffer.sniff(code, report);
        });
    }
}
