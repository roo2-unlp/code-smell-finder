# Switch Statement Sniffer

~Documentación~


La definición de switch statement en la que nos basamos fue extraída del libro "Clean Code: A Handbook of Agile Software Craftsmanship" de Robert C. Martin. En este texto, Martin describe las sentencias switch como estructuras que tienden a ser extensas y complejas, incluso con solo dos caso y que muchas veces, no vamos a poder evitarlos pero sí podemos “enterrarlos” en clases de bajo nivel para que no se repitan.

"It’s hard to make a small switch statement.6 Even a switch statement with only two cases is larger than I’d like a single block or function to be. It’s also hard to make a switch statement that does one thing. By their nature, switch statements always do N things. Unfortunately we can’t always avoid switch statements, but we can make sure that each switch statement is buried in a low-level class and is never repeated. We do this, of course, with polymorphism." 

## ¿Qué es SwitchStatementSniffer? 

```SwitchStatementSniffer``` es una clase que a partir de un código Bython determina si el mismo presenta el mal olor "Switch Statement". 
Para poder analizar el código recibido, implementa las herramientas parser y lexer de ANTLR que se encargan de transfomarlo en un árbol de expresión.
Para recorrer el árbol y encontrar el mal olor en el mismo, utiliza un objeto que implementa el patrón de diseño ***Visitor***, ```SwitchStatementSnifferVisitor```.

## ¿Cómo lo utilizo?


1. #### Instanciarlo
    Creamos una instancia de la clase SwitchStatementSniffer

2. #### Llamar al método sniff
    - Crear una instancia de la clase ```AromaReport```
    - Utilizar el método sniff(), el cual se va a encargar de analizar nuestro código buscando sentencias if-elif.
    - El método sniff() necesita que le enviemos como parametro, nuestra instancia de AromaReport y el código a analizar.

3. #### Ver Resultados

    AromaReport va a almacenar los malos olores encontrados en el código por lo tanto, para conocer el estado de nuestro código vamos a usar el método stinks() de nuestra instancia de AromaReport. Este método nos va a retornar un valor booleano, en caso de hallar malos olores nos devolverá "True" y caso contrario, "False". En caso de que encontremos malos olores podemos acceder a los mismos con el método getAromas() en la misma instancia.

## Casos de prueba

Dejamos a continuación algunos ejemplos de código con los resultados esperados.

#### Caso 1:

```python
                class Perro(){
                    def init(self, nombre, raza){
                        this.nombre = nombre;
                        this.raza = raza;
                    }

                    def bark_smell(){
                        if (this.raza == "Caniche"){
                            print("woof woof");
                        }elif (this.raza == "Pitbull"){
                            print("WRAF WRAF");
                        }elif (this.raza == "Gran Danés"){
                            print("GUAUGUAU");
                        }elif (this.raza == "Beagle"){
                            print("bark bark");
                        }
                    }
                }

                perro = Perro("Jaime", "Caniche");
                perro.bark_smell();
```

En este caso, el código presenta una clara implementación con un mal olor de switch statement. Ya que al momento de llamar al método bark_smell(), va a preguntar por condiciones if e elif para evaluar su comportamiento. Por lo tanto, nuestro sniffer reportaría el mal olor.

#### Caso 2:

```python
                class Animal(){
                    def init(self, tipo_animal){
                        this.tipo_animal = tipo_animal;
                    }
            }

                class Perro(Animal){
                    def ruido(){
                        print("guau guau");
                    }
                }

                class Gato(Animal){
                    def ruido(){
                        print("miau miau");
                    }
                }

                gato = Gato("Purelly");
                perro = Perro("Firulais the 1st");

                list = (gato, perro);

                for i in list{
                    i.ruido();
                }
```

El código presenta la implementación de dos clases que responden de distinta manera al método ruido() y cuando recorremos la lista llamando a este método van a devolver distinto resultado, en este caso, nuestro sniffer no detectaría un switch statement.

#### Caso 3: 

```python
            if x == 1{
               print("Lunes");
            }    
            elif x == 2{
               print("Martes");
            }    
           elif x == 3{
               print("Miércoles");
            }
           elif x == 4{
               print("Jueves");
            }
           elif x == 5{
               print("Viernes");
            }
           elif x == 6{
               print("Sábado");
            }   
           else{
               print("Domingo");
            }
```

Nuestro codigo en este caso analiza el contenido de la variable x y dependiendo su valor imprime un día. Podemos observar la presencia de sentencias if-elif por lo que nuestro sniffer va a detectar el mal olor. 

#### Caso 4:

```python
                if edad >= 18{
                     print ("Eres mayor de edad");
                 }else{
                     print ("Eres menor de edad");
                 }
```

El código presenta una sentencia if-else simple. En este caso, al no determinar si se encuentra en una clase de bajo nivel o si es un caso especifico que no se repite. Y al ser una sentencia if que no va a escalar, el sniffer detectaría que no hay un mal olor. 


#### Caso 5:


```

    switch (aValue)
	case {match_code1} : {action_code1; break;}
	case {match_code2} : {action_code2; break;}
	...
	default {other_code;}

```

Al recibir este código que puede ser un switch statement pero que esta escrito en el lenguaje de programación SmallTalk, nuestro sniffer no lo analizará ya que solo trabaja con código Bython. 



    

## Autores

Simón Mc Govern
Milagros Palacios




