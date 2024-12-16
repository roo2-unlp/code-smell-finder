# Data Class Sniffer

## 🚨 Bad Smell: Data Class

### 📜 ¿Por qué es un mal olor?
El **Data Class** es considerado un mal olor en el código porque generalmente indica que la clase está actuando solo como un contenedor de datos sin comportamiento significativo. 
Esto puede llevar a un diseño pobre donde la lógica de negocio se dispersa en múltiples lugares en lugar de estar encapsulada dentro de la clase. 
Además, las clases de datos a menudo violan el principio de encapsulamiento, ya que exponen sus campos directamente a través de getters y setters.

El principal problema con las **Data Classes** es que suelen ser **difíciles de extender** y pueden aumentar el acoplamiento entre clases. Además, no suelen tener ninguna lógica que justifique su existencia, lo que puede conducir a una arquitectura poco flexible y difícil de cambiar.

### 🧑‍💻 Pautas del Sniffer

En esta sección se describen las pautas implementadas en el **DataClassSnifferVisitor**, un visitante utilizado para detectar clases que cumplen con las características de una **Data Class**. El objetivo de este Sniffer es identificar clases en el código que no contienen lógica compleja y solo tienen atributos o un constructor, lo que las clasifica como Data Classes. Esta es una técnica común para detectar clases que sirven como "contenedores de datos", sin una lógica significativa.

Descripción del Código
El Sniffer está implementado en la clase DataClassSnifferVisitor, que extiende BythonParserBaseVisitor<Void>, y recorre el código de la clase en busca de los siguientes patrones:

Detección de Atributos: La variable hasAttributes se establece en true cuando el Sniffer encuentra asignaciones simples, que suelen ser la declaración de atributos en una clase.

Detección de Constructor: Si encuentra un método llamado __init__, se marca la clase como que contiene un constructor, estableciendo la variable hasConstructor en true.

Detección de Métodos Lógicos: Si la clase contiene algún método con lógica, como condiciones (if), bucles (while, for) o retornos (return), se marca la clase como no siendo una Data Class, ya que una Data Class no debe contener lógica compleja.

Para identificar estos métodos, se utiliza la clase MethodLogicVisitor, que busca estructuras de control o expresiones dentro de los métodos.

Identificación de Data Class: Una vez que el Sniffer ha recorrido todos los miembros de la clase, si la clase no contiene lógica (hasLogicMethods == false) y tiene atributos o un constructor, se la clasifica como una "Data Class".

Si se detecta una Data Class, se agrega un informe (Aroma) al objeto report que contiene información sobre la clase detectada.

#### Clases y Métodos Principales
DataClassSnifferVisitor: Visitante principal que recorre las clases y sus métodos para determinar si cumplen con las condiciones de una Data Class.

**visitClassDecl**: Analiza la declaración de la clase y determina si cumple con las condiciones de una Data Class.
**visitMethodDecl**: Analiza los métodos de la clase para verificar si son constructores o contienen lógica.
**visitSimpleAssignment**: Detecta la presencia de atributos en la clase a través de asignaciones simples.
**hasMethodLogic**: Utiliza el visitante MethodLogicVisitor para determinar si un método contiene lógica compleja.
**MethodLogicVisitor**: Visitante que recorre los métodos de la clase y marca si el método contiene lógica, como condicionales, bucles o retornos.

**visitIfStatement, visitWhileStatement, visitForStatement, visitReturnStatement, visitExpression**: Detectan las estructuras de control o expresiones que indican la presencia de lógica en el código.

🎯 **Alcance y Enfoque de las Pruebas**

Para testear y detectar el Bad Smell: Data Class, hemos adoptado las siguientes consideraciones:

### Criterios de detección:

- Solo verificaremos si una clase tiene exclusivamente atributos o un constructor, ya que este es el criterio que hemos definido para clasificar una clase como Data Class.
- Si una clase contiene lógica adicional (métodos con condicionales, bucles o retornos), no será considerada una Data Class.

### Limitaciones:

- El problema de los getters y setters no puede ser completamente validado en nuestras pruebas porque es una cuestión semántica más que sintáctica. Aunque en muchas ocasiones los métodos llamados get o set son indicadores de un diseño pobre en las Data Classes, no podemos garantizar que estos métodos no contengan lógica adicional solo analizando sus nombres o estructuras básicas.
- Por esta razón, nuestra detección de Data Classes se enfoca exclusivamente en identificar clases que:

- Contienen únicamente atributos, o
- Tienen un constructor sin lógica adicional.

### 🧪 Casos de Prueba para Detección de "Data Class"

#### Caso 1: Clase con solo atributos
**Justificación**: La clase Persona solo tiene atributos sin métodos adicionales, lo que genera un mal olor de clase de datos.

```
@Test
public void testDataClassWithOnlyAttributes() {
    String code = """
        class Persona{
            nombre = "";
            edad = 0;
        }
        """;
    
    AromaReport report = new AromaReport(code);
    codeSniffer.sniff(code, report);

    assertTrue(report.stinks()); // Verifica que el código "huele mal"
    assertEquals(1, report.getAromas().size()); // Verifica que se haya detectado un mal olor
}
```

#### Caso 2: Clase con atributos y solo constructor
**Justificación**: La clase Producto tiene un constructor sin métodos adicionales, lo que también genera un mal olor de clase de datos.

```
class Producto:
    def __init__(self, nombre, precio):
        self.nombre = nombre;
        self.precio = precio;
```

**Prueba**: Se verifica que el código huele mal debido a que solo tiene atributos y un constructor.

```
@Test
public void testDataClassWithAttributesAndConstructorOnly() {
    String code = """
        class Producto{
            def __init__(self, nombre, precio){
                self.nombre = nombre;
                self.precio = precio;
            }
        }
        """;
    
    AromaReport report = new AromaReport(code);
    codeSniffer.sniff(code, report);

    assertTrue(report.stinks()); // Verifica que el código "huele mal"
    assertEquals(1, report.getAromas().size()); // Verifica que se haya detectado un mal olor
}
```

#### Caso 3: Clase no data con métodos de lógica
**Justificación**: La clase Cuenta tiene métodos que implementan lógica, por lo que no debería ser marcada como una "data class".

```
class Cuenta:
    def __init__(self, balance):
        self.balance = balance

    def depositar(self, cantidad):
        if cantidad > 0:
            self.balance += cantidad

    def retirar(self, cantidad):
        if cantidad > 0 and cantidad <= self.balance:
            self.balance -= cantidad
```

**Prueba**: Se verifica que la clase no es detectada como "data class" porque tiene lógica implementada en sus métodos.

```
@Test
public void testNonDataClassWithLogicMethods() {
    String code = """
        class Cuenta {
            def __init__(self, balance){
                self.balance = balance;
            }

            def depositar(cantidad){
                if cantidad > 0 {
                    self.balance += cantidad;
                }
            }

            def retirar(cantidad){
                if cantidad > 0 and cantidad <= self.balance{
                    self.balance -= cantidad;
                }
            }
        }
        """;
    
    AromaReport report = new AromaReport(code);
    codeSniffer.sniff(code, report);

    assertFalse(report.stinks()); // Verifica que el código no es detectado como "Data Class".
}
```
#### Caso 4: Clase mixta con algunos métodos de lógica
**Justificación**: La clase Usuario tiene atributos, pero también incluye un método de lógica para determinar si la persona es mayor de edad.

```
class Usuario:
    def __init__(self, nombre, edad):
        self.nombre = nombre
        self.edad = edad

    def es_mayor_de_edad(self):
        return self.edad >= 18
```

**Prueba**: Se verifica que el código no es marcado como "data class" ya que tiene lógica (el método es_mayor_de_edad).

```
@Test
public void testMixedClassWithSomeLogic() {
    String code = """
        class Usuario {
            def __init__(self, nombre, edad){
                self.nombre = nombre;
                self.edad = edad;
            }

            def es_mayor_de_edad(self){
                return self.edad >= 18;
            }
        }
        """;

    AromaReport report = new AromaReport(code);
    codeSniffer.sniff(code, report);

    assertFalse(report.stinks()); // Verifica que el código no es detectado como "Data Class".
}
```
#### Caso 5: Código aleatorio
**Justificación**: Para verificar que un código que no tenga ninguna clase relevante no sea marcado como "data class".

```
print("Esto es solo texto sin relación con clases")
```

**Prueba**: Se verifica que el código no huele mal porque no contiene una clase.

```
@Test
public void testRandomCode() {
    String code = """
        print("Esto es solo texto sin relación con clases");

        def random_function(){
            return 42;
        }
    """;

    AromaReport report = new AromaReport(code);
    codeSniffer.sniff(code, report);

    assertFalse(report.stinks()); // Verifica que el código no "huele mal"
}
```
#### Caso 6: Clase vacía
**Justificación**: Una clase vacía no debe ser detectada como "data class".

```
class ClaseVacia{

}
    
```

**Prueba**: Se verifica que una clase vacía no es marcada como "data class".

```
@Test
public void testEmptyClass() {
    String code = """
        class ClaseVacia {
        }
    """;

    AromaReport report = new AromaReport(code);
    codeSniffer.sniff(code, report);

    assertFalse(report.stinks()); // La clase vacía no debe ser marcada como Data Class
}
```

✍️ **Autores**
- Gustina Guenaga
- Daiana Jennifer Rios Chaure