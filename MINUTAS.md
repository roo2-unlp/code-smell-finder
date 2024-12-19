**Minuta de Reunión – 23 de Octubre**

**Horario:** 18:45 hs a 19:00 hs

**Participantes:**

- Gregorio Ponce
- Leandro Romagnoli
- Lucio Colli

**Temas tratados:**

1. **Diferenciación entre code smell y código erróneo:**
    - Se discutió la necesidad de establecer criterios claros para diferenciar un *code smell* (problemas de diseño o estilo que no rompen la ejecución del programa) de un código erróneo (errores que generan fallos en la ejecución).
2. **Caso del getter que devuelve una variable inicializada en `None`:**
    - Analizamos un ejemplo en el que un método *getter* retorna una variable que no ha sido correctamente inicializada, causando potenciales confusiones o errores en la lógica del programa.
3. **Caso específico: `return a + None` y `return a.getDato()` :**
    - Se exploró este caso para evaluar cómo se comporta el flujo de código cuando se asigna `None` a una variable y luego se realiza una operación independiente en el *return*.

      En los casos de operaciones aritméticas y acceso a datos de objetos de los que no se sabe que tipo de datos son, se estableció que no generarían mal olor ya que si su uso es correcto o no depende de la semántica del lenguaje y no de la sintaxis.

    - Se destacó la importancia de mantener coherencia entre las asignaciones y el retorno para evitar interpretaciones ambiguas del código.

**Próximos pasos:**

- Desarrollar ejemplos adicionales que ilustren la diferencia entre *code smells* y errores de código.
- Evaluar si estos casos requieren reglas específicas en el análisis de código.

**Minuta de Reunión – 6 de Noviembre**

**Horario:** 18:45 hs a 19:00 hs

**Participantes:**

- Gregorio Ponce
- Leandro Romagnoli
- Lucio Colli

**Temas tratados:**

1. **Casos de error y éxito:**
    - Se definieron los escenarios para casos de éxito y error en las pruebas.
    - Implementamos los tests correspondientes para ambos casos relacionados con los *bad smells*.
2. **Funcionalidades básicas:**
    - Se implementaron funcionalidades iniciales de prueba para validar las reglas y comportamiento esperado.
3. **Puntos pendientes y decisiones técnicas:**
    - **Return al final del código:**

      Se discutió qué sucede si no hay un `return` explícito al final del código, y cómo manejar esta situación en los tests y las implementaciones.

      Se definió que si no hay una sentencia return o no se pasan parametros a un método no se encuentra mal olor.

    - **Reestructuración de los códigos de los tests:**

      Se propuso cambiar la estructura de los tests utilizando triple comillas (`""" """`) para mejorar la legibilidad del código.

    - **Diseño del Visitor:**
        - Evaluamos si implementar un *Visitor* para cada tipo de regla o uno único que contenga métodos específicos para cada regla.
        - Se sugirió partir desde `visitAssignment` y que a partir de ahí se desglosen los métodos individuales por cada regla.
        - Acordamos que es necesario profundizar en cada nivel del árbol sintáctico para simplificar las reglas y hacer el código más modular y eficiente.

**Próximos pasos:**

- Validar las decisiones tomadas sobre el diseño del *Visitor* mediante pruebas concretas.
- Realizar los cambios propuestos en la estructura de los tests.
- Documentar las implicancias del manejo de código sin `return`.
- Revisar en conjunto las reglas implementadas y ajustar según sea necesario.

**Minuta de Reunión – 20 de Noviembre**

**Horario:** 18:45 hs a 19:00 hs

**Participantes:**

- Gregorio Ponce
- Leandro Romagnoli
- Lucio Colli

**Temas tratados:**

1. **Finalización de implementación:**
    - Se completó la implementación de las reglas **Don’t Pass** y **Don’t Return**.
2. **Evaluación de tests:**
    - Se revisaron, modificaron y ejecutaron todos los tests necesarios para garantizar el correcto funcionamiento de las reglas implementadas.
    - Los resultados de los tests validaron la implementación y demostraron que cumple con los criterios definidos.

**Próximos pasos:**

- Integrar las nuevas reglas en el entorno completo de análisis.