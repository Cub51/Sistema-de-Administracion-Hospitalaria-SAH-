package vista;

import controlador.DAO.PersonaDAO;
import controlador.DAO.RolDAO;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;
import modelo.Persona;
import modelo.Rol;
import modelo.tabla.ModeloTablaPaciente;

public class gestionar_paciente extends javax.swing.JFrame {

    private PersonaDAO personaDAO = new PersonaDAO();
    private ModeloTablaPaciente modelo = new ModeloTablaPaciente();
    private RolDAO rolDAO = new RolDAO();
    private String sw = "GUARDAR";
    private TableRowSorter tr;

    public gestionar_paciente() {
        initComponents();
        limpiar();
        CargarTabla();
        this.btnGuardar.setEnabled(false);
        this.btnCancelar.setEnabled(false);
        this.tblPersonas.setEnabled(true);
        activa_desactivar(false);
    }

    public void CargarTabla() {
        modelo.setListaPersona(personaDAO.buscarPersonasPresentar());
        tblPersonas.setModel(modelo);
        tblPersonas.updateUI();
    }

    public void activa_desactivar(boolean v) {
        this.txtCedula.setEnabled(v);
        this.txtNombre.setEnabled(v);
        this.txtApellido.setEnabled(v);
        this.txtCorreo.setEnabled(v);
        this.txtDireccion.setEnabled(v);
        this.txtTelefono.setEnabled(v);
        this.txtTelefonoAuxiliar.setEnabled(v);
        this.cboEstadoCivil.setEnabled(v);
        this.cboGenero.setEnabled(v);
        this.dcFechaNacimiento.setEnabled(v);
    }

    public void activar_desactivarBuscar(boolean v) {
        this.txtBuscar.setEnabled(v);
    }

    public void limpiar() {
        this.txtCedula.setText("");
        this.txtNombre.setText("");
        this.txtApellido.setText("");
        this.txtCorreo.setText("");
        this.txtDireccion.setText("");
        this.txtTelefono.setText("");
        this.txtTelefonoAuxiliar.setText("");
        this.txtBuscar.setText("");
        this.cboEstadoCivil.setSelectedIndex(0);
        this.cboGenero.setSelectedIndex(0);
        this.dcFechaNacimiento.setCalendar(null);
        CargarTabla();
    }

    public void DarBaja() {
        int fila = this.tblPersonas.getSelectedRow();
        if (fila > -1) {
            for (Object p : personaDAO.listarPersonas()) {
                if (personaDAO.buscarPersona((Persona) p).getCedula().equals(this.tblPersonas.getValueAt(fila, 0).toString())) {
                    ((Persona) p).setEstado("inactivo");
                    personaDAO.editarPersona((Persona) p);
                }
            }
            this.btnGuardar.setEnabled(false);
            this.btnDarBaja.setEnabled(true);
            this.btnNuevo.setEnabled(true);
            this.btnEditar.setEnabled(true);
            this.tblPersonas.setEnabled(true);
            this.btnCancelar.setEnabled(false);
            this.btnHistoriaClinica.setEnabled(true);
            activar_desactivarBuscar(true);
            activa_desactivar(false);
            limpiar();
        } else {
            JOptionPane.showMessageDialog(null, "Selecciona un registro", "ERROR: No se selecciono un registro.", JOptionPane.WARNING_MESSAGE);
        }
    }

    public Persona buscarParaEditar() {
        int fila = this.tblPersonas.getSelectedRow();
        for (Object p : personaDAO.listarPersonas()) {
            if (personaDAO.buscarPersona((Persona) p).getCedula().equals(this.tblPersonas.getValueAt(fila, 0).toString())) {
                return (Persona) p;
            }
        }
        return null;
    }

    public void Editar() throws ParseException {
        sw = "EDITAR";
        int fila = this.tblPersonas.getSelectedRow();
        DateFormat fechaFormato = new SimpleDateFormat("dd/MM/yyyy");
        if (fila != -1) {
            this.txtCedula.setText(this.tblPersonas.getValueAt(fila, 0).toString());
            this.txtNombre.setText(this.tblPersonas.getValueAt(fila, 1).toString());
            this.txtApellido.setText(this.tblPersonas.getValueAt(fila, 2).toString());
            this.txtCorreo.setText(this.tblPersonas.getValueAt(fila, 3).toString());
            this.txtTelefono.setText(this.tblPersonas.getValueAt(fila, 4).toString());
            this.txtTelefonoAuxiliar.setText(this.tblPersonas.getValueAt(fila, 5).toString());
            this.txtDireccion.setText(this.tblPersonas.getValueAt(fila, 6).toString());
            this.cboGenero.setSelectedItem(this.tblPersonas.getValueAt(fila, 7).toString());
            this.cboEstadoCivil.setSelectedItem(this.tblPersonas.getValueAt(fila, 8).toString());
            this.dcFechaNacimiento.setDate(fechaFormato.parse(tblPersonas.getValueAt(fila, 9).toString()));
            this.btnGuardar.setEnabled(true);
            this.btnNuevo.setEnabled(false);
            CargarTabla();
            this.btnGuardar.setEnabled(true);
            this.btnNuevo.setEnabled(false);
            this.btnEditar.setEnabled(false);
            this.btnDarBaja.setEnabled(false);
            this.btnNuevo.setEnabled(false);
            this.activar_desactivarBuscar(false);
            this.btnCancelar.setEnabled(true);
            this.btnHistoriaClinica.setEnabled(true);
            activa_desactivar(true);
            this.tblPersonas.setEnabled(false);
        } else {
            JOptionPane.showMessageDialog(null, "Selecciona un registro", "ERROR: No se selecciono un registro.", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void filtro() {
        tr.setRowFilter(RowFilter.regexFilter(txtBuscar.getText().trim(), 0));
    }

    public void Guardar() {
        String cedula = this.txtCedula.getText().trim();
        String nombre = this.txtNombre.getText().trim();
        String apellido = this.txtApellido.getText().trim();
        String telefono = this.txtTelefono.getText().trim();
        String telefonoAuxiliar = this.txtTelefonoAuxiliar.getText().trim();
        String correo = this.txtCorreo.getText().trim();
        String direccion = this.txtDireccion.getText().trim();
        Date date = this.dcFechaNacimiento.getDate();
        if (!cedula.equals("") && !nombre.equals("") && !apellido.equals("") && !correo.equals("") && !direccion.equals("") && !telefono.equals("")
                && !telefonoAuxiliar.equals("") && date != null) {
            if (personaDAO.verificarCedula(cedula)) {
                if (personaDAO.verificarLongitudDiez(telefono, telefonoAuxiliar)) {
                    if (personaDAO.verificarCorreo(correo)) {
                        DateFormat fechaFormato = new SimpleDateFormat("dd/MM/yyyy");
                        String fecha = fechaFormato.format(date);
                        if (personaDAO.MetodoVaidarFechaNacimiento(fecha)) {
                            if (sw.equals("GUARDAR")) {
                                personaDAO.setPersona(null);
                                personaDAO.getPersona().setCedula(cedula);
                                personaDAO.getPersona().setNombre(nombre);
                                personaDAO.getPersona().setApellido(apellido);
                                personaDAO.getPersona().setCorreo(correo);
                                personaDAO.getPersona().setCelular(telefono);
                                personaDAO.getPersona().setContacto_auxiliar(telefonoAuxiliar);
                                personaDAO.getPersona().setDireccion(direccion);
                                personaDAO.getPersona().setGenero(this.cboGenero.getSelectedItem().toString());
                                personaDAO.getPersona().setEstado_civil(this.cboEstadoCivil.getSelectedItem().toString());
                                personaDAO.getPersona().setFecha_nacimiento(fecha);
                                personaDAO.getPersona().setEstado("activo");
                                personaDAO.getPersona().setRol(rolDAO.buscarRolId(1));
                                personaDAO.getPersona().setEstado_disponibilidad("");
                                personaDAO.setPersona(personaDAO.getPersona());
                                personaDAO.agregarPersona(personaDAO.getPersona());
                                limpiar();
                                this.btnGuardar.setEnabled(false);
                                this.btnNuevo.setEnabled(true);
                                this.btnEditar.setEnabled(true);
                                this.btnDarBaja.setEnabled(true);
                                this.tblPersonas.setEnabled(true);
                                this.btnCancelar.setEnabled(false);
                                this.btnHistoriaClinica.setEnabled(true);
                                this.activar_desactivarBuscar(true);
                                activa_desactivar(false);
                                JOptionPane.showMessageDialog(null, "Se almacenó correctamente");
                            } else {
                                Persona p = buscarParaEditar();
                                p.setCedula(cedula);
                                p.setNombre(nombre);
                                p.setApellido(apellido);
                                p.setCorreo(correo);
                                p.setCelular(telefono);
                                p.setContacto_auxiliar(telefonoAuxiliar);
                                p.setDireccion(direccion);
                                p.setGenero(this.cboGenero.getSelectedItem().toString());
                                p.setEstado_civil(this.cboEstadoCivil.getSelectedItem().toString());
                                p.setFecha_nacimiento(fecha);
                                p.setEstado("activo");
                                p.setRol(rolDAO.buscarRolId(1));
                                p.setEstado_disponibilidad("");
                                personaDAO.editarPersona(p);
                                limpiar();
                                this.btnGuardar.setEnabled(false);
                                this.btnNuevo.setEnabled(true);
                                this.btnEditar.setEnabled(true);
                                this.btnDarBaja.setEnabled(true);
                                this.tblPersonas.setEnabled(true);
                                this.btnCancelar.setEnabled(false);
                                this.btnHistoriaClinica.setEnabled(true);
                                this.activar_desactivarBuscar(true);
                                activa_desactivar(false);
                                JOptionPane.showMessageDialog(null, "Se modificó correctamente");
                                sw = "GUARDAR";
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "La fecha de nacimiento no puede ser mayor a la fecha actual", "ERROR: Fecha incorrecta", JOptionPane.WARNING_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "El correo ingresado no es valido", "ERROR: Formato Correo", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Los campos de telefonos debe tener 10 números", "ERROR: Formato Teléfono", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "La cédula ingresa no es valida", "ERROR: Formato Cédula", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Llena correctamente los campos", "ERROR: Datos Personales", JOptionPane.WARNING_MESSAGE);
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanelGestionarPaciente = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtCedula = new javax.swing.JTextField();
        txtDireccion = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        txtCorreo = new javax.swing.JTextField();
        txtTelefono = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtApellido = new javax.swing.JTextField();
        txtTelefonoAuxiliar = new javax.swing.JTextField();
        cboEstadoCivil = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        cboGenero = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        dcFechaNacimiento = new com.toedter.calendar.JDateChooser();
        jPanel3 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        btnNuevo = new javax.swing.JButton();
        btnHistoriaClinica = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPersonas = new javax.swing.JTable();
        btnEditar = new javax.swing.JButton();
        btnDarBaja = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Gestionar Paciente");

        PanelGestionarPaciente.setBackground(new java.awt.Color(153, 204, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos Personales"));

        jLabel1.setText("Cédula:");

        jLabel2.setText("Nombre:");

        jLabel3.setText("Dirección:");

        jLabel4.setText("Correo:");

        jLabel5.setText("Teléfono:");

        jLabel6.setText("Teléfono Auxiliar:");

        jLabel7.setText("Estado Civil:");

        txtCedula.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCedulaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCedulaKeyTyped(evt);
            }
        });

        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });
        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreKeyTyped(evt);
            }
        });

        txtTelefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoKeyTyped(evt);
            }
        });

        jLabel8.setText("Apellido:");

        txtApellido.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtApellidoKeyTyped(evt);
            }
        });

        txtTelefonoAuxiliar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTelefonoAuxiliarActionPerformed(evt);
            }
        });
        txtTelefonoAuxiliar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoAuxiliarKeyTyped(evt);
            }
        });

        cboEstadoCivil.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Soltero/a", "Casado/a", "Divorciado/a", "Viudo/a" }));

        jLabel9.setText("Género:");

        cboGenero.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hombre", "Mujer" }));
        cboGenero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboGeneroActionPerformed(evt);
            }
        });

        jLabel11.setText("Fecha de Nacimiento:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jLabel7)))
                .addGap(42, 42, 42)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 461, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(7, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(cboEstadoCivil, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel9))
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cboGenero, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(29, 29, 29)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jLabel6)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(txtTelefonoAuxiliar))
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jLabel11)
                                                .addGap(31, 31, 31)
                                                .addComponent(dcFechaNacimiento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jLabel8)
                                                .addGap(26, 26, 26)
                                                .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(dcFechaNacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4)
                        .addComponent(jLabel11)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(txtTelefonoAuxiliar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(cboEstadoCivil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(cboGenero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Buscar"));

        jLabel10.setText("Cédula:");

        txtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarActionPerformed(evt);
            }
        });
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBuscarKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel10)
                .addGap(79, 79, 79)
                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnHistoriaClinica.setText("Historia Clínica");
        btnHistoriaClinica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHistoriaClinicaActionPerformed(evt);
            }
        });

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        tblPersonas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblPersonas);

        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnDarBaja.setText("Dar de baja");
        btnDarBaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDarBajaActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(btnNuevo)
                .addGap(61, 61, 61)
                .addComponent(btnCancelar)
                .addGap(54, 54, 54)
                .addComponent(btnEditar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnHistoriaClinica)
                .addGap(49, 49, 49)
                .addComponent(btnDarBaja)
                .addGap(56, 56, 56)
                .addComponent(btnGuardar)
                .addGap(19, 19, 19))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevo)
                    .addComponent(btnHistoriaClinica)
                    .addComponent(btnGuardar)
                    .addComponent(btnEditar)
                    .addComponent(btnDarBaja)
                    .addComponent(btnCancelar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout PanelGestionarPacienteLayout = new javax.swing.GroupLayout(PanelGestionarPaciente);
        PanelGestionarPaciente.setLayout(PanelGestionarPacienteLayout);
        PanelGestionarPacienteLayout.setHorizontalGroup(
            PanelGestionarPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelGestionarPacienteLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(PanelGestionarPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        PanelGestionarPacienteLayout.setVerticalGroup(
            PanelGestionarPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelGestionarPacienteLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        getContentPane().add(PanelGestionarPaciente, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cboGeneroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboGeneroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboGeneroActionPerformed

    private void btnHistoriaClinicaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHistoriaClinicaActionPerformed
        if (tblPersonas.getSelectedRow() != - 1) {
            personaDAO.setPersona(null);
            personaDAO.setPersona((Persona) personaDAO.listarPersonas().get(tblPersonas.getSelectedRow()));
            historial_clinico form = new historial_clinico(personaDAO.getPersona().getId_persona());
            form.setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, primero seleccione un pedido de la tabla");
        }
    }//GEN-LAST:event_btnHistoriaClinicaActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        Guardar();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        try {
            Editar();
        } catch (ParseException ex) {
            Logger.getLogger(gestionar_paciente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnDarBajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDarBajaActionPerformed
        DarBaja();
    }//GEN-LAST:event_btnDarBajaActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        this.btnGuardar.setEnabled(true);
        this.btnEditar.setEnabled(false);
        this.btnDarBaja.setEnabled(false);
        this.btnNuevo.setEnabled(false);
        this.btnCancelar.setEnabled(true);
        this.btnHistoriaClinica.setEnabled(false);
        this.activar_desactivarBuscar(false);
        activa_desactivar(true);
        this.tblPersonas.setEnabled(false);
        limpiar();
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void txtCedulaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCedulaKeyTyped
        char e = evt.getKeyChar();
        if (this.txtCedula.getText().length() >= 10) {
            evt.consume();
            getToolkit().beep();
        }
        if (!(e >= '0' && e <= '9')) {
            evt.consume();
            getToolkit().beep();
        }
    }//GEN-LAST:event_txtCedulaKeyTyped

    private void txtCedulaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCedulaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCedulaKeyPressed

    private void txtTelefonoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoKeyTyped
        char e = evt.getKeyChar();
        if (this.txtTelefono.getText().length() >= 10) {
            evt.consume();
            getToolkit().beep();
        }
        if (!(e >= '0' && e <= '9')) {
            evt.consume();
            getToolkit().beep();
        }
    }//GEN-LAST:event_txtTelefonoKeyTyped

    private void txtTelefonoAuxiliarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTelefonoAuxiliarActionPerformed

    }//GEN-LAST:event_txtTelefonoAuxiliarActionPerformed

    private void txtTelefonoAuxiliarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoAuxiliarKeyTyped
        char e = evt.getKeyChar();
        if (this.txtTelefonoAuxiliar.getText().length() >= 10) {
            evt.consume();
            getToolkit().beep();
        }
        if (!(e >= '0' && e <= '9')) {
            evt.consume();
            getToolkit().beep();
        }
    }//GEN-LAST:event_txtTelefonoAuxiliarKeyTyped

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed

    }//GEN-LAST:event_txtNombreActionPerformed

    private void txtNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyTyped
        char e = evt.getKeyChar();
        if (!Character.isLetter(e) && (e != (char) KeyEvent.VK_BACK_SPACE) && (e != (char) KeyEvent.VK_SPACE)) {
            evt.consume();
            getToolkit().beep();
        }
    }//GEN-LAST:event_txtNombreKeyTyped

    private void txtApellidoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidoKeyTyped
        char e = evt.getKeyChar();
        if (!Character.isLetter(e) && (e != (char) KeyEvent.VK_BACK_SPACE) && (e != (char) KeyEvent.VK_SPACE)) {
            evt.consume();
            getToolkit().beep();
        }
    }//GEN-LAST:event_txtApellidoKeyTyped

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        limpiar();
        this.tblPersonas.setEnabled(true);
        this.btnCancelar.setEnabled(false);
        this.btnNuevo.setEnabled(true);
        this.btnEditar.setEnabled(true);
        this.btnDarBaja.setEnabled(true);
        this.btnGuardar.setEnabled(false);
        this.btnHistoriaClinica.setEnabled(true);
        activa_desactivar(false);
        activar_desactivarBuscar(true);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarKeyReleased

    private void txtBuscarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyTyped
        char e = evt.getKeyChar();
        if (this.txtCedula.getText().length() >= 10) {
            evt.consume();
            getToolkit().beep();
        }
        if (!(e >= '0' && e <= '9')) {
            evt.consume();
            getToolkit().beep();
        }
        txtBuscar.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (txtBuscar.getText());
                txtBuscar.setText(cadena);
                repaint();
                filtro();
            }
        });
        tr = new TableRowSorter(tblPersonas.getModel());
        tblPersonas.setRowSorter(tr);
    }//GEN-LAST:event_txtBuscarKeyTyped

    private void txtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarActionPerformed

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
            java.util.logging.Logger.getLogger(gestionar_paciente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(gestionar_paciente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(gestionar_paciente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(gestionar_paciente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new gestionar_paciente().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JPanel PanelGestionarPaciente;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnDarBaja;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnHistoriaClinica;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JComboBox<String> cboEstadoCivil;
    private javax.swing.JComboBox<String> cboGenero;
    private com.toedter.calendar.JDateChooser dcFechaNacimiento;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblPersonas;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtCedula;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtTelefono;
    private javax.swing.JTextField txtTelefonoAuxiliar;
    // End of variables declaration//GEN-END:variables
}
