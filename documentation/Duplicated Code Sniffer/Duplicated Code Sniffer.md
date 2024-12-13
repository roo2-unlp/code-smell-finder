# 🚨 Código Duplicado  

## 📜 ¿Por qué es un mal olor?  

El **código duplicado** dificulta la comprensión y el mantenimiento del software. Repetir bloques de código significa que cualquier modificación debe realizarse en múltiples lugares, aumentando el riesgo de errores e inconsistencias. Además, genera redundancia que complica el análisis del programa y reduce su eficiencia.  

**Evitar el código duplicado mejora:**  
- La claridad del programa.  
- La capacidad de realizar cambios rápidamente.  
- La calidad general del software al minimizar errores.

---  

## ✏️ Definición  

Consideramos **código duplicado** cualquier **secuencia de líneas** consecutivas textualmente idénticas dentro del **cuerpo de métodos o bloques de código plano**, independientemente de su ubicación.
El análisis se centra en detectar repeticiones exactas dentro de los métodos o funciones y en bloques de código plano.

---  

### 📝 Pautas del Sniffer  

1. **Foco en el Cuerpo del Método o Función:**  
   El análisis se limitará a las líneas comprendidas dentro de las llaves de los métodos o funciones.
2. **Bloques de código plano:**
   Un bloque de código plano son las líneas que no pertenecen al cuerpo de un método que son agrupadas según su origen.
4. **Forma de comparación:**
   La comparación de los bloques se realiza carácter por carácter.
3. **Exclusión de Firmas:**  
   Las diferencias en nombres, firmas o parámetros de métodos no afectan la detección si los cuerpos son idénticos.  
4. **Soporte Exclusivo de Bython:**  
   El sniffer solo analizará código escrito en Bython; otros lenguajes serán ignorados.  
5. **Errores y Alertas:**  
   Todos los casos de código duplicado serán alertados tras analizar el archivo completo. Si el archivo no corresponde a Bython, el análisis finalizará al detectar la primera línea inválida.

---

### 🧑‍💻 Detalles técnicos

La clase `DuplicatedCodeSniffer` utiliza un objeto que implementa el patrón **Visitor**, llamado `DuplicatedCodeVisitor`.  
🔄 Este visitor recorre un árbol sintáctico generado por **ANTLR** a partir del código proporcionado.  
📊 Durante el recorrido, recopila información relevante para comparar y generar alertas en caso de detectar duplicaciones.

---

### 🛠️ ¿Cómo utilizarlo? 

1. **Crear una instancia** 🏗️  
   Inicia creando una instancia de la clase `DuplicatedCodeSniffer`.

2. **Analizar el código** 🔍  
   Utiliza el método `sniff` de `DuplicatedCodeSniffer` para analizar el código.

3. **Parámetros requeridos** 📋  
   El método `sniff` necesita:  
   - 📝 Una instancia de la clase `AromaReport`.  
   - 💻 El código que deseas analizar.

4. **Resultados del análisis** ✅❌  
   - ✅ Si no hay duplicaciones, no se generarán alertas en el `AromaReport`.  
   - ❌ Si hay duplicaciones, `AromaReport` almacenará los "malos olores" (problemas detectados).  
   Usa el método `report.stinks()` para verificar si hay duplicaciones.  
   - Este método devuelve un valor **booleano** (`true` o `false`):  
        - `true`: Se encontraron duplicaciones.  
        - `false`: No se encontraron duplicaciones. 
    
---

## 🧪 Casos de Prueba  

### **Caso 1: Un único método**  

**Justificación:** El cuerpo dentro del método `metodoA()` es único y no tiene ninguna secuencia duplicada con otro cuerpo o bloque de código plano, por lo que el sniffer no detecta código duplicado.  

```python
class Ejemplo() {  
    def metodoA() {  
        print(1);  
        print(2);  
        print(3);  
    }  
}  
```  

---

### **Caso 2: Dos métodos con cuerpos diferentes**  

**Justificación:** El cuerpo de `metodoA` y `metodoB` contiene líneas de código distintas. El sniffer no detecta código duplicado.

```python
class Ejemplo() {  
    def metodoA() {  
        x = 1;  
        print(x);  
        y = 2;  
        print(y);  
    }  

    def metodoB() {  
        print(1);  
        print(2);  
        print(3);  
    }  
}  
```  

---

### **Caso 3: Dos métodos con cuerpos idénticos**  

**Justificación:** El cuerpo de `metodoA` es idéntico al de `metodoB`. Ambos comparten exactamente las mismas tres líneas de código en el mismo orden, por lo que el sniffer detecta código duplicado.

```python
class Ejemplo() {  
    def metodoA() {  
        print(1);  
        print(2);  
        print(3);  
    }  

    def metodoB() {  
        print(1);  
        print(2);  
        print(3);  
    }  
}  
```  

---

### **Caso 4: Dos métodos con cuerpos idénticos y estructuras de control**  

**Justificación:** Ambos métodos tienen cuerpos idénticos, incluyendo las estructuras de control, por lo que el sniffer detecta código duplicado.

```python
class Ejemplo() {  
    def metodoA(a, b) {  
        if (a == b) {  
            print(1);  
        } else {  
            print(2);  
        }  
    }  

    def metodoB(a, b) {  
        if (a == b) {  
            print(1);  
        } else {  
            print(2);  
        }  
    }  
}  
```  

---

### **Caso 5: Cuerpo de método y bloque de código plano duplicado**  

**Justificación:** El cuerpo de `metodoA` es idéntico al bloque de código plano, por lo que el sniffer detecta código duplicado.

```python
def metodoA() {  
    if (a == b) {  
        print(1);  
    } else {  
        print(2);  
    }  
}  

if (a == b) {  
    print(1);  
} else {  
    print(2);  
}  
```  

---

### **Caso 6: Código duplicado entre método y bloque de código**  

**Justificación:** El cuerpo del `metodoA` es idéntico al bloque de código plano.
Que las líneas de código plano no sean consecutivas no altera el análisis, ya que el sniffer las concatena en un bloque de líneas consecutivas.  

```python
print(1);  
print(2);  

class Ejemplo() {  
    def metodoA() {  
        print(1);  
        print(2);  
        mensaje = "Hola";  
        print(mensaje);  
    }  
}  

mensaje = "Hola";  
print(mensaje);  
```  

---

### **Caso 7: Código duplicado entre método y bloque de código plano con imports**  

**Justificación:** El cuerpo del `metodoA` es idéntico al bloque de código plano.  

```python
import random;  
a = random.randint(0, 10);  

class Ejemplo() {  
    def metodoA() {  
        import random;  
        a = random.randint(0, 10);  
    }  
}  
```  

---

### **Caso 8: Diferencia entre método y bloque de código plano con imports**  

**Justificación:** El cuerpo del `metodoA` es diferente al bloque de código plano.  

```python
import random;  
a = random.randint(0, 10);  

class Ejemplo() {  
    def metodoA() {  
        a = random.randint(0, 10);  
    }  
}  
```  

---

### **Caso 9: Métodos y funciones con cuerpos idénticos**  

**Justificación:** Los cuerpos de los métodos `metodoA`, `metodoB` y los cuerpos de las funciones `funcionC` y `funcionD` son idénticos, presentan las mismas tres líneas de código en el mismo orden. 

```python
class Ejemplo() {  
    def metodoA() {  
        print(1);  
        print(2);  
        print(3);  
    }  

    def metodoB() {  
        print(1);  
        print(2);  
        print(3);  
    }  
}  

def funcionC() {  
    print(1);  
    print(2);  
    print(3);  
}  

def funcionD() {  
    print(1);  
    print(2);  
    print(3);  
}  
```  

---

### **Caso 10: Código duplicado entre cuerpo de método y bloque de código plano con estructuras de control anidadas**  

**Justificación:** El cuerpo del `metodoA` es identico al bloque de código plano y presenta estructura de control anidadas.   

```python
class Ejemplo() {  
    def metodoA(number) {  
        for i in range(3) {  
            if (a == b) {  
                print(1);  
            } else {  
                print(2);  
            }  
        }  
    }  
}  

for i in range(3) {  
    if (a == b) {  
        print(1);  
    } else {  
        print(2);  
    }  
}  
```  

---

### **Caso 11: Código plano idéntico en distintas ubicaciones**  

**Justificación:** Las asignaciones de la clase `Ejemplo` son idénticas al cuerpo del método `metodoA`.

```python
class Ejemplo() {  
    def metodoA() {  
        a = 0;  
        b = 1;  
        c = 2;  
    }  

    a = 0;  
    b = 1;  
    c = 2;  
}  
```  

---
### **Caso 12: Código plano distinto en distintas ubicaciones** 

**Justificación:** Las asignaciones de la clase `Ejemplo` son diferentes al cuerpo del método `metodoA`.

```python
class Ejemplo(){
    def metodoA(){
        a = 0;
        b = 1;
        c = 2;
    }
    a = 0;
    b = 1;
    c = 2;
    d = 3;   
}
```

---
### **Caso 13: Cuerpo de método idéntico con asignaciones de otra clase** 

**Justificación:** Las asignaciones de la clase `Ejemplo2` son idénticas al cuerpo del método `metodoA`.

```python
class Ejemplo(){
    def metodoA(){
        a = 0;
        b = 1;
        c = 2;
    }
}

class Ejemplo2(){
    a = 0;
    b = 1;
    c = 2;
}
```

---
### **Caso 14: Asignaciones de dos clases duplicadas** 

**Justificación:** Las asignaciones de la clase `Ejemplo` son idénticas a las de la clase `Ejemplo2`.

```python
class Ejemplo(){
    a = 0;
    b = 1;
    c = 2;
}

class Ejemplo2(){
    a = 0;
    b = 1;
    c = 2;
}
```

---
### **Caso 15: Código plano y asignaciones de una clase duplicados** 

**Justificación:** Las asignaciones de la clase `Ejemplo` son idénticas al bloque de código plano.

```python
class Ejemplo(){
    a = 0;
    b = 1;
    c = 2;
}

a = 0;
b = 1;
c = 2;

```

---
### **Caso 16: Código plano y asignaciones de una clasae diferentes** 

**Justificación:** Las asignaciones de la clase `Ejemplo` son diferentes al bloque de código plano.

```python
class Ejemplo(){
    a = 0;
    b = 1;
    c = 2;
}

a = 0;
b = 1;
c = 2;
d = 3;
```

---
### **Caso 17: Más de un caso** 

**Justificación:** El cuerpo del método `metodoA` es idéntico a las asignaciones de la clase `Ejemplo` y a las asignaciones de la clase `Ejemplo2`.
Por otro lado, el cuerpo de la función `funcionB` es idéntico al bloque de código plano y poseen estructuras de control duplicadas. 


```python
class Ejemplo(){
    def metodoA(){
        a = 0;
        b = 1;
        c = 2;
    }

    a = 0;
    b = 1;
    c = 2;
}

class Ejemplo2(){
    a = 0;
    b = 1;
    c = 2;
}

def funcionB(){
    if (a == b) {  
        print(1);  
    } else {  
        print(2);  
    }
}

if (a == b) {  
    print(1);  
} else {  
    print(2);  
}
```

---
### **Caso 18: Funciones sin cuerpo** 

**Justificación:** Ambas funciones no presentan cuerpo, el sniffer compara bloques de código, por lo que métodos o funciones sin cuerpo no serán identificados como duplicados.

```python
def a(){}

def b(){}
```

---
### **Caso 19: Clases sin cuerpo** 

**Justificación:** Ambas clases no presentan asignaciones, el sniffer no detecta código duplicado

```python
class Ejemplo(){}

class Ejemplo2(){}
```

---
### **Caso 20: Código no escrito en Bython**  

**Justificación:** Este ejemplo contiene código en SQL, el cual no será analizado por el sniffer, ya que no está escrito en Bython.  

```SQL  
SELECT columna1, columna2  
FROM tabla  
WHERE columna3 = 'valor';  
```  

---  

## ✍️ Autores  

- **Lautaro José Gubia**  
- **Sofía Martínez Coria**