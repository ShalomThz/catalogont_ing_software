/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdf;

/**
 *
 * @author josha
 */
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.List;
import modelos.CarritoItem;
import modelos.Ropa;

public class CarritoPDFGenerator {

    public static void generarPDF(String rutaArchivo, String clienteNombre, List<CarritoItem> items) {
        Document documento = new Document();
        try {
            PdfWriter.getInstance(documento, new FileOutputStream(rutaArchivo));
            documento.open();

            // Título
            Font tituloFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Paragraph titulo = new Paragraph("Resumen del Carrito de Compras", tituloFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            documento.add(titulo);
            documento.add(new Paragraph(" "));

            // Info del cliente
            documento.add(new Paragraph("Cliente: " + clienteNombre));
            documento.add(new Paragraph("Fecha: " + new java.util.Date()));
            documento.add(new Paragraph(" "));

            // Tabla
            PdfPTable tabla = new PdfPTable(8); // con imagen
            tabla.setWidthPercentage(100);
            tabla.setWidths(new float[]{1.2f, 3f, 2f, 1.2f, 1.5f, 1.5f, 2f, 2.5f});

            tabla.addCell("Cant.");
            tabla.addCell("Nombre");
            tabla.addCell("Color");
            tabla.addCell("Talla");
            tabla.addCell("Precio");
            tabla.addCell("Descuento");
            tabla.addCell("Total Línea");
            tabla.addCell("Imagen");

            BigDecimal totalGeneral = BigDecimal.ZERO;

            for (CarritoItem item : items) {
                Ropa ropa = item.getRopa();
                int cantidad = item.getCantidad();
                BigDecimal precio = ropa.getPrecio();
                BigDecimal descuento = ropa.getDescuento();
                BigDecimal subtotal = precio.multiply(new BigDecimal(cantidad));
                BigDecimal descuentoAplicado = subtotal.multiply(descuento).divide(new BigDecimal("100"));
                BigDecimal totalLinea = subtotal.subtract(descuentoAplicado);

                totalGeneral = totalGeneral.add(totalLinea);

                tabla.addCell(String.valueOf(cantidad));
                tabla.addCell(ropa.getNombre());
                tabla.addCell(ropa.getColor());
                tabla.addCell(ropa.getTalla());
                tabla.addCell("$" + precio);
                tabla.addCell(descuento + "%");
                tabla.addCell("$" + totalLinea);

                // Imagen
                if (ropa.getImagen() != null && !ropa.getImagen().isEmpty()) {
                    try {
                        Image imagen = Image.getInstance(ropa.getImagen());
                        imagen.scaleAbsolute(60f, 60f);
                        PdfPCell celdaImagen = new PdfPCell(imagen, true);
                        celdaImagen.setPadding(5);
                        tabla.addCell(celdaImagen);
                    } catch (Exception e) {
                        tabla.addCell("Imagen no encontrada");
                    }
                } else {
                    tabla.addCell("Sin imagen");
                }
            }

            documento.add(tabla);
            documento.add(new Paragraph(" "));

            // Total general
            Font totalFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
            Paragraph total = new Paragraph("Total General: $" + totalGeneral, totalFont);
            total.setAlignment(Element.ALIGN_RIGHT);
            documento.add(total);

            documento.close();
            System.out.println("PDF generado correctamente: " + rutaArchivo);

            // Abrir automáticamente el PDF
            abrirPDF(rutaArchivo);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void abrirPDF(String rutaArchivo) {
        try {
            File archivo = new File(rutaArchivo);
            if (archivo.exists()) {
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(archivo);
                } else {
                    System.out.println("El entorno no soporta la apertura automática de archivos.");
                }
            }
        } catch (Exception e) {
            System.out.println("No se pudo abrir el archivo automáticamente.");
        }
    }
}
