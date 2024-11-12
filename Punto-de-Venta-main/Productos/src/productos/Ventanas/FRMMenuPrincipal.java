package Ventanas;

import javax.swing.*;
import java.awt.*;

public class FRMMenuPrincipal {

    public FRMMenuPrincipal() {
        // Crear el frame principal
        JFrame frame = new JFrame("Menú Principal - Punto de Venta");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300); // Ancho y alto del frame
        frame.setLayout(new BorderLayout());

        // Crear el título
        JLabel titulo = new JLabel("Menú Principal", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setOpaque(true);
        titulo.setBackground(new Color(100, 150, 200));
        titulo.setForeground(Color.WHITE);
        frame.add(titulo, BorderLayout.NORTH);

        // Crear el panel para los botones
        JPanel panelBotones = new JPanel(new GridLayout(2, 2, 10, 10));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Colores y diseño de los botones
        Color botonColor = new Color(150, 200, 250);
        Font botonFuente = new Font("Arial", Font.BOLD, 16);

        // Crear los botones
        JButton btnProductos = new JButton("Productos");
        JButton btnClientes = new JButton("Clientes");
        JButton btnEmpleados = new JButton("Empleados");
        JButton btnReporteVentas = new JButton("Reporte de Ventas");

        // Configurar diseño de los botones
        JButton[] botones = {btnProductos, btnClientes, btnEmpleados, btnReporteVentas};
        for (JButton boton : botones) {
            boton.setFont(botonFuente);
            boton.setBackground(botonColor);
            boton.setFocusPainted(false);
        }

        // Añadir botones al panel
        panelBotones.add(btnProductos);
        panelBotones.add(btnClientes);
        panelBotones.add(btnEmpleados);
        panelBotones.add(btnReporteVentas);

        // Añadir el panel al frame
        frame.add(panelBotones, BorderLayout.CENTER);

        // Añadir funcionalidad a los botones
        btnProductos.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Módulo Productos no implementado aún.");
            frame.dispose();
        });

        btnClientes.addActionListener(e -> {
            new FRMClienteCRUD().setVisible(true); // Abre el CRUD de Clientes
            frame.dispose(); // Cierra el menú principal
        });

        btnEmpleados.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Módulo Empleados no implementado aún.");
            frame.dispose();
        });

        btnReporteVentas.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Módulo Reporte de Ventas no implementado aún.");
            frame.dispose();
        });

        // Hacer visible el frame
        frame.setLocationRelativeTo(null); // Centrar en pantalla
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FRMMenuPrincipal());
    }
}
