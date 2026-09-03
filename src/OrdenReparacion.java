/**
 * Clase OrdenReparacion
 * ----------------------
 * Representa un trabajo puntual que se le hace a una bicicleta:
 * por ejemplo cambiar los frenos, ajustar los cambios o hacer
 * un mantenimiento general.
 *
 * Esta es la clase más "pequeña" del sistema: no tiene arreglos
 * porque no necesita guardar otros objetos adentro, solo datos
 * simples (texto, numeros, decimales).
 */
public class OrdenReparacion {

    // Atributos privados (encapsulamiento: nadie desde afuera
    // puede tocar estos datos directamente, solo con getters/setters)
    private int numeroOrden;
    private String descripcionTrabajo;
    private String fecha;
    private double costo;
    private String estado; // "pendiente", "en proceso" o "entregado"

    // Constructor: se usa "this" para diferenciar el atributo de la clase
    // del parametro que recibe el metodo (los dos se llaman igual)
    public OrdenReparacion(int numeroOrden, String descripcionTrabajo, String fecha, double costo, String estado) {
        this.numeroOrden = numeroOrden;
        this.descripcionTrabajo = descripcionTrabajo;
        this.fecha = fecha;
        this.costo = costo;
        this.estado = estado;
    }

    // ---------- Getters y Setters ----------
    // Sirven para leer y modificar los atributos privados desde afuera
    // de una forma controlada, en vez de dejarlos publicos.

    public int getNumeroOrden() {
        return numeroOrden;
    }

    public void setNumeroOrden(int numeroOrden) {
        this.numeroOrden = numeroOrden;
    }

    public String getDescripcionTrabajo() {
        return descripcionTrabajo;
    }

    public void setDescripcionTrabajo(String descripcionTrabajo) {
        this.descripcionTrabajo = descripcionTrabajo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public String getEstado() {
        return estado;
    }

    // Este setter es el que se usaria, por ejemplo, cuando el taller
    // termina el trabajo y hay que cambiar el estado a "entregado"
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Muestra por consola los datos de esta orden de reparacion.
     * Usamos "\n" con moderacion y System.out.println para que
     * cada dato salga en su propia linea, ordenado y facil de leer.
     */
    public void mostrarInformacion() {
        System.out.println("      Orden #" + numeroOrden);
        System.out.println("      Trabajo: " + descripcionTrabajo);
        System.out.println("      Fecha: " + fecha);
        System.out.println("      Costo: $" + costo);
        System.out.println("      Estado: " + estado);
    }
}
