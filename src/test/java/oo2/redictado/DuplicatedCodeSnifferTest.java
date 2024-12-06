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
    public void testMoreOfOneDuplicateMethod() {
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

                    def metodoC() {  
                        print(1);  
                        print(2);  
                        print(3);  
                    }  

                    def metodoD() {  
                        print(1);  
                        print(2);  
                        print(3);  
                    }  
                }  
                """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        assertTrue(report.stinks());
        assertEquals(3, report.getAromas().size());
    }

    @Test
    public void testNoDuplicatedCode() {
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
    public void testDuplicateConstructor() {
        String code = """
                class persona() {  
                    def __init__(self, nombre, apellido, edad) {  
                        self.nombre = "Lautaro";  
                        self.apellido = "Gubia";  
                        self.edad = 20;  
                    }  
                }  

                class alumno() {  
                    def __init__(self, nombre, apellido, edad) {  
                        self.nombre = "Lautaro";  
                        self.apellido = "Gubia";  
                        self.edad = 20;  
                    }  
                }  
                """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        assertTrue(report.stinks());
        assertEquals(1, report.getAromas().size());
    }

    @Test//quizas no
    public void testDuplicateMethodWithControlStatements(){
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
    
    @Test//quizas no
    public void testMoreOfOneDuplicateMethodWithControlStatements() {
        System.out.println("TEST QUE ME INTERESA");
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
                        
                    def metodoC(a, b) {  
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
        System.out.println("como chilla ella");
        assertTrue(report.stinks());
        assertEquals(2, report.getAromas().size()); 
    }

    @Test
    public void testNoDuplicatedCodeMethodsWithControlStatements() {
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
                            print("No son iguales");  
                        } 
                    }  
                }  
                """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        System.out.println("cantidad"+ report.getAromas().size());
        assertFalse(report.stinks());
    }

    @Test//quizas no
    public void testDuplicatedCodeMethodInsideAndOutsideClass(){
        String code = """
                class Ejemplo() { 
                    def metodoA(){
                        print(1);
                        print(2);
                        print(3);
                    }
                }

                def metodoB(){
                    print(1);
                    print(2);
                    print(3);
                }
                """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        assertTrue(report.stinks());
        assertEquals(1, report.getAromas().size());
    }

    @Test//quizas no
    public void testMoreOfOneDuplicateMethodInsideAndOutsideClass() {
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
    public void testDuplicatedCodeMethodInsideAndOutsideClassWithControlStatements(){
        String code = """
                class Ejemplo() { 
                    def metodoA(){
                        if (a == b) {  
                            print(1);  
                        } else {  
                            print(2);  
                        } 
                    }
                }

                def metodoB(){
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
    public void testMoreOfOneDuplicateMethodInsideAndOutsideClassWithControlStatements() {
        String code = """
                class Ejemplo() { 
                    def metodoA(){
                        if (a == b) {  
                            print(1);  
                        } else {  
                            print(2);  
                        } 
                    }

                    def metodoB(){
                        if (a == b) {  
                            print(1);  
                        } else {  
                            print(2);  
                        } 
                    }
                }

                def metodoC(){
                    if (a == b) {  
                        print(1);  
                    } else {  
                        print(2);  
                    } 
                }

                def metodoD(){
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
        assertEquals(3, report.getAromas().size());
    }

    @Test
    public void testNoDuplicatedCodeMethodsWithNestedControlStatements(){
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

                    def metodoB(){
                        for i in range(3){
                            if (a == b) {  
                                print(1);  
                            } else {  
                                print(2);  
                            } 
                        }
                    }
                }
                """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        assertTrue(report.stinks());
        assertEquals(1, report.getAromas().size());
    }

    

    @Test//quizas no
    public void testDuplicateMethodWithScript(){
        String code = """
                def metodoA(){
                    print(1);
                    print(2);
                    print(3);
                }

                print(1);
                print(2);
                print(3);
                """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        assertTrue(report.stinks());
        assertEquals(1, report.getAromas().size());
    }

    
    @Test//quizas no
    public void testDuplicateMethodWithScriptAndControlStatements(){
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
    public void testDuplicateMethodWithScriptAndControlStatementsAndNestedControlStatements(){
        String code = """
                def metodoA(){
                    for i in range(3){
                        if (a == b) {  
                            print(1);  
                        } else {  
                            print(2);  
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
    public void test(){
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
    public void test2(){
        String code = """
                class Ejemplo(){
                    def metodoA(){
                        a = 0;
                        b = 1;
                        c = 2;
                    }
                }

                a = 0;
                b = 1;
                c = 2;

                class Ejemplo2(){
                    a = 0;
                    b = 1;
                    c = 2;
                }
                """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        assertTrue(report.stinks());
        assertEquals(3, report.getAromas().size());
    }

    @Test
    public void test3(){
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
                    a = 0;
                    b = 1;
                    c = 2;   
                }
                """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        assertFalse(report.stinks());
    }

    @Test
    public void test4(){
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
    public void testAssigns(){
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
    public void testAssignsNoDuplicated(){
        String code = """
                class Ejemplo(){
                    f = 2;
                    d = 1;
                    d = 0;
                }
                class Ejemplo2(){    
                    a = 0;
                    b = 1;
                    c = 2;
                }

                """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        assertFalse(report.stinks());
        assertEquals(1, report.getAromas().size());
    }

    @Test
    public void testAssignsAndScriptDuplicated(){
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
    public void testAssignsAndScriptNoDuplicated(){
        String code = """
                class Ejemplo(){
                    a = 0;
                    b = 1;
                    c = 2;
                }
                    
                    d = 3;
                    e = 4;
                    f = 5;

                """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        assertFalse(report.stinks());
    }

}