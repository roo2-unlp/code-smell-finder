package oo2.redictado;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import oo2.redictado.AnosmicCodeSniffer.AnosmicCodeSniffer;

public class MessageChainsSnifferTest {
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
        String code = "print(\\\"Hello, World!\\\")";
        AromaReport report = new AromaReport(code);
        assertThrows(IllegalArgumentException.class, () -> {
            codeSniffer.sniff(code, report);
        });
    }

    @Test
    public void testNingunaCadena() {
        String code = """
        for i in 10 {
            puppy.pet;
        }
        """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        assertFalse(report.stinks());
    }

    @Test
    public void testLimiteInferiorDosLlamadas() {
        String code = "city_name = person.get_address().get_street();";
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        assertFalse(report.stinks());
    }

    @Test
    public void testLimiteSuperiorTresLlamadas() {
        String code = "city_name = person.get_address().get_city().get_name();";
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        assertFalse(report.stinks());
    }

    @Test
    public void testValidacionMasDe5MensajesConcatenados() {
        String code = "country_name = person.get_address().get_city().get_state().get_country().get_name();";
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        assertFalse(report.stinks());
    }

    @Test
    public void testLlamadasAMetodosNoRelacionados() {
        String code = """
        clinic.get_city();
        vet.get_name();
        pet.get_name();
        """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        assertFalse(report.stinks());
    }

    @Test
    public void testLlamadasConsecutivasAMetodosConParametros() {
        String code = "final_price = customer.cart.get_total(21).get_price(10).checkout(50, 21);";
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        assertFalse(report.stinks());
    }

    @Test
    public void testCadenaUsandoAtributosConcatenados() {
        String code = "car_horsepower = person.car.engine.horsepower;";
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        assertFalse(report.stinks());
    }

    @Test
    public void testCadenaDeMetodosYAtributos() {
        String code = "car_horsepower = person.get_car().engine.horsepower;";
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        assertFalse(report.stinks());
    }

    @Test
    public void testCadenaComoParametros() {
        String code = "print_location(person.get_address().get_city().get_state().get_country().get_name());";
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        assertFalse(report.stinks());
    }

}
