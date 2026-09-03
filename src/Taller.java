/**
 * Clase Taller
 * -------------
 * Es la clase "mas grande" del sistema: representa el negocio
 * completo y agrupa a todos los clientes registrados.
 *
 * Relacion con Cliente: AGREGACION.
 * El taller administra un arreglo de clientes, pero un cliente
 * podria pensarse como algo independiente del taller (por eso es
 * agregacion y no composicion, que seria una relacion mas fuerte).
 */
public class Taller {

    private String nombreTaller;

    private Cliente[] clientes;
    private int contadorClientes;

    public Taller(String nombreTaller, int capacidadClientes) {
        this.nombreTaller = nombreTaller;
        this.clientes = new Cliente[capacidadClientes];
        this.contadorClientes = 0;
    }

    // ---------- Getters y Setters ----------

    public String getNombreTaller() {
        return nombreTaller;
    }

    public void setNombreTaller(String nombreTaller) {
        this.nombreTaller = nombreTaller;
    }

    public int getContadorClientes() {
        return contadorClientes;
    }

    /**
     * Agrega un cliente nuevo al arreglo del taller, validando primero
     * que haya espacio disponible.
     */
    public void agregarCliente(Cliente cliente) {
        if (contadorClientes < clientes.length) {
            clientes[contadorClientes] = cliente;
            contadorClientes++;
        } else {
            System.out.println("No se pudo registrar el cliente: el taller ya alcanzo su limite de "
                    + clientes.length + " clientes.");
        }
    }

    /**
     * Recorre todo el arreglo de clientes y muestra la informacion
     * de cada uno (que a su vez muestra sus bicicletas, y cada bicicleta
     * muestra sus ordenes). Asi se ve toda la cadena de objetos
     * relacionados con una sola llamada.
     */
    public void mostrarTodosLosClientes() {
        System.out.println("========================================");
        System.out.println(" Clientes registrados en " + nombreTaller);
        System.out.println("========================================");

        if (contadorClientes == 0) {
            System.out.println("Todavia no hay clientes registrados.");
            return;
        }

        for (int i = 0; i < contadorClientes; i++) {
            clientes[i].mostrarInformacion();
            System.out.println("----------------------------------------");
        }
    }
}
