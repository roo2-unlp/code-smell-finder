package oo2.redictado;

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
    public void testDuplicatedCodeSniffer() {
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
        System.out.println("en el segundo test de duplicated");
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
    }
    @Test
    public void testDuplicateMethodWithStructure() {
        String code ="""
                        def sum(numeros){ 
                            total = 0;
                            for numero in numeros{
                                total += numero; 
                            }    
                            return print("La suma de los números es: " + total);
                        } 
                        def add(numeros){ 
                            total = 0;
                            for numero in numeros{
                                total += numero; 
                            }    
                            return print("La suma de los números es: " + total);
                        } 
                        """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
    }



}

