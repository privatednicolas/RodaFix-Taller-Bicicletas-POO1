public class Taller {

    private String nombreTaller;

    private Cliente[] clientes;
    private int contadorClientes;

    public Taller(String nombreTaller, int capacidadClientes) {
        this.nombreTaller = nombreTaller;
        this.clientes = new Cliente[capacidadClientes];
        this.contadorClientes = 0;
    }

    public String getNombreTaller() {
        return nombreTaller;
    }

    public void setNombreTaller(String nombreTaller) {
        this.nombreTaller = nombreTaller;
    }

    public int getContadorClientes() {
        return contadorClientes;
    }

    public void agregarCliente(Cliente cliente) {
        if (contadorClientes < clientes.length) {
            clientes[contadorClientes] = cliente;
            contadorClientes++;
        } else {
            System.out.println("No se pudo registrar el cliente: el taller ya tiene el maximo de "
                    + clientes.length + " clientes.");
        }
    }

    // cada cliente muestra sus bicicletas y cada bicicleta sus ordenes
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
