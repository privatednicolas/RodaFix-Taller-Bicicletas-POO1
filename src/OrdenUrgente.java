// hereda de OrdenReparacion: es una orden normal pero con recargo
public class OrdenUrgente extends OrdenReparacion {

    private double recargo; // porcentaje, por ejemplo 20

    public OrdenUrgente(int numeroOrden, String descripcionTrabajo, String fecha, double costo, String estado, double recargo) {
        super(numeroOrden, descripcionTrabajo, fecha, costo, estado);
        this.recargo = recargo;
    }

    public double getRecargo() {
        return recargo;
    }

    public void setRecargo(double recargo) {
        this.recargo = recargo;
    }

    @Override
    public double calcularCostoFinal() {
        return getCosto() + getCosto() * recargo / 100;
    }

    @Override
    public void mostrarInformacion() {
        System.out.println("      [URGENTE] recargo del " + recargo + "%");
        super.mostrarInformacion();
    }
}
