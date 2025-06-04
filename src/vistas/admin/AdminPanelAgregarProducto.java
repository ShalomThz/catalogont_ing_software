/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package vistas.admin;

import controladores.CategoriaController;
import controladores.RopaController;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import modelos.Categoria;
import modelos.Ropa;
import com.formdev.flatlaf.FlatDarculaLaf;
import controladores.CarritoController;
import controladores.HistorialController;
import java.math.BigDecimal;
import modelos.Usuario;
import utiles.SesionUtil;

/**
 *
 * @author josha
 */
public class AdminPanelAgregarProducto extends javax.swing.JDialog {

    /**
     * Creates new form AdminPanelProductos
     */
    public AdminPanelAgregarProducto(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        cargarCategoriasEnComboBox();
        this.getRootPane().setDefaultButton(agregarPrendaButton);
    }

    private void cargarCategoriasEnComboBox() {
        CategoriaController categoriaController = new CategoriaController();
        categoriasLista = categoriaController.obtenerCategorias();

        seleccionaCategoriaComboBox.removeAllItems();

        for (Categoria categoria : categoriasLista) {
            seleccionaCategoriaComboBox.addItem(categoria.getNombre());
        }
    }
    
    private void registrarAccion(Ropa ropa){
        HistorialController historial=new HistorialController();
        SesionUtil sesion=new SesionUtil();
        Usuario usuario=sesion.getUsuarioActual();
        historial.registrarAccion(usuario,"agregar","el usuario "+usuario.getNombre()+" agrego la prenda"+ropa.getModelo());
    }

   private void agregarPrenda() {
    try {
        String categoriaNombre = (String) seleccionaCategoriaComboBox.getSelectedItem();
        if (categoriaNombre == null || categoriaNombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor selecciona una categoría.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Categoria categoria = categoriasLista.stream()
                .filter(cat -> cat.getNombre().equals(categoriaNombre))
                .findFirst()
                .orElse(null);

        if (categoria == null) {
            JOptionPane.showMessageDialog(this, "Categoría no encontrada.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String modelo = jTextFieldModelo.getText().trim();
        String nombre = nombreTextField.getText().trim();
        String talla = tallaTextField.getText().trim();
        String color = jTextField3.getText().trim();
        String precioTexto = jTextField4.getText().trim();
        String descripcion = jTextAreaDescripcion.getText().trim();
        String marca = jTextFieldMarca.getText().trim(); // Nuevo campo requerido
        boolean disponible = checkbox1.getState();

        if (modelo.isEmpty() || nombre.isEmpty() || talla.isEmpty() || color.isEmpty() || 
            precioTexto.isEmpty() || descripcion.isEmpty() || imagenSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Por favor completa todos los campos y selecciona una imagen.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        BigDecimal precio = new BigDecimal(precioTexto);
        BigDecimal descuento = BigDecimal.ZERO; // Valor por defecto
        String imagen = imagenSeleccionada;

        Ropa nuevaRopa = new Ropa(
            modelo, 
            nombre, 
            marca, 
            color, 
            talla, 
            precio, 
            descuento, 
            disponible, 
            descripcion, 
            imagen, 
            categoria
        );

        RopaController controller = new RopaController();
        controller.agregarRopa(nuevaRopa);

        JOptionPane.showMessageDialog(this, "Prenda agregada con éxito.");
        registrarAccion(nuevaRopa);
        limpiarCampos();

    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Precio inválido.", "Error", JOptionPane.ERROR_MESSAGE);
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error al agregar prenda: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

private void limpiarCampos() {
    jTextFieldModelo.setText("");
    nombreTextField.setText("");
    tallaTextField.setText("");
    jTextField3.setText("");
    jTextField4.setText("");
    jTextAreaDescripcion.setText("");
    vistaPrevia.setIcon(null);
    imagenSeleccionada = null;
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
        tituloLabel = new javax.swing.JLabel();
        eliminacionProductoButton = new javax.swing.JButton();
        editarProductoButton = new javax.swing.JButton();
        seleccionaCategoriaComboBox = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        nombreTextField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        tallaTextField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        agregarPrendaButton = new javax.swing.JButton();
        jFileChooser1 = new javax.swing.JFileChooser();
        vistaPrevia = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTextFieldModelo = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaDescripcion = new javax.swing.JTextArea();
        regresarButton = new javax.swing.JButton();
        checkbox1 = new java.awt.Checkbox();
        jLabel11 = new javax.swing.JLabel();
        jTextFieldMarca = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(51, 0, 102));

        tituloLabel.setFont(new java.awt.Font("Segoe UI Black", 0, 48)); // NOI18N
        tituloLabel.setText("AGREGADO DE PRODUCTO");

        eliminacionProductoButton.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        eliminacionProductoButton.setText("Eliminacion de Producto");
        eliminacionProductoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminacionProductoButtonActionPerformed(evt);
            }
        });

        editarProductoButton.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        editarProductoButton.setText("Editar Producto Existente");
        editarProductoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editarProductoButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tituloLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 645, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(88, 88, 88)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(editarProductoButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(eliminacionProductoButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(68, 68, 68))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tituloLabel)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(eliminacionProductoButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(editarProductoButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(37, Short.MAX_VALUE))
        );

        seleccionaCategoriaComboBox.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        seleccionaCategoriaComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Dama", "Cabllero" }));
        seleccionaCategoriaComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seleccionaCategoriaComboBoxActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel1.setText("Selecciona Categoría");

        nombreTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nombreTextFieldActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel2.setText("Nombre:");

        jLabel3.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel3.setText("Talla:");

        tallaTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tallaTextFieldActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel4.setText("Color:");

        jLabel5.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel5.setText("Precio:");

        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        agregarPrendaButton.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        agregarPrendaButton.setText("Agregar Prenda");
        agregarPrendaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agregarPrendaButtonActionPerformed(evt);
            }
        });

        jFileChooser1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFileChooser1ActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel6.setText("Seleccione imagen para prenda...");

        jLabel8.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel8.setText("Modelo:");

        jTextFieldModelo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldModeloActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel10.setText("Descripcion");

        jTextAreaDescripcion.setColumns(20);
        jTextAreaDescripcion.setRows(5);
        jScrollPane1.setViewportView(jTextAreaDescripcion);

        regresarButton.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        regresarButton.setText("Regresar");
        regresarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                regresarButtonActionPerformed(evt);
            }
        });

        checkbox1.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        checkbox1.setForeground(new java.awt.Color(204, 204, 204));
        checkbox1.setLabel("Disponibilidad");
        checkbox1.setState(true);
        checkbox1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                checkbox1MouseClicked(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel11.setText("Marca:");

        jTextFieldMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldMarcaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(tallaTextField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE)
                                .addComponent(nombreTextField, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jTextField4, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jTextField3, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jTextFieldModelo)
                                .addComponent(jTextFieldMarca))
                            .addComponent(jLabel8)
                            .addComponent(jLabel10)
                            .addComponent(seleccionaCategoriaComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addComponent(checkbox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(101, 101, 101)
                                .addComponent(jFileChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(83, 83, 83)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(vistaPrevia, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(regresarButton, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(348, 348, 348)
                                .addComponent(agregarPrendaButton, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(269, 269, 269)
                                .addComponent(jLabel6))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(96, 96, 96)
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(jFileChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(agregarPrendaButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(seleccionaCategoriaComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(nombreTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tallaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(vistaPrevia, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(checkbox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(55, 55, 55)
                                .addComponent(regresarButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(47, 47, 47))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tallaTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tallaTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tallaTextFieldActionPerformed

    private void agregarPrendaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agregarPrendaButtonActionPerformed
        // TODO add your handling code here:
        agregarPrenda();
    }//GEN-LAST:event_agregarPrendaButtonActionPerformed

    private void seleccionaCategoriaComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seleccionaCategoriaComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_seleccionaCategoriaComboBoxActionPerformed

    private void nombreTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nombreTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nombreTextFieldActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jFileChooser1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFileChooser1ActionPerformed
        if (evt.getActionCommand().equals(javax.swing.JFileChooser.APPROVE_SELECTION)) {
            java.io.File archivo = jFileChooser1.getSelectedFile();

            if (archivo != null && archivo.exists()) {
                imagenSeleccionada = archivo.getName(); // Solo el nombre del archivo

                // Ruta destino dentro del proyecto
                File carpetaDestino = new File("imagenes/ropa/");
                if (!carpetaDestino.exists()) {
                    carpetaDestino.mkdirs(); // Crea la carpeta si no existe
                }

                // Ruta completa destino
                File destino = new File(carpetaDestino, imagenSeleccionada);

                try {
                    // Copiar archivo a la carpeta del proyecto
                    Files.copy(archivo.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);

                    // Mostrar imagen en JLabel (redimensionada)
                    ImageIcon icon = new ImageIcon(destino.getAbsolutePath());
                    Image img = icon.getImage().getScaledInstance(vistaPrevia.getWidth(), vistaPrevia.getHeight(), Image.SCALE_SMOOTH);
                    vistaPrevia.setIcon(new ImageIcon(img));

                    JOptionPane.showMessageDialog(this, "Imagen cargada correctamente.");
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(this, "Error al copiar la imagen: " + e.getMessage());
                }
            }
        }

    }//GEN-LAST:event_jFileChooser1ActionPerformed

    private void jTextFieldModeloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldModeloActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldModeloActionPerformed

    private void regresarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_regresarButtonActionPerformed
        this.dispose();
        AdminPanelHome nuevoAPanel = new AdminPanelHome(null, true);
        nuevoAPanel.setVisible(true);
    }//GEN-LAST:event_regresarButtonActionPerformed

    private void eliminacionProductoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminacionProductoButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_eliminacionProductoButtonActionPerformed

    private void checkbox1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_checkbox1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_checkbox1MouseClicked

    private void jTextFieldMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldMarcaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldMarcaActionPerformed

    private void editarProductoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editarProductoButtonActionPerformed
        this.dispose();
        //AdminEditarProducto nuevoAdminEditarProducto = new AdminEditarProducto(null, true);
        //nuevoaAdminEditarProducto.setVisible(true);
    }//GEN-LAST:event_editarProductoButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
    /* --- CÓDIGO PARA INICIAR FLATLAF --- */
    try {
        // Usamos el tema Darcula que ya habías seleccionado
        FlatDarculaLaf.setup();
    } catch (Exception ex) {
        System.err.println("No se pudo inicializar el Look and Feel FlatLaf.");
    }
    /* --- FIN DEL CÓDIGO DE FLATLAF --- */


    /* Creamos y mostramos la ventana UNA SOLA VEZ */
    java.awt.EventQueue.invokeLater(new Runnable() {
        @Override
        public void run() {
            AdminPanelAgregarProducto dialog = new AdminPanelAgregarProducto(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        }
    });
}
    private String imagenSeleccionada = "sin_imagen.jpg";
    private ArrayList<Categoria> categoriasLista;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton agregarPrendaButton;
    private java.awt.Checkbox checkbox1;
    private javax.swing.JButton editarProductoButton;
    private javax.swing.JButton eliminacionProductoButton;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextAreaDescripcion;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextFieldMarca;
    private javax.swing.JTextField jTextFieldModelo;
    private javax.swing.JTextField nombreTextField;
    private javax.swing.JButton regresarButton;
    private javax.swing.JComboBox<String> seleccionaCategoriaComboBox;
    private javax.swing.JTextField tallaTextField;
    private javax.swing.JLabel tituloLabel;
    private javax.swing.JLabel vistaPrevia;
    // End of variables declaration//GEN-END:variables
}
