package oo2.redictado;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LongMethodTest {
    
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
        assertTrue(report.stinks()); /*daria stink por eso es true*/
    }
}
