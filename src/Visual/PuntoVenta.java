/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Visual;


import Controladores.DetallesVentaJpaController;
import Controladores.ProductosJpaController;
import Controladores.VentasJpaController;
import Entidades.DetallesVenta;
import Entidades.DetallesVentaPK;
import Entidades.Empleados;
import Entidades.Productos;
import Entidades.Usuarios;
import Entidades.Ventas;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Owner
 */
public class PuntoVenta extends javax.swing.JFrame {
    
    private Productos temp = new Productos();
    public LinkedList<Productos> listaProductos = new LinkedList<>();
    private static Usuarios usuario = new Usuarios();

    public static Usuarios getUsuario() {
        return usuario;
    }

    public static void setUsuario(Usuarios usuario) {
        PuntoVenta.usuario = usuario;
    }
    
    
    public void cargarTablaBusqueda(){
        
        jtBusquedaEx.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                
            },
            new String [] {
                "ID","Nombre", "Marca", "Color", "Precio unitario", "Cantidad"
            }
        ));
        
        DefaultTableModel model = (DefaultTableModel)jtBusquedaEx.getModel();
        List<Productos> lista = new ProductosJpaController().findProductosEntities();
        
        for (int i = 0; i < lista.size(); i++) {
           
         
	Object[] datosRegistro

			 = {

        lista.get(i).getId(),
        
	lista.get(i).getNombre(),
                             
        lista.get(i).getMarca(),

	lista.get(i).getColor(),

	lista.get(i).getPrecio(),
        
        lista.get(i).getCantidad()

	
        };

	

        model.addRow(datosRegistro);
        
        }
        
        jtBusquedaEx.setModel(model);
        
    }
    
    public void cargarTablaBusquedaFiltro(){
        jtBusquedaEx.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                
            },
            new String [] {
                "Nombre", "Marca", "Color", "Precio unitario", "Cantidad"
            }
        ));
         
        DefaultTableModel model = (DefaultTableModel)jtBusquedaEx.getModel();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("DeportesPU");
        EntityManager em = emf.createEntityManager();
        Query consulta = em.createNamedQuery("Productos.findByNombre");
        consulta.setParameter("nombre", txtBusquedaNombre.getText());
        List<Productos> lista = consulta.getResultList();
        
        for (int i = 0; i < lista.size(); i++) {
           
         
	Object[] datosRegistro

			 = {

	lista.get(i).getNombre(),
                             
        lista.get(i).getMarca(),

	lista.get(i).getColor(),

	lista.get(i).getPrecio(),
        
        lista.get(i).getCantidad()

	
        };

	

        model.addRow(datosRegistro);
        
        }
        
        jtBusquedaEx.setModel(model);
         
    }
    
    public void agregarTablaVentas(Productos producto){
        DefaultTableModel model = (DefaultTableModel)jtVentas.getModel();
        
         
	Object[] datosRegistro

			 = {
                             
        producto.getId(),
                             
	producto.getNombre(),
                             
        producto.getMarca(),

	producto.getColor(),

	producto.getPrecio(),
        
        producto.getCantidad()

	
        };

        model.addRow(datosRegistro);

        jtVentas.setModel(model);
    }
    
    public void limpiarTablaVentas(){
        jtVentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre", "Marca", "Color", "Precio unitario", "Cantidad"
            }
        ));
    }
    
    public String agregarCanasta(int index, int sol) throws Exception{
        if(listaProductos.isEmpty()){
            listaProductos.addAll(new ProductosJpaController().findProductosEntities());
            
        }
        String mensaje = null;
        Productos producto = listaProductos.get(index);
        int can = producto.getCantidad();
        if(sol<=0){
            mensaje = "Ingrese la cantidad que desea añadir";
        }else{
            if(can<sol){
            mensaje = "Insuficientes articulos en existencia para completar la solicitud";
        }else{
            producto.setCantidad(sol);
            agregarTablaVentas(producto);
            producto.setCantidad((can-sol));
            listaProductos.set(index, producto);
            
            
            mensaje = "Añadido a la canasta";
        }
        }
        
        return mensaje;
    }
    
    public PuntoVenta() {
        initComponents();
        cargarTablaBusqueda();
        
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtBusquedaNombre = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtBusquedaEx = new javax.swing.JTable();
        btnBusquedaID = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtVentaID = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtVentas = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        txtTotalVentas = new javax.swing.JTextField();
        btnAnadir = new javax.swing.JButton();
        btnFinalizar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        btnQuitar = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtCantidadVenta = new javax.swing.JTextField();
        btnLimpiar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setText("Ventas");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(234, 198, -1, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Nombre del producto:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 52, -1, -1));

        txtBusquedaNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBusquedaNombreActionPerformed(evt);
            }
        });
        getContentPane().add(txtBusquedaNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(168, 52, 202, -1));

        jtBusquedaEx.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Marca", "Color", "Precio unitario", "Cantidad"
            }
        ));
        jtBusquedaEx.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtBusquedaExMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jtBusquedaEx);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 85, 502, 93));

        btnBusquedaID.setText("Buscar");
        btnBusquedaID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBusquedaIDActionPerformed(evt);
            }
        });
        getContentPane().add(btnBusquedaID, new org.netbeans.lib.awtextra.AbsoluteConstraints(388, 51, -1, -1));
        getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 189, 524, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel3.setText("Busqueda de existencias");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(144, 11, -1, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Nombre del producto:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 238, -1, -1));

        txtVentaID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtVentaIDActionPerformed(evt);
            }
        });
        getContentPane().add(txtVentaID, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 238, 186, -1));

        jtVentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre", "Marca", "Color", "Precio unitario", "Cantidad"
            }
        ));
        jtVentas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtVentasMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jtVentas);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 300, 480, 112));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Venta total:");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 424, -1, -1));

        txtTotalVentas.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotalVentas.setText("0");
        getContentPane().add(txtTotalVentas, new org.netbeans.lib.awtextra.AbsoluteConstraints(109, 424, 263, -1));

        btnAnadir.setText("Añadir");
        btnAnadir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnadirActionPerformed(evt);
            }
        });
        getContentPane().add(btnAnadir, new org.netbeans.lib.awtextra.AbsoluteConstraints(451, 238, -1, -1));

        btnFinalizar.setText("Finalizar");
        btnFinalizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFinalizarActionPerformed(evt);
            }
        });
        getContentPane().add(btnFinalizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(443, 423, -1, -1));

        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        getContentPane().add(btnSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 457, -1, -1));

        btnQuitar.setText("Quitar");
        btnQuitar.setEnabled(false);
        btnQuitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarActionPerformed(evt);
            }
        });
        getContentPane().add(btnQuitar, new org.netbeans.lib.awtextra.AbsoluteConstraints(451, 271, -1, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Cantidad:");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(111, 268, -1, -1));

        txtCantidadVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantidadVentaActionPerformed(evt);
            }
        });
        getContentPane().add(txtCantidadVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 268, 62, -1));

        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });
        getContentPane().add(btnLimpiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(459, 51, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBusquedaIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBusquedaIDActionPerformed
        
        cargarTablaBusquedaFiltro();
        
    }//GEN-LAST:event_btnBusquedaIDActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
    txtBusquedaNombre.setText("");
    txtBusquedaNombre.requestFocus();
    txtVentaID.setText("");
    jtBusquedaEx.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre","Marca","Color","Precio unitario","Cantidad"
            }
        ));
    
    jtVentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre","Marca","Color","Precio unitario","Cantidad"
            }
        ));

    cargarTablaBusqueda();
    
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void txtVentaIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtVentaIDActionPerformed

        
    }//GEN-LAST:event_txtVentaIDActionPerformed

    private void btnAnadirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnadirActionPerformed
        boolean borrar = true;
        if(txtCantidadVenta.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Debe ingresar alguna cantidad.");
            borrar = false;
        }else{
            int solicitud = Integer.parseInt(txtCantidadVenta.getText());
            int index = new ProductosJpaController().findProductosEntities().indexOf(temp);
            try {
                JOptionPane.showMessageDialog(this, agregarCanasta(index,solicitud));
            } catch (Exception ex) {
                Logger.getLogger(PuntoVenta.class.getName()).log(Level.SEVERE, null, ex);
                borrar = false;
            }
        }
        if(borrar){
            txtCantidadVenta.setText("");
            txtVentaID.setText("");
            float total = 0;
            float fila = 0;
            
            for (int i = 0; i < jtVentas.getRowCount(); i++) {
                fila = (Float.parseFloat(jtVentas.getValueAt(i,4).toString()))*(Float.parseFloat(jtVentas.getValueAt(i,5).toString()));
                total += fila;
            }
            
            txtTotalVentas.setText(String.valueOf(total));
        }
        
            
    }//GEN-LAST:event_btnAnadirActionPerformed

    private void btnQuitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarActionPerformed
           int fila = jtVentas.getSelectedRow();
           DefaultTableModel model = (DefaultTableModel)jtVentas.getModel();
           model.removeRow(fila);
           jtVentas.setModel(model);
           btnQuitar.setEnabled(false);
           
    }//GEN-LAST:event_btnQuitarActionPerformed

    private void btnFinalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFinalizarActionPerformed

//        Tomar todos los datos de la venta necesarios
        
//        Usar los datos de la venta para ingrearlos en un metodo que los lleve a la capa de negocios
        float totalVenta = Float.parseFloat(txtTotalVentas.getText());
        VentasJpaController conVenta = new VentasJpaController();
        ProductosJpaController proCon = new ProductosJpaController();
        Usuarios ven = getUsuario().getIdEmp().getUsuarios();
        Ventas venta = new Ventas();
        venta.setTotal(totalVenta);
        venta.setIdVendedor(ven);
        boolean resultado = true;
        ArrayList<Integer> idProductos = new ArrayList();
        ArrayList<Integer> cantidades = new ArrayList();
        int cantidad = 0;
        int id = 0;
        for (int i = 0; i <= jtVentas.getRowCount(); i++) {
            if (jtVentas.getRowCount()==i) {
                cantidades.add(cantidad);
            }else{
                if((i==0)&&(jtVentas.getRowCount()>1)){
                    cantidad = (int)jtVentas.getValueAt(i, 5);
                }else{
                    if(id ==(Integer.valueOf(jtVentas.getValueAt(i, 0).toString()))){
                        cantidad += (int)jtVentas.getValueAt(i, 5);
                    }else{
                        cantidades.add(cantidad);
                        cantidad = (int)jtVentas.getValueAt(i, 5);
                    }
                }
                
                id = Integer.valueOf(jtVentas.getValueAt(i, 0).toString());
                Productos producto = new ProductosJpaController().findProductos(id);
                idProductos.add(id);
                int cantidadPrevia = producto.getCantidad();
                int nuevaCantidad = cantidadPrevia - ((int)jtVentas.getValueAt(i, 5));
                producto.setCantidad(nuevaCantidad);

                try {
                    proCon.edit(producto);
                } catch (Exception ex) {
                    Logger.getLogger(PuntoVenta.class.getName()).log(Level.SEVERE, null, ex);
                    resultado = false;
                    break;
                }
                
            }
            
            
        }
        if (resultado) {
            try {
                conVenta.create(venta);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al realizar la venta");
                resultado = false;
            }
        }
        
        if (resultado) {
            DetallesVentaJpaController detCon = new DetallesVentaJpaController();
            
            Set<Integer> hashSet = new HashSet<Integer>(idProductos);
            idProductos.clear();
            idProductos.addAll(hashSet);
            
            List<Ventas> infoVentas = new VentasJpaController().findVentasEntities();
            int idVenta = infoVentas.get(infoVentas.size()-1).getNoVenta();
            int contador = 0;
            for (Integer idActual : idProductos) {
                DetallesVentaPK dvpk = new DetallesVentaPK(idVenta, idActual);
                float precio = new ProductosJpaController().findProductos(idActual).getPrecio();
                DetallesVenta detalle = new DetallesVenta(dvpk, precio, cantidades.get(contador));
                detalle.setVentas(new VentasJpaController().findVentas(idVenta));
                detalle.setProductos(new ProductosJpaController().findProductos(idActual));
                try {
                    detCon.create(detalle);
                } catch (Exception ex) {
                    Logger.getLogger(PuntoVenta.class.getName()).log(Level.SEVERE, null, ex);
                }
                contador++;
            }
            
            JOptionPane.showMessageDialog(this, "Venta finalizada");
            txtTotalVentas.setText("");
            limpiarTablaVentas();
            cargarTablaBusqueda();
        }
        
        
//        Metodo que solicite datos de la venta para mandarlos a la capa de negocios
        
    }//GEN-LAST:event_btnFinalizarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        new Login().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void jtBusquedaExMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtBusquedaExMouseClicked
        int fila = jtBusquedaEx.getSelectedRow();
        temp = new ProductosJpaController().findProductos((int)jtBusquedaEx.getValueAt(fila, 0));
        txtVentaID.setText((String)jtBusquedaEx.getValueAt(fila, 1));
        txtCantidadVenta.setText("");
        txtCantidadVenta.requestFocus();
    }//GEN-LAST:event_jtBusquedaExMouseClicked

    private void txtBusquedaNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBusquedaNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBusquedaNombreActionPerformed

    private void txtCantidadVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadVentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadVentaActionPerformed

    private void jtVentasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtVentasMouseClicked
        btnQuitar.setEnabled(true);
    }//GEN-LAST:event_jtVentasMouseClicked

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
            java.util.logging.Logger.getLogger(PuntoVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PuntoVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PuntoVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PuntoVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PuntoVenta().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAnadir;
    private javax.swing.JButton btnBusquedaID;
    private javax.swing.JButton btnFinalizar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnQuitar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jtBusquedaEx;
    private javax.swing.JTable jtVentas;
    private javax.swing.JTextField txtBusquedaNombre;
    private javax.swing.JTextField txtCantidadVenta;
    private javax.swing.JTextField txtTotalVentas;
    private javax.swing.JTextField txtVentaID;
    // End of variables declaration//GEN-END:variables
}
