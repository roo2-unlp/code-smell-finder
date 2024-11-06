package oo2.redictado;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import oo2.redictado.PrintSniffer.PrintSniffer;

public class PrintSnifferTest {
    PrintSniffer codeSniffer;

    @BeforeEach
    public void setUp() {
        codeSniffer = new PrintSniffer();
    }
    
    @Test
    public void testPrints() {
        String code = "print(\"Hello, World!\");";
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);

        assertTrue(report.stinks());
        assertEquals(1, report.getAromas().size());
    }

    @Test
    public void testMorePrints() {
        String code = """
               x = 5;

               if (x > 0) {
                   print("Positive number");
                   print("Value of x is: " + x);
               }

               if (x < 10) {
                   print("x is less than 10");
               }

               for i in range(3) {
                   print("Iteration number: " + i);
               }

               print("End of the program");
               """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);

        assertTrue(report.stinks());
        assertEquals(5, report.getAromas().size());
    }

    @Test
    public void testNoPrints() {
        String code = "person = Person(\"Alice\", 30);";
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);

        assertFalse(report.stinks());
    }

    @Test
    public void testSyntaxError() {
        String code = "for (int i = 0; i < 10; i++) {}";
        AromaReport report = new AromaReport(code);
        
        assertThrows(IllegalArgumentException.class, () -> {
            codeSniffer.sniff(code, report);
        });
    }
    
    @Test
    public void testSwitchStatementSmellDetected_1(){
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
        }
    }
}


perro = Perro("Jaime", "Caniche");
perro.bark_smell();
                """;
    AromaReport report = new AromaReport(code);
    codeSniffer.sniff(code, report);

    assertTrue(report.stinks());
    assertEquals(1, report.getAromas().size());
    }


    @Test
    public void testNoBadSmell_1(){
        String code= """
                class Animal(){
    def init(self, tipo_animal){
        this.tipo_animal = tipo_animal;
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
    codeSniffer.sniff(code, report);

    assertFalse(report.stinks());
    }

    
@Test
public void testSwitchStatementSmellDetected_2() {
    // Un código con muchos elif
    String code = """
           if x == 1:
               print("Lunes");
           elif x == 2:
               print("Martes");
           elif x == 3:
               print("Miércoles");
           elif x == 4:
               print("Jueves");
           elif x == 5:
               print("Viernes");
           elif x == 6:
               print("Sábado");
           else:
               print("Domingo");
           """;
    AromaReport report = new AromaReport(code); //analiza el codigo para determinar si huele mal
    codeSniffer.sniff(code, report);

    assertTrue(report.stinks()); // Debería oler mal
    assertEquals(1, report.getAromas().size());
}

@Test
    public void testNoSwitchStatementSmell_1() {
        // Un código con solo un if-else simple
        String code = """
               if x == 1:
                   print("Lunes");
               else:
                   print("Otro día");
               """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);

        assertFalse(report.stinks()); // No debería oler mal
    }
    

    @Test
    public void testCalcularImpuestoBadSmell() {
        String code = """
            def calcular_impuesto_bad_smell(categoria_producto, precio):
                if categoria_producto == "alimento":
                    return precio * 0.05  # Impuesto del 5%
                elif categoria_producto == "ropa":
                    return precio * 0.1  # Impuesto del 10%
                elif categoria_producto == "electrónica":
                    return precio * 0.15  # Impuesto del 15%
                elif categoria_producto == "muebles":
                    return precio * 0.12  # Impuesto del 12%
                else:
                    return "Categoría de producto inválida"
            """;

        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);

        // Verifica que el código tiene un bad smell
        assertTrue(report.stinks(), "El código tiene un bad smell (switch statement smell).");
        
        // Verifica que se detectaron aromas
        assertEquals(1, report.getAromas().size(), "El número de aromas debe ser 1.");
    }

    @Test
    public void testNoBadSmell_2() {
        String code = """
            def calcular_impuesto_buen(categoria_producto, precio):
                impuestos = {
                    "alimento": 0.05,
                    "ropa": 0.1,
                    "electrónica": 0.15,
                    "muebles": 0.12
                }
                return precio * impuestos.get(categoria_producto, 0) if categoria_producto 
                != "libros" else "Categoría de producto inválida"
            """;

        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);

        // Verifica que no haya bad smell
        assertFalse(report.stinks(), "El código no debe tener bad smell.");
        
        // Verifica que no se detectaron aromas
        assertEquals(0, report.getAromas().size(), "No debe haber aromas.");
    }
}

