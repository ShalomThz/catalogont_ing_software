/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package vistas;

import controladores.CategoriaController;
import controladores.RopaController;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import modelos.Categoria;
import modelos.Ropa;
import com.formdev.flatlaf.FlatDarculaLaf; // O el tema que quieras
import java.awt.Cursor;
import modelos.Usuario;
import sesion.SesionUtil;

/**
 *
 * @author Villegas Velazquez Alejandro
 */
public class ClientePanelProductos extends javax.swing.JDialog {

    /**
     * Creates new form ClientePanelCaballero
     */
    public ClientePanelProductos(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        cargarCategoriasEnToolBar();
        productosPanel.setLayout(new javax.swing.BoxLayout(productosPanel, javax.swing.BoxLayout.Y_AXIS));
        cargarRopa();
        cargarEtiquetas();

    }

    public ClientePanelProductos(java.awt.Frame parent, boolean modal, Categoria categoriaRequerida) {
        super(parent, modal);
        initComponents();
        this.categoriaRequerida = categoriaRequerida;
        cargarCategoriasEnToolBar();
        productosPanel.setLayout(new javax.swing.BoxLayout(productosPanel, javax.swing.BoxLayout.Y_AXIS));
        cargarRopa();
        cargarEtiquetas();

    }

    private void cargarEtiquetas() {
        // --- NUEVA VERIFICACIÓN AL INICIO ---
        if (this.categoriaRequerida == null) {
            titulo.setText("Catálogo General");
            Font fuenteTitulo = new Font("Arial", Font.BOLD, 24); // Puedes definir una fuente general
            titulo.setFont(fuenteTitulo);
            titulo.setForeground(Color.white);
            titulo.setHorizontalAlignment(SwingConstants.CENTER);
            banner.setIcon(null); 
            banner.setText("Todos los productos"); 
            return; 
        }

        // Si llegamos aquí, this.categoriaRequerida NO es null
        Font fuenteTitulo = new Font("Arial", Font.BOLD, 24);
        titulo.setText("Ropa para " + this.categoriaRequerida.getNombre());
        titulo.setFont(fuenteTitulo);
        titulo.setForeground(Color.white);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);

        // Cargar imagen en el banner
        try {
            String rutaImagen = "imagenes/categoria/" + this.categoriaRequerida.getFoto();
            // System.out.println(rutaImagen); 
            ImageIcon icono = new ImageIcon(rutaImagen);
            if (banner.getWidth() > 0 && banner.getHeight() > 0) {
                Image imagenRedimensionada = icono.getImage().getScaledInstance(banner.getWidth(), banner.getHeight(), Image.SCALE_SMOOTH);
                banner.setIcon(new ImageIcon(imagenRedimensionada));
            } else {
                banner.setIcon(icono);
            }
        } catch (Exception e) {
            System.out.println("No se pudo cargar la imagen del banner: " + e.getMessage());
            banner.setText("Imagen de categoría no disponible");
        }
    }

    private void cargarCategoriasEnToolBar() {
        CategoriaController categoriaController = new CategoriaController();
        categoriasLista = categoriaController.obtenerCategorias();

        jToolBar1.removeAll(); // Limpia por si acaso se vuelve a llamar

        for (Categoria categoria : categoriasLista) {
            JButton boton = new JButton(categoria.getNombre());

            boton.addActionListener(e -> {
                ClientePanelProductos.this.categoriaRequerida = categoria;

                cargarEtiquetas();     
                cargarRopa(); 

                ClientePanelProductos.this.setTitle("Catálogo - " + categoria.getNombre());
            });

            jToolBar1.add(boton);
        }

        jToolBar1.revalidate();
        jToolBar1.repaint();
    }

    private void cargarRopa() {
        RopaController controller = new RopaController();
        ArrayList<Ropa> listaDeRopa; // Declaramos la lista

        // --- LÓGICA MODIFICADA PARA CARGAR ROPA ---
        if (this.categoriaRequerida != null) {
            // Si SÍ tenemos una categoría específica, filtramos por ella
            listaDeRopa = controller.buscarRopaPorNombreCategoria(this.categoriaRequerida.getNombre());
        } else {
            // Si NO tenemos categoría (ej. al abrir desde el main), obtenemos toda la ropa
            listaDeRopa = controller.obtenerRopa(); // ¡Usamos el método que ya tienes!
        }

        // Limpiar el panel antes de agregar nuevos productos
        productosPanel.removeAll();

        if (listaDeRopa.isEmpty()) {
            productosPanel.add(new JLabel("No hay productos para mostrar en esta categoría o no hay productos en general."));
        } else {
            for (Ropa ropa : listaDeRopa) {
                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                panel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));

                // Imagen
                try {
                    ImageIcon icon = new ImageIcon("imagenes/ropa/" + ropa.getFoto());
                    Image img = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                    JLabel imgLabel = new JLabel(new ImageIcon(img));
                    panel.add(imgLabel);
                } catch (Exception e) {
                    panel.add(new JLabel("Imagen no disponible"));
                    System.err.println("Error al cargar imagen de ropa: " + ropa.getFoto() + " - " + e.getMessage());
                }

                // Atributos
                panel.add(new JLabel("Nombre: " + ropa.getNombre()));
                panel.add(new JLabel("Talla: " + ropa.getTalla()));
                panel.add(new JLabel("Color: " + ropa.getColor()));
                panel.add(new JLabel("Precio: $" + String.format("%.2f", ropa.getPrecio()))); // Formatear precio
            // Botón para ver más detalles
            JButton verDetalleBtn = new JButton("Ver Detalles");
            verDetalleBtn.setBackground(new Color(52, 152, 219));
            verDetalleBtn.setForeground(Color.WHITE);
            verDetalleBtn.setFocusPainted(false);
            verDetalleBtn.setBorderPainted(false);
            verDetalleBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            verDetalleBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));

            // Evento del botón
            verDetalleBtn.addActionListener(e -> {
                ClientePanelProducto vistaDetalle = new ClientePanelProducto(null, true);
                vistaDetalle.mostrarProducto(ropa);
                vistaDetalle.setLocationRelativeTo(null);
                vistaDetalle.setVisible(true);
            });

            panel.add(verDetalleBtn);
            productosPanel.add(panel);
        
            }
        }

        productosPanel.revalidate();
        productosPanel.repaint();
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
        jButtonVerCarrito = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        productosPanel = new javax.swing.JPanel();
        titulo = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        banner = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        Carrito = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 204, 204));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Arial Black", 0, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("CatalogoNT");

        jButtonVerCarrito.setText("ver mi carrito");
        jButtonVerCarrito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonVerCarritoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(479, 479, 479)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonVerCarrito)
                .addGap(244, 244, 244))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonVerCarrito)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        javax.swing.GroupLayout productosPanelLayout = new javax.swing.GroupLayout(productosPanel);
        productosPanel.setLayout(productosPanelLayout);
        productosPanelLayout.setHorizontalGroup(
            productosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1081, Short.MAX_VALUE)
        );
        productosPanelLayout.setVerticalGroup(
            productosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 488, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(productosPanel);
        productosPanel.getAccessibleContext().setAccessibleDescription("");

        titulo.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        titulo.setText("CATALOGO");

        jToolBar1.setRollover(true);

        jButton1.setText("Regresar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        Carrito.setText("Ir al Carrito");
        Carrito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CarritoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1093, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(banner, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(51, 51, 51))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(47, 47, 47)
                                .addComponent(Carrito, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(titulo, javax.swing.GroupLayout.PREFERRED_SIZE, 1406, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(titulo, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(banner, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Carrito, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(16, 16, 16))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();
        Home nuevoHome = new Home(null, true);
        nuevoHome.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed


    private void jButtonVerCarritoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonVerCarritoActionPerformed
            // Crear e inicializar la vista del carrito
    CarritoVista carritoVista = new CarritoVista(new javax.swing.JFrame(), true);
    usuarioActual=SesionUtil.getUsuarioActual();
    // Asegúrate de tener el usuario actual disponible
    carritoVista.cargarCarrito(usuarioActual);
    
    // Mostrar el diálogo
    carritoVista.setLocationRelativeTo(null); // Centrar en pantalla
    carritoVista.setVisible(true);
    }//GEN-LAST:event_jButtonVerCarritoActionPerformed

    private void CarritoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CarritoActionPerformed

    }//GEN-LAST:event_CarritoActionPerformed


    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
          
        } catch (Exception ex) {
            System.err.println("No se pudo inicializar el Look and Feel FlatLaf.");
        }


 /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ClientePanelProductos dialog = new ClientePanelProductos(new javax.swing.JFrame(), true);
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
    private ArrayList<Categoria> categoriasLista;
    private Categoria categoriaRequerida = null;
    private Usuario usuarioActual=null;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Carrito;
    private javax.swing.JLabel banner;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButtonVerCarrito;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JPanel productosPanel;
    private javax.swing.JLabel titulo;
    // End of variables declaration//GEN-END:variables
}
