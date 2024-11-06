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
	String code= "def set_name_familia(name, cantHijos){\r\n"
			+ "\r\n"
			+ "if cantHijos>0{\r\n"
			+ "\r\n"
			+ "return generarCodigo(name, cantHijos);\r\n"
			+ "\r\n"
			+ "}else{\r\n"
			+ "\r\n"
			+ "return generarCodigo(name, None);\r\n"
			+ "\r\n"
			+ "}\r\n"
			+ "\r\n"
			+ "}";
	AromaReport report= new AromaReport(code);
	codeSniffer.sniff(code, report);
	assertTrue(report.stinks());
	assertEquals(1, report.getAromas().size());
}
//Codigo erroneo porque pasa explicitamente None a la funcion generarCodigo que puede o no estar construido para soportar None.
@Test
public void testPassNullLLamadoConNull() {
	String code= "def call(aux){\r\n"
			+ "\r\n"
			+ "if aux is None{\r\n"
			+ "\r\n"
			+ "print(\"No se paso un valor valido\");\r\n"
			+ "\r\n"
			+ "}\r\n"
			+ "\r\n"
			+ "}\r\n"
			+ "\r\n"
			+ "def main(){\r\n"
			+ "\r\n"
			+ "call(None);\r\n"
			+ "\r\n"
			+ "}";
	AromaReport report= new AromaReport(code);
	codeSniffer.sniff(code, report);
	assertTrue(report.stinks());
	assertEquals(1,report.getAromas().size());			
}
//codigo erroneo porque independientemente de que el codigo maneja el caso de que se envie None sigue siendo un error hacerlo
@Test
public void testPassNullLlamadoConVariablesSinNull(){
	String code="def funcion (a,b){\r\n"
			+ "\r\n"
			+ "sumar(a,b);\r\n"
			+ "\r\n"
			+ "}";
	AromaReport report= new AromaReport(code);
	codeSniffer.sniff(code, report);
	assertFalse(report.stinks());
	assertEquals(1,report.getAromas().size());	
}
//Codigo correcto por uso adecuado de los parametros.
@Test
public void testPassNullLlamarConVariableNull() {
String code="def llamar_funcion(){\r\n"
		+ "\r\n"
		+ "a = None;\r\n"
		+ "\r\n"
		+ "generarString(a);\r\n"
		+ "\r\n"
		+ "}";
AromaReport report= new AromaReport(code);
codeSniffer.sniff(code, report);
assertTrue(report.stinks());
assertEquals(1,report.getAromas().size());		
}
//Codigo erroneo porque una variable fue inicializada en None y se pasa como parametro sin que esto se modifique.
@Test
public void testPassNullControlDeNull() {
	String code="def comprobar(a, b){\r\n"
			+ "\r\n"
			+ "if a is None{\r\n"
			+ "\r\n"
			+ "sumar (5, b);\r\n"
			+ "\r\n"
			+ "}\r\n"
			+ "\r\n"
			+ "if b is None{\r\n"
			+ "\r\n"
			+ "sumar (a,5);\r\n"
			+ "\r\n"
			+ "}\r\n"
			+ "\r\n"
			+ "}";
	AromaReport report= new AromaReport(code);
	codeSniffer.sniff(code, report);
	assertFalse(report.stinks());
	assertEquals(1,report.getAromas().size());
}
//Codigo correcto porque se verifica que el parametro no sea None y en caso de que lo sea se evita el uso de ese parametro.
}

