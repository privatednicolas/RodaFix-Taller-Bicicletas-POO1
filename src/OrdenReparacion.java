public class OrdenReparacion {

    private int numeroOrden;
    private String descripcionTrabajo;
    private String fecha;
    private double costo;
    private String estado; // pendiente, en proceso o entregado

    public OrdenReparacion(int numeroOrden, String descripcionTrabajo, String fecha, double costo, String estado) {
        this.numeroOrden = numeroOrden;
        this.descripcionTrabajo = descripcionTrabajo;
        this.fecha = fecha;
        this.costo = costo;
        this.estado = estado;
    }

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

    // se usa cuando el trabajo cambia de pendiente a entregado
    public void setEstado(String estado) {
        this.estado = estado;
    }

    // en la orden normal el costo final es el mismo costo base
    public double calcularCostoFinal() {
        return costo;
    }

    public void mostrarInformacion() {
        System.out.println("      Orden #" + numeroOrden);
        System.out.println("      Trabajo: " + descripcionTrabajo);
        System.out.println("      Fecha: " + fecha);
        System.out.println("      Estado: " + estado);
        System.out.println("      Costo base: $" + costo);
        System.out.println("      Costo final: $" + calcularCostoFinal());
    }
}
