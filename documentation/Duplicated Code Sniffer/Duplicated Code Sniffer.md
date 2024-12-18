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
5. **Exclusión de Firmas:**  
   Las diferencias en nombres, firmas o parámetros de métodos no afectan la detección si los cuerpos son idénticos.  
6. **Soporte Exclusivo de Bython:**  
   El sniffer solo analizará código escrito en Bython; otros lenguajes serán ignorados.  
7. **Errores y Alertas:**  
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
**Descripción:** Este caso de prueba contiene una clase `Ejemplo()` con un solo cuerpo de método `metodoA()`, sin secuencias duplicadas con otros cuerpos de métodos o bloques de código plano. Por lo tanto, el sniffer no detecta código duplicado.

**Justificación:** Se establece como control inicial para verificar que el análisis funcione correctamente cuando solo hay un único método en el código. Al no existir otro bloque de código duplicado, el sniffer debe finalizar el análisis sin generar falsos positivos.

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
**Descripción:** Este caso contiene una clase `Ejemplo()` con dos métodos `metodoA` y `metodoB`, los cuales contienen bloques de código que son distintos entre sí. Por lo tanto, el sniffer no detecta código duplicado.

**Justificación:** Este caso prueba que el análisis no debe identificar duplicación cuando los cuerpos de dos métodos son textualmente distintos, carácter por carácter. Es útil para validar la precisión del sniffer al descartar código no duplicado.



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
**Descripción:** El cuerpo de `metodoA` es idéntico al de `metodoB`. Ambos comparten exactamente las mismas tres líneas de código en el mismo orden, por lo que el sniffer detecta código duplicado

**Justificación:** Este caso evalúa la detección de duplicación exacta entre dos métodos de una misma clase. Al tener cuerpos textualmente idénticos, el sniffer debe ser capaz de identificar la duplicación sin considerar diferencias en las firmas.


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
**Descripción:** Ambos métodos tienen cuerpos idénticos, incluyendo las estructuras de control, por lo que el sniffer detecta código duplicado.

**Justificación:** Este caso se introduce para verificar que el sniffer detecte duplicación exacta en métodos con estructuras de control idénticas (if-else). Permite validar que el análisis compare solo los bloques enteros de los metodos y no los bloques mas internos correspondientes a las estructuras de control.

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
**Descripción:** Este caso contiene el cuerpo de `metodoA` que es idéntico al bloque de código plano, por lo que el sniffer detecta código duplicado.

**Justificación:** Este caso establece que la duplicación también puede ocurrir entre métodos y bloques de código plano. Evalúa la capacidad del sniffer para realizar las comparaciones entre estos bloques.

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

### **Caso 6: Código duplicado entre método y bloque de código no consecutivo**  
**Descripción:** Este caso presenta el cuerpo del `metodoA` idéntico al bloque de código plano. Donde las líneas de código plano no son consecutivas.

**Justificación:** En este caso se profundiza en la duplicación entre un método y un bloque de código plano, se utiliza para mostrar que aunque las líneas de código plano no sean consecutivas, el sniffer las concatena formando un bloque de código plano para utilizar en el análisis.

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
**Descripción:** El cuerpo del `metodoA` es idéntico al bloque de código plano.

**Justificación:** En este caso se introducen bloques de codigo idénticos que incluyen importaciones (import random) en métodos y bloques planos. Sirve para demostrar que el sniffer no excluye líneas de código plano especiales y las utiliza el análisis.
 
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
**Descripción:** El cuerpo del `metodoA` es diferente al bloque de código plano.  

**Justificación:** Este caso contrasta el anterior, verifica que el sniffer no detecte duplicación cuando hay bloques de codigo con diferencias (aunque estas diferencias incluyan lineas de codigo especiales). 

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
**Descripción:** Los cuerpos de los métodos `metodoA`, `metodoB` y los cuerpos de las funciones `funcionC` y `funcionD` son idénticos, presentan las mismas tres líneas de código en el mismo orden. 

**Justificación:** Este caso evalúa la duplicación en bloques provenientes de métodos y funciones. Demuestra que el sniffer detecta cuerpos duplicados independientemente de su ubicación y en más de un caso.


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
**Descripción:** El cuerpo del `metodoA` es identico al bloque de código plano y presenta estructura de control anidadas.  

**Justificación:** Este caso establece que incluso estructuras anidadas (if-else dentro de un bucle for) deben ser evaluadas como un bloque completo. Permite verificar que el sniffer no omita duplicaciones en estructuras anidadas.


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
**Descripción:** Las asignaciones de la clase `Ejemplo` son idénticas al cuerpo del método `metodoA`.

**Justificación:** Este caso verifica que el análisis detecte duplicación entre un bloque de código plano y un método que se encuentren dentro de una misma clase. Es importante para garantizar que el sniffer no dependa de la ubicación del código.


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
**Descripción:** Las asignaciones de la clase `Ejemplo` son diferentes al cuerpo del método `metodoA`.

**Justificación:** Este caso contrasta el anterior, sirve para validar que, en ausencia de coincidencias exactas entre un bloque de código plano y un método de una misma clase, el sniffer no reporte falsos positivos.


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
**Descripción:** Las asignaciones de la clase `Ejemplo2` son idénticas al cuerpo del método `metodoA`.

**Justificación:** Este caso evalua la capacidad del sniffer para encontrar la duplicación entre cuerpos de métodos y bloques de código plano que esten dentro de diferentes clases.   

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
**Descripción:** Las asignaciones de la clase `Ejemplo` son idénticas a las de la clase `Ejemplo2`, lo que genera código duplicado entre ambas clases.

**Justificación:** Este caso valida que el sniffer al comparar bloques de código plano identicos entre clases diferentes detecte duplicación.

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
**Descripción:**Las asignaciones de la clase `Ejemplo` son idénticas al bloque de código plano.

**Justificación:** Este caso verifica la duplicación entre bloques planos externos e internos de una clase. Se establece para cubrir la interacción entre los distintos origenes de bloques.

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
### **Caso 16: Código plano y asignaciones de una clase diferentes** 
**Descripción:** Este caso contiene asignaciones en la clase `Ejemplo` que son diferentes al bloque de código plano.

**Justificación:** Este caso contrasta con el anterior al introducir diferencias mínimas. Permite validar que el análisis carácter por carácter no detecte duplicación cuando los bloques no son exactamente idénticos.

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
**Descripción:** El cuerpo del método `metodoA` es idéntico a las asignaciones de la clase `Ejemplo` y a las asignaciones de la clase `Ejemplo2`.
Por otro lado, el cuerpo de la función `funcionB` es idéntico al bloque de código plano y poseen estructuras de control duplicadas. 

**Justificación:** Este caso se establece para validar la detección múltiple de duplicación en un mismo archivo. Permite verificar que el sniffer identifique todos los casos relevantes y genere alertas adecuadas.


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
**Descripción:** Este caso contiene dos funciones `a()` y `b()`, ambas funciones no presentan cuerpo, por lo que no serán identificados como duplicados.

**Justificación:** Dado que el sniffer, compara bloques de código, se debe verificas que este maneje funciones o métodos sin cuerpo correctamente, descartando escenarios donde no existen líneas a comparar.

```python
def a(){}

def b(){}
```

---
### **Caso 19: Clases sin cuerpo** 
**Descripción:** En este caso, hay dos clases `Ejemplo()` y `Ejemplo2()`, ambas clases no contienen ningún bloque de código plano ni cuerpos de métodos. El sniffer no detecta código duplicado debido a la ausencia de líneas consecutivas.

**Justificación:** Verifica que el sniffer no genere alertas ni realice un análisis innecesario cuando las clases no contienen ningún bloque de código, conforme a las reglas de detección de código duplicado. 

```python
class Ejemplo(){}

class Ejemplo2(){}
```

---
### **Caso 20: Código no escrito en Bython**  
**Descripción:** Este caso contiene código en SQL, un lenguaje no compatible con el análisis del sniffer.

**Justificación:** Se verifica que el sniffer finalice el análisis al detectar código no compatible con el lenguaje Bython, conforme a la regla de soporte exclusivo definida.

```SQL  
SELECT columna1, columna2  
FROM tabla  
WHERE columna3 = 'valor';  
```  

---  

## ✍️ Autores  

- **Lautaro José Gubia**  
- **Sofía Martínez Coria**