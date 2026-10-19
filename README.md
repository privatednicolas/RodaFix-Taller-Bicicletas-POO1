# RodaFix - Sistema de Gestion para Taller de Bicicletas

Proyecto para la materia de Programacion Orientada a Objetos I (CUN).

Autores: Juan Verano Murcia y Nicolas Rodriguez Sanchez

## Descripcion
Sistema en Java que gestiona los clientes de un taller de bicicletas, las bicicletas de cada
cliente y las ordenes de reparacion de cada bicicleta, usando arreglos nativos de tamano fijo.
Se puede usar de dos formas: con una interfaz grafica (Swing) o con una demostracion por consola.

## Como ejecutarlo

### En NetBeans
1. Crear un proyecto Java with Ant (o abrir uno existente) y copiar los archivos de `src/`
   a Source Packages (paquete por defecto).
2. Run Project (F6) abre la ventana, porque la clase principal es `Main`.
3. Tambien se puede abrir `VentanaPrincipal.java` y usar Run File (Shift+F6): abre la misma ventana.

### Desde la terminal
```
cd src
javac *.java
java Main            # abre la interfaz grafica
java Main consola    # demostracion por consola
```

## Estructura
- `src/Taller.java` - administra un arreglo de clientes (agregacion)
- `src/Cliente.java` - administra un arreglo de bicicletas (composicion)
- `src/Bicicleta.java` - administra un arreglo de ordenes (asociacion)
- `src/OrdenReparacion.java` - clase padre de las ordenes
- `src/OrdenUrgente.java` - hereda de OrdenReparacion, cobra un recargo
- `src/OrdenMantenimiento.java` - hereda de OrdenReparacion, aplica un descuento
- `src/VentanaPrincipal.java` - interfaz grafica (valor agregado)
- `src/Main.java` - punto de entrada: abre la ventana, o la demo de consola con el argumento `consola`

## Valor agregado: interfaz grafica
La ventana usa las mismas clases del proyecto de consola. Tiene cuatro pestanas:
Clientes, Bicicletas, Ordenes de reparacion y Resumen (un arbol Taller > Cliente > Bicicleta > Orden).
Valida los datos (fechas dd/mm/aaaa, costos numericos) y avisa cuando un arreglo se llena.
Para conectarla con las clases, `agregarCliente`, `agregarBicicleta` y `agregarOrden` ahora
devuelven `true` o `false`, y se agregaron getters como `getCliente(i)`, `getBicicleta(i)` y `getOrden(i)`.

## Temas del curso
- Encapsulamiento: atributos privados con getters y setters
- Herencia: OrdenUrgente y OrdenMantenimiento extienden OrdenReparacion
- Polimorfismo: calcularCostoFinal() se sobrescribe en cada tipo de orden
- Relaciones: agregacion, composicion y asociacion
- Ciclo de vida: los objetos se crean con new, se guardan en arreglos y se usan hasta que termina el programa
- Interfaces graficas con Swing (valor agregado)
