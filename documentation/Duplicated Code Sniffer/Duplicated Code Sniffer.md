# 🚨 Duplicated Code

## 📜 ¿Porque es un mal olor?

Dificulta la comprensión y el mantenimiento del software. Duplicar código significa que cualquier cambio deberá realizarse en múltiples lugares, lo que aumenta el riesgo de errores y genera inconsistencias. Además, leer múltiples copias de código similar complica el análisis del programa y puede inducir a errores.

---
## Definicion

Consideramos código duplicado cualquier secuencia o subsecuencia de líneas consecutivas de código que sea textualmente idéntica en contenido y orden. Esto incluye:

Bloques parciales: Coincidencias exactas dentro de métodos, funciones o scripts, aunque no abarque el método completo.
Múltiples ubicaciones: Repeticiones de las mismas líneas en diferentes métodos, scripts.

---

### 🧑‍💻 Pautas del Sniffer

+ **Secuencia de líneas y texto idénticos en scripts**: nuestro umbral de deteccion toma 4 lineas de codigo exacto en scripts
+ **Código Bython**: el sniffer solo analizará sintaxis escrita en Bython; otros lenguajes serán ignorados.
+ **Detección**: el sniffer identificará todos los codigos duplicados que cumplan la definicion y los alertará al finalizar el análisis completo del archivo. Si el archivo no corresponde a Bython, detendrá su ejecución en la primera línea donde detecte un error de sintaxis.

---

## 🧪 Casos de Prueba

### 1

**Justificación**: 

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

### 2

**Justificación**: 

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

### 3

**Justificación**: 

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
        self.marca = "Yamaha";
        self.modelo = "Scooter";
        self.color = "Rojo";
    }
}
```

---

### 4

**Justificación**: 

```bython
def suma(a, y) {
    return a + y;
}

def add(a, y) {
    return a + y;
}
```

---

### 5

**Justificación**: 

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

### 6

**Justificación**: 

```bython
def inicio() {
   lista.add(x);
   print(x); 
}

y= 5
x= 2
elem = x + y
print("suma" + elem)
resta = 4 - 2
print("resta")
```

---

### 7

**Justificación**: 

```bython
def inicio(x, y) {
    print(x);
    print(y);
}

def arrancar(x, y) {
    print(x);
    print(y);
}

def comenzar(x, y) {
    x = 1;
    y = 2;
    print(x);
    print(y);
}

```

---

### 8

**Justificación**: 

```bython
x = elem1 + elem2;
lista.add(x);
print(x);
print(lista);
x = elem1 + elem2;
lista.add(x);
print(x);
print(lista);
x = elem1 + elem2;
lista.add(x);
print(x);
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

### Mas de un caso 

**Justificación**: Este ejemplo presenta un codigo con dos clases, con constructor y metodos identicos, en ambas clases se encuentran tres atributos duplicados (`nombre`, `apellido` y `edad`) en sus constructores, los cuales tienen el mismo nombre y valor asignado. Ademas se encuentran dos métodos (`cumpleanios` y `birthday`) que realizan la misma operación con una secuencia de líneas idéntica. Esto debería ser detectado por el sniffer como código duplicado.

```bython
class Mujer() {
    def __init__(self, nombre, apellido, edad) {
        self.nombre = nombre;
        self.apellido = apellido;
        self.edad = edad;
    }
    
    def cumpleanios(self){
      self.edad += 1;
    }

print(1);
print(2);
print(3);

}

class Hombre() {
    def __init__(self, nombre, apellido, edad) {
        self.nombre = nombre;
        self.apellido = apellido;
        self.edad = edad;
    }
    
    def birthday(self){
      self.edad += 1;
    }

print(1);
print(2);
print(3);
}
```

---

### ✍️ Autores

- **Lautaro José Gubia**  
- **Sofía Martínez Coria**