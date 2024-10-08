package oo2.redictado;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import oo2.redictado.AnosmicCodeSniffer.*;

public class AnosmicCodeSnifferTest {
    AnosmicCodeSniffer codeSniffer;

    @BeforeEach
    public void setUp() {
        codeSniffer = new AnosmicCodeSniffer();
    }

    @Test
    public void testSmell() {
        String code = "print(\"Hello, World!\");";
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        assertFalse(report.stinks());
    }

    @Test
    public void testSmellSyntaxError() {
        String code = "for (int i = 0; i < 10; i++) {}";
        AromaReport report = new AromaReport(code);
        assertThrows(IllegalArgumentException.class, () -> {
            codeSniffer.sniff(code, report);
        });
    }
}
