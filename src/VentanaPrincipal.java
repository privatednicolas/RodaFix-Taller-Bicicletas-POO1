import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 * Interfaz grafica del sistema RodaFix.
 * Usa las mismas clases del proyecto de consola (Taller, Cliente, Bicicleta,
 * OrdenReparacion, OrdenUrgente y OrdenMantenimiento) con sus arreglos de tamano fijo.
 */
@SuppressWarnings("serial")
public class VentanaPrincipal extends JFrame {

    // limites de los arreglos (pequenos a proposito para poder probar que se llenan)
    private static final int CAP_CLIENTES = 5;
    private static final int CAP_BICICLETAS = 3;
    private static final int CAP_ORDENES = 4;

    private static final DateTimeFormatter FORMATO_FECHA
            = DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);

    private static final Color COLOR_PRINCIPAL = new Color(27, 94, 32);

    // ---- modelo ----
    private final Taller taller = new Taller("RodaFix", CAP_CLIENTES);
    private int siguienteOrden = 1;
    private boolean actualizando = false;
    private final NumberFormat formatoDinero = NumberFormat.getNumberInstance(Locale.forLanguageTag("es-CO"));

    // ---- componentes: clientes ----
    private JTextField txtCedula;
    private JTextField txtNombre;
    private JTextField txtTelefono;
    private DefaultTableModel modeloClientes;

    // ---- componentes: bicicletas ----
    private JComboBox<String> cmbClienteBici;
    private JTextField txtCodigo;
    private JTextField txtMarca;
    private JTextField txtModelo;
    private JComboBox<String> cmbTipoBici;
    private DefaultTableModel modeloBicis;

    // ---- componentes: ordenes ----
    private JComboBox<String> cmbClienteOrden;
    private JComboBox<String> cmbBiciOrden;
    private JTextField txtNumeroOrden;
    private JTextField txtDescripcion;
    private JTextField txtFecha;
    private JTextField txtCosto;
    private JComboBox<String> cmbEstado;
    private JComboBox<String> cmbTipoOrden;
    private JLabel lblPorcentaje;
    private JTextField txtPorcentaje;
    private JTable tablaOrdenes;
    private DefaultTableModel modeloOrdenes;
    private JLabel lblTotalBici;

    // ---- componentes: resumen y barra de estado ----
    private JTree arbol;
    private JLabel lblEstado;

    public VentanaPrincipal() {
        super("RodaFix - Taller de Bicicletas");
        formatoDinero.setMaximumFractionDigits(2);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        setJMenuBar(crearMenu());
        add(crearEncabezado(), BorderLayout.NORTH);

        JTabbedPane pestanas = new JTabbedPane();
        pestanas.addTab("Clientes", crearPanelClientes());
        pestanas.addTab("Bicicletas", crearPanelBicicletas());
        pestanas.addTab("Ordenes de reparacion", crearPanelOrdenes());
        pestanas.addTab("Resumen", crearPanelResumen());
        add(pestanas, BorderLayout.CENTER);

        lblEstado = new JLabel(" Listo.");
        lblEstado.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        add(lblEstado, BorderLayout.SOUTH);

        refrescarTodo();

        setSize(960, 640);
        setMinimumSize(new java.awt.Dimension(820, 560));
        setLocationRelativeTo(null);
    }

    // =====================================================================
    //  Construccion de la interfaz
    // =====================================================================
    private JMenuBar crearMenu() {
        JMenuBar barra = new JMenuBar();
        JMenu menuArchivo = new JMenu("Archivo");

        JMenuItem itemEjemplo = new JMenuItem("Cargar datos de ejemplo");
        itemEjemplo.addActionListener(e -> cargarEjemplo());

        JMenuItem itemSalir = new JMenuItem("Salir");
        itemSalir.addActionListener(e -> System.exit(0));

        menuArchivo.add(itemEjemplo);
        menuArchivo.addSeparator();
        menuArchivo.add(itemSalir);
        barra.add(menuArchivo);
        return barra;
    }

    private JPanel crearEncabezado() {
        JPanel encabezado = new JPanel(new BorderLayout());
        encabezado.setBackground(COLOR_PRINCIPAL);
        encabezado.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));

        JLabel titulo = new JLabel("RodaFix - Sistema de Gestion para Taller de Bicicletas");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 20f));
        encabezado.add(titulo, BorderLayout.NORTH);

        JLabel limites = new JLabel("Limites: " + CAP_CLIENTES + " clientes | "
                + CAP_BICICLETAS + " bicicletas por cliente | "
                + CAP_ORDENES + " ordenes por bicicleta");
        limites.setForeground(new Color(200, 230, 201));
        encabezado.add(limites, BorderLayout.SOUTH);
        return encabezado;
    }

    private JPanel crearPanelClientes() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("Nuevo cliente"));

        txtCedula = new JTextField(15);
        txtNombre = new JTextField(15);
        txtTelefono = new JTextField(15);
        agregarFila(form, 0, 0, "Cedula:", txtCedula);
        agregarFila(form, 0, 1, "Nombre:", txtNombre);
        agregarFila(form, 1, 0, "Telefono:", txtTelefono);

        JButton btnAgregar = new JButton("Registrar cliente");
        btnAgregar.addActionListener(e -> agregarCliente());
        agregarBoton(form, 1, 1, btnAgregar);

        modeloClientes = crearModelo("Cedula", "Nombre", "Telefono", "Bicicletas");
        JTable tabla = crearTabla(modeloClientes);

        panel.add(form, BorderLayout.NORTH);
        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelBicicletas() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("Nueva bicicleta"));

        cmbClienteBici = new JComboBox<String>();
        cmbClienteBici.addActionListener(e -> {
            if (!actualizando) {
                refrescarTablaBicis();
            }
        });
        txtCodigo = new JTextField(15);
        txtMarca = new JTextField(15);
        txtModelo = new JTextField(15);
        cmbTipoBici = new JComboBox<String>(new String[]{"Montana", "Urbana", "Ruta", "BMX", "Electrica"});

        agregarFila(form, 0, 0, "Cliente:", cmbClienteBici);
        agregarFila(form, 0, 1, "Codigo:", txtCodigo);
        agregarFila(form, 1, 0, "Marca:", txtMarca);
        agregarFila(form, 1, 1, "Modelo:", txtModelo);
        agregarFila(form, 2, 0, "Tipo:", cmbTipoBici);

        JButton btnAgregar = new JButton("Registrar bicicleta");
        btnAgregar.addActionListener(e -> agregarBicicleta());
        agregarBoton(form, 2, 1, btnAgregar);

        modeloBicis = crearModelo("Codigo", "Marca", "Modelo", "Tipo", "Ordenes", "Total");
        JTable tabla = crearTabla(modeloBicis);

        panel.add(form, BorderLayout.NORTH);
        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelOrdenes() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("Nueva orden de reparacion"));

        cmbClienteOrden = new JComboBox<String>();
        cmbClienteOrden.addActionListener(e -> {
            if (!actualizando) {
                actualizando = true;
                llenarComboBicis();
                actualizando = false;
                refrescarTablaOrdenes();
            }
        });
        cmbBiciOrden = new JComboBox<String>();
        cmbBiciOrden.addActionListener(e -> {
            if (!actualizando) {
                refrescarTablaOrdenes();
            }
        });

        txtNumeroOrden = new JTextField(15);
        txtNumeroOrden.setEditable(false);
        txtDescripcion = new JTextField(15);
        txtFecha = new JTextField(LocalDate.now().format(FORMATO_FECHA), 15);
        txtFecha.setToolTipText("Formato dd/MM/aaaa");
        txtCosto = new JTextField(15);
        txtCosto.setToolTipText("Valor en pesos, por ejemplo 45000");
        cmbEstado = new JComboBox<String>(new String[]{"pendiente", "en proceso", "entregado"});
        cmbTipoOrden = new JComboBox<String>(new String[]{"Normal", "Urgente", "Mantenimiento"});
        lblPorcentaje = new JLabel("Porcentaje (%):");
        txtPorcentaje = new JTextField(15);

        cmbTipoOrden.addActionListener(e -> actualizarCampoPorcentaje());

        agregarFila(form, 0, 0, "Cliente:", cmbClienteOrden);
        agregarFila(form, 0, 1, "Bicicleta:", cmbBiciOrden);
        agregarFila(form, 1, 0, "Numero de orden:", txtNumeroOrden);
        agregarFila(form, 1, 1, "Tipo de orden:", cmbTipoOrden);
        agregarFila(form, 2, 0, "Trabajo:", txtDescripcion);
        agregarFila(form, 2, 1, "Fecha:", txtFecha);
        agregarFila(form, 3, 0, "Costo base ($):", txtCosto);
        agregarFila(form, 3, 1, "Estado:", cmbEstado);
        agregarFila(form, 4, 0, lblPorcentaje, txtPorcentaje);

        JButton btnAgregar = new JButton("Agregar orden");
        btnAgregar.addActionListener(e -> agregarOrden());
        agregarBoton(form, 4, 1, btnAgregar);

        actualizarCampoPorcentaje();

        modeloOrdenes = crearModelo("N.", "Tipo", "Trabajo", "Fecha", "Estado", "Costo base", "Costo final");
        tablaOrdenes = crearTabla(modeloOrdenes);
        tablaOrdenes.getColumnModel().getColumn(0).setMaxWidth(60);

        JPanel inferior = new JPanel(new BorderLayout(10, 0));
        lblTotalBici = new JLabel("Total de la bicicleta: $0");
        lblTotalBici.setFont(lblTotalBici.getFont().deriveFont(Font.BOLD, 14f));
        JButton btnEntregada = new JButton("Marcar orden seleccionada como entregada");
        btnEntregada.addActionListener(e -> marcarEntregada());
        inferior.add(lblTotalBici, BorderLayout.WEST);
        inferior.add(btnEntregada, BorderLayout.EAST);

        panel.add(form, BorderLayout.NORTH);
        panel.add(new JScrollPane(tablaOrdenes), BorderLayout.CENTER);
        panel.add(inferior, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel crearPanelResumen() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        arbol = new JTree(new DefaultMutableTreeNode("Taller"));
        panel.add(new JScrollPane(arbol), BorderLayout.CENTER);
        return panel;
    }

    // ---- ayudas para armar formularios y tablas ----
    private void agregarFila(JPanel panel, int fila, int columna, String texto, JComponent campo) {
        agregarFila(panel, fila, columna, new JLabel(texto), campo);
    }

    private void agregarFila(JPanel panel, int fila, int columna, JComponent etiqueta, JComponent campo) {
        GridBagConstraints g = new GridBagConstraints();
        g.gridx = columna * 2;
        g.gridy = fila;
        g.anchor = GridBagConstraints.WEST;
        g.insets = new Insets(5, 8, 5, 8);
        panel.add(etiqueta, g);

        g = new GridBagConstraints();
        g.gridx = columna * 2 + 1;
        g.gridy = fila;
        g.weightx = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        g.insets = new Insets(5, 8, 5, 8);
        panel.add(campo, g);
    }

    private void agregarBoton(JPanel panel, int fila, int columna, JButton boton) {
        GridBagConstraints g = new GridBagConstraints();
        g.gridx = columna * 2 + 1;
        g.gridy = fila;
        g.anchor = GridBagConstraints.EAST;
        g.insets = new Insets(5, 8, 5, 8);
        panel.add(boton, g);
    }

    private DefaultTableModel crearModelo(String... columnas) {
        return new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
    }

    private JTable crearTabla(DefaultTableModel modelo) {
        JTable tabla = new JTable(modelo);
        tabla.setRowHeight(24);
        tabla.setFillsViewportHeight(true);
        tabla.getTableHeader().setReorderingAllowed(false);
        return tabla;
    }

    private void actualizarCampoPorcentaje() {
        int tipo = cmbTipoOrden.getSelectedIndex();
        if (tipo == 1) {
            lblPorcentaje.setText("Recargo (%):");
            txtPorcentaje.setEnabled(true);
        } else if (tipo == 2) {
            lblPorcentaje.setText("Descuento (%):");
            txtPorcentaje.setEnabled(true);
        } else {
            lblPorcentaje.setText("Porcentaje (%):");
            txtPorcentaje.setText("");
            txtPorcentaje.setEnabled(false);
        }
    }

    // =====================================================================
    //  Acciones de los botones
    // =====================================================================
    private void agregarCliente() {
        String cedula = txtCedula.getText().trim();
        String nombre = txtNombre.getText().trim();
        String telefono = txtTelefono.getText().trim();

        if (cedula.isEmpty() || nombre.isEmpty() || telefono.isEmpty()) {
            aviso("Completa la cedula, el nombre y el telefono.");
            return;
        }
        if (taller.buscarCliente(cedula) != null) {
            aviso("Ya existe un cliente con la cedula " + cedula + ".");
            return;
        }

        Cliente cliente = new Cliente(cedula, nombre, telefono, CAP_BICICLETAS);
        if (!taller.agregarCliente(cliente)) {
            aviso("No se pudo registrar el cliente: el taller ya tiene el maximo de "
                    + taller.getCapacidad() + " clientes.");
            return;
        }

        txtCedula.setText("");
        txtNombre.setText("");
        txtTelefono.setText("");
        refrescarTodo();
        estado("Cliente registrado: " + nombre);
    }

    private void agregarBicicleta() {
        Cliente cliente = taller.getCliente(cmbClienteBici.getSelectedIndex());
        if (cliente == null) {
            aviso("Primero registra un cliente y seleccionalo.");
            return;
        }

        String codigo = txtCodigo.getText().trim();
        String marca = txtMarca.getText().trim();
        String modelo = txtModelo.getText().trim();
        String tipo = (String) cmbTipoBici.getSelectedItem();

        if (codigo.isEmpty() || marca.isEmpty() || modelo.isEmpty()) {
            aviso("Completa el codigo, la marca y el modelo.");
            return;
        }
        if (existeBicicleta(codigo)) {
            aviso("Ya existe una bicicleta con el codigo " + codigo + ".");
            return;
        }

        Bicicleta bici = new Bicicleta(codigo, marca, modelo, tipo, CAP_ORDENES);
        if (!cliente.agregarBicicleta(bici)) {
            aviso("No se pudo agregar la bicicleta: " + cliente.getNombre()
                    + " ya tiene el maximo de " + cliente.getCapacidad() + " bicicletas.");
            return;
        }

        txtCodigo.setText("");
        txtMarca.setText("");
        txtModelo.setText("");
        refrescarTodo();
        estado("Bicicleta " + codigo + " registrada a " + cliente.getNombre());
    }

    private void agregarOrden() {
        Bicicleta bici = biciSeleccionadaEnOrdenes();
        if (bici == null) {
            aviso("Selecciona un cliente que tenga al menos una bicicleta.");
            return;
        }

        String descripcion = txtDescripcion.getText().trim();
        if (descripcion.isEmpty()) {
            aviso("Escribe la descripcion del trabajo.");
            return;
        }

        String fecha = txtFecha.getText().trim();
        try {
            LocalDate.parse(fecha, FORMATO_FECHA);
        } catch (DateTimeParseException ex) {
            aviso("Fecha invalida. Usa el formato dd/MM/aaaa, por ejemplo 15/09/2026.");
            return;
        }

        double costo;
        try {
            String limpio = txtCosto.getText().trim().replace("$", "").replace(".", "")
                    .replace(",", "").replace(" ", "");
            costo = Double.parseDouble(limpio);
        } catch (NumberFormatException ex) {
            aviso("El costo debe ser un numero, por ejemplo 45000.");
            return;
        }
        if (costo <= 0) {
            aviso("El costo debe ser mayor que cero.");
            return;
        }

        String estadoOrden = (String) cmbEstado.getSelectedItem();
        int tipo = cmbTipoOrden.getSelectedIndex();
        double porcentaje = 0;

        if (tipo != 0) {
            try {
                porcentaje = Double.parseDouble(txtPorcentaje.getText().trim().replace(",", "."));
            } catch (NumberFormatException ex) {
                aviso("El porcentaje debe ser un numero, por ejemplo 20.");
                return;
            }
            if (porcentaje < 0 || porcentaje > 100) {
                aviso("El porcentaje debe estar entre 0 y 100.");
                return;
            }
        }

        // polimorfismo: la variable es OrdenReparacion pero el objeto puede ser de cualquier tipo
        OrdenReparacion orden;
        if (tipo == 1) {
            orden = new OrdenUrgente(siguienteOrden, descripcion, fecha, costo, estadoOrden, porcentaje);
        } else if (tipo == 2) {
            orden = new OrdenMantenimiento(siguienteOrden, descripcion, fecha, costo, estadoOrden, porcentaje);
        } else {
            orden = new OrdenReparacion(siguienteOrden, descripcion, fecha, costo, estadoOrden);
        }

        if (!bici.agregarOrden(orden)) {
            aviso("No se pudo agregar la orden: el historial de la bicicleta " + bici.getCodigo()
                    + " ya esta lleno (maximo " + bici.getCapacidadOrdenes() + ").");
            return;
        }

        siguienteOrden++;
        txtDescripcion.setText("");
        txtCosto.setText("");
        txtPorcentaje.setText("");
        refrescarTodo();
        estado("Orden #" + orden.getNumeroOrden() + " agregada a la bicicleta " + bici.getCodigo());
    }

    private void marcarEntregada() {
        Bicicleta bici = biciSeleccionadaEnOrdenes();
        int fila = tablaOrdenes.getSelectedRow();
        if (bici == null || fila < 0) {
            aviso("Selecciona una orden en la tabla.");
            return;
        }
        OrdenReparacion orden = bici.getOrden(fila);
        if (orden == null) {
            return;
        }
        orden.setEstado("entregado");
        refrescarTodo();
        tablaOrdenes.setRowSelectionInterval(fila, fila);
        estado("Orden #" + orden.getNumeroOrden() + " marcada como entregada.");
    }

    private void cargarEjemplo() {
        if (taller.getContadorClientes() > 0) {
            aviso("Los datos de ejemplo solo se pueden cargar cuando el taller esta vacio.");
            return;
        }

        Cliente laura = new Cliente("1001", "Laura Gomez", "3001112233", CAP_BICICLETAS);
        Cliente andres = new Cliente("1002", "Andres Pardo", "3009998877", CAP_BICICLETAS);
        Cliente diana = new Cliente("1003", "Diana Rios", "3005554433", CAP_BICICLETAS);

        Bicicleta trek = new Bicicleta("B-001", "Trek", "Marlin 5", "Montana", CAP_ORDENES);
        Bicicleta gw = new Bicicleta("B-002", "GW", "Alligator", "Urbana", CAP_ORDENES);
        Bicicleta specialized = new Bicicleta("B-003", "Specialized", "Sirrus", "Ruta", CAP_ORDENES);

        trek.agregarOrden(new OrdenReparacion(siguienteOrden++, "Cambio de frenos delanteros", "01/09/2026", 45000, "entregado"));
        trek.agregarOrden(new OrdenUrgente(siguienteOrden++, "Ajuste de cambios", "15/09/2026", 20000, "en proceso", 20));
        gw.agregarOrden(new OrdenMantenimiento(siguienteOrden++, "Mantenimiento general", "20/09/2026", 60000, "pendiente", 10));
        gw.agregarOrden(new OrdenReparacion(siguienteOrden++, "Cambio de llanta trasera", "21/09/2026", 35000, "pendiente"));
        specialized.agregarOrden(new OrdenReparacion(siguienteOrden++, "Revision de frenos", "05/09/2026", 18000, "pendiente"));

        laura.agregarBicicleta(trek);
        laura.agregarBicicleta(gw);
        andres.agregarBicicleta(specialized);

        taller.agregarCliente(laura);
        taller.agregarCliente(andres);
        taller.agregarCliente(diana);

        refrescarTodo();
        estado("Datos de ejemplo cargados.");
    }

    // =====================================================================
    //  Actualizacion de combos, tablas y arbol
    // =====================================================================
    private void refrescarTodo() {
        actualizando = true;
        try {
            llenarComboClientes(cmbClienteBici);
            llenarComboClientes(cmbClienteOrden);
            llenarComboBicis();
            refrescarTablaClientes();
            refrescarTablaBicis();
            refrescarTablaOrdenes();
            refrescarArbol();
            txtNumeroOrden.setText(String.valueOf(siguienteOrden));
        } finally {
            actualizando = false;
        }
    }

    private void llenarComboClientes(JComboBox<String> combo) {
        int seleccion = combo.getSelectedIndex();
        combo.removeAllItems();
        int total = taller.getContadorClientes();
        for (int i = 0; i < total; i++) {
            Cliente c = taller.getCliente(i);
            combo.addItem(c.getCedula() + " - " + c.getNombre());
        }
        if (total > 0) {
            combo.setSelectedIndex(seleccion >= 0 && seleccion < total ? seleccion : 0);
        }
    }

    private void llenarComboBicis() {
        int seleccion = cmbBiciOrden.getSelectedIndex();
        cmbBiciOrden.removeAllItems();
        Cliente cliente = taller.getCliente(cmbClienteOrden.getSelectedIndex());
        if (cliente == null) {
            return;
        }
        int total = cliente.getContadorBicicletas();
        for (int i = 0; i < total; i++) {
            Bicicleta b = cliente.getBicicleta(i);
            cmbBiciOrden.addItem(b.getCodigo() + " - " + b.getMarca() + " " + b.getModelo());
        }
        if (total > 0) {
            cmbBiciOrden.setSelectedIndex(seleccion >= 0 && seleccion < total ? seleccion : 0);
        }
    }

    private void refrescarTablaClientes() {
        modeloClientes.setRowCount(0);
        for (int i = 0; i < taller.getContadorClientes(); i++) {
            Cliente c = taller.getCliente(i);
            modeloClientes.addRow(new Object[]{c.getCedula(), c.getNombre(), c.getTelefono(),
                c.getContadorBicicletas() + " / " + c.getCapacidad()});
        }
    }

    private void refrescarTablaBicis() {
        modeloBicis.setRowCount(0);
        Cliente cliente = taller.getCliente(cmbClienteBici.getSelectedIndex());
        if (cliente == null) {
            return;
        }
        for (int i = 0; i < cliente.getContadorBicicletas(); i++) {
            Bicicleta b = cliente.getBicicleta(i);
            modeloBicis.addRow(new Object[]{b.getCodigo(), b.getMarca(), b.getModelo(), b.getTipo(),
                b.getContadorOrdenes() + " / " + b.getCapacidadOrdenes(), dinero(b.calcularTotal())});
        }
    }

    private void refrescarTablaOrdenes() {
        modeloOrdenes.setRowCount(0);
        Bicicleta bici = biciSeleccionadaEnOrdenes();
        if (bici == null) {
            lblTotalBici.setText("Total de la bicicleta: $0");
            return;
        }
        for (int i = 0; i < bici.getContadorOrdenes(); i++) {
            OrdenReparacion o = bici.getOrden(i);
            modeloOrdenes.addRow(new Object[]{o.getNumeroOrden(), nombreTipo(o), o.getDescripcionTrabajo(),
                o.getFecha(), o.getEstado(), dinero(o.getCosto()),
                dinero(o.calcularCostoFinal())}); // cada orden calcula su costo final (polimorfismo)
        }
        lblTotalBici.setText("Total de la bicicleta " + bici.getCodigo() + ": " + dinero(bici.calcularTotal()));
    }

    private void refrescarArbol() {
        DefaultMutableTreeNode raiz = new DefaultMutableTreeNode("Taller " + taller.getNombreTaller()
                + " (" + taller.getContadorClientes() + " / " + taller.getCapacidad() + " clientes)");

        for (int i = 0; i < taller.getContadorClientes(); i++) {
            Cliente c = taller.getCliente(i);
            DefaultMutableTreeNode nodoCliente = new DefaultMutableTreeNode(
                    "Cliente: " + c.getNombre() + " | Cedula: " + c.getCedula() + " | Tel: " + c.getTelefono());

            for (int j = 0; j < c.getContadorBicicletas(); j++) {
                Bicicleta b = c.getBicicleta(j);
                DefaultMutableTreeNode nodoBici = new DefaultMutableTreeNode(
                        "Bicicleta [" + b.getCodigo() + "] " + b.getMarca() + " " + b.getModelo()
                        + " - " + b.getTipo() + " | Total: " + dinero(b.calcularTotal()));

                for (int k = 0; k < b.getContadorOrdenes(); k++) {
                    OrdenReparacion o = b.getOrden(k);
                    nodoBici.add(new DefaultMutableTreeNode("Orden #" + o.getNumeroOrden()
                            + " [" + nombreTipo(o) + "] " + o.getDescripcionTrabajo()
                            + " - " + o.getFecha() + " - " + o.getEstado()
                            + " - " + dinero(o.calcularCostoFinal())));
                }
                nodoCliente.add(nodoBici);
            }
            raiz.add(nodoCliente);
        }

        arbol.setModel(new DefaultTreeModel(raiz));
        for (int i = 0; i < arbol.getRowCount(); i++) {
            arbol.expandRow(i);
        }
    }

    // =====================================================================
    //  Utilidades
    // =====================================================================
    private Bicicleta biciSeleccionadaEnOrdenes() {
        Cliente cliente = taller.getCliente(cmbClienteOrden.getSelectedIndex());
        if (cliente == null) {
            return null;
        }
        return cliente.getBicicleta(cmbBiciOrden.getSelectedIndex());
    }

    private boolean existeBicicleta(String codigo) {
        for (int i = 0; i < taller.getContadorClientes(); i++) {
            if (taller.getCliente(i).buscarBicicleta(codigo) != null) {
                return true;
            }
        }
        return false;
    }

    private String nombreTipo(OrdenReparacion orden) {
        if (orden instanceof OrdenUrgente) {
            return "Urgente (+" + ((OrdenUrgente) orden).getRecargo() + "%)";
        }
        if (orden instanceof OrdenMantenimiento) {
            return "Mantenimiento (-" + ((OrdenMantenimiento) orden).getDescuento() + "%)";
        }
        return "Normal";
    }

    private String dinero(double valor) {
        return "$" + formatoDinero.format(valor);
    }

    private void aviso(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "RodaFix", JOptionPane.WARNING_MESSAGE);
        estado(mensaje);
    }

    private void estado(String mensaje) {
        lblEstado.setText(" " + mensaje);
    }

    // =====================================================================
    //  Punto de entrada de la interfaz grafica
    // =====================================================================
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                // si falla se usa el aspecto por defecto de Java
            }
            new VentanaPrincipal().setVisible(true);
        });
    }
}
