package vista;

import controlador.DAO.LaboratorioDAO;
import controlador.DAO.MedicoDAO;
import javax.swing.JOptionPane;
import modelo.Medico;
import modelo.tabla.LaboratoriosTabla;
import vista.utilidades.UtilidadesVista;

public class Frm_Laboratorio extends javax.swing.JFrame {
    
    private LaboratorioDAO laboratorioDAO = new LaboratorioDAO();
    private LaboratoriosTabla laboratoriosTabla = new LaboratoriosTabla();
    private MedicoDAO medicoDAO = new MedicoDAO();

    public Frm_Laboratorio() {
        initComponents();
        cargarTabla();
        cargarCbxPersonas();
    }
    
    public void cargarCbxPersonas() {
        UtilidadesVista.cargarCbxMedicos(cbxEncargado, medicoDAO.filtrarPor_EstadoDiponibilidad_IdRol(Long.parseLong("3"), "Disponible"));
    }
    
    public void limpiarCampos() {
        txtNombre.setText("");
        txtDescripcion.setText("");
    }
    
    public void cargarCampos() {
        txtNombre.setText(laboratorioDAO.getLaboratorio().getNombre_lab());
        txtDescripcion.setText(laboratorioDAO.getLaboratorio().getDescripcion_lab());
    }
    
    public void habilitarCampos() {
        txtNombre.setEnabled(true);
        txtDescripcion.setEnabled(true);
        btnGuardar.setEnabled(true);
        btnCancelar.setEnabled(true);
        cbxEncargado.setEnabled(true);
    }
    
    public void deshabilitarCampos() {
        txtNombre.setEnabled(false);
        txtDescripcion.setEnabled(false);
        btnGuardar.setEnabled(false);
        btnCancelar.setEnabled(false);
        cbxEncargado.setEnabled(false);
    }
    
    public void guardar() {
        switch (btnGuardar.getText().toUpperCase()) {
            case "GUARDAR":
                if (!txtNombre.getText().equals("") && !txtDescripcion.getText().equals("")) {
                    laboratorioDAO.getLaboratorio().setNombre_lab(txtNombre.getText());
                    laboratorioDAO.getLaboratorio().setDescripcion_lab(txtDescripcion.getText());
                    medicoDAO.setMedico((Medico) cbxEncargado.getSelectedItem());
                    medicoDAO.getMedico().setEstado_disponibilidad("NO DISPONIBLE");
                    medicoDAO.editarMedico(medicoDAO.getMedico());
                    laboratorioDAO.getLaboratorio().setId_encargado(medicoDAO.getMedico().getId_persona());
                    medicoDAO.setMedico(null);
                    laboratorioDAO.getLaboratorio().setEstado("activo");
                    if (laboratorioDAO.agregar(laboratorioDAO.getLaboratorio())) {
                        
                        JOptionPane.showMessageDialog(this, "Laboratorio ingresado exitosamente");
                        laboratorioDAO.setLaboratorio(null);
                        limpiarCampos();
                        deshabilitarCampos();
                        cargarTabla();
                        
                    } else {
                        JOptionPane.showMessageDialog(this, "Ha ocurrido un error, el laboratorio no se pudo ingresar");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Por favor, primero llene todos los campos");
                }
                break;
            case "ACTUALIZAR":
                if (!txtNombre.getText().equals("") && !txtDescripcion.getText().equals("")) {
                    laboratorioDAO.setLaboratorio(laboratorioDAO.listar().get(tblLaboratorios.getSelectedRow()));
                    laboratorioDAO.getLaboratorio().setNombre_lab(txtNombre.getText());
                    laboratorioDAO.getLaboratorio().setDescripcion_lab(txtDescripcion.getText());
                    laboratorioDAO.getLaboratorio().setId_encargado(medicoDAO.getMedico().getId_persona());
                    medicoDAO.setMedico(null);

                    if (laboratorioDAO.editar(laboratorioDAO.getLaboratorio())) {
                        
                        JOptionPane.showMessageDialog(this, "Laboratorio actualizado exitosamente");
                        laboratorioDAO.setLaboratorio(null);
                        limpiarCampos();
                        deshabilitarCampos();
                        cargarTabla();
                        btnGuardar.setText("Guardar");
                    } else {
                        JOptionPane.showMessageDialog(this, "Ha ocurrido un error, el laboratorio no se pudo actualizar");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Por favor, primero llene todos los campos");
                }
                break;
        }
    }
    
    public void nuevo() {
        
        habilitarCampos();
    }
    
    public void editar() {

        if (tblLaboratorios.getSelectedRow() != - 1) {
            laboratorioDAO.setLaboratorio(laboratorioDAO.laboratoriosPorEstado("activo").get(tblLaboratorios.getSelectedRow()));
            habilitarCampos();
            cargarCampos();
            btnGuardar.setText("Actualizar");
            cbxEncargado.setEnabled(false);
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, primero seleccione un laboratorio de la tabla");
        }
    }
    
    public void darDeBaja() {
        
        if (tblLaboratorios.getSelectedRow() != - 1) {
            laboratorioDAO.setLaboratorio(laboratorioDAO.listar().get(tblLaboratorios.getSelectedRow()));
            laboratorioDAO.getLaboratorio().setEstado("inactivo");
            
            if (laboratorioDAO.editar(laboratorioDAO.getLaboratorio())) {
                
                JOptionPane.showMessageDialog(this, "Laboratorio eliminado exitosamente");
                laboratorioDAO.setLaboratorio(null);
                cargarTabla();
                
            } else {
                JOptionPane.showMessageDialog(this, "Ha ocurrido un error, el laboratorio no se pudo eliminar");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, primero seleccione un laboratorio de la tabla");
        }
    }
    
    public void cargarTabla() {
        laboratoriosTabla.setListaLaboratorios(laboratorioDAO.laboratoriosPorEstado("ACTIVO"));
        tblLaboratorios.setModel(laboratoriosTabla);
        tblLaboratorios.updateUI();
    }
    
    public void cancelar() {
        btnGuardar.setText("Guardar");
        limpiarCampos();
        deshabilitarCampos();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanelLab = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        txtDescripcion = new javax.swing.JTextField();
        btnCancelar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        cbxEncargado = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblLaboratorios = new javax.swing.JTable();
        btnEliminar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        PanelLab.setBackground(new java.awt.Color(153, 204, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setText("Nombre:");

        jLabel2.setText("Descripción:");

        txtNombre.setEnabled(false);

        txtDescripcion.setEnabled(false);

        btnCancelar.setText("Cancelar");
        btnCancelar.setEnabled(false);
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnGuardar.setText("Guardar");
        btnGuardar.setEnabled(false);
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        jLabel3.setText("Encargado:");

        cbxEncargado.setEnabled(false);
        cbxEncargado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxEncargadoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(cbxEncargado, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardar)
                    .addComponent(jLabel1))
                .addGap(11, 11, 11)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel2))
                    .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbxEncargado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnCancelar)))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tblLaboratorios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tblLaboratorios);

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditar)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap(18, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(btnNuevo)
                        .addGap(34, 34, 34)
                        .addComponent(btnEditar)
                        .addGap(37, 37, 37)
                        .addComponent(btnEliminar)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout PanelLabLayout = new javax.swing.GroupLayout(PanelLab);
        PanelLab.setLayout(PanelLabLayout);
        PanelLabLayout.setHorizontalGroup(
            PanelLabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelLabLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(PanelLabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        PanelLabLayout.setVerticalGroup(
            PanelLabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelLabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(PanelLab, java.awt.BorderLayout.CENTER);

        setSize(new java.awt.Dimension(566, 447));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        darDeBaja();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        guardar();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        editar();
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        nuevo();
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        cancelar();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void cbxEncargadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxEncargadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxEncargadoActionPerformed

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
            java.util.logging.Logger.getLogger(Frm_Laboratorio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frm_Laboratorio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frm_Laboratorio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frm_Laboratorio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Frm_Laboratorio().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JPanel PanelLab;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JComboBox<String> cbxEncargado;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblLaboratorios;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
