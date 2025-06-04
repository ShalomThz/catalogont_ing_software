/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package vistas.cliente;

import controladores.CarritoController;
import java.awt.GridLayout;
import java.awt.Image;
import java.math.BigDecimal;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import modelos.CarritoItem;
import modelos.Usuario;
import utiles.CarritoPDFGenerator;
import modelos.Carrito;

/**
 *
 * @author Villegas Velázquez Alejandro
 */
public class CarritoVista extends javax.swing.JDialog {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(CarritoVista.class.getName());

    /**
     * Creates new form CarritoVista
     */
    public CarritoVista(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        productosPanel.setLayout(new BoxLayout(productosPanel, BoxLayout.Y_AXIS));
        jScrollPane1.setViewportView(productosPanel);
    }
    //HACER LA CANTIDAD DE PRODUCTO MODIFICABLE QUE SE MUUESTRE EL IMPORTE TOTAL Y AGREGAR STOCK

    public void cargarCarrito(Usuario usuario) {
        this.usuario = usuario;
        clienteActualLabel.setText(this.usuario.getNombre());
        fechaActualLabel.setText(java.time.LocalDate.now().toString());

        CarritoController controller = new CarritoController();
        Carrito carritoUsuario = controller.obtenerCarritoUsuario(this.usuario.getNombre());
        productosCarrito = carritoUsuario.getItems();

        productosPanel.removeAll();

        if (productosCarrito.isEmpty()) {
            productosPanel.add(new JLabel("El carrito está vacío."));
            totalLabel.setText("Total: $0.00");
        } else {
            for (CarritoItem item : productosCarrito) {
                JPanel itemPanel = new JPanel();
                itemPanel.setLayout(new GridLayout(1, 6));
                itemPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                try {
                    ImageIcon icon = new ImageIcon("imagenes/ropa/" + item.getRopa().getFoto());
                    Image img = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                    JLabel imgLabel = new JLabel(new ImageIcon(img));
                    itemPanel.add(imgLabel);
                } catch (Exception e) {
                    itemPanel.add(new JLabel("Imagen no disponible"));
                    System.err.println("Error al cargar imagen de ropa: " + item.getRopa().getFoto() + " - " + e.getMessage());
                }

                JLabel nombreLabel = new JLabel("Producto: " + item.getRopa().getNombre());
                JLabel precioUnitarioLabel = new JLabel("Precio Unitario: $" + String.format("%.2f", item.getRopa().getPrecio()));
                JLabel subtotalLabel = new JLabel("  Subtotal: $" + String.format("%.2f", item.getRopa().getPrecio().multiply(new BigDecimal(item.getCantidad()))));

                SpinnerNumberModel spinnerModel = new SpinnerNumberModel(item.getCantidad(), 0, item.getRopa().getStock(), 1);
                JSpinner cantidadSpinner = new JSpinner(spinnerModel);

                cantidadSpinner.addChangeListener(e -> {
                    int newQuantity = (int) cantidadSpinner.getValue();
                    if (newQuantity < 0) {
                        cantidadSpinner.setValue(0);
                        newQuantity = 0;
                    }

                    if (newQuantity > item.getRopa().getStock()) {
                        javax.swing.JOptionPane.showMessageDialog(this, "No hay suficiente stock para " + item.getRopa().getNombre() + ". Stock disponible: " + item.getRopa().getStock(), "Stock Insuficiente", javax.swing.JOptionPane.WARNING_MESSAGE);
                        cantidadSpinner.setValue(item.getCantidad());
                    } else if (newQuantity == 0) { 
                        int dialogResult = javax.swing.JOptionPane.showConfirmDialog(this, "¿Quieres eliminar " + item.getRopa().getNombre() + " del carrito?", "Eliminar Producto", javax.swing.JOptionPane.YES_NO_OPTION);
                        if (dialogResult == javax.swing.JOptionPane.YES_OPTION) {
                            for (java.awt.Component comp : itemPanel.getComponents()) {
                                if (comp instanceof javax.swing.JButton && ((javax.swing.JButton) comp).getText().equals("Eliminar")) {
                                    ((javax.swing.JButton) comp).doClick();
                                    break;
                                }
                            }
                        } else {
                            cantidadSpinner.setValue(item.getCantidad());
                        }
                    } else {
                        item.setCantidad(newQuantity);
                        subtotalLabel.setText("Subtotal: $" + String.format("%.2f", item.getRopa().getPrecio().multiply(new BigDecimal(newQuantity))));
                        actualizarTotalCarrito();
                    }
                });

                javax.swing.JButton eliminarBtn = new javax.swing.JButton("Eliminar");
                eliminarBtn.addActionListener(e -> {
                    controller.quitarDelCarrito(usuario.getNombre(), item.getRopa());
                    cargarCarrito(usuario);
                });

                itemPanel.add(nombreLabel);
                itemPanel.add(precioUnitarioLabel);
                itemPanel.add(cantidadSpinner);
                itemPanel.add(subtotalLabel);
                itemPanel.add(eliminarBtn);

                productosPanel.add(itemPanel);
            }
        }

        productosPanel.revalidate();
        productosPanel.repaint();
        actualizarTotalCarrito();
    }

    private void actualizarTotalCarrito() {
        BigDecimal total = BigDecimal.ZERO;

        for (CarritoItem item : productosCarrito) {
            int cantidad = item.getCantidad();
            BigDecimal precio = item.getRopa().getPrecio();

            BigDecimal cantidadBigDecimal = new BigDecimal(cantidad);
            BigDecimal subtotal = precio.multiply(cantidadBigDecimal);

            total = total.add(subtotal);
        }
        totalLabel.setText("Total: $" + String.format("%.2f", total));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollBar1 = new javax.swing.JScrollBar();
        globalPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        productosPanel = new javax.swing.JPanel();
        regresarButton = new javax.swing.JButton();
        generarPDFButton = new javax.swing.JButton();
        fechaLabel = new javax.swing.JLabel();
        fechaActualLabel = new javax.swing.JLabel();
        clienteLabel = new javax.swing.JLabel();
        clienteActualLabel = new javax.swing.JLabel();
        tituloLabel = new javax.swing.JLabel();
        totalLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        globalPanel.setBackground(new java.awt.Color(255, 204, 204));

        javax.swing.GroupLayout productosPanelLayout = new javax.swing.GroupLayout(productosPanel);
        productosPanel.setLayout(productosPanelLayout);
        productosPanelLayout.setHorizontalGroup(
            productosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1015, Short.MAX_VALUE)
        );
        productosPanelLayout.setVerticalGroup(
            productosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 403, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(productosPanel);

        regresarButton.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        regresarButton.setText("Regresar");
        regresarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                regresarButtonActionPerformed(evt);
            }
        });

        generarPDFButton.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        generarPDFButton.setText("Generar PDF");
        generarPDFButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generarPDFButtonActionPerformed(evt);
            }
        });

        fechaLabel.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        fechaLabel.setForeground(new java.awt.Color(102, 102, 102));
        fechaLabel.setText("Fecha:");

        fechaActualLabel.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        fechaActualLabel.setForeground(new java.awt.Color(102, 102, 102));
        fechaActualLabel.setText("...");

        clienteLabel.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        clienteLabel.setForeground(new java.awt.Color(102, 102, 102));
        clienteLabel.setText("Cliente:");

        clienteActualLabel.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        clienteActualLabel.setForeground(new java.awt.Color(102, 102, 102));
        clienteActualLabel.setText("...");

        tituloLabel.setFont(new java.awt.Font("Segoe UI Black", 0, 48)); // NOI18N
        tituloLabel.setForeground(new java.awt.Color(102, 102, 102));
        tituloLabel.setText("TU CARRRITO");

        totalLabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        totalLabel.setForeground(new java.awt.Color(51, 51, 51));
        totalLabel.setText("jLabel1");

        javax.swing.GroupLayout globalPanelLayout = new javax.swing.GroupLayout(globalPanel);
        globalPanel.setLayout(globalPanelLayout);
        globalPanelLayout.setHorizontalGroup(
            globalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, globalPanelLayout.createSequentialGroup()
                .addGap(116, 116, 116)
                .addGroup(globalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(clienteLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fechaLabel))
                .addGap(18, 18, 18)
                .addGroup(globalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fechaActualLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clienteActualLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(generarPDFButton, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(regresarButton, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
            .addGroup(globalPanelLayout.createSequentialGroup()
                .addGap(282, 282, 282)
                .addComponent(tituloLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 936, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, globalPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(totalLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63))
        );
        globalPanelLayout.setVerticalGroup(
            globalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(globalPanelLayout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(tituloLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(globalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(globalPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                        .addGroup(globalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(generarPDFButton, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(regresarButton, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26))
                    .addGroup(globalPanelLayout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addGroup(globalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(clienteLabel)
                            .addComponent(clienteActualLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(globalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(fechaLabel)
                            .addComponent(fechaActualLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(totalLabel)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(globalPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(globalPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void generarPDFButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generarPDFButtonActionPerformed
        if (productosCarrito == null || productosCarrito.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "El carrito está vacío, no se puede generar el PDF.");
            return;
        }

        String nombreCliente = usuario.getNombre();
        String rutaArchivo = "CarritoDeCompras_" + nombreCliente + ".pdf";

        CarritoPDFGenerator.generarPDF(rutaArchivo, nombreCliente, productosCarrito);
    }//GEN-LAST:event_generarPDFButtonActionPerformed

    private void regresarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_regresarButtonActionPerformed
        this.dispose();
        ClientePanelProductos nuevoPProducto = new ClientePanelProductos(null, true);
        nuevoPProducto.setVisible(true);

    }//GEN-LAST:event_regresarButtonActionPerformed

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
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                CarritoVista dialog = new CarritoVista(new javax.swing.JFrame(), true);
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
    private List<CarritoItem> productosCarrito = null;
    private Usuario usuario = null;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel clienteActualLabel;
    private javax.swing.JLabel clienteLabel;
    private javax.swing.JLabel fechaActualLabel;
    private javax.swing.JLabel fechaLabel;
    private javax.swing.JButton generarPDFButton;
    private javax.swing.JPanel globalPanel;
    private javax.swing.JScrollBar jScrollBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel productosPanel;
    private javax.swing.JButton regresarButton;
    private javax.swing.JLabel tituloLabel;
    private javax.swing.JLabel totalLabel;
    // End of variables declaration//GEN-END:variables
}
