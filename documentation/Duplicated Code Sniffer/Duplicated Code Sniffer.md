# 🚨 Código Duplicado  

## 📜 ¿Por qué es un mal olor?  

El **código duplicado** dificulta la comprensión y el mantenimiento del software. Repetir bloques de código significa que cualquier modificación debe realizarse en múltiples lugares, aumentando el riesgo de errores e inconsistencias. Además, genera redundancia que complica el análisis del programa y reduce su eficiencia.  

**Evitar el código duplicado mejora:**  
- La claridad del programa.  
- La capacidad de realizar cambios rápidamente.  
- La calidad general del software al minimizar errores.

---  

## Definición  

Consideramos **código duplicado** cualquier **secuencia de líneas** consecutivas textualmente idénticas dentro del **cuerpo de métodos o funciones**, sin importar su firma, parámetros o ubicación dentro del programa.  
El análisis se centra en detectar repeticiones exactas dentro de los métodos o funciones y en bloques de código externos.

---  

### 🧑‍💻 Pautas del Sniffer  

1. **Foco en el Cuerpo del Método:**  
   El análisis se limitará a las líneas comprendidas dentro de las llaves de los métodos o funciones.  
2. **Exclusión de Firmas:**  
   Las diferencias en nombres, firmas o parámetros de métodos no afectan la detección si los cuerpos son idénticos.  
3. **Soporte Exclusivo de Bython:**  
   El sniffer solo analizará código escrito en Bython; otros lenguajes serán ignorados.  
4. **Errores y Alertas:**  
   Todos los casos de código duplicado serán alertados tras analizar el archivo completo. Si el archivo no corresponde a Bython, el análisis finalizará al detectar la primera línea inválida.

---  

## 🧪 Casos de Prueba  

### **1. Dos métodos con cuerpos idénticos**  

**Justificación:** El cuerpo de `metodoA` es idéntico al de `metodoB`. Ambos comparten exactamente las mismas tres líneas de código en el mismo orden.  

```bython
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

### **2. Más de dos métodos con cuerpos idénticos**  

**Justificación:** Los métodos `metodoA`, `metodoB`, `metodoC` y `metodoD` tienen cuerpos idénticos con las mismas tres líneas de código en el mismo orden.  

```bython
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

    def metodoC() {  
        print(1);  
        print(2);  
        print(3);  
    }  

    def metodoD() {  
        print(1);  
        print(2);  
        print(3);  
    }  
}  
```

---  

### **3. Duplicación entre un método y código externo**  

**Justificación:** El bloque de código externo es idéntico al cuerpo del método `metodoA`.  

```bython
class Ejemplo() {  
    def metodoA() {  
        print(1);  
        print(2);  
        print(3);  
    }  
}  

print(1);  
print(2);  
print(3);  
```

---  

### **4. Dos constructores con cuerpos idénticos**  

**Justificación:** El cuerpo del constructor de la clase `persona` es idéntico al de la clase `alumno`. Ambos inicializan los mismos atributos con los mismos valores.  

```bython
class persona() {  
    def __init__(self, nombre, apellido, edad) {  
        self.nombre = "Lautaro";  
        self.apellido = "Gubia";  
        self.edad = 20;  
    }  
}  

class alumno() {  
    def __init__(self, nombre, apellido, edad) {  
        self.nombre = "Lautaro";  
        self.apellido = "Gubia";  
        self.edad = 20;  
    }  
}  
```

---  

### **5. Más de un caso en el mismo código**  

**Justificación:** Este código presenta múltiples duplicaciones:  
1. Los constructores de las clases `Mujer` y `Hombre` son idénticos.  
2. Los métodos `cumpleanios` y `birthday` también son idénticos.  
3. El bloque externo de `print(1); print(2); print(3);` se repite en ambos casos.  

```bython
class Mujer() {  
    def __init__(self, nombre, apellido, edad) {  
        self.nombre = nombre;  
        self.apellido = apellido;  
        self.edad = edad;  
    }  

    def cumpleanios() {  
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

    def birthday() {  
        self.edad += 1;  
    }  

    print(1);  
    print(2);  
    print(3);  
}  
```
---  

### **6. Dos métodos con cuerpos diferentes**  

**Justificación:** El cuerpo de `metodoA` y `metodoB` contiene líneas de código distintas. No se consideran duplicados.  

```bython
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

### **7. Código externo diferente al cuerpo de un método** 

**Justificación:** El bloque de código externo es diferente al cuerpo del método `metodoA`. Las instrucciones no coinciden en contenido ni en orden, por lo que no se consideran duplicados.  

```bython
class Ejemplo() {  
    def metodoA() {  
        print(1);  
        print(2);  
        print(3);  
    }  
}  

x + 1; 
print(x);  
print("hola");  
```

---  

### **8. Código no escrito en Bython**  

**Justificación:** Este ejemplo contiene código en SQL, el cual no será analizado por el sniffer ya que no está escrito en Bython.  

```SQL  
SELECT columna1, columna2  
FROM tabla  
WHERE columna3 = 'valor';  
```  

---  

## ✍️ Autores  

- **Lautaro José Gubia**  
- **Sofía Martínez Coria**