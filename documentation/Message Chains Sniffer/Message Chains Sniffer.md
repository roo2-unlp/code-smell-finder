# Message Chains Sniffer

- [✏️ Definición](#️-definición-para-el-code-sniffer)
- [🤔 ¿En donde puede aparecer?](#-en-donde-puede-aparecer)
- [🤔 ¿En donde NO puede aparecer?](#-en-donde-no-puede-aparecer)
- [💻 Códigos Bython](#-códigos-bython)
    - [✅ Código 1 - Ninguna cadena](#-código-1---ninguna-cadena)
    - [✅ Código 2 - Límite inferior: dos llamadas concatenadas](#-código-2---límite-inferior-dos-llamadas-concatenadas)
    - [✅ Código 3 - Límite superior: tres llamadas concatenadas](#-código-3---límite-superior-tres-llamadas-concatenadas)
    - [✅ Código 4 - Validación: 5 o más mensajes concatenados](#-código-4---validación-5-o-más-mensajes-concatenados)
    - [✅ Código 5 - Llamadas a métodos no relacionados](#-código-5---llamadas-a-métodos-no-relacionados)
    - [✅ Código 6 - Llamadas consecutivas a métodos con parámetros](#-código-6---llamadas-consecutivas-a-métodos-con-parámetros)
    - [✅ Código 7 - Cadena usando atributos concatenados](#-código-7---cadena-usando-atributos-concatenados)
    - [✅ Código 8 - Cadena de métodos y atributos](#-código-8---cadena-de-métodos-y-atributos)
    - [✅ Código 9 - Cadena como parámetro](#-código-9---cadena-como-parámetro)
    - [✅ Código 10 - Cadena como retorno de función o método](#-código-10---cadena-como-retorno-de-función-o-método)
    - [✅ Código 11 - Cadena como condición de una estructura de control](#-código-11---cadena-como-condición-de-una-estructura-de-control)
    - [✅ Código 12 - Cadena como instrucción aislada](#-código-12---cadena-como-instrucción-aislada)
    - [✅ Código 13 - Cadena como asignación](#-código-13---cadena-como-asignación)
- [🤝🏼 Integrantes](#-integrantes)

---

## 🤝🏼 Integrantes

Fabian Martinez Rincon | Lucia Lamella |
--- | --- |
![@Fabian-Martinez-Rincon](https://avatars.githubusercontent.com/Fabian-Martinez-Rincon?s=150&v=1) | ![@juliasaenz](https://avatars.githubusercontent.com/juliasaenz?s=150&v=1) |
[@Fabian-Martinez-Rincon](https://github.com/juliasaenz) | [@juliasaenz](https://github.com/juliasaenz) | 

---

## ✏️ Definición para el Code Sniffer

Un código tiene el mal olor Message Chain (cadena de mensajes) cuando un cliente le pide a un objeto otro objeto, que luego pide otro objeto, y así sucesivamente. Para este sniffer, definimos que hay una cadena de mensajes cuando hay 3 o más llamados a métodos o atributos de forma concatenada en una única instrucción.

Las llamadas pueden ser:
- A métodos `a.foo();`
- A métodos usando parámetros `a.foo( param );`
- A atributos `a.foo;`

---

## 🤔 ¿En donde puede aparecer?

Como una instrucción aislada

```python
a.foo().too().roo();
```

Como una asignación
```python
String s = a.foo().too().roo();
a.foo().too().roo() = s;
```

Como parámetro de un método

```python
a.foo( a.too().roo().woo() );
a.foo( param, a.too().roo().woo );
```

Retorno de una función o método

```python
return a.foo().too().roo;
```

Condición de una estructura de control:
```python
if( a.foo.too().roo ) {}
```

Operación aritmética

```python
int x = a.foo().too().roo + a.foo().too().roo;
int x = a.foo().too().roo - a.foo().too().roo;
int x = a.foo().too().roo * a.foo().too().roo;
int x = a.foo().too().roo / a.foo().too().roo;
```

Operación entre colecciones

```python
numeros = [1, 2, 3, 4, 5];
print(numeros[a.foo().too().roo;]);  # 1
```

---

## 🤔 ¿En donde NO puede aparecer?

En este caso depende de la semantica

```python
persona = barco.pasajero();
nuevo = persona.padre();
familiar = nuevo.hijo();
```

---

## ⌨️Casos de prueba en Bython

- ✅ Ninguna cadena de mensajes 
- ✅ Límite inferior: dos mensajes concatenados
- ✅ Límite superior: tres mensajes concatenados
- ✅ Validación: 5 o más mensajes concatenados
- ✅ Llamadas a métodos no relacionados
- ✅ Llamadas concatenadas a métodos con parámetros
- ✅ Cadena usando atributos concatenados
- ✅ Cadena mixta, usando métodos y atributos concatenados
- ✅ Cadena como parámetro
- ✅ Cadena como retorno de una función o método
- ✅ Cadena como condición de una estructura de control
- ✅ Cadena como instrucción aislada
- ✅ Cadena como asignación


---

## 💻 Códigos Bython

### ✅ Código 1 - Ninguna cadena

**Justificación**: Este caso verifica que, en ausencia de cualquier cadena de mensajes, el sistema no genere alertas de mal olor `"Message Chain"`. Es fundamental que el sniffer no de falsos positivos cuando no hay cadena.



```python
class Dog {
	def init(self, name){
    	self.name = name;
    	self.happiness = 0;
	}
	def get_name(self){
    	return self.name;
	}
	def pet(){
    	self.happiness = self.happiness + 1;
	}
}

puppy = Dog("Dalinar");

for i in range(99){
	puppy.pet;
}
```

---

### ✅ Código 2 - Límite inferior: dos llamadas concatenadas

**Justificación**: Prueba de límite inferior, verifica que el sistema no detecte una cadena de mensajes cuando el número de llamadas es menor que el límite establecido.

<details><summary>Objetos</summary>

```python
class Address{
    def init(self, street){
        self.street = street;
    }
    def get_street(self){
        return self.street;
    }
}
class Person{
    def init(self, name, address){
        self.name = name;
        self.address = address;
    }
    def get_address(self){
        return self.address;
    }
}
```
</details>

```python
city = City("La Plata", 1900);
address = Address(city);
person = Person("Leo", address);


city_name = person.get_address().get_street()
```

---

### ✅ Código 3 - Límite superior: tres llamadas concatenadas

**Justificación**: Prueba límite superior, al alcanzar el número mínimo de llamadas establecido, se confirma que el sistema puede detectar correctamente cadenas de mensajes en situaciones que cumplen con los criterios definidos.

<details><summary>Objetos</summary>

```python
class City {
	def init(self, name, postal_code){
    	self.name = name;
    	self.postal_code = postal_code;
	}
	def get_name(self){
    	return self.name;
	}
}
class Address{
	def init(self, city){
    	self.city = city;
    	self.street = street;
	}
	def get_city(self){
    	return self.city;
	}
}
class Person{
	def init(self, name, address){
    	self.name = name;
    	self.address = address;
	}
	def get_address(self){
    	return self.address;
	}
}
```
</details>

```python
city = City("La Plata", 1900);
address = Address(city);
person = Person("Leo", address);

city_name = person.get_address().get_city().get_name();
```

---

### ✅ Código 4 - Validación: 5 o más mensajes concatenados

**Justificación**: Prueba de validación, verifica que el sistema identifique correctamente cadenas de mensajes que excedan el límite establecido de llamadas encadenadas. Es esencial para garantizar que el sniffer sea capaz de detectar patrones complejos y no se limite a los casos en los límites.

<details><summary>Objetos</summary>

```python
class Country {
	def init(self, name){
    	self.name = name;
	}
	def get_name(self){
    	return self.name;
	}
}

class State {
	def init(self, country){
    	self.country = country;
	}
	def get_country(self){
    	return self.country;
	}
}

class City {
	def init(self, state){
    	self.state = state;
	}
	def get_state(self){
    	return self.state;
	}
}

class Address {
	def init(self, city, street){
    	self.city = city;
    	self.street = street;
	}
	def get_city(self){
    	return self.city;
	}
	def get_street(self){
    	return self.street;
	}
}

class Person {
	def init(self, name, address){
    	self.name = name;
    	self.address = address;
	}
	def get_address(self){
    	return self.address;
	}
}
```
</details>


```python
country = Country("Argentina");
state = State(country);
city = City(state);
address = Address(city, "Calle 123");
person = Person("Fabian", address);

country_name = person.get_address().get_city().get_state().get_country().get_name();
```

---


### ✅ Código 5 - Llamadas a métodos no relacionados
**Justificación**: Este caso evalúa cuando hay llamadas a métodos no relacionados de forma consecutiva. Aunque la estructura puede parecer similar a una cadena de mensajes, es necesario asegurar que el sistema no clasifique incorrectamente estas llamadas como un mal olor `"Message Chain"`.

<details><summary>Objetos</summary>
```python
class Pet {
	def init(self, name, species){
    	self.name = name;
    	self.species = species;
	}
	def get_name(self){
    	return self.name;
	}
}
class Clinic{
	def init(self, city, street){
    	self.city = city;
    	self.street = street;
	}
	def get_city(self){
    	return self.city;
	}
	def get_street(self){
    	return self.street;
	}
}
class Vet{
	def init(self, name, clinic){
    	self.name = name;
    	self.clinic = clinic;
	}
	def get_name(self){
    	return self.name;
	}
}
```
</details>

```python
clinic = Clinic("La Plata", "34");
vet = Vet("Felipe", clinic);
pet = Pet("Navani","cat");

clinic.get_city();
vet.get_name();
pet.get_name();
```

---

### ✅ Código 6 - Llamadas consecutivas a métodos con parámetros
**Justificación**: Probar que se detecte como cadena de mensajes cuando una o más partes de la cadena consisten de métodos que reciben parámetros. Esto es importante para asegurar que el análisis no se limite a llamadas sin parámetros, sino que también identifique patrones complejos.

<details><summary>Objetos</summary>

```python
class Product {
	def init(self, name, price){
    	self.name = name;
    	self.price = price;
	}
	def get_price(self, discount){
    	return self.price - (self.price * discount / 100);
	}
}

class ShoppingCart {
	def init(self){
    	self.products = [];
	}
	def add_product(self, product){
    	self.products.append(product);
	}
	def get_total(self, tax){
    	total = sum([p.get_price(10) for p in self.products]);
    	return total + (total * tax / 100);
	}
}

class Customer {
	def init(self, name, cart){
    	self.name = name;
    	self.cart = cart;
	}
	def checkout(self, discount, tax){
    	return self.cart.get_total(tax) - discount;
	}
}
```
</details>

```python
product1 = Product("Laptop", 1500);
product2 = Product("Phone", 800);
cart = ShoppingCart();
cart.add_product(product1);
cart.add_product(product2);
customer = Customer("Lucia", cart);

final_price = customer.cart.get_total(21).get_price(10).checkout(50, 21);
```

---

### ✅ Código 7 - Cadena usando atributos concatenados
**Justificación**: Probar que se detecte como cadena de mensajes cuando una o más partes de la cadena consiste en una llamada a un atributo. Este caso es importante para garantizar que el sniffer reconozca no solo las llamadas a métodos, sino también las interacciones directas con los atributos de los objetos.

<details><summary>Objetos</summary>

```python
class Engine {
	def init(self, horsepower){
    	self.horsepower = horsepower;
	}
}

class Car {
	def init(self, model, engine){
    	self.model = model;
    	self.engine = engine;
	}
	def get_model(self){
    	return self.model;
	}
}

class Person {
	def init(self, name, car){
    	self.name = name;
    	self.car = car;
	}
}
```
</details>

```python
engine = Engine(300);
car = Car("Mustang", engine);
person = Person("Carlos", car);

car_horsepower = person.car.engine.horsepower;
```

---

### ✅ Código 8 - Cadena de métodos y atributos
**Justificación**: El sistema debería poder detectar cualquier tipo de combinación de los tres tipos de llamadas especificados en la definición. Esto es fundamental para validar que el sniffer sea lo suficientemente robusto para identificar cadenas en códigos más complejos.

<details><summary>Objetos</summary>

```python
class Engine {
	def init(self, horsepower){
    	self.horsepower = horsepower;
	}
}

class Car {
	def init(self, model, engine){
    	self.model = model;
    	self.engine = engine;
	}
	def get_model(self){
    	return self.model;
	}
}

class Person {
	def init(self, name, car){
    	self.name = name;
    	self.car = car;
	}
	def get_car(self){
    	return self.car;
	}
}
```
</details>

```python
engine = Engine(300);
car = Car("Mustang", engine);
person = Person("Carlos", car);

car_horsepower = person.get_car().engine.horsepower;
```

---

### ✅ Código 9 - Cadena como parámetro
**Justificación**: Detectar la cadena cuando está siendo pasada como parámetro a otra función. Especial atención a que la función que está usando esa cadena no se cuente como parte de la cadena misma, para no generar falsos positivos.

<details><summary>Objetos</summary>

```python
class Country {
	def init(self, name){
    	self.name = name;
	}
	def get_name(self){
    	return self.name;
	}
}

class State {
	def init(self, country){
    	self.country = country;
	}
	def get_country(self){
    	return self.country;
	}
}

class City {
	def init(self, state){
    	self.state = state;
	}
	def get_state(self){
    	return self.state;
	}
}

class Address {
	def init(self, city, street){
    	self.city = city;
    	self.street = street;
	}
	def get_city(self){
    	return self.city;
	}
}

class Person {
	def init(self, name, address){
    	self.name = name;
    	self.address = address;
	}
	def get_address(self){
    	return self.address;
	}
}
```

</details>

```python
def print_location(location):
    print(f"Location: {location}")

country = Country("Argentina");
state = State(country);
city = City(state);
address = Address(city, "Calle 123");
person = Person("Lucia", address);

print_location(person.get_address().get_city().get_state().get_country().get_name());
```

---

### ✅ Código 10 - Cadena como retorno de función o método
**Justificación**: Detectar la cadena cuando es el valor de retorno de un método o función. Este test es importante para asegurar que el análisis abarca todos los contextos en los que pueden aparecer cadenas de mensajes, incluyendo aquellos que se devuelven a otros componentes del sistema.

<details><summary>Objetos</summary>

```python
class Phone {
	def init(self, model){
    	self.model = model;
	}
	def get_model(self){
    	return self.model;
	}
}
class Store {
	def init(self, phone, location){
    	self.phone = phone;
    	self.location = location;
	}
	def get_phone(self){
    	return self.phone;
	}
}
class Customer {
	def init(self, name, store){
    	self.name = name;
    	self.store = store;
	}
	def get_store(self){
    	return self.store;
	}
	def get_phone_model(self){
    	return self.get_store().get_phone().get_model();
	}
}

```
</details>

```python
phone = Phone("Nokia 3310");
store = Store(phone, "Local");
customer = Customer("Fabian", store);

def get_customer_phone_model(cust){
	return cust.get_store().get_phone().get_model();
}
print(customer.get_phone_model());
print(get_customer_phone_manufacturer(customer));
```

---

### ✅ Código 11 - Cadena como condición de una estructura de control
**Justificación**: El sniffer tiene que poder encontrar una cadena de mensajes cuando esta está contenida dentro de una estructura de control. Es necesario garantizar que no se pasen por alto situaciones donde la cadena pueda influir en el flujo del programa.

<details><summary>Objetos</summary>

```python
class Car {
	def init(self, model, year){
    	self.model = model;
    	self.year = year;
	}
	def get_year(self){
    	return self.year;
	}
}
class Garage {
	def init(self, car, location){
    	self.car = car;
    	self.location = location;
	}
	def get_car(self){
    	return self.car;
	}
}
class Owner {
	def init(self, name, garage){
    	self.name = name;
    	self.garage = garage;
	}
	def get_garage(self){
    	return self.garage;
	}
}

```
</details>

```python
car = Car("Tesla Model S", 2022);
garage = Garage(car, "Downtown");
owner = Owner("Martín", garage);

if (owner.get_garage().get_car().get_year() > 2020) {
	print("You have a new car");
}{
	print("You have an old car");
}
```

---

### ✅ Código 12 - Cadena como instrucción aislada
**Justificación**: Detectar la cadena de mensajes cuando es una instrucción aislada. Este código valida que el sniffer pueda identificar cadenas de mensajes en su forma más simple y directa.

<details><summary>Objetos</summary>

```python
class Book {
	def init(self, title){
    	self.title = title;
    	self.reading = false;
	}
	def read(self){
    	return reading = true;
	}
}

class Library {
	def init(self, book, shelf_number){
    	self.book = book;
    	self.shelf_number = shelf_number;
	}
	def get_book(self){
    	return self.book;
	}
}

class Reader {
	def init(self, name, library){
    	self.name = name;
    	self.library = library;
	}
	def get_library(self){
    	return self.library;
	}
}
```

</details>

```python
book = Book("Dune");
library = Library(book, "A1");
reader = Reader("Julia", library);

reader.get_library().get_book().read();
```


---

### ✅ Código 13 - Cadena como asignación
**Justificación**: Encontrar la cadena de mensajes cuando es un valor asignado a una variable. Este caso es importante para garantizar que el sniffer reconozca las cadenas de mensajes en situaciones donde se almacenan para su uso posterior.

<details><summary>Objetos</summary>

```python
class Book {
	def init(self, title, isbn){
    	self.title = title;
    	self.isbn = isbn;
	}
	def get_title(self){
    	return self.title;
	}
}

class Library {
	def init(self, book, shelf_number){
    	self.book = book;
    	self.shelf_number = shelf_number;
	}
	def get_book(self){
    	return self.book;
	}
}

class Reader {
	def init(self, name, library){
    	self.name = name;
    	self.library = library;
	}
	def get_library(self){
    	return self.library;
	}
}

```

</details>

```python
book = Book("The Way of Kings", "123456789");
library = Library(book, "A1");
reader = Reader("Julia", library);

book_title = reader.get_library().get_book().get_title();
```