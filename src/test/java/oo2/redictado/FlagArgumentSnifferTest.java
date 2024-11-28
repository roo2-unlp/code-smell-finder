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
           def set_equipo(tipo_cancha1){
           if tipo_cancha1 == "Futbol 5"{
                   self._equipo = 5;}
           elif tipo_cancha2 == "Futbol 7"{
                   self._equipo = 7;}
           elif tipo_cancha2 == "Futbol 11"{
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
           def set_tiempo(tipo_cancha2){
           if tipo_cancha2 == "Futbol 5"{
                   self._tiempo = 60;}
           elif tipo_cancha2 == "Futbol 7"{
                   self._tiempo = 80;}
           elif tipo_cancha2 == "Futbol 11"{
                   self._tiempo = 90;
            }
        }

        def set_equipo(tipo_cancha3){
        	   tipo_cancha3 = "Futbol 5";
               if tipo_cancha3 == "Futbol 5"{
                       self._equipo = 5;}
               elif tipo_cancha3 == "Futbol 7"{
                       self._equipo = 7;}
               elif tipo_cancha3 == "Futbol 11"{
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
    public void testWithThreeVariables() {
        String code = """
           def set_tiempo(tipo_cancha2){
           tipo_cancha1 = "Futbol 5";
           if tipo_cancha1 == "Futbol 5"{
                   self._tiempo = 60;}
           elif tipo_cancha1 == "Futbol 7"{
                   self._tiempo = 80;}
           elif tipo_cancha1 == "Futbol 11"{
                   self._tiempo = 90;
            }
        }

        def set_equipo(tipo_cancha3){
        	   tipo_cancha2 = "Futbol 5";
               if tipo_cancha2 == "Futbol 5"{
                       self._equipo = 5;}
               elif tipo_cancha3 == "Futbol 7"{
                       self._equipo = 7;}
               elif tipo_cancha3 == "Futbol 11"{
                       self._equipo = 11;
            }
        }
        def set_referi(tipo_cancha1){
        	   tipo_cancha3 = "Futbol 5";
               if tipo_cancha3 == "Futbol 5"{
                       self._referi = "Garcia Pedro";}
               elif tipo_cancha2 == "Futbol 7"{
                       self._referi = "Perez Guzman Joaquin";}
               elif tipo_cancha2 == "Futbol 11"{
                       self._referi = "Strabinsky Victor";
            }
        }
        """;

        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);

        assertTrue(report.stinks());
        assertEquals(1, report.getAromas().size()); 
    }
    
    @Test
    public void testWithTwoVariablesNests() {
        String code = """
           def set_tiempo(tipo_cancha4, hay_iluminacion){
           tipo1 = "Futbol 5";
           if tipo1 == "Futbol 5" and hay_iluminacion{
                   self._tiempo = 60;}
           elif tipo2 == "Futbol 7" and hay_iluminacion{
                   self._tiempo = 80;}
           elif tipo2 == "Futbol 11" and hay_iluminacion{
                   self._tiempo = 90;
            }
        }

        def set_equipo(tipo3){
        	   tipo2 = "Futbol 5";
               if tipo2 == "Futbol 5"{
                       self._equipo = 5;}
               elif tipo3 == "Futbol 7"{
                       self._equipo = 7;}
               elif tipo3 == "Futbol 11"{
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
    public void testWithFlagsChange() {
        String code = """
           def set_equipo(tipo_cancha1){
           if "Futbol 5" == tipo_cancha1{
                   self._equipo = 5;}
           elif "Futbol 7" == tipo_cancha1{
                   self._equipo = 7;}
           elif "Futbol 11" == tipo_cancha1{
                   self._equipo = 11;}
            }
        """;
        
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);

        assertTrue(report.stinks());
        assertEquals(1, report.getAromas().size()); 
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
