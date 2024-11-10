/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package productos;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JTable;

//Erik Inicio 1
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

    public static void actualizarDineroCaja(JLabel Dinerocaja) { double dineroEnCaja = 2000.00; // Establecer el valor de $2000 
    Dinerocaja.setText(String.valueOf(dineroEnCaja)); }
    
    //-----------------------------------------------------Erik Fin1

   
  

/*
    
    
    */
    private String dburl = "jdbc:mysql://localhost:3306/puntoventa";
    private String dbusername = "root";
    private String dbpassword = "Roderik2442$";
    
    java.sql.Connection connection = null;
    PreparedStatement stmt = null;
    
    public void insertarEmpleado(String nombre, String direccion, String telefono, String correoElectronico, String rfc, String curp, String salario, String nombreUsuario, String contrasena, String turno, String rol) { 
        try { 
            connection = DriverManager.getConnection(dburl, dbusername, dbpassword);
            String query = "INSERT INTO empleados (nombre, direccion, telefono, correo_electronico, rfc, curp, turno, salario, nombre_usuario, contrasena, rol) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            stmt = connection.prepareStatement(query);
            
            stmt.setString(1, nombre);
            stmt.setString(2, direccion);
            stmt.setString(3, telefono);
            stmt.setString(4, correoElectronico);
            stmt.setString(5, rfc);
            stmt.setString(6, curp);
            stmt.setString(7, turno);
            stmt.setString(8, salario);
            stmt.setString(9, nombreUsuario);
            stmt.setString(10, contrasena);
            stmt.setString(11, rol);
            
            stmt.executeUpdate();
            stmt.close();
            connection.close();
        } catch (Exception ex) { 
            ex.printStackTrace(); 
        } 
    }
    
    public List<String[]> cargarEmpleados() {
        List<String[]> empleados = new ArrayList<>();
        
        java.sql.ResultSet rs = null;
        try {
            connection = DriverManager.getConnection(dburl, dbusername, dbpassword);
            String query = "SELECT empleado_id, nombre_usuario, telefono, correo_electronico, direccion, turno, rol, nombre, salario FROM empleados";
            stmt = connection.prepareStatement(query);
            rs = stmt.executeQuery(query);
            
            while (rs.next()) {
                String[] empleado = new String[]{
                    rs.getString("empleado_id"),
                    rs.getString("nombre_usuario"),
                    rs.getString("nombre"),
                    rs.getString("telefono"),
                    rs.getString("correo_electronico"),
                    rs.getString("direccion"),
                    rs.getString("turno"),
                    rs.getString("rol"),
                    rs.getString("Salario"),
                    
                };
                empleados.add(empleado);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (connection != null) connection.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return empleados;
    }

    public String[] obtenerEmpleadoPorId(String empleadoId) {
        String[] empleado = null;
        java.sql.ResultSet rs = null;
        try {
            connection = DriverManager.getConnection(dburl, dbusername, dbpassword);
            String query = "SELECT empleado_id, nombre, direccion, telefono, correo_electronico, rfc, curp, turno, salario, nombre_usuario, contrasena, rol FROM empleados WHERE empleado_id = ?";
            stmt = connection.prepareStatement(query);
            stmt.setString(1, empleadoId);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                empleado = new String[]{
                    rs.getString("empleado_id"),
                    rs.getString("nombre"),
                    rs.getString("direccion"),
                    rs.getString("telefono"),
                    rs.getString("correo_electronico"),
                    rs.getString("rfc"),
                    rs.getString("curp"),
                    rs.getString("turno"),
                    rs.getString("salario"),
                    rs.getString("nombre_usuario"),
                    rs.getString("contrasena"),
                    rs.getString("rol")
                };
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (connection != null) connection.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return empleado;
    }

    public void actualizarEmpleado(String empleadoId, String nombre, String direccion, String telefono, String correoElectronico, String rfc, String curp, String salario, String nombreUsuario, String contrasena, String turno, String rol) {
        try {
            connection = DriverManager.getConnection(dburl, dbusername, dbpassword);
            String query = "UPDATE empleados SET nombre = ?, direccion = ?, telefono = ?, correo_electronico = ?, rfc = ?, curp = ?, turno = ?, salario = ?, nombre_usuario = ?, contrasena = ?, rol = ? WHERE empleado_id = ?";
            stmt = connection.prepareStatement(query);

            stmt.setString(1, nombre);
            stmt.setString(2, direccion);
            stmt.setString(3, telefono);
            stmt.setString(4, correoElectronico);
            stmt.setString(5, rfc);
            stmt.setString(6, curp);
            stmt.setString(7, turno);
            stmt.setString(8, salario);
            stmt.setString(9, nombreUsuario);
            stmt.setString(10, contrasena);
            stmt.setString(11, rol);
            stmt.setString(12, empleadoId);

            stmt.executeUpdate();
            stmt.close();
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public List<String[]> buscarEmpleados(String criterio) {
        List<String[]> empleados = new ArrayList<>();
        java.sql.ResultSet rs = null;
        try {
            connection = DriverManager.getConnection(dburl, dbusername, dbpassword);
            String query = "SELECT empleado_id, nombre_usuario, nombre, telefono, correo_electronico, direccion, turno, rol, salario FROM empleados WHERE nombre LIKE ? OR turno LIKE ? OR nombre_usuario LIKE ? OR rol LIKE ?";
            stmt = connection.prepareStatement(query);
            String searchPattern = "%" + criterio + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);
            stmt.setString(3, searchPattern);
            stmt.setString(4, searchPattern);
            rs = stmt.executeQuery();
        
            while (rs.next()) {
                String[] empleado = new String[]{
                    rs.getString("empleado_id"),
                    rs.getString("nombre_usuario"),
                    rs.getString("nombre"),
                    rs.getString("telefono"),
                    rs.getString("correo_electronico"),
                    rs.getString("direccion"),
                    rs.getString("turno"),
                    rs.getString("rol"),
                    rs.getString("salario")
                };
                empleados.add(empleado);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (connection != null) connection.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return empleados;
    }
    
    public void eliminarEmpleado(String empleadoId) {
        try {
            connection = DriverManager.getConnection(dburl, dbusername, dbpassword);
            String query = "DELETE FROM empleados WHERE empleado_id = ?";
            stmt = connection.prepareStatement(query);
            stmt.setString(1, empleadoId);
            stmt.executeUpdate();
            stmt.close();
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(password.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public void agregarAdministrador(String nombreUsuario, String contrasena, String correo, String nombreCompleto, String telefono, String direccion) {
        try {
            connection = DriverManager.getConnection(dburl, dbusername, dbpassword);
            String query = "INSERT INTO administrador (nombre_usuario, contrasena, correo, nombre_completo, telefono, direccion) VALUES (?, ?, ?, ?, ?, ?)";
            stmt = connection.prepareStatement(query);
            stmt.setString(1, nombreUsuario);
            stmt.setString(2, hashPassword(contrasena));
            stmt.setString(3, correo);
            stmt.setString(4, nombreCompleto);
            stmt.setString(5, telefono);
            stmt.setString(6, direccion);
            stmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
                if (connection != null)
                    connection.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public boolean hayAdministradores() {
        boolean existenAdmins = false;
        java.sql.ResultSet rs = null;
    
        try {
            connection = DriverManager.getConnection(dburl, dbusername, dbpassword);
            String query = "SELECT COUNT(*) AS total FROM administrador";
            stmt = connection.prepareStatement(query);
            rs = stmt.executeQuery();
        
            if (rs.next()) {
                existenAdmins = rs.getInt("total") > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (connection != null) connection.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return existenAdmins;
    }

    public boolean verificarCredenciales(String usuario, String contrasena, String rol) {
    java.sql.ResultSet rs = null;
    boolean credencialesValidas = false;
    
    try {
        connection = DriverManager.getConnection(dburl, dbusername, dbpassword);
        String query;

        if ("Administrador".equals(rol)) {
            query = "SELECT * FROM administrador WHERE nombre_usuario = ? AND contrasena = ?";
            stmt = connection.prepareStatement(query);
            stmt.setString(1, usuario);
            stmt.setString(2, contrasena); // Usar la contraseña encriptada directamente
        } else {
            query = "SELECT * FROM empleados WHERE nombre_usuario = ? AND contrasena = ? AND rol = ?";
            stmt = connection.prepareStatement(query);
            stmt.setString(1, usuario);
            stmt.setString(2, contrasena); // Contraseña no encriptada
            stmt.setString(3, rol);
        }

        rs = stmt.executeQuery();

        if (rs.next()) {
            credencialesValidas = true;
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    return credencialesValidas;
}
    
   

    private static final String url = "jdbc:mysql://localhost:3306/puntoventa";
    private static final String username = "root";
    private static final String password = "Roderik2442$";

    public static int obtenerIdEmpleado(String nombreUsuario) {
        int empleado_id = -1;

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT empleado_id FROM empleados WHERE nombre_usuario = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, nombreUsuario);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                empleado_id = rs.getInt("empleado_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return empleado_id;
    }
    //-------------------------------------------------------------------------Erik----Inicio2
   public static int obtenerIDProducto(String codigoProducto) {
    int idProducto = -1;
    try (Connection connection = DriverManager.getConnection(burl, busername, bpassword)) {
        String query = "SELECT IDproducto FROM Inventario WHERE CodigoBarras = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, codigoProducto);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            idProducto = rs.getInt("IDproducto");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return idProducto;
}

    private static final String burl = "jdbc:mysql://localhost:3306/puntoventa";
    private static final String busername = "root";
    private static final String bpassword = "Roderik2442$";

public static void guardarVenta(procesoVenta frame, int empleado_id, Integer ClienteID, JTable jTable1, JLabel Dinerovendido, JLabel Dinerocaja) {
    double totalVendido = Double.parseDouble(Dinerovendido.getText());
    double dineroEnCaja = Double.parseDouble(Dinerocaja.getText());

    Connection connection = null;
    try {
        connection = DriverManager.getConnection(burl, busername, bpassword);
        connection.setAutoCommit(false); // Iniciar transacción

        // Guardar la venta
        String ventaQuery = "INSERT INTO Ventas (FechaVenta, empleado_id, ClienteID, Total, DineroEnCaja, DineroVendido) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement ventaStmt = connection.prepareStatement(ventaQuery, PreparedStatement.RETURN_GENERATED_KEYS);
        ventaStmt.setDate(1, new java.sql.Date(System.currentTimeMillis()));
        ventaStmt.setInt(2, empleado_id);
        if (ClienteID == null) {
            ventaStmt.setNull(3, java.sql.Types.INTEGER);
        } else {
            ventaStmt.setInt(3, ClienteID);
        }
        ventaStmt.setDouble(4, totalVendido); // Utilizamos sólo Dinerovendido para el total a pagar
        ventaStmt.setDouble(5, dineroEnCaja);
        ventaStmt.setDouble(6, totalVendido);
        ventaStmt.executeUpdate();

        ResultSet generatedKeys = ventaStmt.getGeneratedKeys();
        int ventaID = 0;
        if (generatedKeys.next()) {
            ventaID = generatedKeys.getInt(1);
        }

        // Guardar los detalles de la venta
        String detalleQuery = "INSERT INTO DetalleVentas (VentaID, IDproducto, Cantidad, PrecioUnitario) VALUES (?, ?, ?, ?)";
        PreparedStatement detalleStmt = connection.prepareStatement(detalleQuery);

        for (int i = 0; i < jTable1.getRowCount(); i++) {
            String codigoProducto = jTable1.getValueAt(i, 2).toString();
            int idProducto = obtenerIDProducto(codigoProducto); // Obtener IDproducto desde codigo
            if (idProducto == -1) {
                JOptionPane.showMessageDialog(frame, "Error: Producto no encontrado en la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
                continue; // Saltar esta fila si el ID de producto no es válido
            }
            int cantidad = Integer.parseInt(jTable1.getValueAt(i, 4).toString());
            double precioUnitario = Double.parseDouble(jTable1.getValueAt(i, 3).toString());

            detalleStmt.setInt(1, ventaID);
            detalleStmt.setInt(2, idProducto);
            detalleStmt.setInt(3, cantidad);
            detalleStmt.setDouble(4, precioUnitario);
            detalleStmt.addBatch();
        }
        detalleStmt.executeBatch();

        connection.commit(); // Confirmar transacción

        // Generar el ticket
        Ticket.writeTicket(frame.getUsuario(), jTable1, totalVendido);

        JOptionPane.showMessageDialog(frame, "Venta guardada con éxito");

        // Reiniciar JTable y total vendido
        DefaultTableModel tableModel = (DefaultTableModel) jTable1.getModel();
        tableModel.setRowCount(0);
        Dinerovendido.setText("0.00");
        Dinerocaja.setText("2000.00"); // Restablecer a valor inicial
    } catch (Exception e) {
        e.printStackTrace();
        try {
            if (connection != null) {
                connection.rollback(); // Revertir transacción en caso de error
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        JOptionPane.showMessageDialog(frame, "Error al guardar la venta", "Error", JOptionPane.ERROR_MESSAGE);
    } finally {
        try {
            if (connection != null) {
                connection.setAutoCommit(true);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}

    // Método para verificar si el cliente existe o no
    public static boolean verificarCliente(String codigoCliente) {
        boolean clienteExiste = false;

        try (Connection connection = DriverManager.getConnection(burl, busername, bpassword)) {
            String query = "SELECT * FROM Clientes WHERE CodigoBarrasCliente = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, codigoCliente);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                clienteExiste = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return clienteExiste;
    }

    // Método para obtener ClienteID desde el código del cliente
    public static int obtenerClienteID(String codigoCliente) {
        int clienteID = -1;

        try (Connection connection = DriverManager.getConnection(burl, busername, bpassword)) {
            String query = "SELECT ClienteID FROM Clientes WHERE CodigoBarrasCliente = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, codigoCliente);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                clienteID = rs.getInt("ClienteID");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clienteID;
    }

 public static List<String> obtenerDetallesVenta(int ventaID) {
    List<String> detallesVenta = new ArrayList<>();

    try (Connection connection = DriverManager.getConnection(burl, busername, bpassword)) {
        String query = "SELECT i.NombreProducto, d.PrecioUnitario, d.Cantidad " +
                       "FROM DetalleVentas d " +
                       "JOIN Inventario i ON d.IDproducto = i.IDproducto " +
                       "WHERE d.VentaID = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, ventaID);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            String producto = rs.getString("NombreProducto");
            double precio = rs.getDouble("PrecioUnitario");
            int cantidad = rs.getInt("Cantidad");
            detallesVenta.add(producto + " - $" + precio + " x " + cantidad);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return detallesVenta;
}







   public static double obtenerTotalVenta(int ventaID) {
    double total = 0.0;

    try (Connection connection = DriverManager.getConnection(burl, busername, bpassword)) {
        String query = "SELECT Total FROM Ventas WHERE VentaID = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, ventaID);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            total = rs.getDouble("Total");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return total;
}



 
}



