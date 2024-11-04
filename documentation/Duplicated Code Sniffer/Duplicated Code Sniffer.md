# 🚨 Duplicated Code

---

## 📜 Definición

El código duplicado es un "mal olor" en la programación, ya que dificulta la comprensión y el mantenimiento del software. Duplicar código significa que cualquier cambio deberá realizarse en múltiples lugares, lo que aumenta el riesgo de errores y genera inconsistencias. Además, leer múltiples copias de código similar complica el análisis del programa y puede inducir a errores.

---

### 🧑‍💻 Pautas del Sniffer

+ **Relaciones de herencia y clases derivadas**: no serán analizadas, ya que pueden estar definidas en archivos separados que el sniffer no recibe.
+ **Atributos en constructores** (`__init__`): serán considerados duplicados si se encuentran al menos tres atributos con el mismo nombre y valor asignado, sin importar el orden ni la cantidad de líneas del constructor.
+ **Código Bython**: El sniffer solo analizará sintaxis escrita en Bython; otros lenguajes serán ignorados.

---

## 🧪 Casos de Prueba

### Tres atributos repetidos en dos constructores

**Justificación**: En dos clases se encuentran tres atributos duplicados (`color`, `marca` y `modelo`) en sus constructores. Como estos atributos tienen el mismo nombre y valores asignados, el sniffer debería detectar esto como duplicación.

```bython
class Coche() {
    def __init__(self, marca, modelo, color, asientos) {
        self.marca = marca;
        self.modelo = modelo;
        self.color = color;
        self.encendido = False;
        self.asientos = asientos;
        self.velocidad = 0;
    }
}

class Moto() {
    def __init__(self, marca, modelo, color) {
        self.marca = marca;
        self.modelo = modelo;
        self.color = color;
    }
}
```

---

### Atributos no repetidos en dos constructores

**Justificación**: En este ejemplo, dos clases (`Persona` y `Moto`) no presentan atributos duplicados en sus constructores. El sniffer no debería detectar esto como un problema.

```bython
class Persona() {
    def __init__(self, nombre, apellido) {
        self.nombre = nombre;
        self.apellido = apellido;
    }
}

class Moto() {
    def __init__(self, marca, modelo, color) {
        self.marca = marca;
        self.modelo = modelo;
        self.color = color;
    }
}
```

---

### Menos de tres atributos repetidos en dos constructores

**Justificación**: En dos clases se encuentra un atributo duplicado, `nombre`, en sus constructores. Aunque el atributo tiene el mismo nombre
y almacena el mismo tipo de valor, al ser solo uno, no alcanza el umbral para que el sniffer lo detecte como problema.

```bython
class Persona() {
    def __init__(self, nombre, apellido) {
        self.nombre = nombre;
        self.apellido = apellido;
    }
}

class Empresa() {
    def __init__(self, nombre, empleados) {
        self.nombre = nombre;
        self.empleados = empleados;
    }
}
```

---

### Variables de instancia con mismo nombre pero distinto valor asignado

**Justificación**: Ambas clases (`Coche` y `Moto`) contienen atributos `marca`, `modelo` y `color`, pero con valores diferentes asignados. Este caso no debería ser considerado un problema, ya que el sniffer solo detecta duplicación si los atributos tienen el mismo nombre y valor asignado.

```bython
class Coche() {
    def __init__(self, marca, modelo, color, asientos) {
        self.marca = marca;
        self.modelo = modelo;
        self.color = color;
        self.encendido = False;
        self.asientos = asientos;
        self.velocidad = 0;
    }
}

class Moto() {
    def __init__(self, modelo, color) {
        self.marca = "Yamaha";
        self.modelo = "Scooter";
        self.color = "Rojo";
    }
}
```

---

### Métodos con la misma estructura

**Justificación**: En este caso, dos métodos (`suma` y `add`) realizan la misma operación de suma con una estructura idéntica, lo cual debería ser identificado como código duplicado por el sniffer.

```bython
def suma(a, y) {
    return a + y;
}

def add(elem1, elem2) {
    return elem1 + elem2;
}
```

---

### Estructura perteneciente a un método repetida en otro

**Justificación**: En este ejemplo, el `metodoA` repite una secuencia de operaciones que ya está definida en `metodoB` de la misma clase. Este patrón debería ser reconocido como duplicación.

```bython
class Ejemplo() {

   def metodoA() { 
      valor1 = 20.5;
      print("Inicio"); 
      resultado = 5 * 4;
      print("Fin"); 
   }
   
   def metodoB() {
      print("Inicio"); 
      resultado = 5 * 4;
      print("Fin");
   }
   
}
```

---

### Estructura perteneciente a un método repetida en un script

**Justificación**: En este caso, el script contiene una secuencia de operaciones idéntica a la que está definida en el método `inicio`, lo cual representa código duplicado.

```bython
def inicio() {
    print("1")
    print("2")
    print("3")
}

print("1")
print("2")
print("3")
suma = 1 + 3
print("suma")
resta = 4 - 2
print("resta")
```

---

### Métodos con diferente estructura

**Justificación**: Los métodos `suma` y `resta` tienen estructuras diferentes, por lo que el sniffer no debería identificarlos como duplicados.

```bython
def suma(x, y) {
    return x + y;
}

def resta(x, y) {
    return x - y;
}
```

---

### Repetición de secuencias de operaciones en script

**Justificación**: Este script muestra una repetición de la misma secuencia de operaciones, que incluye una suma, la adición del resultado a una lista y la impresión del resultado, lo cual representa un patrón duplicado. Esto debería ser identificado como duplicación por el sniffer.

```bython
x = elem1 + elem2;
lista.add(x);
print(x);
print(lista);
y = elem2 + elem3;
lista.add(y);
print(y);
print(lista);
```

---

### No hay repetición de secuencias de operaciones en script

**Justificación**: En este script, cada bloque realiza acciones diferentes, sin secuencias repetidas, lo cual indica una estructura única que no debería ser detectada como duplicada.

```bython
x = elem1 + elem2;
lista.add(x);
print("algo");
print(x);
print(lista);
y = elem2 + elem3;
lista.add(y);
print(y);
print(lista);
```

---

### Código que no es Bython

**Justificación**: Este ejemplo presenta código SQL, el cual no está escrito en Bython y, por lo tanto, no debería ser analizado por el sniffer.

```SQL
SELECT columna1, columna2
    WHERE columna3 = 'valor';
```

---

### ✍️ Autores

- **Lautaro José Gubia**  
- **Sofía Coria Martínez**

---