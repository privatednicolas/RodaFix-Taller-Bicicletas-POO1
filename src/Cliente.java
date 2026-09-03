/**
 * Clase Cliente
 * --------------
 * Representa a la persona dueña de una o varias bicicletas.
 *
 * Relacion con Bicicleta: COMPOSICION.
 * Cada cliente tiene su propio arreglo de bicicletas. Se considera
 * composicion porque una bicicleta, dentro de este sistema, no tiene
 * mucho sentido si no esta asociada a un cliente especifico.
 */
public class Cliente {

    private String cedula;
    private String nombre;
    private String telefono;

    // Arreglo nativo de bicicletas (composicion)
    private Bicicleta[] bicicletas;
    private int contadorBicicletas; // cuantas bicicletas hay guardadas

    // Constructor: recibe la capacidad maxima de bicicletas del cliente
    public Cliente(String cedula, String nombre, String telefono, int capacidadBicicletas) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.telefono = telefono;
        this.bicicletas = new Bicicleta[capacidadBicicletas];
        this.contadorBicicletas = 0;
    }

    // ---------- Getters y Setters ----------

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getContadorBicicletas() {
        return contadorBicicletas;
    }

    /**
     * Agrega una bicicleta al arreglo del cliente, siempre y cuando
     * todavia haya espacio disponible (contador menor que la capacidad).
     */
    public void agregarBicicleta(Bicicleta bici) {
        if (contadorBicicletas < bicicletas.length) {
            bicicletas[contadorBicicletas] = bici;
            contadorBicicletas++;
        } else {
            System.out.println("No se pudo agregar la bicicleta: el cliente " + nombre
                    + " ya alcanzo su limite de " + bicicletas.length + " bicicletas registradas.");
        }
    }

    /**
     * Muestra los datos del cliente y, dentro de eso, recorre su arreglo
     * de bicicletas llamando al mostrarInformacion() de cada una.
     * Esto es un ejemplo de como un objeto puede "delegar" en otros
     * objetos la tarea de mostrar su propia informacion.
     */
    public void mostrarInformacion() {
        System.out.println("Cliente: " + nombre + " | Cedula: " + cedula + " | Tel: " + telefono);

        if (contadorBicicletas == 0) {
            System.out.println("   Este cliente no tiene bicicletas registradas todavia.");
        } else {
            System.out.println("   Bicicletas registradas (" + contadorBicicletas + "):");
            for (int i = 0; i < contadorBicicletas; i++) {
                bicicletas[i].mostrarInformacion();
            }
        }
    }
}
