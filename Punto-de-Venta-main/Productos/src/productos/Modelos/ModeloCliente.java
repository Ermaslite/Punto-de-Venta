
package Modelos;
public class ModeloCliente {

    private int id;
    private String nombreCliente;        
    private String direccion;           
    private String curp;                
    private String codigoPostal;        
    private String pais;                 
    private String telefono;             
    private String codigoBarrasCliente;  

    public ModeloCliente(int aInt, String nombreCliente, String direccion, String curp, String codigoPostal, String pais, String telefono, String codigoBarrasCliente) {
        this.nombreCliente = nombreCliente;
        this.direccion = direccion;
        this.curp = curp;
        this.codigoPostal = codigoPostal;
        this.pais = pais;
        this.telefono = telefono;
        this.codigoBarrasCliente = codigoBarrasCliente;
    }

    
    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCodigoBarrasCliente() {
        return codigoBarrasCliente;
    }

    public void setCodigoBarrasCliente(String codigoBarrasCliente) {
        this.codigoBarrasCliente = codigoBarrasCliente;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}


