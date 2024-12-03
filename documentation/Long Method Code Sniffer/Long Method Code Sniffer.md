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
    Para identificar mediante el codigo creado cuando es un "Long Mehtod" tomamos en cuenta tres cosas fundamentales:
1. **Que sea un método**
    Para que nosotros tomemos en cuenta si analizar o no el codigo, antes que nada, debemos saber que lo que estamos analizando sea un método, caso contrario no analizaremos al mismo.
2. **Limite de líneas**
    Otra cosa importante a  tener en cuenta, es que nuestro codigo no pase un número de lineas en específico, una vez sobrepasado el limite establecido, lo consideraremos un método con mal olor.
3. **Limite de caracteres**
    Por ultimo a tener en cuenta es el límite de caracteres, si bien pueden ser pocas lineas un metodo puede ser largo e ilegible por la cantidad de caracteres que puede tener el mismo, transformandose asi en un mal olor.

# Ejemplos