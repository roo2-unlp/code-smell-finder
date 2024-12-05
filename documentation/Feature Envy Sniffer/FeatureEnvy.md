# Documentation

<aside>
📌
The methods of a class should be interested in the variables and functions of the class they belong to, and not the variables and functions of other classes. When a method uses accessors and mutators of some other object to manipulate the data within that object, then it envies the scope of the class of that other object. It wishes that it were inside that other class so that it could have direct access to the variables it is manipulating.

*Clean Code* - Fowler
</aside>

## ¿En dónde puede aparecer?

- Cuando una clase accede frecuentemente a los atributos de otra clase que tiene como variable de instancia.
- Cuando un método de una clase accede frecuentemente a los atributos de otra clase mediante parámetros.


## ¿En dónde NO puede aparecer?

- Métodos que operan principalmente sobre sus propios datos.
- Casos donde los accesos son limitados y no impactan significativamente en el diseño.
- Métodos vacíos o con cadenas que sean `self`, ya que no podemos determinar la semántica y nuestro análisis se enfoca en la sintaxis.
- No consideramos invocaciones a funciones externas sin clase, porque *Feature Envy*, según Fowler, se enfoca específicamente en la relación entre clases y en cómo los métodos dentro de una clase utilizan atributos o métodos de otra clase. Funciones globales y módulos no forman parte de una clase y están diseñados para ser utilizados desde cualquier contexto.
- Tampoco consideramos una clase dentro de otra, ya que la clase anidada está definida dentro de la clase principal. El acoplamiento es intencional y controlado, lo que puede no ser problemático.

## ¿Por qué usamos un límite?

Usamos un límite para restringir, dentro de un rango razonable, el número de accesos a comportamientos o estados externos. Aunque idealmente una clase debería ser completamente independiente, en la práctica esto no siempre es posible, por lo que permitir ciertos accesos en un nivel controlado resulta necesario. Además, queremos cumplir con los siguientes principios:

**Principio de Responsabilidad Única (SRP)**: Cada clase o método debería tener una única razón para cambiar. Cuando un método accede a múltiples atributos de otra clase, su comportamiento puede volverse demasiado dependiente de la estructura interna de esa clase, rompiendo así el SRP. Esto implica que cualquier cambio en esa otra clase podría requerir modificaciones en el método.

**Reducción del acoplamiento**: El acoplamiento mide el grado de dependencia entre clases. Un método que accede a varios atributos de otra clase puede volverse altamente dependiente de ella, aumentando el acoplamiento. Esto provoca que los cambios en una clase puedan impactar en muchas otras partes del sistema, dificultando su mantenimiento.

## Casos de prueba

- **Ejemplo 1**: No hay *feature envy*.
- **Ejemplo 2**: `self.variable.cadena sin ()`, menos de 2 ocurrencias.
- **Ejemplo 3**: `self.variable.cadena sin ()`, más de 2 ocurrencias.
- **Ejemplo 4**: `parámetro.cadena sin ()`, más de 2 ocurrencias.
- **Ejemplo 5**: `parámetro.cadena sin ()`, menos de 2 ocurrencias.
- **Ejemplo 6**: No es código Python.
- **Ejemplo 7**: Se accede a un método de otra clase y como parámetro se le pasa `self`.
- **Ejemplo 8**: El codigo no está detro de una clase.


### Ejemplo 1

No hay *feature envy* porque se accede a comportamiento propio.

```python
 class Persona{
                def __init__(self, nombre, apellido){
                    self.nombre = nombre;
                    self.apellido = apellido;
                }
                def nombre_igual(self, nombre){
                    return self.nombre == nombre;
                }
            }
```

### Ejemplo 2
NO HAY *feature envy* por acceso menor a 2 de atributos de otro objeto. 
```python
 class Orden{
                            def __init__(self, cliente){
                                self.cliente= cliente;
                                self.suscripcion = 0;
                                self.total = 0;
                            
                            }
                                
                            def calcular_descuento(self){
                                if self.cliente.suscripcion == 'premium'{
                                    self.total == 1;
                                    return self.total * 0.2;
                                }

                                if self.cliente.order_history > 10{
                                    return self.total * 0.1;
                                }

                                return 0;
                            }
                        }
```

### Ejemplo 3
HAY *feature envy* debido a un acceso mayor a 2 de atributos de otro objeto.


```python
 class Orden{
                            def __init__(self, cliente){
                                self.cliente= cliente;
                                self.total = 0;
                            }

                            def calcular_descuento(self){
                                if self.cliente.suscripcion == "premium"{
                                    return self.total * 0.2;
                                }
                                if self.cliente.order_history > 10{
                                    return self.total * 0.1;
                                }
                                if self.cliente.otra_cosa < 5{
                                    return self.total * 10;
                                }
                                return 0;
                            }
                        }
```

### Ejemplo 4
Hay *feature envy* .testParameterDependence. Se busca que no se acceda 2 veces o más a un parámetro. Si res > 2, se detecta como feature envy, siendo res la cantidad de accesos.

```python
 class Notificacion{
                            def enviar_notificacion(self, usuario){	
                                if "email" in usuario.contactos{
                                    usuario.nombre_usuario == "Juan";
                                    mail = usuario.email;
                                }
                                print ("No se pudo enviar nada");
                            }    
                        }
```
### Ejemplo 5
 No hay *feature envy* .testParameterNotDependence.
 Si res ≤ 2, no se detecta como feature envy, siendo res la cantidad de accesos.() 
```python
class Notificacion{
                            def enviar_notificacion(self, usuario){
                                if "email" in usuario.contactos{
                                    print("Enviar email a {usuario.nombre_usuario}");
                                }
                                print ("No se pudo enviar nada");
                            }
                        }
```

### Ejemplo 6
No hay *feature envy* . Da error porque no es código Bython, en este caso Ruby
```ruby
class Orden
  def initialize(cliente)
    @cliente = cliente
    @total = 0
  end

  def calcular_descuento
    if @cliente.suscripcion == 'premium'
      return @total * 0.2
    end
    
end
```

### Ejemplo 7
No hay *feature envy* porque dentro de la llamada de un metodo se pasa self
```python
class VTV{
	def __init__(self, auto){
		self.auto = auto;
		self.precioBase = 20000;
	}
	
	def calcularPrecio(){
		return self.auto.precioTotal(VTV);
	}
}
```

### Ejemplo 8
No hay *feature envy* porque el código no está dentro de una clase
```python
class VTV{
	def __init__(self, auto){
		self.auto = auto;
		self.precioBase = 20000;
	}
	
	def calcularPrecio(){
		return self.auto.precioTotal(VTV);
	}
}
```

