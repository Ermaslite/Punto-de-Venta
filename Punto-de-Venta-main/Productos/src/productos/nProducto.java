package productos;

import java.awt.Color;
import java.awt.Image;
import java.awt.Insets;
import java.io.File;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class nProducto extends javax.swing.JFrame {
    
    private ListaProducto listaProductoWindow;
    private Productos backend;
    private String productoId;
    private String ultimaMarcaSeleccionada;
    private String ultimoRubroSeleccionado;
    private String ultimoProveedorSeleccionado;

    
    public nProducto(Productos backend, ListaProducto listaProductoWindow) {
        this(backend, null, listaProductoWindow);
    }
    
    public nProducto(Productos backend, String productoId, ListaProducto listaProductoWindow) {
        this.listaProductoWindow = listaProductoWindow;
        this.backend = backend;
        this.productoId = productoId;
        initComponents();
        setTitle("Punto de Venta - Agregar Producto");
        jTextField1.setBorder(new CompoundBorder(jTextField1.getBorder(),new EmptyBorder(new Insets(5, 10, 5, 10))));
        jTextField2.setBorder(new CompoundBorder(jTextField2.getBorder(),new EmptyBorder(new Insets(5, 10, 5, 10))));
        jTextField3.setBorder(new CompoundBorder(jTextField3.getBorder(),new EmptyBorder(new Insets(5, 10, 5, 10))));
        jTextField4.setBorder(new CompoundBorder(jTextField4.getBorder(),new EmptyBorder(new Insets(5, 10, 5, 10))));
        jTextField5.setBorder(new CompoundBorder(jTextField5.getBorder(),new EmptyBorder(new Insets(5, 10, 5, 10))));
        jTextField6.setBorder(new CompoundBorder(jTextField6.getBorder(),new EmptyBorder(new Insets(5, 10, 5, 10))));
        jTextField7.setBorder(new CompoundBorder(jTextField7.getBorder(),new EmptyBorder(new Insets(5, 10, 5, 10))));
        if (productoId != null) {
            setTitle("Punto de Venta - Editar Producto");
            cargarOpciones();
            cargarDatosProducto();
        }
    }
    
    private void cargarDatosProducto() {
    String[] datos = backend.obtenerProductoPorId(productoId);
    if (datos != null) {
        jLabel1.setText("Editar Producto");
        jTextField1.setText(datos[1]);
        jTextField5.setText(datos[2]);
        jTextField2.setText(datos[3]);
        jComboBox1.setSelectedItem(datos[4]);
        jComboBox2.setSelectedItem(datos[5]);
        jComboBox3.setSelectedItem(datos[6]);
        jTextField3.setText(datos[7]);
        jTextField4.setText(datos[8]);
        jTextField6.setText(datos[9]);
        jTextField7.setText(datos[10]);
        String imagenProducto = datos[11];

        if (imagenProducto != null && !imagenProducto.isEmpty()) {
            escalarImagen(new File(imagenProducto));
        } else {
            jLabel10.setIcon(null); // Establece un icono por defecto o vacío si no hay imagen
        }
    } else {
        JOptionPane.showMessageDialog(this, "Error al cargar el producto.");
    }
}


    
    private void escalarImagen(File file) {
        try { 
            ImageIcon icon = new ImageIcon(file.getAbsolutePath());
            Image img = icon.getImage();
            Image imgEscalada = img.getScaledInstance(jLabel10.getWidth(), jLabel10.getHeight(), Image.SCALE_SMOOTH);
            jLabel10.setIcon(new ImageIcon(imgEscalada));
        } catch (Exception e) { 
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar la imagen: " + e.getMessage());
        }
    }
    
    private void agregarProducto() {
    String codigoProducto = jTextField1.getText();
    String codigoBarras = jTextField5.getText();
    String descripcion = jTextField2.getText();
    String marca = (String) jComboBox1.getSelectedItem();
    String rubro = (String) jComboBox2.getSelectedItem();
    String proveedor = (String) jComboBox3.getSelectedItem();

    int stockMinimo;
    try {
        stockMinimo = Integer.parseInt(jTextField3.getText());
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Stock Mínimo debe ser un número entero.");
        return;
    }

    int existencia;
    try {
        existencia = Integer.parseInt(jTextField4.getText());
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Existencia debe ser un número entero.");
        return;
    }

    double iva;
    try {
        iva = Double.parseDouble(jTextField6.getText());
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "IVA debe ser un número decimal.");
        return;
    }

    double precioFinal;
    try {
        precioFinal = Double.parseDouble(jTextField7.getText());
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Precio Final debe ser un número decimal.");
        return;
    }

    String imagenProductoPath = jLabel10.getText();
    File imagenProductoFile = imagenProductoPath != null && !imagenProductoPath.isEmpty() ? new File(imagenProductoPath) : null;

    boolean resultado;
    if (productoId == null) {
        resultado = backend.insertarProducto(codigoProducto, codigoBarras, descripcion, marca, rubro, proveedor, stockMinimo, existencia, iva, imagenProductoFile, precioFinal);
    } else {
        resultado = backend.actualizarProducto(productoId, codigoProducto, codigoBarras, descripcion, marca, rubro, proveedor, stockMinimo, existencia, iva, imagenProductoFile, precioFinal);
    }

    if (resultado) {
        JOptionPane.showMessageDialog(this, "Producto guardado con éxito!");
        
    } else {
        JOptionPane.showMessageDialog(this, "El Código de Producto ya existe. Por favor, usa uno diferente.");
    }
}

    
    private void generarCodigoBarras() {
    // Generar un código de barras simple (por ejemplo, un número aleatorio)
    long codigoBarras = (long) (Math.random() * 100000000000L);

    // Mostrar el código de barras en el JTextField
    jTextField5.setText(String.valueOf(codigoBarras));
}

    private void agregarOpcionTemp(String tipo, String opcion) {
    switch (tipo) {
        case "Marca":
            jComboBox1.addItem(opcion);
            jComboBox1.setSelectedItem(opcion); // Selecciona automáticamente la nueva opción
            break;
        case "Rubro":
            jComboBox2.addItem(opcion);
            jComboBox2.setSelectedItem(opcion); // Selecciona automáticamente la nueva opción
            break;
        case "Proveedor":
            jComboBox3.addItem(opcion);
            jComboBox3.setSelectedItem(opcion); // Selecciona automáticamente la nueva opción
            break;
    }
}
    
    private void cargarOpciones() {
    // Cargar opciones de marca
    List<String> marcas = backend.obtenerMarcas();
    jComboBox1.removeAllItems();
    for (String marca : marcas) {
        jComboBox1.addItem(marca);
    }
    if (ultimaMarcaSeleccionada != null) {
        jComboBox1.setSelectedItem(ultimaMarcaSeleccionada);
    }

    // Cargar opciones de rubro
    List<String> rubros = backend.obtenerRubros();
    jComboBox2.removeAllItems();
    for (String rubro : rubros) {
        jComboBox2.addItem(rubro);
    }
    if (ultimoRubroSeleccionado != null) {
        jComboBox2.setSelectedItem(ultimoRubroSeleccionado);
    }

    // Cargar opciones de proveedor
    List<String> proveedores = backend.obtenerProveedores();
    jComboBox3.removeAllItems();
    for (String proveedor : proveedores) {
        jComboBox3.addItem(proveedor);
    }
    if (ultimoProveedorSeleccionado != null) {
        jComboBox3.setSelectedItem(ultimoProveedorSeleccionado);
    }
}

    
    
private boolean editarOpcion(String tipo, String opcion) {
    String nuevaOpcion = JOptionPane.showInputDialog(this, "Ingresa el nuevo valor para " + tipo + ":");
    if (nuevaOpcion != null && !nuevaOpcion.trim().isEmpty()) {
        switch (tipo) {
            case "Marca":
                jComboBox1.removeItem(opcion);
                jComboBox1.addItem(nuevaOpcion);
                jComboBox1.setSelectedItem(nuevaOpcion);
                break;
            case "Rubro":
                jComboBox2.removeItem(opcion);
                jComboBox2.addItem(nuevaOpcion);
                jComboBox2.setSelectedItem(nuevaOpcion);
                break;
            case "Proveedor":
                jComboBox3.removeItem(opcion);
                jComboBox3.addItem(nuevaOpcion);
                jComboBox3.setSelectedItem(nuevaOpcion);
                break;
        }
        return true;
    }
    return false;
}
    
    private void eliminarOpcionTemp(String tipo, String opcion) {
    switch (tipo) {
        case "Marca":
            jComboBox1.removeItem(opcion);
            break;
        case "Rubro":
            jComboBox2.removeItem(opcion);
            break;
        case "Proveedor":
            jComboBox3.removeItem(opcion);
            break;
    }
}


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jTextField6 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 16)); // NOI18N
        jLabel1.setText("Agregar Producto");

        jLabel2.setText("Cod. Producto");

        jTextField1.setBackground(new java.awt.Color(250, 241, 233));
        jTextField1.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jTextField1.setBorder(null);

        jTextField2.setBackground(new java.awt.Color(250, 241, 233));
        jTextField2.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jTextField2.setBorder(null);

        jLabel3.setText("Descripcion");

        jLabel4.setText("Marca");

        jLabel5.setText("Rubro");

        jLabel6.setText("Proveedor");

        jTextField3.setBackground(new java.awt.Color(250, 241, 233));
        jTextField3.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jTextField3.setBorder(null);

        jLabel7.setText("Stock Minimo");

        jTextField4.setBackground(new java.awt.Color(250, 241, 233));
        jTextField4.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jTextField4.setBorder(null);

        jLabel8.setText("Existencia");

        jLabel9.setText("Cod. Barras");

        jTextField5.setBackground(new java.awt.Color(250, 241, 233));
        jTextField5.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jTextField5.setBorder(null);

        jButton1.setBackground(new java.awt.Color(69, 81, 114));
        jButton1.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Generar");
        jButton1.setBorder(null);
        jButton1.setBorderPainted(false);
        jButton1.setContentAreaFilled(false);
        jButton1.setOpaque(true);
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton1MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButton1MouseReleased(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel10.setBackground(new java.awt.Color(0, 51, 51));
        jLabel10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jLabel11.setText("Seleccionar imagen");

        jButton2.setBackground(new java.awt.Color(69, 81, 114));
        jButton2.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Seleccionar");
        jButton2.setBorder(null);
        jButton2.setBorderPainted(false);
        jButton2.setContentAreaFilled(false);
        jButton2.setOpaque(true);
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton2MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButton2MouseReleased(evt);
            }
        });
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jTextField6.setBackground(new java.awt.Color(250, 241, 233));
        jTextField6.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jTextField6.setBorder(null);

        jLabel12.setText("IVA");

        jButton3.setBackground(new java.awt.Color(250, 140, 1));
        jButton3.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jButton3.setText("Cancelar");
        jButton3.setBorder(null);
        jButton3.setBorderPainted(false);
        jButton3.setContentAreaFilled(false);
        jButton3.setOpaque(true);
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton3MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButton3MouseReleased(evt);
            }
        });
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(69, 81, 114));
        jButton4.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Guardar");
        jButton4.setBorder(null);
        jButton4.setBorderPainted(false);
        jButton4.setContentAreaFilled(false);
        jButton4.setOpaque(true);
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton4MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButton4MouseReleased(evt);
            }
        });
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton5.setText("+");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton6.setText("+");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton7.setText("+");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jLabel13.setText("Precio Final");

        jTextField7.setBackground(new java.awt.Color(250, 241, 233));
        jTextField7.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jTextField7.setBorder(null);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel12))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel13)
                                        .addGap(27, 27, 27)
                                        .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel9))
                                    .addComponent(jComboBox1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jTextField2)
                                        .addGap(29, 29, 29)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE))
                                        .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(16, 16, 16)
                                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel11)
                                        .addGap(63, 63, 63))))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jTextField4, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jTextField3, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jComboBox3, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jComboBox2, javax.swing.GroupLayout.Alignment.LEADING, 0, 205, Short.MAX_VALUE))
                                    .addGap(6, 6, 6)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jButton7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1)
                .addGap(40, 40, 40)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel11))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(jButton6))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(jButton7))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12)
                            .addComponent(jLabel13)
                            .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Imágenes", "jpg", "png", "gif", "bmp"));
        
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            escalarImagen(selectedFile);
        } 
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MousePressed
        jButton2.setBackground(jButton2.getBackground().darker());
    }//GEN-LAST:event_jButton2MousePressed

    private void jButton2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseReleased
        jButton2.setBackground(new Color(69,81,114));
    }//GEN-LAST:event_jButton2MouseReleased

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        agregarProducto();
        ListaProducto ListpWindow = new ListaProducto();
        ListpWindow.setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton4MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MousePressed
        jButton4.setBackground(jButton4.getBackground().darker());
    }//GEN-LAST:event_jButton4MousePressed

    private void jButton4MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseReleased
       jButton4.setBackground(new Color(69,81,114));
    }//GEN-LAST:event_jButton4MouseReleased

    private void jButton3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MousePressed
        jButton3.setBackground(jButton3.getBackground().darker());
    }//GEN-LAST:event_jButton3MousePressed

    private void jButton3MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseReleased
        jButton3.setBackground(new Color(250,140,1));
    }//GEN-LAST:event_jButton3MouseReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        generarCodigoBarras();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MousePressed
        jButton1.setBackground(jButton1.getBackground().darker());
    }//GEN-LAST:event_jButton1MousePressed

    private void jButton1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseReleased
        jButton1.setBackground(new Color(69,81,114));
    }//GEN-LAST:event_jButton1MouseReleased

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        ListaProducto ListEmpleadosWindow = new ListaProducto();
        ListEmpleadosWindow.setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        configurarOpcion("Marca");
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        configurarOpcion("Rubro");
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        configurarOpcion("Proveedor");
    }//GEN-LAST:event_jButton7ActionPerformed
    
    private void configurarOpcion(String tipo) {
    String[] acciones = {"Agregar", "Editar", "Eliminar"};
    String accion = (String) JOptionPane.showInputDialog(this, "Selecciona una acción para " + tipo + ":", "Configurar " + tipo, JOptionPane.PLAIN_MESSAGE, null, acciones, acciones[0]);

    if (accion != null) {
        String opcion = JOptionPane.showInputDialog(this, "Ingresa el valor de " + tipo + " que deseas " + accion.toLowerCase() + ":");

        if (opcion != null && !opcion.trim().isEmpty()) {
            boolean resultado = false;
            switch (accion) {
                case "Agregar":
                    agregarOpcionTemp(tipo, opcion);
                    JOptionPane.showMessageDialog(this, tipo + " agregada temporalmente. Se guardará al guardar el producto.");
                    break;
                case "Editar":
                    resultado = editarOpcion(tipo, opcion);
                    if (resultado) {
                        JOptionPane.showMessageDialog(this, tipo + " editada temporalmente. Se guardará al guardar el producto.");
                    } else {
                        JOptionPane.showMessageDialog(this, "Error al editar " + tipo + ". Inténtalo de nuevo.");
                    }
                    break;
                case "Eliminar":
                    int confirm = JOptionPane.showConfirmDialog(this, "¿Estás seguro de que deseas eliminar " + tipo + " " + opcion + "? Esta acción no se puede deshacer y afectará todos los productos.", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        eliminarOpcionTemp(tipo, opcion);
                        JOptionPane.showMessageDialog(this, tipo + " eliminada temporalmente. Se guardará al guardar el producto.");
                    }
                    break;
            }
        }
    }
}

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(nProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(nProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(nProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(nProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Productos backend = new Productos();
                ListaProducto listaProductoWindow = new ListaProducto();
                new nProducto(backend, listaProductoWindow).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    // End of variables declaration//GEN-END:variables
}
