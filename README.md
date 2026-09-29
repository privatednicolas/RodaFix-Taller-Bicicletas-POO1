# RodaFix - Sistema de Gestion para Taller de Bicicletas

Proyecto para la materia de Programacion Orientada a Objetos I.

## Descripcion
Programa de consola en Java que gestiona los clientes de un taller de bicicletas,
las bicicletas de cada cliente y las ordenes de reparacion de cada bicicleta,
usando arreglos nativos de tamano fijo.

## Estructura
- `src/Taller.java` - administra un arreglo de clientes (agregacion)
- `src/Cliente.java` - administra un arreglo de bicicletas (composicion)
- `src/Bicicleta.java` - administra un arreglo de ordenes (asociacion)
- `src/OrdenReparacion.java` - clase padre de las ordenes
- `src/OrdenUrgente.java` - hereda de OrdenReparacion, cobra un recargo
- `src/OrdenMantenimiento.java` - hereda de OrdenReparacion, aplica un descuento
- `src/Main.java` - punto de entrada, prueba el limite de los arreglos

## Temas del curso
- Encapsulamiento: atributos privados con getters y setters
- Herencia: OrdenUrgente y OrdenMantenimiento extienden OrdenReparacion
- Polimorfismo: calcularCostoFinal() se sobrescribe en cada tipo de orden
- Ciclo de vida: los objetos se crean con new, se guardan en arreglos y se usan hasta que termina el programa
