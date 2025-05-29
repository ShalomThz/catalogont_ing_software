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
        cargarRopaCaballero();
        cargarEtiquetas();

    }

    public ClientePanelProductos(java.awt.Frame parent, boolean modal, Categoria categoriaRequerida) {
        super(parent, modal);
        initComponents();
        this.categoriaRequerida = categoriaRequerida;
        cargarCategoriasEnToolBar();
        productosPanel.setLayout(new javax.swing.BoxLayout(productosPanel, javax.swing.BoxLayout.Y_AXIS));
        cargarRopaCaballero();
        cargarEtiquetas();

    }

    private void cargarEtiquetas() {
        // --- NUEVA VERIFICACIÓN AL INICIO ---
        if (this.categoriaRequerida == null) {
            titulo.setText("Catálogo General");
            Font fuenteTitulo = new Font("Arial", Font.ITALIC, 22); // Puedes definir una fuente general
            titulo.setFont(fuenteTitulo);
            titulo.setForeground(Color.white);
            titulo.setHorizontalAlignment(SwingConstants.CENTER);
            banner.setIcon(null); // No hay banner específico
            banner.setText("Todos los productos"); // O un mensaje genérico
            return; // Salimos del método si no hay categoría
        }

        // Si llegamos aquí, this.categoriaRequerida NO es null
        Font fuenteTitulo = new Font("Arial", Font.ITALIC, 22);
        titulo.setText("Ropa para " + this.categoriaRequerida.getNombre());
        titulo.setFont(fuenteTitulo);
        titulo.setForeground(Color.white);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);

        // Cargar imagen en el banner
        try {
            String rutaImagen = "imagenes/categoria/" + this.categoriaRequerida.getFoto();
            // System.out.println(rutaImagen); // Puedes dejarlo para depurar
            ImageIcon icono = new ImageIcon(rutaImagen);
            // Asegúrate de que el banner tenga dimensiones antes de redimensionar
            if (banner.getWidth() > 0 && banner.getHeight() > 0) {
                Image imagenRedimensionada = icono.getImage().getScaledInstance(banner.getWidth(), banner.getHeight(), Image.SCALE_SMOOTH);
                banner.setIcon(new ImageIcon(imagenRedimensionada));
            } else {
                // Si el banner aún no tiene dimensiones, considera cargarlo más tarde o con un tamaño fijo
                // Por ahora, lo dejamos con el ícono original o un texto
                banner.setIcon(icono);
                // O banner.setText("Cargando imagen...");
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
                // 1. Actualiza la 'categoriaRequerida' de ESTA MISMA INSTANCIA de la ventana
                // Usamos ClientePanelProductos.this para ser explícitos de que es el campo de la clase externa
                ClientePanelProductos.this.categoriaRequerida = categoria;

                // 2. Vuelve a cargar las etiquetas y los productos con la nueva categoría
                cargarEtiquetas();      // Este método actualizará el título y el banner
                cargarRopaCaballero();  // Este método limpiará y cargará los nuevos productos

                ClientePanelProductos.this.setTitle("Catálogo - " + categoria.getNombre());
            });

            jToolBar1.add(boton);
        }

        // Redibuja la barra
        jToolBar1.revalidate();
        jToolBar1.repaint();
    }

    private void cargarRopaCaballero() {
        RopaController controller = new RopaController();
        ArrayList<Ropa> listaDeRopa; // Declaramos la lista

        // --- LÓGICA MODIFICADA PARA CARGAR ROPA ---
        if (this.categoriaRequerida != null) {
            // Si SÍ tenemos una categoría específica, filtramos por ella
            listaDeRopa = controller.obtenerRopaPorCategoria(this.categoriaRequerida.getNombre());
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
                    ImageIcon icon = new ImageIcon("imagenes/ropa/" + ropa.getImagen());
                    Image img = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                    JLabel imgLabel = new JLabel(new ImageIcon(img));
                    panel.add(imgLabel);
                } catch (Exception e) {
                    panel.add(new JLabel("Imagen no disponible"));
                    System.err.println("Error al cargar imagen de ropa: " + ropa.getImagen() + " - " + e.getMessage());
                }

                // Atributos
                panel.add(new JLabel("Nombre: " + ropa.getNombre()));
                panel.add(new JLabel("Talla: " + ropa.getTalla()));
                panel.add(new JLabel("Color: " + ropa.getColor()));
                panel.add(new JLabel("Precio: $" + String.format("%.2f", ropa.getPrecio()))); // Formatear precio

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
        jScrollPane1 = new javax.swing.JScrollPane();
        productosPanel = new javax.swing.JPanel();
        titulo = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        banner = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 102, 153));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Arial Black", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("CatalogoNT");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(568, 568, 568))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel1)
                .addContainerGap(34, Short.MAX_VALUE))
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

        titulo.setText("CATALOGO");

        jToolBar1.setRollover(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1093, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(banner, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(51, 51, 51))
                    .addComponent(titulo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addComponent(titulo, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(banner, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(139, 139, 139))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* --- CÓDIGO PARA INICIAR FLATLAF --- */
        try {
            FlatDarculaLaf.setup(); // Tema oscuro estilo Darcula
            // com.formdev.flatlaf.FlatIntelliJLaf.setup(); // Tema de IntelliJ

        } catch (Exception ex) {
            System.err.println("No se pudo inicializar el Look and Feel FlatLaf.");
        }
        /* --- FIN DEL CÓDIGO DE FLATLAF --- */


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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel banner;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JPanel productosPanel;
    private javax.swing.JLabel titulo;
    // End of variables declaration//GEN-END:variables
}
