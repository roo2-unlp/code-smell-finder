package oo2.redictado;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import oo2.redictado.FeatureEnvySniffer.FeatureEnvySniffer;

public class FeatureEnvySnifferTest {
    FeatureEnvySniffer codeSniffer;

    @BeforeEach
    public void setUp() {
        codeSniffer = new FeatureEnvySniffer();
    }

    @Test 
    public void testSmell() {
        String code = """
        class Persona{
    def __init__(self, nombre, apellido){
        self.nombre = nombre;
        self.apellido = apellido;
    }
    def nombre_igual(self, nombre){
        return self.nombre == nombre;
    }
}
        """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        assertFalse(report.stinks());
    }
    
    @Test
    public void testSmellSyntaxError() {
        String code = """
                        class Orden\r\n" + //
                        "  def initialize(cliente)\r\n" + //
                        "    @cliente = cliente\r\n" + //
                        "    @total = 0\r\n" + //
                        "  end\r\n" + //
                        "\r\n" + //
                        "  def calcular_descuento\r\n" + //
                        "    if @cliente.suscripcion == 'premium'\r\n" + //
                        "      return @total * 0.2\r\n" + //
                        "    end\r\n" + //
                        "    \r\n" + //
                        "end"
                        """;
        AromaReport report = new AromaReport(code);
        assertThrows(IllegalArgumentException.class, () -> {
			codeSniffer.sniff(code, report);
		});
    }

    @Test
    public void testNotFeatureEnvy() {
        String code = """
                        class Persona{
                            def __init__(self, nombre, apellido){
                                self.nombre = nombre;
                                self.apellido = apellido;
                            }
                            def nombre_igual(self, nombre){
                                return self.nombre == nombre;
                            }
                        }
                        persona = Persona("John", "Lennon");
                        print(persona.nombre_igual("Juan"));

                    """;
                        
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        assertFalse(report.stinks());
    }

    @Test void testInferiorLimit(){
        String code = """
                        class Orden{
                            def __init__(self, cliente){
                                self.cliente= cliente;
                                self.total = 0;
                            }

                            def calcular_descuento(self){
                                if self.cliente.suscripcion == "premium"{
                                    return self.total * 0.2;
                                }
                                return 0;
                            }
                        }
                        """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        assertFalse(report.stinks());
    }

    @Test void testSuperiorLimit(){
        String code = """
                        class Orden{
                            def __init__(self, cliente){
                                self.cliente= cliente;
                                self.total = 0;
                            }

                            def calcular_descuento(self){
                                if self.cliente.suscripcion == "premium"{
                                    return self.total * 0.2;
                                }
                                if self.cliente.order_history > 10{
                                    return self.total * 0.1;
                                }
                                if self.cliente.otra_cosa < 5{
                                    return self.total * 10;
                                }
                                return 0;
                            }
                        }
                        """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        assertTrue(report.stinks());
    }
    
    @Test void testParameterDependence(){
        String code = """
                        class Notificacion{
                            def enviar_notificacion(self, usuario){	
                                if "email" in usuario.contactos{
                                    print("Enviar email a {usuario.nombre_usuario}, que tiene mail{usuario.email}");
                                }
                                print ("No se pudo enviar nada");
                            }    
                        }
                        """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        assertTrue(report.stinks());
    }

    @Test void testNotParameterDependence(){
        String code = """
                        class Notificacion{
                            def enviar_notificacion(self, usuario){
                                if "email" in usuario.contactos{
                                    print("Enviar email a {usuario.nombre_usuario}");
                                }
                                print ("No se pudo enviar nada");
                            }
                        }
                        """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        assertFalse(report.stinks());
    }

    @Test void testMethodSelf(){
        String code = """
                        class VTV{
                            def __init__(self, auto){
                                self.auto = auto;
                                self.precioBase = 20000;
                            }
                            
                            def calcularPrecio(){
                                return self.auto.precioTotal(VTV);
                            }
                        } 
                        """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        assertFalse(report.stinks());
    }

}
