package oo2.redictado;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import oo2.redictado.MessageChainsSniffer.MessageChainsSniffer;

public class MessageChainsSnifferTest {
	MessageChainsSniffer codeSniffer;

	@BeforeEach
	public void setUp() {
		codeSniffer = new MessageChainsSniffer();
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
		assertTrue(report.stinks());
	}

	@Test
	public void testLimiteSuperiorTresLlamadasEnVariasLineas() {
		String code = """
				city_name = person.get_address().get_city().get_name();
				city_name = person.get_address().get_city().get_name();
				city_name = person.get_address().get_city().get_name();
				""";
		AromaReport report = new AromaReport(code);
		codeSniffer.sniff(code, report);
		assertTrue(report.stinks());
		assertEquals(3, report.getAromas().size());
	}

	@Test
	public void testValidacionMasDe5MensajesConcatenados() {
		String code = "country_name = person.get_address().get_city().get_state().get_country().get_name();";
		AromaReport report = new AromaReport(code);
		codeSniffer.sniff(code, report);
		assertTrue(report.stinks());
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
		assertTrue(report.stinks());
	}

	@Disabled
	public void testCadenaUsandoAtributosConcatenados() {
		String code = "car_horsepower = person.car.engine.horsepower;";
		AromaReport report = new AromaReport(code);
		codeSniffer.sniff(code, report);
		assertFalse(report.stinks());
	}

	@Disabled
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
		assertTrue(report.stinks());
	}

	@Test
	public void testCadenaComoRetornoFuncion() {
		String code = """
				def get_customer_phone_model(cust){
					return cust.get_store().get_phone().get_model();
				}
				""";
		AromaReport report = new AromaReport(code);
		codeSniffer.sniff(code, report);
		assertTrue(report.stinks());
	}

	@Test
	public void testCadenaComoCondicion() {
		String code = """
				if (owner.get_garage().get_car().get_year() > 2020) {
					print(\"You have a new car\");
				}
				""";
		AromaReport report = new AromaReport(code);
		codeSniffer.sniff(code, report);
		assertTrue(report.stinks());
	}

	@Test
	public void testCadenaInstruccionAislada() {
		String code = "reader.get_library().get_book().read();";
		AromaReport report = new AromaReport(code);
		codeSniffer.sniff(code, report);
		assertTrue(report.stinks());
	}

}
