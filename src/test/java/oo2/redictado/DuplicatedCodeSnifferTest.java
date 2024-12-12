package oo2.redictado;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import oo2.redictado.DuplicatedCodeSniffer.DuplicatedCodeSniffer;

public class DuplicatedCodeSnifferTest {
    DuplicatedCodeSniffer codeSniffer;

    @BeforeEach
    public void setUp() {
        codeSniffer = new DuplicatedCodeSniffer();
    } 

    @Test
    public void testOneMethod(){
        String code = """
                class Ejemplo() {  
                    def metodoA() {  
                        print(1);  
                        print(2);  
                        print(3);  
                    }  
                }  
                """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        assertFalse(report.stinks());
    }

    @Test
    public void testNoDuplicatedMethod() {
        String code = """
                class Ejemplo() {  
                    def metodoA() {  
                        x = 1;  
                        print(x);  
                        y = 2;
                        print(y);  
                    }  

                    def metodoB() {  
                        print(1);  
                        print(2);  
                        print(3);  
                    }  
                }   
                """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        assertFalse(report.stinks());
    }

    @Test
    public void testDuplicateMethod() {
        String code = """
                class Ejemplo() {  
                    def metodoA() {  
                        print(1);  
                        print(2);  
                        print(3);  
                    }  

                    def metodoB() {  
                        print(1);  
                        print(2);  
                        print(3);  
                    }  
                }  
                """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        assertTrue(report.stinks());
        assertEquals(1, report.getAromas().size());
    }

    @Test
    public void testDuplicatedMethodWithControlStatements(){
        String code = """
                class Ejemplo() {  
                    def metodoA(a, b) {  
                        if (a == b) {  
                            print(1);  
                        } else {  
                            print(2);  
                        } 
                    }  

                    def metodoB(a, b) {  
                        if (a == b) {  
                            print(1);  
                        } else {  
                            print(2);  
                        } 
                    }
                }  
                """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        assertTrue(report.stinks());
        assertEquals(1, report.getAromas().size());
    }

    @Test
    public void testDuplicatedMethodWithFlatCodeAndControlStatements(){
        String code = """
                def metodoA(){
                    if (a == b) {  
                        print(1);  
                    } else {  
                        print(2);  
                    } 
                }

                if (a == b) {  
                    print(1);  
                } else {  
                    print(2);  
                } 
                """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        assertTrue(report.stinks());
        assertEquals(1, report.getAromas().size());
    }

    @Test
    public void testDuplicatedCodeWithFlatCodeMessy(){
        String code = """
                print(1);
                print(2);

                class Ejemplo(){
                    def metodoA(){
                        print(1);
                        print(2);
                        mensaje = "Hola";
                        print(mensaje);
                    }
                }

                mensaje = "Hola";
                print(mensaje);
                """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        assertTrue(report.stinks());
        assertEquals(1, report.getAromas().size());
    }

    @Test
    public void testDuplicatedCodeWithImport(){
        String code = """
                import random;
                a = random.randint(0, 10);

                class Ejemplo(){
                    def metodoA(){
                        import random;
                        a = random.randint(0, 10);
                    }
                }
                """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        assertTrue(report.stinks());
        assertEquals(1, report.getAromas().size());
    }

    @Test
    public void testNoDuplicatedCodedWithImport(){
        String code = """
                import random;
                a = random.randint(0, 10);

                class Ejemplo(){
                    def metodoA(){
                        a = random.randint(0, 10);
                    }
                }
                """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        assertFalse(report.stinks());
    }

    @Test
    public void testMoreOfOneDuplicatedMethodInsideAndOutsideClass() {
        String code = """
                class Ejemplo() { 
                    def metodoA(){
                        print(1);
                        print(2);
                        print(3);
                    }

                    def metodoB(){
                        print(1);
                        print(2);
                        print(3);
                    }
                }

                def metodoC(){
                    print(1);
                    print(2);
                    print(3);
                }

                def metodoD(){
                    print(1);
                    print(2);
                    print(3);
                }
                """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        assertTrue(report.stinks());
        assertEquals(3, report.getAromas().size());
    }

    @Test
    public void testNoDuplicatedCodeFlatCodeWithNestedControlStatements(){
        String code = """
                class Ejemplo() { 
                    def metodoA(number){
                        for i in range(3){
                            if (a == b) {  
                                print(1);  
                            } else {  
                                print(2);  
                            } 
                        }
                    }
                }
                    
                for i in range(3){
                    if (a == b) {  
                        print(1);  
                    } else {  
                        print(2);  
                    } 
                }
                """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        assertTrue(report.stinks());
        assertEquals(1, report.getAromas().size());
    }

    @Test
    public void testDuplicatedMethodWithAssings(){
        String code = """
                class Ejemplo(){
                    def metodoA(){
                        a = 0;
                        b = 1;
                        c = 2;
                    }
                    
                    a = 0;
                    b = 1;
                    c = 2;
                }
                """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        assertTrue(report.stinks());
        assertEquals(1, report.getAromas().size());
    }

    @Test
    public void testNoDuplicatedMethodWithAssings(){
        String code = """
                class Ejemplo(){
                    def metodoA(){
                        a = 0;
                        b = 1;
                        c = 2;
                    }
                    a = 0;
                    b = 1;
                    c = 2;
                    d = 3;   
                }
                """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        assertFalse(report.stinks());
    }

    @Test
    public void testDuplicatedCodeWithAssingsOfOtherClass(){
        String code = """
                class Ejemplo(){
                    def metodoA(){
                        a = 0;
                        b = 1;
                        c = 2;
                    }
                }

                class Ejemplo2(){
                    a = 0;
                    b = 1;
                    c = 2;
                }
                """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        assertTrue(report.stinks());
        assertEquals(1, report.getAromas().size());
    }

    @Test
    public void testDuplicatedAssings(){
        String code = """
                class Ejemplo(){
                    a = 0;
                    b = 1;
                    c = 2;
                }
                
                class Ejemplo2(){
                    a = 0;
                    b = 1;
                    c = 2;
                }
                """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        assertTrue(report.stinks());
        assertEquals(1, report.getAromas().size());
    }

    @Test
    public void testDuplicatedAssingsWithFlatCode(){
        String code = """
                class Ejemplo(){
                    a = 0;
                    b = 1;
                    c = 2;
                }

                a = 0;
                b = 1;
                c = 2;
                """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        assertTrue(report.stinks());
        assertEquals(1, report.getAromas().size());
    }

    @Test
    public void testNoDuplicatedAssingsWithFlatCode(){
        String code = """
                class Ejemplo(){
                    a = 0;
                    b = 1;
                    c = 2;
                }

                a = 0;
                b = 1;
                c = 2;
                d = 3;
                """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        assertFalse(report.stinks());
    }

    @Test
    public void testMultipleCases(){
        String code = """
                class Ejemplo(){
                    def metodoA(){
                        a = 0;
                        b = 1;
                        c = 2;
                    }

                    a = 0;
                    b = 1;
                    c = 2;
                }

                class Ejemplo2(){
                    a = 0;
                    b = 1;
                    c = 2;
                }

                def metodoB(){
                    if (a == b) {  
                        print(1);  
                    } else {  
                        print(2);  
                    }
                }

                if (a == b) {  
                    print(1);  
                } else {  
                    print(2);  
                }
                """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        assertTrue(report.stinks());
        assertEquals(3, report.getAromas().size());
    }

    @Test
    public void testDuplicatedMethodsWithNoContent(){
        String code = """
                def a(){}

                def b(){}
                """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        assertFalse(report.stinks());
        assertEquals(1, report.getAromas().size());
    }

    @Test
    public void testClassEmpty(){
        String code = """
                class Ejemplo(){}

                class Ejemplo2(){}
                """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        assertFalse(report.stinks());
    }

    @Test
    public void testSyntaxError(){
        String code = """
                SELECT columna1, columna2  
                FROM tabla  
                WHERE columna3 = 'valor'; 
                """;
        AromaReport report = new AromaReport(code);
        assertThrows(IllegalArgumentException.class, () -> {
            codeSniffer.sniff(code, report);
        });
    }

}