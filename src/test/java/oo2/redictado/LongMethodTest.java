package oo2.redictado;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

/* IMPORTAR SNIFFER */

public class LongMethodTest {
    
    /* DECLARAR SNIFFER */

    public void testSuperaLineasConMetodos(){
        String code = """
        llamada.setOrigen(origen)
        llamada.setDestino(destino)
        llamada.setDuracion(duracion)

        if duracion > 100:
            llamada.setExtra(50)
        else:
            llamada.setExtra(0)

        llamada.impuestos(origen, destino)  # Corrobora que ambos números sean del mismo país y ejecuta métodos internos
        llamada.calcular_costo(tipo)        # Calcula el costo dependiendo de si es fija o móvil
        llamada.calcular_credito()          # Calcula la deuda restante (con variables ya modificadas)
        llamadas.add(get_llamada())
        """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        assertFalse(report.stinks()); /*no daria ningun stink por eso es false*/
    }

    public void testSuperaLineasSinMetodos(){
        String code = 
            """
            resultado + alfajor precio(); 
            resultado + Cocacola.precio();
            resultado + galletitas.precio(); 
            resultado + barradecereal.precio();
            resultado + medialuna.precio(); 
            resultado + cereales.precio();
            resultado + gomitas.precio();
            resultado + saladix.precio();
            resultado + chicle.precio();
            resultado + mentos.precio(); 
            resultado + oblea.precio();    
            """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        assertTrue(report.stinks()); /*daria stink por eso es true*/
    }

    public void testSuperaCaracteres(){

        String code = 
        """
            if ((vehiculo1.getPeso() > vehiculo2.getPeso()) and (vehiculo1.getTamaño() > vehiculo2.getTamaño())
                and (vehiculo1.getVelocidadPromedio < vehiculo2.getVelocidadPromedio())){
            return true;
            }
            if ((vehiculo1.getCantPuertas() > vehiculo2.getCantPuertas()) and (vehiculo1.getEspacioBaul() > vehiculo2.getEspacioBaul())
                and (vehiculo1.getCantidadLitrosNafta() > vehiculo2.getCantidadLitrosNafta())){
            return true;
            }
            if ((vehiculo1.getCantidadCaballosFuerza() < vehiculo2.getCantidadCaballosFuerza()) and (vehiculo1.getVelocidadMaxima()
                < vehiculo2.getVelocidadMaxima())) {
                return true;    
            }
            
            return false
        """;
        AromaReport report = new AromaReport(code);
        codeSniffer.sniff(code, report);
        assertTrue(report.stinks()); /*daria stink por eso es true*/

    }



}
