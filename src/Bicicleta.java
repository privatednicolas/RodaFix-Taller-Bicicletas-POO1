public class Bicicleta {

    private String codigo; // identificador interno del taller
    private String marca;
    private String modelo;
    private String tipo;

    // el arreglo guarda ordenes normales, urgentes y de mantenimiento
    private OrdenReparacion[] historialOrdenes;
    private int contadorOrdenes;

    public Bicicleta(String codigo, String marca, String modelo, String tipo, int capacidadOrdenes) {
        this.codigo = codigo;
        this.marca = marca;
        this.modelo = modelo;
        this.tipo = tipo;
        this.historialOrdenes = new OrdenReparacion[capacidadOrdenes];
        this.contadorOrdenes = 0;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getContadorOrdenes() {
        return contadorOrdenes;
    }

    // solo guarda si todavia hay espacio en el arreglo
    public int getCapacidadOrdenes() {
        return historialOrdenes.length;
    }

    // devuelve la orden de la posicion i o null si no existe
    public OrdenReparacion getOrden(int i) {
        if (i >= 0 && i < contadorOrdenes) {
            return historialOrdenes[i];
        }
        return null;
    }

    public boolean agregarOrden(OrdenReparacion orden) {
        if (contadorOrdenes < historialOrdenes.length) {
            historialOrdenes[contadorOrdenes] = orden;
            contadorOrdenes++;
            return true;
        } else {
            System.out.println("No se pudo agregar la orden: el historial de la bicicleta "
                    + codigo + " ya esta lleno (maximo " + historialOrdenes.length + ").");
            return false;
        }
    }

    // suma el costo final de cada orden, cada una calcula el suyo
    public double calcularTotal() {
        double total = 0;
        for (int i = 0; i < contadorOrdenes; i++) {
            total += historialOrdenes[i].calcularCostoFinal();
        }
        return total;
    }

    public void mostrarInformacion() {
        System.out.println("   Bicicleta [" + codigo + "] " + marca + " " + modelo + " - Tipo: " + tipo);

        if (contadorOrdenes == 0) {
            System.out.println("      Sin ordenes de reparacion registradas.");
        } else {
            System.out.println("      Historial de reparaciones (" + contadorOrdenes + "):");
            for (int i = 0; i < contadorOrdenes; i++) {
                historialOrdenes[i].mostrarInformacion();
            }
            System.out.println("      Total de la bicicleta: $" + calcularTotal());
        }
    }
}
