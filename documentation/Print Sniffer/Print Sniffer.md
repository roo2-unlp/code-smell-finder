# Print Sniffer

**Print Sniffer** es una herramienta de análisis de código que detecta las invocaciones a la función `print` en el código fuente.

- Si se encuentran llamadas a `print`, el sniffer informa que el código presenta un "smell" (indicación de un posible problema en el diseño) y proporciona la cantidad total de veces que aparece esta función.

- En caso de que no se detecten llamadas a `print`, el sniffer indica que el código no presenta ningún "smell".
