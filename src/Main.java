public class Main {

    // por defecto abre la interfaz grafica (tambien se puede ejecutar VentanaPrincipal directamente)
    // si se ejecuta con el argumento "consola" corre la demostracion por consola
    public static void main(String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("consola")) {
            demoConsola();
        } else {
            VentanaPrincipal.main(args);
        }
    }

    // demostracion original por consola: prueba el limite de los arreglos
    public static void demoConsola() {

        // el taller acepta maximo 3 clientes para probar el limite
        Taller miTaller = new Taller("RodaFix", 3);

        Cliente cliente1 = new Cliente("1001", "Laura Gomez", "3001112233", 2);
        Cliente cliente2 = new Cliente("1002", "Andres Pardo", "3009998877", 2);

        Bicicleta bici1 = new Bicicleta("B-001", "Trek", "Marlin 5", "Montana", 3);
        Bicicleta bici2 = new Bicicleta("B-002", "GW", "Alligator", "Urbana", 2);

        cliente1.agregarBicicleta(bici1);
        cliente1.agregarBicicleta(bici2);

        // las variables son de tipo OrdenReparacion pero los objetos son de distinto tipo
        OrdenReparacion orden1 = new OrdenReparacion(1, "Cambio de frenos delanteros", "01/09/2026", 45000, "entregado");
        OrdenReparacion orden2 = new OrdenUrgente(2, "Ajuste de cambios", "15/09/2026", 20000, "en proceso", 20);

        bici1.agregarOrden(orden1);
        bici1.agregarOrden(orden2);

        OrdenReparacion orden3 = new OrdenMantenimiento(3, "Mantenimiento general", "20/09/2026", 60000, "pendiente", 10);
        OrdenReparacion orden4 = new OrdenReparacion(4, "Cambio de llanta trasera", "21/09/2026", 35000, "pendiente");
        OrdenReparacion orden5 = new OrdenReparacion(5, "Ajuste de frenos", "22/09/2026", 15000, "pendiente");

        bici2.agregarOrden(orden3);
        bici2.agregarOrden(orden4);
        bici2.agregarOrden(orden5); // no entra, el historial de bici2 ya esta lleno

        Bicicleta bici3 = new Bicicleta("B-003", "Specialized", "Sirrus", "Ruta", 2);
        OrdenReparacion orden6 = new OrdenReparacion(6, "Revision de frenos", "05/09/2026", 18000, "pendiente");
        bici3.agregarOrden(orden6);

        cliente2.agregarBicicleta(bici3);

        Bicicleta bici4 = new Bicicleta("B-004", "Merida", "Big Nine", "Montana", 1);
        Bicicleta bici5 = new Bicicleta("B-005", "Orbea", "Vector", "Ruta", 1);

        cliente2.agregarBicicleta(bici4);
        cliente2.agregarBicicleta(bici5); // no entra, cliente2 ya tiene 2 bicicletas

        Cliente cliente3 = new Cliente("1003", "Diana Rios", "3005554433", 1);
        Cliente cliente4 = new Cliente("1004", "Felipe Torres", "3007778899", 1);

        miTaller.agregarCliente(cliente1);
        miTaller.agregarCliente(cliente2);
        miTaller.agregarCliente(cliente3);
        miTaller.agregarCliente(cliente4); // no entra, el taller ya esta lleno

        miTaller.mostrarTodosLosClientes();
    }
}
