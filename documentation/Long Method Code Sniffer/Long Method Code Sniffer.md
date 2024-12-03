# Long Method
## ¿Por qué consideramos que es un mal olor?

El "Long Method" es un mal olor que se le adjudica a un codigo, cuando al programador se le es difícil a la hora de entender, utilizar y RE-utilizar el mismo. 
Se encuentra al mismo, cuando en nuestros métodos hay demasiadas lineas de codigo, responsabilidades o incluso cuando son complejos. Esto puede generar muchos problemas ya que la poca legibilidad o la dificultad de mantenimiento de un codigo es muy importante.

## Tecnicas de Refactoring
Con el fin de dejar nuestro codigo libre de este mal olor se pueden emplear ciertas tecnicas sobre el mismo, el cual dejaria un codigo libre de este bad smell que tantos problemas nos podria traer.

1. **Extract Method:**
    Esta tecnica consiste en extraer fragmentos del método y reemplazarlos por un nuevo metodo con el codigo extraido. Lo que lograremos con esto es una reutilizacion de codigo y una mayor legibilidad.
2. **Replace Temp with Query:**
    Esta tecnica consiste en sustituir las variables temporales que tengamos por métodos que realices las mismas operaciones.
3. **Introduce Parameter Object:**
    Si el método tiene muchos parametros que estan relacionados, se pueden agrupar en un objeto.

## ¿Como lo identificamos mediante nuestro Sniffer?
Para identificar mediante el codigo creado cuando es un "Long Method" tomamos en cuenta tres cosas fundamentales:
1. **Que sea un método**
    Para que nosotros tomemos en cuenta si analizar o no el codigo, antes que nada, debemos saber que lo que estamos analizando sea un método, caso contrario no analizaremos al mismo.
2. **Limite de líneas**
    Otra cosa importante a  tener en cuenta, es que nuestro codigo no pase un número de lineas en específico, una vez sobrepasado el limite establecido, lo consideraremos un método con mal olor.
3. **Limite de caracteres**
    Por ultimo a tener en cuenta es el límite de caracteres, si bien pueden ser pocas lineas un metodo puede ser largo e ilegible por la cantidad de caracteres que puede tener el mismo, transformandose asi en un mal olor.

# Ejemplos

1. **Test que supera las lineas maximas**
    En este test se reporta el mal olor debido a que supera las lineas y caracteres maximos establecidos.
    ```
    def llamadas(origen, destino, duracion, tipo){
            llamada.setOrigen(origen);
            llamada.setDestino(destino);
            llamada.setDuracion(duracion);
            if (duracion > 100) {
                llamada.setExtra(50);
            } else {
                llamada.setExtra(0);
            }
            llamada.impuestos(origen, destino);
            llamada.calcular_costo(tipo);
            llamada.calcular_credito();
            llamadas.add(get_llamada());
        }
    ```
2. **Test que supera las lineas maximas establecidas**
    En este ejemplo se reporta el mal olor debido a que el codigo sobrepasa las lineas maximas establecidas.
    ```
    def resultado(){
            res + alfajor.p; 
            res + cocacola.p;
            res + galletitas.p; 
            res + barradecereal.p;
            res + medialuna.p; 
            res + cereales.p;
            res + gomitas.p;
            res + saladix.p;
            res + chicle.p;
            res + mentos.p; 
            res + oblea.p;
        }
    ```

3. **Test que supera caracteres**
    En este ejemplo de codigo se reporta el bad smell debido a que se excede de los caracteres maximos permitidos.

    ```
    def algo(vehiculo1, vehiculo2){
            if ((vehiculo1.getPeso() > vehiculo2.getPeso() and vehiculo1.getTamanio() > vehiculo2.getTamanio)
                and (vehiculo1.getVelocidadPromedio() < vehiculo2.getVelocidadPromedio())){
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
            
            return false;
        }
    ```

4. **Test el cual no es un método**
    Este ejemplo no reportara mal olor alguno ya que si bien se excede de las lineas maximas permitidas, el long method DEBE ser dentro de un metodo, y en este caso es codigo suelto.
    ```
        monitor1 = 20000;
        monitor2 = 10000;
        mouse = 5000;
        teclado = 7000;
        procesador = 20000;
        placaVideo = 30000;
        ram = 15000;
        fuente = 10000;
        gabinete = 17000;
        coolers = 3000;
        resultado = monitor1 + monitor2 + mouse + teclado + procesador + placaVideo + ram + fuente + gabinete + coolers;
    ```

5. **Test correcto**
    En este ultimo ejemplo se muestra un codigo el cual esta contemplado dentro de todos los limites y parametros establecidos, por lo cual no reportaria un mal olor.
    ```
    def sumar (a,b,c){
            return a + b + c;
        }
    ```