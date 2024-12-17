package oo2.redictado;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import oo2.redictado.AlternativeClassesSniffer.AlternativeClassesSniffer;

public class AlternativeClassesTest {
    AlternativeClassesSniffer codeSniffer;

    @BeforeEach
    public void setUp() {
        codeSniffer = new AlternativeClassesSniffer();
    }

    @Test
    public void testEsPolimorfico() {
        String code = """
                class Perro {
                    def hacerRuido(ruido) {
                        print(ruido);
                    }
                    def Moverse(mover) {
                          mover.adelante();
                    }
                }
                class Gato {
                   def hacerRuido(ruido) {
                        print(ruido);
                    }
                    def Moverse(mover) {
                        mover.adelante();
                    }
                }
                """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);

        assertFalse(report.stinks());
    }

    @Test
    public void testClaseConMasMetodos() {
        String code = """
                class Perro {
                    def hacerRuido(ruido) {
                        print(ruido);
                    }
                }
                class Gato {
                   def hacerRuido(ruido) {
                        print(ruido);
                    }
                    def Moverse() {
                        mover.adelante();
                    }
                }
                """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);

        assertTrue(report.stinks());
    }

    @Test
    public void testClaseMetodosDistintosPeroMismosParametros() {
        String code = """
                class Perro {
                    def ladrar(volumen) {
                        print("Ladrando");
                    }
                }
                class Gato {
                    def maullar(volumen) {
                        print("Ladrando");
                    }
                }
                """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);

        assertTrue(report.stinks()); 
    }

    @Test
    public void testMetodosYParametrosIgualesConContenidoDiferentes() {
        String code = """
                class Perro {
                    def ladrar(volumen) {
                        print("Ladrando fuerte");
                    }
                }
                class Gato {
                    def ladrar(volumen) {
                        print("Ladrando suave");
                    }
                }
                """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);

        assertFalse(report.stinks());
    }


    @Test
    public void testMetodosConNombresIgualesPeroParametrosDiferentes() {
        String code = """
                class Perro {
                    def hacerRuido(ruido) {
                        print(ruido);
                    }

                }
                class Gato {
                   def hacerRuido(maullar) {
                        print(maullar);
                    }

                }
                """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);

        assertTrue(report.stinks()); 
    }



     
     @Test
     public void testMetodosConNombresDiferentesParametrosDiferentes() {
         String code = """
                 class Perro {
                     def ladrar(volume) {
                         print("Ladrando");
                     }
                 }
                 class Gato {
                     def maullar(potencia) {
                         print("Ladrando");
                     }
                 }
                 """;
         AromaReport report = new AromaReport(code);
         codeSniffer.sniff(code, report);

         assertTrue(report.stinks()); 
    }

@Test
public void testMasDeDosClasesPolimorficas() {
    String code = """
            class Perro {
                def Comer(comida) {
                    print("que rico"); 
                }
            }
            class Gato {
                def Comer(comida) {
                    print("que rico"); 
                }
            }
            class Pajaro {
                def Comer(comida) {
                    print("que rico"); 
                }
            }
            """;
    AromaReport report = new AromaReport(code);
    codeSniffer.sniff(code, report);

    assertFalse(report.stinks()); 
}

@Test
    public void testConMetodosInvertidas() {
        String code = """
                class Perro {
                    def Moverse(mover) {
                          mover.adelante();
                    }
                    def hacerRuido(ruido) {
                        print(ruido);
                    }   
                }
                class Gato {
                   def hacerRuido(ruido) {
                        print(ruido);
                    }
                    def Moverse(mover) {
                        mover.adelante();
                    }
                }
                """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);

        assertFalse(report.stinks()); 
    }

     
@Test
public void testParametrosInvertidos() {
    String code = """
            class Perro {
                
                def hacerRuido(ruido,rugir) {
                    print(ruido);
                }   
                def Moverse(mover) {
                      mover.adelante();
                }
            }
            class Gato {
               def hacerRuido(rugir,ruido) {
                    print(ruido);
                }
                def Moverse(mover) {
                    mover.adelante();
                }
            }
            """;
    AromaReport report = new AromaReport(code);
    codeSniffer.sniff(code, report);

    assertTrue(report.stinks()); 
}

@Test
    public void testVacio() {
        String code = """
                
                """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);

        assertFalse(report.stinks()); 
    }

}