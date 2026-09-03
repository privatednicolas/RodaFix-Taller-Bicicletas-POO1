/**
 * Clase Main
 * -----------
 * Aqui es donde arranca el programa. En este archivo:
 *   1. Creamos el taller y algunos clientes.
 *   2. Le agregamos bicicletas a esos clientes.
 *   3. Le agregamos ordenes de reparacion a esas bicicletas.
 *   4. Probamos que pasa cuando un arreglo se llena (limite maximo).
 *   5. Mostramos toda la informacion por consola.
 */
public class Main {

    public static void main(String[] args) {

        // 1) Creamos el taller. Le decimos que puede tener maximo 3 clientes,
        //    a proposito, para poder probar el limite mas adelante.
        Taller miTaller = new Taller("RodaFix", 3);

        // 2) Creamos algunos clientes.
        //    El ultimo numero del constructor es la capacidad maxima
        //    de bicicletas que puede tener ese cliente.
        Cliente cliente1 = new Cliente("1001", "Laura Gomez", "3001112233", 2);
        Cliente cliente2 = new Cliente("1002", "Andres Pardo", "3009998877", 2);

        // 3) Creamos bicicletas para el cliente 1.
        //    El ultimo numero del constructor es la capacidad maxima
        //    de ordenes de reparacion que puede tener esa bicicleta.
        Bicicleta bici1 = new Bicicleta("B-001", "Trek", "Marlin 5", "Montaña", 3);
        Bicicleta bici2 = new Bicicleta("B-002", "GW", "Alligator", "Urbana", 2);

        // Le agregamos las bicicletas al cliente 1
        cliente1.agregarBicicleta(bici1);
        cliente1.agregarBicicleta(bici2);

        // 4) Creamos ordenes de reparacion para la bici1
        OrdenReparacion orden1 = new OrdenReparacion(1, "Cambio de frenos delanteros", "01/09/2026", 45000, "entregado");
        OrdenReparacion orden2 = new OrdenReparacion(2, "Ajuste de cambios", "15/09/2026", 20000, "en proceso");

        bici1.agregarOrden(orden1);
        bici1.agregarOrden(orden2);

        // Le probamos el limite al historial de bici2, que solo tiene
        // capacidad para 2 ordenes.
        OrdenReparacion orden3 = new OrdenReparacion(3, "Mantenimiento general", "20/09/2026", 60000, "pendiente");
        OrdenReparacion orden4 = new OrdenReparacion(4, "Cambio de llanta trasera", "21/09/2026", 35000, "pendiente");
        OrdenReparacion orden5 = new OrdenReparacion(5, "Ajuste de frenos", "22/09/2026", 15000, "pendiente");

        bici2.agregarOrden(orden3); // entra (posicion 0)
        bici2.agregarOrden(orden4); // entra (posicion 1, llega al limite)
        bici2.agregarOrden(orden5); // NO entra, deberia mostrar el mensaje de arreglo lleno

        // 5) Creamos una bicicleta para el cliente 2
        Bicicleta bici3 = new Bicicleta("B-003", "Specialized", "Sirrus", "Ruta", 2);
        OrdenReparacion orden6 = new OrdenReparacion(6, "Revision de frenos", "05/09/2026", 18000, "pendiente");
        bici3.agregarOrden(orden6);

        cliente2.agregarBicicleta(bici3);

        // Probamos tambien el limite de bicicletas del cliente2 (capacidad 2)
        Bicicleta bici4 = new Bicicleta("B-004", "Merida", "Big Nine", "Montaña", 1);
        Bicicleta bici5 = new Bicicleta("B-005", "Orbea", "Vector", "Ruta", 1);

        cliente2.agregarBicicleta(bici4); // entra (posicion 1, llega al limite)
        cliente2.agregarBicicleta(bici5); // NO entra, deberia mostrar el mensaje de arreglo lleno

        // 6) Agregamos los clientes al taller
        Cliente cliente3 = new Cliente("1003", "Diana Rios", "3005554433", 1);
        Cliente cliente4 = new Cliente("1004", "Felipe Torres", "3007778899", 1);

        miTaller.agregarCliente(cliente1); // entra (posicion 0)
        miTaller.agregarCliente(cliente2); // entra (posicion 1)
        miTaller.agregarCliente(cliente3); // entra (posicion 2, llega al limite del taller)
        miTaller.agregarCliente(cliente4); // NO entra, el taller ya esta lleno (capacidad 3)

        // 7) Por ultimo, mostramos toda la informacion registrada.
        //    Con un solo metodo, el taller recorre todos sus clientes,
        //    cada cliente recorre sus bicicletas y cada bicicleta
        //    recorre sus ordenes de reparacion.
        miTaller.mostrarTodosLosClientes();
    }
}
