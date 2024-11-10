package applogin;

import com.sun.jdi.connect.spi.Connection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class Productos {
    private String dburl = "jdbc:mysql://localhost:3306/puntoventav1";
    private String dbusername = "root";
    private String dbpassword = "Saqueme100profe";
    
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


    
}
