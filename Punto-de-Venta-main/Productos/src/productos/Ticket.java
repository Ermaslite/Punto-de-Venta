/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package productos;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import javax.swing.JTable;
import java.util.Date;


public class Ticket {
  
    public static void writeTicket(String nombreEmpleado, int ventaID) {
        List<String> detallesVenta = Productos.obtenerDetallesVenta(ventaID);
        double total = Productos.obtenerTotalVenta(ventaID);

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

            for (String detalle : detallesVenta) {
                writer.write(detalle);
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
