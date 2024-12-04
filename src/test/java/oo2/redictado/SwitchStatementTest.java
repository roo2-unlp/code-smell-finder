package oo2.redictado;
import static org.junit.jupiter.api.Assertions.*;
import oo2.redictado.SwitchStatementSniffer.SE.SwitchStatementSniffer;
import org.junit.jupiter.api.Test;

public class SwitchStatementTest {

    @Test
    public void testSwitchStatementSmellDetected_1() {
        String code = """
                class Perro{
                    def init(self, nombre, raza){
                        this.nombre = nombre;
                        this.raza = raza;
                    }

                    def bark_smell(){
                        if (this.raza == "Caniche"){
                            print("woof woof");
                        }elif (this.raza == "Pitbull"){
                            print("WRAF WRAF");
                        }elif (this.raza == "Gran Danés"){
                            print("GUAUGUAU");
                        }elif (this.raza == "Beagle"){
                            print("bark bark");
                        }
                    }
                }

                perro = Perro("Jaime", "Caniche");
                perro.bark_smell();
                """;

        // Se crea el reporte de aromas
        AromaReport report = new AromaReport(code);

        // Se crea el sniffer para detectar el mal olor
        CodeSniffer sniffer = new SwitchStatementSniffer();
        sniffer.sniff(code, report);


        assertTrue(report.stinks());
        assertEquals(1, report.getAromas().size());

        // Verificar que el aroma es el esperado
        assertEquals("SwitchStatementSniffer", report.getAromas().get(0).name());
        assertEquals("The code has a Switch Statement smell.", report.getAromas().get(0).message());
    }

    @Test
    public void testNoBadSmell_1(){
        String code= """
                class Animal(){
                    def init(self, tipo_animal){
                        this.tipo_animal = tipo_animal;
                    }
            }

                class Perro(Animal){
                    def ruido(){
                        print("guau guau");
                    }
                }

                class Gato(Animal){
                    def ruido(){
                        print("miau miau");
                    }
                }

                gato = Gato("Purelly");
                perro = Perro("Firulais the 1st");

                list = (gato, perro);

                for i in list{
                    i.ruido();
                }

            """;

        AromaReport report = new AromaReport(code);
        CodeSniffer sniffer = new SwitchStatementSniffer(); // Suponiendo que SwitchStatementSniffer implementa CodeSniffer

        // Ejecutar el sniffing en el código
        sniffer.sniff(code, report);
        assertFalse(report.stinks());
        assertEquals(0, report.getAromas().size());
    }

    // -------------------------------------------------------------------------------------

    @Test
    public void testSwitchStatementSmellDetected_2() {
        // Un código con muchos elif
        String code = """
           if x == 1{
               print("Lunes");
            }    
            elif x == 2{
               print("Martes");
            }    
           elif x == 3{
               print("Miércoles");
            }
           elif x == 4{
               print("Jueves");
            }
           elif x == 5{
               print("Viernes");
            }
           elif x == 6{
               print("Sábado");
            }   
           else{
               print("Domingo");
            }
           """;
        AromaReport report = new AromaReport(code); //analiza el codigo para determinar si huele mal
        CodeSniffer sniffer = new SwitchStatementSniffer(); // Suponiendo que SwitchStatementSniffer implementa CodeSniffer

        // Ejecutar el sniffing en el código
        sniffer.sniff(code, report);

        assertTrue(report.stinks()); // Debería oler mal
        assertEquals(1, report.getAromas().size());
    }

    @Test
    public void testNoSwitchStatementSmell_1() {
        // Un código con solo un if-else simple
        String code = """
               if x == 1{
                   print("Lunes");
                }else{
                   print("Otro día");
                }
               """;
        AromaReport report = new AromaReport(code);
        CodeSniffer sniffer = new SwitchStatementSniffer(); // Suponiendo que SwitchStatementSniffer implementa CodeSniffer

        // Ejecutar el sniffing en el código
        sniffer.sniff(code, report);

        assertFalse(report.stinks()); // No debería oler mal
        assertEquals(0, report.getAromas().size());
    }

    //-------------------------------------------------

    @Test
    public void testCalcularImpuestoBadSmell() {
        String code = """
                def calcular_impuesto_bad_smell(categoria_producto, precio){
                      if categoria_producto == "alimento"{
                          return precio * 0.05;}
                      elif categoria_producto == "ropa"{
                          return precio * 0.1;}
                      elif categoria_producto == "electrónica"{
                          return precio * 0.15;}
                      elif categoria_producto == "muebles"{
                          return precio * 0.12;}
                      else{
                          return "Categoría de producto inválida";}
                  }
            """;

        AromaReport report = new AromaReport(code);
        CodeSniffer sniffer = new SwitchStatementSniffer(); // Suponiendo que SwitchStatementSniffer implementa CodeSniffer


        sniffer.sniff(code, report);
        assertTrue(report.stinks(), "El código tiene un bad smell (switch statement smell).");
        assertEquals(1, report.getAromas().size(), "El número de aromas debe ser 1.");
    }

    @Test
    public void testSwitchStatementSmellDetected_3() {
        // Código con múltiples if-elif (Bad Smell)
        String code = """
        class Restaurante{
                     def calcular_precio(plato){
                         if plato == "Pizza"{
                             print(10);}
                         elif plato == "Hamburguesa"{
                             print(8);}
                         elif plato == "Pasta"{
                             print(12);}
                         elif plato == "Ensalada"{
                             print(6);}
                         else{
                             print("Plato no disponible");}
                     }
        }
    
    """;

        AromaReport report = new AromaReport(code);
        CodeSniffer sniffer = new SwitchStatementSniffer();
        sniffer.sniff(code, report);
        assertTrue(report.stinks());
        assertEquals(1, report.getAromas().size());

    }

    @Test
    public void testNoSwitchStatementSmellDetected() {

        String code = """
                 if edad >= 18{
                     print ("Eres mayor de edad");
                 }else{
                     print ("Eres menor de edad");
                }
    """;



        AromaReport report = new AromaReport(code);
        CodeSniffer sniffer = new SwitchStatementSniffer();
        sniffer.sniff(code, report);
        assertFalse(report.stinks());
        assertEquals(0, report.getAromas().size());

    }















}


