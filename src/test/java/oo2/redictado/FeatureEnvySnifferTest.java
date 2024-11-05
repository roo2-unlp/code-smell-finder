package oo2.redictado;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import oo2.redictado.AnosmicCodeSniffer.*;
import oo2.redictado.FeatureEnvySniffer.FeatureEnvySniffer;
import 

public class FeatureEnvySnifferTest {
    FeatureEnvySniffer codeSniffer;

    @BeforeEach
    public void setUp() {
        codeSniffer = new FeatureEnvySniffer();
    }

    @Test 
    public void testSmell() {
        String code = "print(\"Hello, World!\");";
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        assertFalse(report.stinks());
    }
    
}
