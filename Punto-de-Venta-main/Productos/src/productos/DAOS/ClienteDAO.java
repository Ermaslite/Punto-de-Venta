package DAOS;

import Modelos.ModeloCliente;
import Utileria.newpackage.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    // Método para insertar un cliente
    public static void insertarCliente(ModeloCliente cliente) {
        String sql = "INSERT INTO Clientes (NombreCliente, Direccion, CURP, CodigoPostal, Pais, Telefono, CodigoBarrasCliente) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexion.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNombreCliente());
            stmt.setString(2, cliente.getDireccion());
            stmt.setString(3, cliente.getCurp());
            stmt.setString(4, cliente.getCodigoPostal());
            stmt.setString(5, cliente.getPais());
            stmt.setString(6, cliente.getTelefono());
            stmt.setString(7, cliente.getCodigoBarrasCliente());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al insertar cliente: " + e.getMessage());
        }
    }

    // Método para actualizar un cliente
    public static void actualizarCliente(int clienteID, ModeloCliente cliente) {
        String sql = "UPDATE Clientes SET NombreCliente=?, Direccion=?, CURP=?, CodigoPostal=?, Pais=?, Telefono=?, CodigoBarrasCliente=? "
                + "WHERE ClienteID=?";
        try (Connection conn = Conexion.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNombreCliente());
            stmt.setString(2, cliente.getDireccion());
            stmt.setString(3, cliente.getCurp());
            stmt.setString(4, cliente.getCodigoPostal());
            stmt.setString(5, cliente.getPais());
            stmt.setString(6, cliente.getTelefono());
            stmt.setString(7, cliente.getCodigoBarrasCliente());
            stmt.setInt(8, clienteID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar cliente: " + e.getMessage());
        }
    }

    // Método para eliminar un cliente
    public static void eliminarCliente(int clienteID) {
        String sql = "DELETE FROM Clientes WHERE ClienteID=?";
        try (Connection conn = Conexion.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, clienteID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar cliente: " + e.getMessage());
        }
    }

    // Método para listar todos los clientes
    public static List<ModeloCliente> listarClientes() {
        List<ModeloCliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM Clientes";

        try (Connection conn = Conexion.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                ModeloCliente cliente = new ModeloCliente(
                        rs.getInt("ClienteID"), // ID del cliente
                        rs.getString("NombreCliente"),
                        rs.getString("Direccion"),
                        rs.getString("CURP"),
                        rs.getString("CodigoPostal"),
                        rs.getString("Pais"),
                        rs.getString("Telefono"),
                        rs.getString("CodigoBarrasCliente")
                );
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar clientes: " + e.getMessage());
        }
        return clientes;
    }

    public static boolean existeClienteConCurpOCodigoBarras(String curp, String codigoBarras) {
        String sql = "SELECT COUNT(*) FROM clientes WHERE curp = ? OR codigo_barras = ?";
        try (Connection conn = Conexion.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, curp);
            stmt.setString(2, codigoBarras);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;  // Devuelve true si hay coincidencias
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
