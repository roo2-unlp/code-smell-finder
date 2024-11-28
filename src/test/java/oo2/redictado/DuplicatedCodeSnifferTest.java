package oo2.redictado;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import oo2.redictado.DuplicatedCodeSniffer.DuplicatedCodeSniffer;

import static org.junit.jupiter.api.Assertions.*;

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
   




}