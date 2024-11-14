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
	assertEquals(1, report.getAromas().size());
}
//Codigo erroneo porque pasa explicitamente None a la funcion generarCodigo que puede o no estar construido para soportar None.
@Test
public void testPassNullLLamadoConNull() {
	String code= """
			def call(aux){
				if aux is None{
				print("No se paso un valor valido");
				}
			 }
			def main(){
				call(None);
			}
			""";
	AromaReport report= new AromaReport(code);
	codeSniffer.sniff(code, report);
	assertTrue(report.stinks());
	assertEquals(1,report.getAromas().size());			
}
//codigo erroneo porque independientemente de que el codigo maneja el caso de que se envie None sigue siendo un error hacerlo
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
	assertEquals(1,report.getAromas().size());	
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
assertEquals(1,report.getAromas().size());		
}

//Codigo erroneo porque una variable fue inicializada en None y se pasa como parametro sin que esto se modifique.
@Test
public void testPassNullControlDeNull() {
	String code="""
			def comprobar(a, b){
			if a is None{
			 sumar (5, b);
				}
			if b is None{
				sumar (a,5);
			 }
			}
			""";
	AromaReport report= new AromaReport(code);
	codeSniffer.sniff(code, report);
	assertFalse(report.stinks());
	assertEquals(1,report.getAromas().size());
}
//Codigo correcto porque se verifica que el parametro no sea None y en caso de que lo sea se evita el uso de ese parametro.
@Test
public void testPassNullConAsignacionNull() {
	String code="""
			def comprobar(a, b){
			sumar (b=None);
			}
			""";
	AromaReport report= new AromaReport(code);
	codeSniffer.sniff(code, report);
	assertTrue(report.stinks());
	assertEquals(1,report.getAromas().size());
}

	@Test
	public void testPassNullConAsignacionNull2() {
		String code="""
			def comprobar(a, b){
			sumar (b.prueba=None);
			}
			""";
		AromaReport report= new AromaReport(code);
		codeSniffer.sniff(code, report);
		assertTrue(report.stinks());
		assertEquals(1,report.getAromas().size());
	}
	@Test
	public void testPassNullConAsignacionNull3() {
		String code="""
			def comprobar(a, b){
			b.prueba=None;
			sumar (b.prueba);
			}
			""";
		AromaReport report= new AromaReport(code);
		codeSniffer.sniff(code, report);
		assertTrue(report.stinks());
		assertEquals(1,report.getAromas().size());
	}
	@Test
	public void testPassNullConAsignacionNull4() {
		String code="""
			def comprobar(a, b){
			b[1]=None;
			sumar (b[1]);
			}
			""";
		AromaReport report= new AromaReport(code);
		codeSniffer.sniff(code, report);
		assertTrue(report.stinks());
		assertEquals(1,report.getAromas().size());
	}
}

