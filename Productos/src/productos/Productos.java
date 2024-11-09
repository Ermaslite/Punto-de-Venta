/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package productos;
import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.table.DefaultTableModel;
public class Productos {

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void buscarProducto(String codigo, procesoVenta frame, JTable jTable1, JLabel Dinerovendido) {
        String url = "jdbc:mysql://localhost:3306/puntoventa";
        String user = "root";
        String password = "Roderik2442$";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT NombreProducto, Precio FROM Inventario WHERE CodigoBarras = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, codigo);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String nombreProducto = resultSet.getString("NombreProducto");
                double precio = resultSet.getDouble("Precio");
                String fechaHora = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

                addProductoToTable(jTable1, fechaHora, nombreProducto, codigo, precio);
                actualizarTotalVendido(Dinerovendido, precio);
            } else {
                JOptionPane.showMessageDialog(frame, "Producto no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error al conectar con la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    public static void eliminarProducto(procesoVenta frame, JTable jTable1, JLabel Dinerovendido, int selectedRow) {
        DefaultTableModel tableModel = (DefaultTableModel) jTable1.getModel();
        double precio = (double) tableModel.getValueAt(selectedRow, 3); 

        double totalVendido = Double.parseDouble(Dinerovendido.getText());
        totalVendido -= precio;
        Dinerovendido.setText(String.valueOf(totalVendido));

        tableModel.removeRow(selectedRow);
    }

    private static void addProductoToTable(JTable jTable1, String fechaHora, String nombreProducto, String codigo, double precio) {
        DefaultTableModel tableModel = (DefaultTableModel) jTable1.getModel();
        tableModel.addRow(new Object[]{fechaHora, nombreProducto, codigo, precio, 1});
    }

    private static void actualizarTotalVendido(JLabel Dinerovendido, double precio) {
        double totalVendido = Double.parseDouble(Dinerovendido.getText());
        totalVendido += precio;
        Dinerovendido.setText(String.valueOf(totalVendido));
    }

    public static void guardarVenta(procesoVenta frame, int idEmpleado, int ClienteID, JTable jTable1, JLabel Dinerovendido, JLabel Dinerocaja) {
        String url = "jdbc:mysql://localhost:3306/puntoventa";
        String user = "root";
        String password = "Roderik2442$";
        double totalVendido = Double.parseDouble(Dinerovendido.getText());
        double dineroEnCaja = 2000;
        double total = totalVendido - dineroEnCaja;
        Dinerocaja.setText(String.valueOf(dineroEnCaja));

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "INSERT INTO Ventas (FechaVenta, idEmpleado, ClienteID, Total, DineroEnCaja, DineroVendido) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setDate(1, new java.sql.Date(System.currentTimeMillis()));
            statement.setInt(2, idEmpleado);
            statement.setInt(3, ClienteID);
            statement.setDouble(4, total);
            statement.setDouble(5, dineroEnCaja);
            statement.setDouble(6, totalVendido);

            statement.executeUpdate();
            JOptionPane.showMessageDialog(frame, "Venta guardada con éxito");

            // Reiniciar el JTable y el total vendido
            DefaultTableModel tableModel = (DefaultTableModel) jTable1.getModel();
            tableModel.setRowCount(0);
            Dinerovendido.setText("0.0");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error al guardar la venta", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}






