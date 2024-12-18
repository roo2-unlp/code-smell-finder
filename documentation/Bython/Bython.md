
# Bython

## ¿Qué es Bython?

**Bython** es una herramienta que te permite escribir código en Python usando una sintaxis parecida a la de lenguajes como C o Java. En vez de usar la indentación para marcar los bloques de código, como es común en Python, en Bython se emplean llaves `{}` para delimitar las estructuras de control (como los condicionales, bucles y funciones).

Este proyecto está pensado para quienes ya están familiarizados con otros lenguajes que usan llaves o simplemente prefieren esa manera de estructurar el código. Bython se encarga de traducir automáticamente este código a Python estándar, por lo que podés seguir usando cualquier intérprete de Python sin problemas.

Si querés conocer más sobre el proyecto, podés visitar el [repositorio oficial en GitHub](https://github.com/mathialo/bython).

## Capacidades de la gramática

En este repositorio hemos creado una gramática para ANTLR4 de Bython, pero es reducida. A continuación se darán ejemplos de lo que se puede hacer:

### Asignaciones

```python
number = 12.2;
my_bool = True;
my_none = None;
y = x + 5;
x += 1;
persona.nombre = "María";
```

### Operadores

```python
z = (x + y) * 2 / 4 - 5;
is_greater = x > y;
is_equal = x == y;
is_between = x > 5 and x < 15; 
```

### Listas

```python
numbers = [5, 10, 15, 20];
exists_in_list = x in numbers;
not_in_list = y not in numbers;
```

### Diccionarios

```python
alicia = {
    "nombre": "Alicia",
    "edad": 30,
    "ciudad": "Buenos Aires"
};
```

### Tuplas

```python
tupla = (1, 2, 3, 4);
```

### Conjuntos

```python
set = {1, 2, 3, 4};
```


### Condicional if

```python
if (person.age > 30) {
    print("Eres mayor de 30!");
} else {
    print("NO eres mayor de 30");
}
```

### Bucle while

```python
while (x < 5) {
    print(x);
    x += 1;
}
```

### Bucle for

```python
for i in range(5) {
    print(i);
}
```

### Funciones

```python
def hello() {
    print("Hello, World!");
}
```

```python
def multiplicar(a, b) {
    return a * b;
}
```

### Clases

```python
class Person(Animal) {
    def greet() {
        print("Hello, " + this.name);
    }
    def birthday() {
        this.age = this.age + 1;
    }
    def getAge() {
        return this.age;
    }
}
```

### Objetos y métodos

```python
person = Person("Alice", 30);
person.nombre = "Carlos";
person.greet();
person.birthday();
```

### Estructura try-except

```python
try {
    x = int(input("Ingresa un número: "));
} except ValueError {
    print("Eso no es un número válido");
} finally {
    print("Fin del bloque try-except");
}
```

### Expresiones lambda

```python
lambda x, y: x + y;
lambda a, b { return a + b; };
```

### Import

```python
import math;
from os import path;
```






































