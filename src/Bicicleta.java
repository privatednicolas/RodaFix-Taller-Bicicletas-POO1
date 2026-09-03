/**
 * Clase Bicicleta
 * ----------------
 * Representa una bicicleta que un cliente lleva al taller.
 *
 * Relacion con OrdenReparacion: ASOCIACION.
 * Cada bicicleta guarda un arreglo con las ordenes de reparacion
 * que se le han hecho (su "historial"). Usamos un arreglo nativo
 * de tamaño fijo y un contador para saber cuantas ordenes hay
 * guardadas hasta el momento.
 */
public class Bicicleta {

    private String codigo;   // identificador interno del taller, ej: "B-001"
    private String marca;
    private String modelo;
    private String tipo;     // montaña, ruta, urbana, etc.

    // Arreglo nativo de ordenes de reparacion (composicion/asociacion simulada)
    private OrdenReparacion[] historialOrdenes;
    private int contadorOrdenes; // cuantas ordenes hay guardadas ahora mismo

    // Constructor: recibe la capacidad maxima del historial de ordenes
    public Bicicleta(String codigo, String marca, String modelo, String tipo, int capacidadOrdenes) {
        this.codigo = codigo;
        this.marca = marca;
        this.modelo = modelo;
        this.tipo = tipo;
        this.historialOrdenes = new OrdenReparacion[capacidadOrdenes];
        this.contadorOrdenes = 0; // arranca en cero, todavia no hay ordenes
    }

    // ---------- Getters y Setters ----------

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

    /**
     * Agrega una nueva orden de reparacion al historial de esta bicicleta.
     * Antes de guardar, revisa si todavia hay espacio en el arreglo
     * comparando el contador contra el largo del arreglo (.length).
     */
    public void agregarOrden(OrdenReparacion orden) {
        if (contadorOrdenes < historialOrdenes.length) {
            historialOrdenes[contadorOrdenes] = orden;
            contadorOrdenes++; // aumentamos el contador despues de guardar
        } else {
            System.out.println("No se pudo agregar la orden: el historial de la bicicleta "
                    + codigo + " ya esta lleno (capacidad maxima: " + historialOrdenes.length + ").");
        }
    }

    /**
     * Muestra los datos de la bicicleta y recorre su arreglo de ordenes
     * para mostrar cada una. Recorremos solo hasta "contadorOrdenes"
     * porque de ahi en adelante las posiciones del arreglo estan vacias (null).
     */
    public void mostrarInformacion() {
        System.out.println("   Bicicleta [" + codigo + "] " + marca + " " + modelo + " - Tipo: " + tipo);

        if (contadorOrdenes == 0) {
            System.out.println("      Sin ordenes de reparacion registradas.");
        } else {
            System.out.println("      Historial de reparaciones (" + contadorOrdenes + "):");
            for (int i = 0; i < contadorOrdenes; i++) {
                historialOrdenes[i].mostrarInformacion();
            }
        }
    }
}
