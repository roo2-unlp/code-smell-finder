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
}
