package Ventanas;

import DAOS.ClienteDAO;
import Modelos.ModeloCliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.regex.Pattern;

public class FRMClienteCRUD extends JFrame {

    private JTextField txtNombre, txtDireccion, txtCurp, txtCodigoPostal, txtPais, txtTelefono, txtCodigoBarras;
    private JButton btnInsertar, btnActualizar, btnEliminar, btnListar, btnVolver;
    private JTable tablaClientes;
    private JScrollPane scrollPane;

    public FRMClienteCRUD() {
        setTitle("Gestión de Clientes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Establecer la ventana en pantalla completa
        setExtendedState(JFrame.MAXIMIZED_BOTH);  // Maximizar la ventana
        setLayout(new BorderLayout());

        // Estilo general
        Font fuenteGeneral = new Font("Arial", Font.PLAIN, 16);
        Font fuenteTitulo = new Font("Arial", Font.BOLD, 20);
        Color colorPrincipal = new Color(34, 45, 50);  // Gris oscuro
        Color colorBoton = new Color(0, 102, 204);     // Azul para botones
        Color colorTextoBoton = Color.WHITE;
        Color colorFondoPanel = new Color(240, 240, 240); // Fondo claro para paneles

        // Panel superior: Formulario
        JPanel panelSuperior = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        panelSuperior.setBackground(colorFondoPanel);
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Ajuste de las etiquetas y campos de texto
        agregarCampoConIcono(panelSuperior, gbc, "Nombre:", "name_icon.png", 0, 0);
        txtNombre = new JTextField(20);
        agregarCampo(panelSuperior, gbc, txtNombre, 1, 0);

        agregarCampoConIcono(panelSuperior, gbc, "Dirección:", "address_location_map_navigation_icon.png", 0, 1);
        txtDireccion = new JTextField(20);
        agregarCampo(panelSuperior, gbc, txtDireccion, 1, 1);

        agregarCampoConIcono(panelSuperior, gbc, "CURP:", "curp_icon.png", 0, 2);
        txtCurp = new JTextField(20);
        agregarCampo(panelSuperior, gbc, txtCurp, 1, 2);

        agregarCampoConIcono(panelSuperior, gbc, "Código Postal:", "postalcode_icon.png", 0, 3);
        txtCodigoPostal = new JTextField(20);
        agregarCampo(panelSuperior, gbc, txtCodigoPostal, 1, 3);

        agregarCampoConIcono(panelSuperior, gbc, "País:", "country_icon.png", 0, 4);
        txtPais = new JTextField(20);
        agregarCampo(panelSuperior, gbc, txtPais, 1, 4);

        agregarCampoConIcono(panelSuperior, gbc, "Teléfono:", "smartphone_icon.png", 0, 5);
        txtTelefono = new JTextField(20);
        agregarCampo(panelSuperior, gbc, txtTelefono, 1, 5);

        agregarCampoConIcono(panelSuperior, gbc, "Código de Barras:", "barcode_icon.png", 0, 6);
        txtCodigoBarras = new JTextField(20);
        agregarCampo(panelSuperior, gbc, txtCodigoBarras, 1, 6);

        add(panelSuperior, BorderLayout.NORTH);

        // Panel central: Botones con estilo
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotones.setBackground(colorFondoPanel);

        btnInsertar = crearBoton("Insertar", colorBoton, colorTextoBoton);
        btnActualizar = crearBoton("Actualizar", colorBoton, colorTextoBoton);
        btnEliminar = crearBoton("Eliminar", colorBoton, colorTextoBoton);
        btnListar = crearBoton("Listar", colorBoton, colorTextoBoton);
        btnVolver = crearBoton("Volver", colorBoton, colorTextoBoton);

        panelBotones.add(btnInsertar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnListar);
        panelBotones.add(btnVolver);  // Agregar el botón Volver

        add(panelBotones, BorderLayout.CENTER);

        // Panel inferior: Tabla
        tablaClientes = new JTable();
        scrollPane = new JScrollPane(tablaClientes);
        add(scrollPane, BorderLayout.SOUTH);

        // Eventos de los botones
        btnInsertar.addActionListener(e -> insertarCliente());
        btnActualizar.addActionListener(e -> actualizarCliente());
        btnEliminar.addActionListener(e -> eliminarCliente());
        btnListar.addActionListener(e -> listarClientes());
        btnVolver.addActionListener(e -> volverAtras());

        // Configurar ventana
        setLocationRelativeTo(null);  // Centrar la ventana
        setVisible(true);
    }

    private void agregarCampoConIcono(JPanel panel, GridBagConstraints gbc, String etiqueta, String iconName, int x, int y) {
        gbc.gridx = x;
        gbc.gridy = y;

        // Cargar el icono
        ImageIcon icon = new ImageIcon(getClass().getResource("/productos/Utileria/" + iconName)); // Ruta de icono en carpeta /icons
        Image img = icon.getImage();  // Obtener la imagen de la imagen icono
        Image scaledImg = img.getScaledInstance(24, 24, Image.SCALE_SMOOTH);  // Redimensionar la imagen a 24x24 píxeles
        icon = new ImageIcon(scaledImg); // Crear un nuevo ImageIcon con la imagen redimensionada

        JLabel label = new JLabel(icon);
        label.setText(etiqueta);
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(label, gbc);
    }

    private void agregarCampo(JPanel panel, GridBagConstraints gbc, JTextField campo, int x, int y) {
        gbc.gridx = x;
        gbc.gridy = y;
        panel.add(campo, gbc);
    }

    private JButton crearBoton(String texto, Color colorFondo, Color colorTexto) {
        JButton boton = new JButton(texto);
        boton.setBackground(colorFondo);
        boton.setForeground(colorTexto);
        boton.setFont(new Font("Arial", Font.BOLD, 16));
        boton.setFocusPainted(false);
        boton.setPreferredSize(new Dimension(120, 40));
        boton.setBorder(BorderFactory.createLineBorder(colorFondo.darker(), 2));
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(colorFondo.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(colorFondo);
            }
        });
        return boton;
    }

    private void insertarCliente() {
        // Validaciones de campos vacíos y formatos
        if (txtNombre.getText().trim().isEmpty()) {
            mostrarMensaje("Por favor, ingresa el nombre.");
            return;
        }
        if (!Pattern.matches("[A-Za-z ]+", txtNombre.getText().trim())) {
            mostrarMensaje("El nombre solo puede contener letras y espacios.");
            return;
        }

        if (txtDireccion.getText().trim().isEmpty()) {
            mostrarMensaje("Por favor, ingresa la dirección.");
            return;
        }

        String curp = txtCurp.getText().trim();
        if (curp.isEmpty()) {
            mostrarMensaje("Por favor, ingresa el CURP.");
            return;
        }
        if (!Pattern.matches("[A-Z0-9]{18}", curp)) {
            mostrarMensaje("El CURP debe ser de 18 caracteres y solo puede contener mayúsculas y números.");
            return;
        }

        String codigoBarras = txtCodigoBarras.getText().trim();
        if (codigoBarras.isEmpty()) {
            mostrarMensaje("Por favor, ingresa el código de barras.");
            return;
        }

        // Validación de CURP y código de barras únicos
        if (ClienteDAO.existeClienteConCurpOCodigoBarras(curp, codigoBarras)) {
            mostrarMensaje("Ya existe un cliente con el mismo CURP o código de barras.");
            return;
        }

        // Crear el objeto Cliente
        ModeloCliente cliente = obtenerClienteDeFormulario();
        if (cliente != null) {
            ClienteDAO.insertarCliente(cliente);
            mostrarMensaje("Cliente insertado correctamente.");
            listarClientes();  // Refrescar la lista después de insertar
            limpiarCampos(); //Limpiar los campos de texto al añadir
        }
    }

    private void actualizarCliente() {
        try {
            int clienteID = Integer.parseInt(txtCodigoPostal.getText().trim());  // Verificar si el ID existe
            ModeloCliente cliente = obtenerClienteDeFormulario();
            if (cliente != null) {
                ClienteDAO.actualizarCliente(clienteID, cliente);
                mostrarMensaje("Cliente actualizado correctamente.");
                listarClientes();  // Refrescar la lista después de actualizar
                limpiarCampos(); 
            }
        } catch (NumberFormatException e) {
            mostrarMensaje("Por favor, ingresa un ID válido.");
        }
    }

    private void eliminarCliente() {
        try {
            int clienteID = Integer.parseInt(txtCodigoPostal.getText().trim());  // Verificar si el ID es válido
            ClienteDAO.eliminarCliente(clienteID);
            mostrarMensaje("Cliente eliminado correctamente.");
            listarClientes();  // Refrescar la lista después de eliminar
            listarClientes();  // Refrescar la lista después de actualizar
            limpiarCampos();   // Limpiar los campos
        } catch (NumberFormatException e) {
            mostrarMensaje("Por favor, ingresa un ID válido.");
        }
    }

    private void listarClientes() {
        List<ModeloCliente> clientes = ClienteDAO.listarClientes();

        String[] columnNames = {"ID", "Nombre", "Dirección", "CURP", "Código Postal", "País", "Teléfono", "Código de Barras"};
        Object[][] data = new Object[clientes.size()][8];

        for (int i = 0; i < clientes.size(); i++) {
            ModeloCliente cliente = clientes.get(i);
            data[i][0] = cliente.getId();
            data[i][1] = cliente.getNombreCliente();
            data[i][2] = cliente.getDireccion();
            data[i][3] = cliente.getCurp();
            data[i][4] = cliente.getCodigoPostal();
            data[i][5] = cliente.getPais();
            data[i][6] = cliente.getTelefono();
            data[i][7] = cliente.getCodigoBarrasCliente();
        }

        // Crear un DefaultTableModel para la tabla
        tablaClientes.setModel(new DefaultTableModel(data, columnNames));
    }

    private ModeloCliente obtenerClienteDeFormulario() {
        return new ModeloCliente(
                0,
                txtNombre.getText().trim(),
                txtDireccion.getText().trim(),
                txtCurp.getText().trim(),
                txtCodigoPostal.getText().trim(),
                txtPais.getText().trim(),
                txtTelefono.getText().trim(),
                txtCodigoBarras.getText().trim()
        );
    }

    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    private void limpiarCampos() {
    txtNombre.setText("");
    txtDireccion.setText("");
    txtCurp.setText("");
    txtCodigoPostal.setText("");
    txtPais.setText("");
    txtTelefono.setText("");
    txtCodigoBarras.setText("");
}

    
    private void volverAtras() {
        new FRMMenuPrincipal();
        this.dispose();
    }
}
