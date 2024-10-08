# Code Smell Finder :detective::deciduous_tree:

Este es un proyecto de ejemplo que utiliza [ANTLR4](https://www.antlr.org/) con Maven y la gramática "Bython". Se implementa un **code sniffer** que no detecta olores ([`AnosmicCodeSniffer`](./src/main/java/oo2/redictado/AnosmicCodeSniffer/AnosmicCodeSniffer.java)) y uno que busca todos los llamados a sentencias `print` ([`PrintSniffer`](./src/main/java/oo2/redictado/PrintSniffer/PrintSniffer.java)). Además, se incluyen [tests de unidad](./src/test/java/oo2/redictado/) para validar su funcionamiento.

## :wrench: Requerimientos

Asegurate de tener instalados y configurados los **paths** de:

- **Java 21**

- **Maven**

## :building_construction: Estructura del proyecto

El árbol de archivos se ve de la siguiente manera:

```tree
.
├── documentation
│   ├── Anosmic Code Sniffer
│   │   └── Anosmic Code Sniffer.md
│   ├── Bython
│   │   ├── Bython.md
│   │   └── Examples
│   │       ├── bank_account.by
│   │       ├── binary_search.by
│   │       ├── example.by
│   │       ├── factorial_recursivo.by
│   │       ├── lineal_search.by
│   │       └── priority_queue.by
│   └── Print Sniffer
│       └── Print Sniffer.md
├── pom.xml
├── README.md
└── src
    ├── main
    │   ├── antlr4
    │   │   ├── BythonLexer.g4
    │   │   └── BythonParser.g4
    │   └── java
    │       └── oo2
    │           └── redictado
    │               ├── AnosmicCodeSniffer
    │               │   ├── AnosmicCodeSniffer.java
    │               │   └── AnosmicVisitor.java
    │               ├── antlr4
    │               │   ├── BythonLexer.java
    │               │   ├── BythonLexer.tokens
    │               │   ├── BythonParserBaseListener.java
    │               │   ├── BythonParserBaseVisitor.java
    │               │   ├── BythonParser.java
    │               │   ├── BythonParserListener.java
    │               │   ├── BythonParser.tokens
    │               │   └── BythonParserVisitor.java
    │               ├── Aroma.java
    │               ├── AromaReport.java
    │               ├── CodeSniffer.java
    │               ├── PrintSniffer
    │               │   ├── PrintSniffer.java
    │               │   └── PrintSnifferVisitor.java
    │               └── SerialSniffer.java
    └── test
        └── java
            └── oo2
                └── redictado
                    ├── AnosmicCodeSnifferTest.java
                    └── PrintSnifferTest.java
```

- Las clases deben ubicarse en la carpeta [`src/main/java/oo2/redictado`](./src/main/java/oo2/redictado/).

  - La interfaz [`CodeSniffer`](./src/main/java/oo2/redictado/CodeSniffer.java) define el protocolo que debe seguir un **code sniffer**.

  - También hay carpetas/paquetes de ejemplo para el [`AnosmicCodeSniffer`](./src/main/java/oo2/redictado/AnosmicCodeSniffer/) y [`PrintSniffer`](./src/main/java/oo2/redictado/PrintSniffer/). Cada nuevo **code sniffer** que se implemente debe estar en su propia carpeta.

  - La clase [`SerialSniffer`](./src/main/java/oo2/redictado/SerialSniffer.java) es la encargada de ejecutar los **code sniffers** en un orden específico y configurable.

  - La clase [`Aroma`](./src/main/java/oo2/redictado/Aroma.java) representa un mensaje de olor, que contiene información sobre el olor detectado (si lo hubiera).

  - La clase [`AromaReport`](./src/main/java/oo2/redictado/AromaReport.java) es un reporte que contiene todos los `Aroma` generados por los **code sniffers** de un `SerialSniffer`.

- Las clases de prueba deben ir en [`src/test/java/oo2/redictado`](./src/test/java/oo2/redictado/).

  - El proyecto incluye clases de test para probar el `AnosmicCodeSniffer` y el `PrintSniffer`.

- Las gramáticas para ANTLR4 deben colocarse en la carpeta [`src/main/antlr4`](./src/main/antlr4/).

- La carpeta [`documentation`](./documentation/) está destinada a almacenar la documentación de los **code sniffers** implementados.
  - En el ejemplo, se incluyen carpetas para el "Anosmic Code Sniffer" y "Print Sniffer". También hay una carpeta que documenta la gramática "Bython", explicando su origen y mostrando ejemplos.

## :old_key: Cómo utilizar ANTLR4

Para generar las clases de Java correspondientes al **Lexer**, **Parser**, **Listener** y **Visitor** de una gramática específica, seguí estos pasos:

1. Colocá la gramática en la carpeta [`src/main/antlr4`](./src/main/antlr4/).
2. Ejecutá el comando:

   ```bash
   mvn antlr4:antlr4
   ```

## :construction_worker_woman: Cómo compilar y ejecutar los tests

Para compilar las clases y ejecutar las pruebas con JUnit, usá el siguiente comando:

```bash
mvn test
```
