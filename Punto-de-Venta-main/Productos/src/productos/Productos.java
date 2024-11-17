/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package productos;
import com.mysql.cj.jdbc.Blob;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
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
import java.util.Arrays;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JTable;

//Erik Inicio 1--------------------------------------------------------------------------------------
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
        String query = "SELECT Descripcion, precioFinal FROM productos WHERE CodigoBarras = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, codigo);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            String descripcion = resultSet.getString("Descripcion");
            double precio = resultSet.getDouble("precioFinal");
            String fechaHora = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

            addProductoToTable(jTable1, fechaHora, descripcion, codigo, precio);
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

private static void addProductoToTable(JTable jTable1, String fechaHora, String descripcion, String codigo, double precio) {
    DefaultTableModel tableModel = (DefaultTableModel) jTable1.getModel();
    tableModel.addRow(new Object[]{fechaHora, descripcion, codigo, precio, 1});
}


private static void actualizarTotalVendido(JLabel Dinerovendido, double precio) {
    double totalVendido = Double.parseDouble(Dinerovendido.getText());
    totalVendido += precio;
    Dinerovendido.setText(String.valueOf(totalVendido));
}


public static void actualizarDineroCaja(JLabel Dinerocaja) {
    double dineroEnCaja = 2000.00; // Establecer el valor de $2000
    Dinerocaja.setText(String.valueOf(dineroEnCaja));
}


    
    //-----------------------------------------------------Erik Fin1

   
  

/*
    
    
    */
        private String dburl = "jdbc:mysql://localhost:3306/puntoventa";
    private String dbusername = "root";
    private String dbpassword = "Roderik2442$";
    java.sql.Connection connection = null;
    PreparedStatement stmt = null;
    
    
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
                stmt.setString(2, contrasena);
            } else {
                query = "SELECT * FROM empleados WHERE nombre_usuario = ? AND contrasena = ? AND rol = ?";
                stmt = connection.prepareStatement(query);
                stmt.setString(1, usuario);
                stmt.setString(2, contrasena);
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
    
    
    public boolean insertarEmpleado(String nombre, String direccion, String telefono, String correoElectronico, String rfc, String curp, String salario, String nombreUsuario, String contrasena, String turno, String rol) { 
        try { 
            connection = DriverManager.getConnection(dburl, dbusername, dbpassword);
        if (datoExiste("curp", curp) || datoExiste("nombre_usuario", nombreUsuario)){
            return false;
        }
        
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
        return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    
    public boolean actualizarEmpleado(String empleadoId, String nombre, String direccion, String telefono, String correoElectronico, String rfc, String curp, String salario, String nombreUsuario, String contrasena, String turno, String rol) {
        try {
            connection = DriverManager.getConnection(dburl, dbusername, dbpassword);
            
            if (datoExisteActualizacion("curp", curp, empleadoId) || datoExisteActualizacion("nombre_usuario", nombreUsuario, empleadoId)) {
                return false;
            }
            
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
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
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
    
    
    private boolean datoExiste(String columna, String valor) throws Exception {
        String query = "SELECT COUNT(*) FROM empleados WHERE " + columna + " = ?";
        stmt = connection.prepareStatement(query);
        stmt.setString(1, valor);
        java.sql.ResultSet rs = stmt.executeQuery();
        rs.next();
        boolean existe = rs.getInt(1) > 0;
        rs.close();
        stmt.close();
        return existe;
    }
    
    
    private boolean datoExisteActualizacion(String columna, String valor, String empleadoId) throws Exception {
        String query = "SELECT COUNT(*) FROM empleados WHERE " + columna + " = ? AND empleado_id != ?";
        stmt = connection.prepareStatement(query);
        stmt.setString(1, valor);
        stmt.setString(2, empleadoId);
        java.sql.ResultSet rs = stmt.executeQuery();
        rs.next();
        boolean existe = rs.getInt(1) > 0;
        rs.close();
        stmt.close();
        return existe;
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
    
   

private static final String url = "jdbc:mysql://localhost:3306/puntoventa";
private static final String username = "root";
private static final String password = "Roderik2442$";

public static int obtenerIdEmpleado(String nombre_usuario) {
    int empleado_id = -1;

    try (Connection connection = DriverManager.getConnection(url, username, password)) {
        String query = "SELECT empleado_id FROM empleados WHERE nombre_usuario = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, nombre_usuario);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            empleado_id = rs.getInt("empleado_id");
        } else {
            System.out.println("No se encontró ningún empleado con el nombre de usuario: " + nombre_usuario);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Error al obtener el ID del empleado para el nombre de usuario: " + nombre_usuario);
    }
    return empleado_id;
}


    //-------------------------------------------------------------------------Erik----Inicio2
public static int obtenerIDProducto(String CodigoBarras) {
    int idProducto = -1;
    try (Connection connection = DriverManager.getConnection(burl, busername, bpassword)) {
        String query = "SELECT ID FROM productos WHERE CodigoBarras = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, CodigoBarras);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            idProducto = rs.getInt("ID");
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

        // Verificar si el empleado existe
        String verificarEmpleadoQuery = "SELECT COUNT(*) FROM empleados WHERE empleado_id = ?";
        PreparedStatement verificarEmpleadoStmt = connection.prepareStatement(verificarEmpleadoQuery);
        verificarEmpleadoStmt.setInt(1, empleado_id);
        ResultSet rsEmpleado = verificarEmpleadoStmt.executeQuery();
        if (rsEmpleado.next() && rsEmpleado.getInt(1) == 0) {
            throw new SQLException("Empleado no encontrado.");
        }

        // Insertar una venta
        String ventaQuery = "INSERT INTO Ventas (FechaVenta, empleado_id, ClienteID, Total, DineroEnCaja, DineroVendido) VALUES (NOW(), ?, ?, ?, ?, ?)";
        PreparedStatement ventaStmt = connection.prepareStatement(ventaQuery, PreparedStatement.RETURN_GENERATED_KEYS);
        ventaStmt.setInt(1, empleado_id);
        if (ClienteID == null) {
            ventaStmt.setNull(2, java.sql.Types.INTEGER);
        } else {
            ventaStmt.setInt(2, ClienteID);
        }
        ventaStmt.setDouble(3, totalVendido);
        ventaStmt.setDouble(4, dineroEnCaja);
        ventaStmt.setDouble(5, totalVendido);
        ventaStmt.executeUpdate();

        // Obtener el ID de la venta recién insertada
        ResultSet generatedKeys = ventaStmt.getGeneratedKeys();
        int ventaID = 0;
        if (generatedKeys.next()) {
            ventaID = generatedKeys.getInt(1);
        }

        // Insertar detalles de venta
        String detalleQuery = "INSERT INTO DetalleVentas (VentaID, IDproducto, Cantidad, PrecioUnitario) VALUES (?, ?, ?, ?)";
        PreparedStatement detalleStmt = connection.prepareStatement(detalleQuery);

        for (int i = 0; i < jTable1.getRowCount(); i++) {
            String CodigoBarras = jTable1.getValueAt(i, 2).toString();
            int ID = obtenerIDProducto(CodigoBarras); // Obtener IDproducto desde codigo
            if (ID == -1) {
                JOptionPane.showMessageDialog(frame, "Error: Producto no encontrado en la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
                continue; // Saltar esta fila si el ID de producto no es válido
            }
            int cantidad = Integer.parseInt(jTable1.getValueAt(i, 4).toString());
            double precioUnitario = Double.parseDouble(jTable1.getValueAt(i, 3).toString());

            detalleStmt.setInt(1, ventaID);
            detalleStmt.setInt(2, ID);
            detalleStmt.setInt(3, cantidad);
            detalleStmt.setDouble(4, precioUnitario);
            detalleStmt.addBatch();
        }
        detalleStmt.executeBatch();

        connection.commit(); // Confirmar transacción

        // Generar el ticket
        Ticket.writeTicket(frame.getUsuario(), jTable1, totalVendido);

        // Mostrar mensaje de confirmación
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
                JOptionPane.showMessageDialog(frame, "Transacción revertida debido a un error.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    } finally {
        try {
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
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
        String query = "SELECT p.Descripcion, d.PrecioUnitario, d.Cantidad " +
                       "FROM DetalleVentas d " +
                       "JOIN productos p ON d.IDproducto = p.ID " +
                       "WHERE d.VentaID = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, ventaID);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            String producto = rs.getString("Descripcion");
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
//--------------------------------------------------------------------------------------------fin ventas Erik
//-----------------------------------Clientes Erik_------------------

    private static final String aburl = "jdbc:mysql://localhost:3306/puntoventa";
    private static final String absername = "root";
    private static final String abassword = "Roderik2442$";

    // Generar un código de barras de 13 caracteres
    public static String generarCodigoDeBarras() {
        Random rand = new Random();
        StringBuilder codigo = new StringBuilder();

        for (int i = 0; i < 13; i++) {
            int num = rand.nextInt(10); // Generar un dígito aleatorio entre 0 y 9
            codigo.append(num);
        }

        return codigo.toString();
    }

    // Validar los datos del cliente
    public static boolean validarCliente(String nombre, String direccion, String pais, String curp, String codigoPostal, String telefono, String codigoBarras) {
        if (!nombre.matches("[a-zA-Z\\s]+")) {
            JOptionPane.showMessageDialog(null, "El nombre solo debe contener letras y espacios.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!pais.matches("[a-zA-Z\\s]+")) {
            JOptionPane.showMessageDialog(null, "El país solo debe contener letras y espacios.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!curp.matches("[A-Z0-9]{18}") || verificarCurpExistente(curp)) {
            JOptionPane.showMessageDialog(null, "CURP inválida o ya registrada.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!codigoPostal.matches("\\d{5,10}")) {
            JOptionPane.showMessageDialog(null, "El código postal debe tener entre 5 y 10 dígitos.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!telefono.matches("\\d{10,15}")) {
            JOptionPane.showMessageDialog(null, "El teléfono debe tener entre 10 y 15 dígitos.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (verificarCodigoBarrasExistente(codigoBarras)) {
            JOptionPane.showMessageDialog(null, "Código de barras ya registrado.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    // Insertar un cliente en la base de datos
    public static void insertarCliente(String nombre, String direccion, String pais, String curp, String codigoPostal, String telefono, String codigoBarras) {
        try (Connection connection = DriverManager.getConnection(aburl, absername, abassword)) {
            String query = "INSERT INTO Clientes (NombreCliente, Direccion, Pais, CURP, CodigoPostal, Telefono, CodigoBarrasCliente) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, nombre);
            stmt.setString(2, direccion);
            stmt.setString(3, pais);
            stmt.setString(4, curp);
            stmt.setString(5, codigoPostal);
            stmt.setString(6, telefono);
            stmt.setString(7, codigoBarras);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al insertar cliente en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Verificar si la CURP ya existe en la base de datos
    public static boolean verificarCurpExistente(String curp) {
        try (Connection connection = DriverManager.getConnection(aburl, absername, abassword)) {
            String query = "SELECT 1 FROM Clientes WHERE CURP = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, curp);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Verificar si el código de barras ya existe en la base de datos
    public static boolean verificarCodigoBarrasExistente(String codigoBarras) {
        try (Connection connection = DriverManager.getConnection(aburl, absername, abassword)) {
            String query = "SELECT 1 FROM Clientes WHERE CodigoBarrasCliente = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, codigoBarras);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Actualizar la tabla de clientes en la interfaz
    public static void actualizarTablaClientes(javax.swing.JTable jTable2) {
        try (Connection connection = DriverManager.getConnection(aburl, absername, abassword)) {
            String query = "SELECT CodigoBarrasCliente, NombreCliente, Direccion, Telefono FROM Clientes";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) jTable2.getModel();
            model.setRowCount(0); // Limpiar tabla

            while (rs.next()) {
                String codigoBarras = rs.getString("CodigoBarrasCliente");
                String nombre = rs.getString("NombreCliente");
                String direccion = rs.getString("Direccion");
                String telefono = rs.getString("Telefono");
                model.addRow(new Object[]{codigoBarras, nombre, direccion, telefono});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void eliminarCliente(String codigoBarras) {
        try (Connection connection = DriverManager.getConnection(aburl, absername, abassword)) {
            String query = "DELETE FROM Clientes WHERE CodigoBarrasCliente = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, codigoBarras);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al eliminar cliente.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

 

    // Método para verificar si la CURP ya existe en la base de datos, excluyendo el cliente actual
    public static boolean verificarCurpExistente(String curp, String codigoBarrasActual) {
        try (Connection connection = DriverManager.getConnection(aburl, absername, abassword)) {
            String query = "SELECT 1 FROM Clientes WHERE CURP = ? AND CodigoBarrasCliente <> ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, curp);
            stmt.setString(2, codigoBarrasActual);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    // Método para buscar clientes por nombre
    public static void buscarClientesPorNombre(String nombre, javax.swing.JTable jTable2) {
        try (Connection connection = DriverManager.getConnection(aburl, absername, abassword)) {
            String query = "SELECT CodigoBarrasCliente, NombreCliente, Direccion, Telefono FROM Clientes WHERE NombreCliente LIKE ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, "%" + nombre + "%"); // Usar LIKE para buscar por nombre parcial
            ResultSet rs = stmt.executeQuery();

            javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) jTable2.getModel();
            model.setRowCount(0); // Limpiar tabla

            while (rs.next()) {
                String codigoBarras = rs.getString("CodigoBarrasCliente");
                String nombreCliente = rs.getString("NombreCliente");
                String direccion = rs.getString("Direccion");
                String telefono = rs.getString("Telefono");
                model.addRow(new Object[]{codigoBarras, nombreCliente, direccion, telefono});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

public static void editarCliente(String codigoBarras, String nombre, String direccion, String pais, String curp, String codigoPostal, String telefono) {
    try (Connection connection = DriverManager.getConnection(aburl, absername, abassword)) {
        String query = "UPDATE Clientes SET NombreCliente = ?, Direccion = ?, Pais = ?, CURP = ?, CodigoPostal = ?, Telefono = ? WHERE CodigoBarrasCliente = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, nombre);
        stmt.setString(2, direccion);
        stmt.setString(3, pais);
        stmt.setString(4, curp);
        stmt.setString(5, codigoPostal);
        stmt.setString(6, telefono);
        stmt.setString(7, codigoBarras);
        stmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error al editar cliente en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

    public static boolean datosClienteHanCambiado(String codigoBarras, String nombre, String direccion, String pais, String curp, String codigoPostal, String telefono) {
        try (Connection connection = DriverManager.getConnection(aburl, absername, abassword)) {
            String query = "SELECT * FROM Clientes WHERE CodigoBarrasCliente = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, codigoBarras);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String nombreActual = rs.getString("NombreCliente");
                String direccionActual = rs.getString("Direccion");
                String paisActual = rs.getString("Pais");
                String curpActual = rs.getString("CURP");
                String codigoPostalActual = rs.getString("CodigoPostal");
                String telefonoActual = rs.getString("Telefono");

                return !nombre.equals(nombreActual) || !direccion.equals(direccionActual) || !pais.equals(paisActual) || !curp.equals(curpActual) || !codigoPostal.equals(codigoPostalActual) || !telefono.equals(telefonoActual);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


// Mofle---------------------------------------------------------------------------------------
    
    
    public void agregarProducto(String nombre, Date fechaCaducidad, int cantidad, String codigoBarras, int categoryID, double precio, String marca, String proveedor, String descripcion, int stockMinimo) {
    String sql = "INSERT INTO Inventario (NombreProducto, FechaCaducidad, Cantidad, CodigoBarras, CategoryID, Precio, Marca, Proveedor, Descripcion, StockMinimo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    try (
        Connection connection = DriverManager.getConnection(burl, busername, bpassword);
        PreparedStatement stmt = connection.prepareStatement(sql)
    ) {
        stmt.setString(1, nombre);
        stmt.setDate(2, new java.sql.Date(fechaCaducidad.getTime()));
        stmt.setInt(3, cantidad);
        stmt.setString(4, codigoBarras);
        stmt.setInt(5, categoryID);
        stmt.setDouble(6, precio);
        stmt.setString(7, marca);
        stmt.setString(8, proveedor);
        stmt.setString(9, descripcion);
        stmt.setInt(10, stockMinimo);
        stmt.executeUpdate();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    

// Modelo de datos de inventario
    public class Inventario {
        private int idProducto;
        private String nombreProducto;
        private String fechaCaducidad;
        private int cantidad;
        private String codigoBarras;
        private int categoryID;
        private double precio;
        private String marca;
        private String proveedor;
        private String descripcion;
        private int stockMinimo;

        
       public int getIdProducto() { return idProducto; }
        public void setIdProducto(int idProducto) { this.idProducto = idProducto; }
        public String getNombreProducto() { return nombreProducto; }
        public void setNombreProducto(String nombreProducto) { this.nombreProducto = nombreProducto; }
        public String getFechaCaducidad() { return fechaCaducidad; }
        public void setFechaCaducidad(String fechaCaducidad) { this.fechaCaducidad = fechaCaducidad; }
        public int getCantidad() { return cantidad; }
        public void setCantidad(int cantidad) { this.cantidad = cantidad; }
        public String getCodigoBarras() { return codigoBarras; }
        public void setCodigoBarras(String codigoBarras) { this.codigoBarras = codigoBarras; }
        public int getCategoryID() { return categoryID; }
        public void setCategoryID(int categoryID) { this.categoryID = categoryID; }
        public double getPrecio() { return precio; }
        public void setPrecio(double precio) { this.precio = precio; }
        public String getMarca() { return marca; }
        public void setMarca(String marca) { this.marca = marca; }
        public String getProveedor() { return proveedor; }
        public void setProveedor(String proveedor) { this.proveedor = proveedor; }
        public String getDescripcion() { return descripcion; }
        public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
        public int getStockMinimo() { return stockMinimo; }
        public void setStockMinimo(int stockMinimo) { this.stockMinimo = stockMinimo; }
}
      // Método para obtener datos del inventario
    public List<Inventario> obtenerInventario() {
    List<Inventario> inventarioList = new ArrayList<>();
    String sql = "SELECT * FROM Inventario";

    try (Connection connection = DriverManager.getConnection(burl, busername, bpassword);
        PreparedStatement stmt = connection.prepareStatement(sql)
    ){
        java.sql.ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            Inventario item = new Inventario();
            item.setIdProducto(rs.getInt("IDproducto"));
            item.setNombreProducto(rs.getString("NombreProducto"));

            Date fechaCaducidad = rs.getDate("FechaCaducidad");
            if (fechaCaducidad != null) {
                item.setFechaCaducidad(fechaCaducidad.toString());
            } else {
                item.setFechaCaducidad(null);
            }

            item.setCantidad(rs.getInt("Cantidad"));
            item.setCodigoBarras(rs.getString("CodigoBarras"));
            item.setCategoryID(rs.getInt("CategoryID"));
            item.setPrecio(rs.getDouble("Precio"));
            item.setMarca(rs.getString("Marca"));
            item.setProveedor(rs.getString("Proveedor"));
            item.setDescripcion(rs.getString("Descripcion"));
            item.setStockMinimo(rs.getInt("StockMinimo"));
            inventarioList.add(item);
        }

    } catch (Exception e) {
        System.err.println("Error al obtener el inventario: " + e.getMessage());
        e.printStackTrace();
    }

    return inventarioList;
}


    // Método para mostrar el inventario en la consola (pruebas)
    private void mostrarInventario() {
        List<Inventario> inventarioList = obtenerInventario();
        for (Inventario item : inventarioList) {
            System.out.println("Producto: " + item.getNombreProducto() + ", Precio: " + item.getPrecio());
        }
    }
    
    public int obtenerCategoryID(String nombreCategoria) {
    int categoryID = -1;
    String sql = "SELECT categoryID FROM Categoria WHERE nombreCategoria = ?";
    
    try (
         Connection connection = DriverManager.getConnection(burl, busername, bpassword);
         PreparedStatement stmt = connection.prepareStatement(sql)) {
        
        stmt.setString(1, nombreCategoria);
        java.sql.ResultSet rs = stmt.executeQuery();
        
        if (rs.next()) {
            categoryID = rs.getInt("categoryID");
        }
        
    } catch (Exception ex) {
        ex.printStackTrace();
    }
    
    return categoryID;
}
    
    public boolean insertarProducto(String codigoProducto, String codigoBarras, String descripcion, String marca, String rubro, String proveedor, int stockMinimo, int existencia, double iva, File imagenProducto, double precioFinal) {
    String sql = "INSERT INTO Productos (CodigoProducto, CodigoBarras, Descripcion, Marca, Rubro, Proveedor, StockMinimo, Existencia, IVA, ImagenProducto, precioFinal) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    try (Connection connection = DriverManager.getConnection(burl, busername, bpassword);
         PreparedStatement pstmt = connection.prepareStatement(sql)) {
        pstmt.setString(1, codigoProducto);
        pstmt.setString(2, codigoBarras);
        pstmt.setString(3, descripcion);
        pstmt.setString(4, marca);
        pstmt.setString(5, rubro);
        pstmt.setString(6, proveedor);
        pstmt.setInt(7, stockMinimo);
        pstmt.setInt(8, existencia);
        pstmt.setDouble(9, iva);
        if (imagenProducto != null) {
            InputStream is = new FileInputStream(imagenProducto);
            pstmt.setBlob(10, is);
        } else {
            pstmt.setNull(10, java.sql.Types.BLOB);
        }
        pstmt.setDouble(11, precioFinal);

        pstmt.executeUpdate();
        return true;
    } catch (Exception ex) {
        ex.printStackTrace();
        return false;
    }
}


    public boolean actualizarProducto(String productoId, String codigoProducto, String codigoBarras, String descripcion, String marca, String rubro, String proveedor, int stockMinimo, int existencia, double iva, File imagenProducto, double precioFinal) {
    String sql = "UPDATE Productos SET CodigoProducto = ?, CodigoBarras = ?, Descripcion = ?, Marca = ?, Rubro = ?, Proveedor = ?, StockMinimo = ?, Existencia = ?, IVA = ?, ImagenProducto = ?, precioFinal = ? WHERE ID = ?";
    try (Connection connection = DriverManager.getConnection(burl, busername, bpassword);
         PreparedStatement pstmt = connection.prepareStatement(sql)) {
        pstmt.setString(1, codigoProducto);
        pstmt.setString(2, codigoBarras);
        pstmt.setString(3, descripcion);
        pstmt.setString(4, marca);
        pstmt.setString(5, rubro);
        pstmt.setString(6, proveedor);
        pstmt.setInt(7, stockMinimo);
        pstmt.setInt(8, existencia);
        pstmt.setDouble(9, iva);
        if (imagenProducto != null) {
            InputStream is = new FileInputStream(imagenProducto);
            pstmt.setBlob(10, is);
        } else {
            pstmt.setNull(10, java.sql.Types.BLOB);
        }
        pstmt.setDouble(11, precioFinal);
        pstmt.setString(12, productoId);

        pstmt.executeUpdate();
        return true;
    } catch (Exception ex) {
        ex.printStackTrace();
        return false;
    }
}

    
    public List<String[]> cargarProductos() {
    List<String[]> productos = new ArrayList<>();
    String sql = "SELECT ID, CodigoProducto, Descripcion, CodigoBarras, Marca, Rubro, Proveedor, precioFinal FROM Productos";
    try (Connection connection = DriverManager.getConnection(burl, busername, bpassword);
         PreparedStatement pstmt = connection.prepareStatement(sql);
         java.sql.ResultSet rs = pstmt.executeQuery()) {
        while (rs.next()) {
            productos.add(new String[] {
                rs.getString("ID"),
                rs.getString("CodigoProducto"),
                rs.getString("Descripcion"),
                rs.getString("CodigoBarras"),
                rs.getString("Marca"),
                rs.getString("Rubro"),
                rs.getString("Proveedor"),
                String.valueOf(rs.getDouble("precioFinal"))
            });
        }
    } catch (Exception ex) {
        ex.printStackTrace();
    }
    return productos;
}


public String[] obtenerProductoPorId(String productoId) {
    String[] producto = null;
    java.sql.ResultSet rs = null;
    try (Connection connection = DriverManager.getConnection(burl, busername, bpassword);
         PreparedStatement stmt = connection.prepareStatement("SELECT ID, CodigoProducto, CodigoBarras, Descripcion, Marca, Rubro, Proveedor, StockMinimo, Existencia, IVA, precioFinal, ImagenProducto FROM Productos WHERE ID = ?")) {
        stmt.setString(1, productoId);
        rs = stmt.executeQuery();
        
        if (rs.next()) {
            producto = new String[]{
                rs.getString("ID"),
                rs.getString("CodigoProducto"),
                rs.getString("CodigoBarras"),
                rs.getString("Descripcion"),
                rs.getString("Marca"),
                rs.getString("Rubro"),
                rs.getString("Proveedor"),
                rs.getString("StockMinimo"),
                rs.getString("Existencia"),
                rs.getString("IVA"),
                rs.getString("precioFinal")
            };

            // Convertir el BLOB a un archivo temporal
            Blob blob = (Blob) rs.getBlob("ImagenProducto");
            if (blob != null) {
                producto = Arrays.copyOf(producto, producto.length + 1);
                producto[producto.length - 1] = saveBlobToFile(blob);
            } else {
                producto = Arrays.copyOf(producto, producto.length + 1);
                producto[producto.length - 1] = null;
            }
        }
    } catch (Exception ex) {
        ex.printStackTrace();
    } finally {
        try {
            if (rs != null) rs.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    return producto;
}

private String saveBlobToFile(Blob blob) {
    try {
        InputStream is = blob.getBinaryStream();
        File tempFile = File.createTempFile("product_image", ".png");
        tempFile.deleteOnExit();
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
        }
        return tempFile.getAbsolutePath();
    } catch (Exception ex) {
        ex.printStackTrace();
        return null;
    }
}

public boolean eliminarProducto(String productoId) {
    String sql = "DELETE FROM Productos WHERE ID = ?";
    try (Connection connection = DriverManager.getConnection(burl, busername, bpassword);
         PreparedStatement pstmt = connection.prepareStatement(sql)) {
        pstmt.setString(1, productoId);
        int rowsAffected = pstmt.executeUpdate();
        return rowsAffected > 0;
    } catch (Exception ex) {
        ex.printStackTrace();
        return false;
    }
}

    
    public List<String> obtenerMarcas() {
        List<String> marcas = new ArrayList<>();
        String sql = "SELECT DISTINCT Marca FROM Productos";
        try (Connection connection = DriverManager.getConnection(dburl, dbusername, dbpassword);
             PreparedStatement pstmt = connection.prepareStatement(sql);
             java.sql.ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                marcas.add(rs.getString("Marca"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return marcas;
    }

    public List<String> obtenerRubros() {
        List<String> rubros = new ArrayList<>();
        String sql = "SELECT DISTINCT Rubro FROM Productos";
        try (Connection connection = DriverManager.getConnection(dburl, dbusername, dbpassword);
             PreparedStatement pstmt = connection.prepareStatement(sql);
             java.sql.ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                rubros.add(rs.getString("Rubro"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return rubros;
    }

    public List<String> obtenerProveedores() {
        List<String> proveedores = new ArrayList<>();
        String sql = "SELECT DISTINCT Proveedor FROM Productos";
        try (Connection connection = DriverManager.getConnection(dburl, dbusername, dbpassword);
             PreparedStatement pstmt = connection.prepareStatement(sql);
             java.sql.ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                proveedores.add(rs.getString("Proveedor"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return proveedores;
    }


//agustin
    
 
   public static List<String[]> obtenerReporteVentas() {
    List<String[]> reporteVentas = new ArrayList<>();
    String sql = "SELECT " +
                 "e.nombre_usuario AS Empleado, " +
                 "v.FechaVenta AS Fecha, " +
                 "p.CodigoProducto AS Producto, " +
                 "dv.Cantidad AS Cantidad, " +
                 "dv.PrecioUnitario AS PrecioUnitario, " +
                 "v.Total AS PrecioFinal " +
                 "FROM ventas v " +
                 "JOIN empleados e ON v.empleado_id = e.empleado_id " +
                 "JOIN detalleventas dv ON v.VentaID = dv.VentaID " +
                 "JOIN productos p ON dv.IDproducto = p.ID " +
                 "ORDER BY e.nombre_usuario, v.FechaVenta";
    try (Connection connection = DriverManager.getConnection(burl, busername, bpassword);
         PreparedStatement pstmt = connection.prepareStatement(sql);
         ResultSet rs = pstmt.executeQuery()) {
        while (rs.next()) {
            String[] venta = {
                rs.getString("Empleado"),
                rs.getString("Fecha"),
                rs.getString("Producto"),
                rs.getString("Cantidad"),
                rs.getString("PrecioUnitario"),
                rs.getString("PrecioFinal")
            };
            reporteVentas.add(venta);
        }
    } catch (Exception ex) {
        ex.printStackTrace();
    }
    return reporteVentas;
}


    //fin mofle----------------------------------------------------------------------------------------
    

}





