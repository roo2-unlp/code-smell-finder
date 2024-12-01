# Flag Arguments Sniffer

## Definición

“A flag argument is a kind of function argument that tells the function to carry out a different operation depending on its value.” Martin Fowler

Cambio de comportamiento basado en el valor de un argumento, en otras palabras, una función realiza una operación diferente dependiendo del valor de un parámetro.

## Refactoring
- Remove Flag Argument
- Extract Method
- Decompose conditional


## Casos de prueba en Bython

### Código 1 - Validación de parámetros

**Justificación**: Validamos que no marcamos como problema los parámetros que están siendo utilizados de forma correcta dentro del método. Es fundamental que el sniffer no de falsos positivos cuando hay parametros pero no se utilizan como Flags.

```python
def Sumador(x, y, z){
	sum = x + y + z;
	print("La suma es {sum}");
}
```

---
### Código 2 - Validación sin definición.

**Justificación**: En este ejemplo, si un archivo o segmento de código no contiene definiciones de funciones, no debería haber parámetros que analizar, porque simplemente no existen. Por lo tanto, es importante que nuestro sniffer no genere falsos positivos en estos casos.

```python
print("Hello, World!");
```

---

### Código 3 - Validación sin parámetros.

**Justificación**: Cuando un método o función no tiene parámetros definidos, no hay posibilidad de que existan flag arguments. Por lo tanto, nuestro sniffer debe ser capaz de reconocer esta situación y no generar falsos positivos.

```python
def Mensajero(){
	print("Bienvenido al sistema");
}
```

---
### Código 4 - Validación de presencia de frag arguments

**Justificación**: En este ejemplo, estamos analizando un caso donde se utiliza un parámetro, tipo_cancha1, como un flag argument. Este parámetro actúa como un indicador que modifica el comportamiento del método, seleccionando diferentes configuraciones (en este caso, el número de jugadores en un equipo de fútbol) según su valor.

```python
def set_equipo(tipo_cancha1){
	if tipo_cancha1 == "Futbol 5"{
		self._equipo = 5;}
	elif tipo_cancha2 == "Futbol 7"{
		self._equipo = 7;}
	elif tipo_cancha2 == "Futbol 11"{
		self._equipo = 11;
	}
}
```

---
### Código 5 - Validación de flag argument en dos métodos diferentes.

**Justificación**: Este ejemplo analiza cómo un sniffer puede identificar correctamente la presencia de flag arguments en dos métodos distintos y, al mismo tiempo, evitar falsos positivos en situaciones donde los parámetros no se utilizan como flags y donde variables tienen el mismo nombre que los parámetros.

```python
def set_tiempo(tipo_cancha2){
	if tipo_cancha2 == "Futbol 5"{
		self._tiempo = 60;}
	elif tipo_cancha2 == "Futbol 7"{
		self._tiempo = 80;}
	elif tipo_cancha2 == "Futbol 11"{
		self._tiempo = 90;
	}
}


def set_equipo(tipo_cancha3){
	tipo_cancha2 = "Futbol 5";
	if tipo_cancha3 == "Futbol 5"{
		self._equipo = 5;}
	elif tipo_cancha3 == "Futbol 7"{
		self._equipo = 7;}
	elif tipo_cancha3 == "Futbol 11"{
		self._equipo = 11;
	}
}
```

---
### Código 6 - Validación del frag argument con dos variables anidadas.

**Justificación**: Se analiza el uso de múltiples flag arguments dentro de métodos diferentes, incluyendo la combinación de parámetros con variables anidadas. El objetivo principal es verificar que el sniffer detecte correctamente los flag arguments sin generar falsos positivos, incluso cuando hay múltiples parámetros o variables en juego.

```python
  	def set_tiempo(tipo_cancha4, hay_iluminacion){
		tipo1 = "Futbol 5";
		if tipo1 == "Futbol 5" and hay_iluminacion{
			self._tiempo = 60;}
		elif tipo2 == "Futbol 7" and hay_iluminacion{
			self._tiempo = 80;}
		elif tipo2 == "Futbol 11" and hay_iluminacion{
			self._tiempo = 90;
		}
	}


	def set_equipo(tipo3){
		tipo2 = "Futbol 5";
		if tipo2 == "Futbol 5"{
			self._equipo = 5;}
		elif tipo3 == "Futbol 7"{
			self._equipo = 7;}
		elif tipo3 == "Futbol 11"{
			self._equipo = 11;
		}
	}

```

---
### Código 7 - Verificación de error de sintaxis de código.

**Justificación**: Se utiliza para probar cómo el sniffer maneja un escenario en el que el código analizado contiene errores de sintaxis evidentes. 

```python
for (int i = 0; i < 10; i++) {}
```

---

## Referencias
- Fowler, Martin. Refactoring: improving the design of existing code. Addison-Wesley Professional, 2018.

- Martin, Robert C. Clean code: a handbook of agile software craftsmanship. Pearson Education, 2009.

- https://victorsmirnov.blog/remove-boolean-flag-argument/
- https://martinfowler.com/bliki/FlagArgument.html 	