public class Cliente {

    private String cedula;
    private String nombre;
    private String telefono;

    private Bicicleta[] bicicletas;
    private int contadorBicicletas;

    public Cliente(String cedula, String nombre, String telefono, int capacidadBicicletas) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.telefono = telefono;
        this.bicicletas = new Bicicleta[capacidadBicicletas];
        this.contadorBicicletas = 0;
    }

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

    public void agregarBicicleta(Bicicleta bici) {
        if (contadorBicicletas < bicicletas.length) {
            bicicletas[contadorBicicletas] = bici;
            contadorBicicletas++;
        } else {
            System.out.println("No se pudo agregar la bicicleta: el cliente " + nombre
                    + " ya tiene el maximo de " + bicicletas.length + " bicicletas.");
        }
    }

    public void mostrarInformacion() {
        System.out.println("Cliente: " + nombre + " | Cedula: " + cedula + " | Tel: " + telefono);

        if (contadorBicicletas == 0) {
            System.out.println("   Este cliente no tiene bicicletas registradas.");
        } else {
            System.out.println("   Bicicletas registradas (" + contadorBicicletas + "):");
            for (int i = 0; i < contadorBicicletas; i++) {
                bicicletas[i].mostrarInformacion();
            }
        }
    }
}
