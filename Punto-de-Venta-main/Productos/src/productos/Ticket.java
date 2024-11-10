/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package productos;



import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Ticket {
  
    public static void writeTicket(String nombreEmpleado, JTable jTable1, double total) {
        DefaultTableModel tableModel = (DefaultTableModel) jTable1.getModel();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("ticket.txt", true))) {
            writer.write("********** TICKET DE COMPRA **********");
            writer.newLine();
            writer.write("Fecha y Hora: " + new Date());
            writer.newLine();
            writer.write("Atendido por: " + nombreEmpleado);
            writer.newLine();
            writer.write("--------------------------------------");
            writer.newLine();
            writer.write("Productos:");
            writer.newLine();

            for (int i = 0; i < tableModel.getRowCount(); i++) {
                String fechaHora = tableModel.getValueAt(i, 0).toString();
                String nombreProducto = tableModel.getValueAt(i, 1).toString();
                String codigo = tableModel.getValueAt(i, 2).toString();
                double precio = Double.parseDouble(tableModel.getValueAt(i, 3).toString());
                int cantidad = Integer.parseInt(tableModel.getValueAt(i, 4).toString());

                writer.write(fechaHora + " " + nombreProducto + " - $" + precio + " x " + cantidad);
                writer.newLine();
            }

            writer.write("--------------------------------------");
            writer.newLine();
            writer.write("Total a pagar: $" + total);
            writer.newLine();
            writer.write("--------------------------------------");
            writer.newLine();
            writer.write("Gracias por su compra");
            writer.newLine();
            writer.write("**************************************");
            writer.newLine();
            writer.newLine(); // Espacio entre tickets
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
