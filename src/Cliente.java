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

    public int getCapacidad() {
        return bicicletas.length;
    }

    // devuelve la bicicleta de la posicion i o null si no existe
    public Bicicleta getBicicleta(int i) {
        if (i >= 0 && i < contadorBicicletas) {
            return bicicletas[i];
        }
        return null;
    }

    public Bicicleta buscarBicicleta(String codigo) {
        for (int i = 0; i < contadorBicicletas; i++) {
            if (bicicletas[i].getCodigo().equals(codigo)) {
                return bicicletas[i];
            }
        }
        return null;
    }

    public boolean agregarBicicleta(Bicicleta bici) {
        if (contadorBicicletas < bicicletas.length) {
            bicicletas[contadorBicicletas] = bici;
            contadorBicicletas++;
            return true;
        } else {
            System.out.println("No se pudo agregar la bicicleta: el cliente " + nombre
                    + " ya tiene el maximo de " + bicicletas.length + " bicicletas.");
            return false;
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
