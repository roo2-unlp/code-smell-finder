package oo2.redictado;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import oo2.redictado.DontPassNullSniffer.DontPassNullSniffer;


public class DontPassNullTest {

	DontPassNullSniffer codeSniffer;

	@BeforeEach
	public void setUp() {
		codeSniffer = new DontPassNullSniffer();
	}

	@Test
	public void testPassNullConReturn() {
		String code= """
				def set_name_familia(name, cantHijos){
				 if cantHijos>0{
				return generarCodigo(name, cantHijos);
				}else{
				 return generarCodigo(name, None);
				 }
				}
				""";
		AromaReport report= new AromaReport(code);
		codeSniffer.sniff(code, report);
		assertTrue(report.stinks());
	}
	//Codigo erroneo porque pasa explicitamente None a la funcion generarCodigo que puede o no estar construido para soportar None.

	@Test
	public void testPassNullLLamadoConNull() {
		String code= """
				def main(){
					call(a.getData());
				}
				""";
		AromaReport report= new AromaReport(code);
		codeSniffer.sniff(code, report);
		assertFalse(report.stinks());
	}
	// test de una propiedad de un objeto con valor desconocido.
	// No devuelve mal olor ya que no hay forma de determinar el resultado

	@Test
	public void testPassNullLlamadoConVariablesSinNull(){
		String code="""
					def funcion (a,b){
					sumar(a,b);
				}
				""";
		AromaReport report= new AromaReport(code);
		codeSniffer.sniff(code, report);
		assertFalse(report.stinks());
	}
	//Codigo correcto por uso adecuado de los parametros.

	@Test
	public void testPassNullLlamarConVariableNull() {
	String code="""
			def llamar_funcion(){
				a = None;
				generarString(a);
			}
			""";
	AromaReport report= new AromaReport(code);
	codeSniffer.sniff(code, report);
	assertTrue(report.stinks());
	}
	//Codigo erroneo porque una variable fue inicializada en None y se pasa como parametro sin que esto se modifique.

	@Test
	public void testPassNullLlamarConVariableSinNullModificada() {
		String code="""
			def llamar_funcion(){
				a = None;
				a=50;
				generarString(a);
			}
			""";
		AromaReport report= new AromaReport(code);
		codeSniffer.sniff(code, report);
		assertFalse(report.stinks());
	}
	//Codigo correcto porque una variable fue inicializada en None y luego fue modificada.

	@Test
	public void testPassNullConAsignacionNullAVariable() {
		String code="""
				def comprobar(a, b){
				sumar (b=None);
				}
				""";
		AromaReport report= new AromaReport(code);
		codeSniffer.sniff(code, report);
		assertTrue(report.stinks());
	}
	//test de asignar None en el llamado a la funcion.

	@Test
	public void testPassNullConAsignacionNullAIndexAccess() {
		String code="""
			def comprobar(a, b){
			b[1]=None;
			sumar (b[1]);
			}
			""";
		AromaReport report= new AromaReport(code);
		codeSniffer.sniff(code, report);
		assertTrue(report.stinks());
	}
	//test de asignar None en una posicion de un arreglo.

	@Test
	public void testPassImplicitAssignment() {
		String code="""
			def llamar_funcion(){
				generarString(a+=None);
			}
			""";
		AromaReport report= new AromaReport(code);
		codeSniffer.sniff(code, report);
		assertFalse(report.stinks());
	}
	// test de una operacion al momento de llamar una funcion.
	// No devuelve mal olor ya que no hay forma de determinar el resultado.

}

