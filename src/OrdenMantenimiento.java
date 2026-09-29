// hereda de OrdenReparacion: es una orden normal pero con descuento
public class OrdenMantenimiento extends OrdenReparacion {

    private double descuento; // porcentaje, por ejemplo 10

    public OrdenMantenimiento(int numeroOrden, String descripcionTrabajo, String fecha, double costo, String estado, double descuento) {
        super(numeroOrden, descripcionTrabajo, fecha, costo, estado);
        this.descuento = descuento;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    @Override
    public double calcularCostoFinal() {
        return getCosto() - getCosto() * descuento / 100;
    }

    @Override
    public void mostrarInformacion() {
        System.out.println("      [MANTENIMIENTO] descuento del " + descuento + "%");
        super.mostrarInformacion();
    }
}
