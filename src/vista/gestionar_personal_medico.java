package vista;

import controlador.DAO.CuentaDAO;
import controlador.DAO.MedicoDAO;
import controlador.DAO.PersonaDAO;
import controlador.DAO.RolDAO;
import javax.swing.JOptionPane;
import modelo.Persona;
import controlador.Seguridad;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;
import modelo.Cuenta;
import modelo.Medico;
import modelo.tabla.ModeloTablaPersonalMedico;

public class gestionar_personal_medico extends javax.swing.JFrame {

    private MedicoDAO medicoDAO = new MedicoDAO();
    private PersonaDAO personaDAO = new PersonaDAO();
    private CuentaDAO cuentaDAO = new CuentaDAO();
    private RolDAO rolDAO = new RolDAO();
    private ModeloTablaPersonalMedico modelo = new ModeloTablaPersonalMedico();
    private Seguridad seguridad = new Seguridad();
    private String sw = "GUARDAR";
    private TableRowSorter tr;

    public gestionar_personal_medico() {
        initComponents();
        CargarTabla();
        this.btnGuardar.setEnabled(false);
        this.btnCancelar.setEnabled(false);
        this.tblPersonalMedico.setEnabled(true);
        activa_desactivar(false);
    }

    public void CargarTabla() {
        modelo.setListaMedico(medicoDAO.FiltrarMedicos());
        tblPersonalMedico.setModel(modelo);
        tblPersonalMedico.updateUI();
    }

    public void activa_desactivar(boolean v) {
        this.txtCedula.setEnabled(v);
        this.txtNombre.setEnabled(v);
        this.txtApellido.setEnabled(v);
        this.txtCorreo.setEnabled(v);
        this.txtDireccion.setEnabled(v);
        this.txtTelefono.setEnabled(v);
        this.txtTelefonoAuxiliar.setEnabled(v);
        this.txtUsuario.setEnabled(v);
        this.txtClave.setEnabled(v);
        this.txtConfirmarClave.setEnabled(v);
        this.cboEstadoCivil.setEnabled(v);
        this.cboGenero.setEnabled(v);
        this.cboRol.setEnabled(v);
        this.cboEspecialidad.setEnabled(v);
        this.dcFechaNacimiento.setEnabled(v);
    }

    public void activar_desactivarBuscar(boolean v) {
        this.txtBuscar.setEnabled(v);
    }

    public void filtro() {
        tr.setRowFilter(RowFilter.regexFilter(txtBuscar.getText().trim(), 0));
    }

    public void Editar() throws ParseException {
        sw = "EDITAR";
        int fila = this.tblPersonalMedico.getSelectedRow();
        Cuenta aux = buscarCuenta();
        DateFormat fechaFormato = new SimpleDateFormat("dd/MM/yyyy");
        if (fila != -1) {
            this.txtCedula.setText(this.tblPersonalMedico.getValueAt(fila, 0).toString());
            this.txtNombre.setText(this.tblPersonalMedico.getValueAt(fila, 1).toString());
            this.txtApellido.setText(this.tblPersonalMedico.getValueAt(fila, 2).toString());
            this.txtCorreo.setText(this.tblPersonalMedico.getValueAt(fila, 3).toString());
            this.txtTelefono.setText(this.tblPersonalMedico.getValueAt(fila, 4).toString());
            this.txtTelefonoAuxiliar.setText(this.tblPersonalMedico.getValueAt(fila, 5).toString());
            this.txtDireccion.setText(this.tblPersonalMedico.getValueAt(fila, 6).toString());
            this.cboGenero.setSelectedItem(this.tblPersonalMedico.getValueAt(fila, 7).toString());
            this.cboEstadoCivil.setSelectedItem(this.tblPersonalMedico.getValueAt(fila, 8).toString());
            this.dcFechaNacimiento.setDate(fechaFormato.parse(tblPersonalMedico.getValueAt(fila, 9).toString()));
            this.cboRol.setSelectedItem(this.tblPersonalMedico.getValueAt(fila, 10).toString());
            this.cboEspecialidad.setSelectedItem(this.tblPersonalMedico.getValueAt(fila, 11).toString());
            this.txtUsuario.setText(aux.getUsuario());
            this.txtClave.setText(seguridad.Desencriptar(aux.getClave()));
            this.txtConfirmarClave.setText(seguridad.Desencriptar(aux.getClave()));
            CargarTabla();
            this.btnGuardar.setEnabled(true);
            this.btnNuevo.setEnabled(false);
            this.btnEditar.setEnabled(false);
            this.btnDarBaja.setEnabled(false);
            this.btnNuevo.setEnabled(false);
            this.activar_desactivarBuscar(false);
            this.btnCancelar.setEnabled(true);
            activa_desactivar(true);
            this.tblPersonalMedico.setEnabled(false);
        } else {
            JOptionPane.showMessageDialog(null, "Selecciona un registro", "ERROR: No se selecciono un registro.", JOptionPane.WARNING_MESSAGE);
        }
    }

    public Medico buscarMedico() {
        int fila = this.tblPersonalMedico.getSelectedRow();
        for (Object p : medicoDAO.listarMedicos()) {
            if (medicoDAO.buscarMedico((Medico) p).getCedula().equals(this.tblPersonalMedico.getValueAt(fila, 0).toString())) {
                return (Medico) p;
            }
        }
        return null;
    }

    public Cuenta buscarCuenta() {
        int fila = this.tblPersonalMedico.getSelectedRow();
        for (Object p : medicoDAO.listarMedicos()) {
            if (medicoDAO.buscarMedico((Medico) p).getCedula().equals(this.tblPersonalMedico.getValueAt(fila, 0).toString())) {
                for (Object c : cuentaDAO.listarCuentas()) {
                    if (medicoDAO.buscarMedico((Medico) p).getCedula().equals(cuentaDAO.buscarCuenta((Cuenta) c).getPersona().getCedula())) {
                        return (Cuenta) c;
                    }
                }
            }
        }
        return null;
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
        this.txtUsuario.setText("");
        this.txtClave.setText("");
        this.txtConfirmarClave.setText("");
        this.cboEstadoCivil.setSelectedIndex(0);
        this.cboGenero.setSelectedIndex(0);
        this.cboRol.setSelectedIndex(0);
        this.cboEspecialidad.setSelectedIndex(0);
        this.dcFechaNacimiento.setCalendar(null);
        this.tblPersonalMedico.setCellSelectionEnabled(false);
        CargarTabla();
    }

    public void DarBaja() {
        int fila = this.tblPersonalMedico.getSelectedRow();
        if (fila > -1) {
            for (Object p : medicoDAO.listarMedicos()) {
                if (medicoDAO.buscarMedico((Medico) p).getCedula().equals(this.tblPersonalMedico.getValueAt(fila, 0).toString())) {
                    ((Persona) p).setEstado("inactivo");
                    medicoDAO.editarMedico((Medico) p);
                    JOptionPane.showMessageDialog(null, "Persona dada de baja correctamente");
                }
            }
            this.btnGuardar.setEnabled(false);
            this.btnDarBaja.setEnabled(true);
            this.btnNuevo.setEnabled(true);
            this.btnEditar.setEnabled(true);
            this.tblPersonalMedico.setEnabled(true);
            this.btnCancelar.setEnabled(false);
            activar_desactivarBuscar(true);
            activa_desactivar(false);
            limpiar();
        } else {
            JOptionPane.showMessageDialog(null, "Selecciona un registro", "ERROR: No se selecciono un registro.", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void Guardar() {
        String cedula = this.txtCedula.getText().trim();
        String nombre = this.txtNombre.getText().trim();
        String apellido = this.txtApellido.getText().trim();
        String telefono = this.txtTelefono.getText().trim();
        String telefonoAuxiliar = this.txtTelefonoAuxiliar.getText().trim();
        String correo = this.txtCorreo.getText().trim();
        String direccion = this.txtDireccion.getText().trim();
        String clave = this.txtClave.getText();
        Date date = this.dcFechaNacimiento.getDate();
        if (!cedula.equals("") && !nombre.equals("") && !apellido.equals("") && !correo.equals("") && !direccion.equals("") && !telefono.equals("")
                && !telefonoAuxiliar.equals("") && date != null) {
            DateFormat fechaFormato = new SimpleDateFormat("dd/MM/yyyy");
            String fecha = fechaFormato.format(date);
            if (personaDAO.verificarCedula(cedula)) {
                if (personaDAO.verificarLongitudDiez(telefono, telefonoAuxiliar)) {
                    if (personaDAO.verificarCorreo(correo)) {
                        if (personaDAO.MetodoVaidarFechaNacimiento(fecha)) {
                            if (sw.equals("GUARDAR")) {
                                medicoDAO.setMedico(null);
                                medicoDAO.getMedico().setCedula(cedula);
                                medicoDAO.getMedico().setNombre(nombre);
                                medicoDAO.getMedico().setApellido(apellido);
                                medicoDAO.getMedico().setCorreo(correo);
                                medicoDAO.getMedico().setCelular(telefono);
                                medicoDAO.getMedico().setContacto_auxiliar(telefonoAuxiliar);
                                medicoDAO.getMedico().setDireccion(direccion);
                                medicoDAO.getMedico().setGenero(this.cboGenero.getSelectedItem().toString());
                                medicoDAO.getMedico().setEstado_civil(this.cboEstadoCivil.getSelectedItem().toString());
                                medicoDAO.getMedico().setFecha_nacimiento(fecha);
                                medicoDAO.getMedico().setEstado("activo");
                                medicoDAO.getMedico().setRol(rolDAO.buscarRolId(rolDAO.asignarValorRol(this.cboRol.getSelectedItem().toString())));
                                medicoDAO.getMedico().setEstado_disponibilidad("Disponible");
                                medicoDAO.getMedico().setEspecialidad(this.cboEspecialidad.getSelectedItem().toString());
                                medicoDAO.setMedico(medicoDAO.getMedico());
                                if (clave.equals(this.txtConfirmarClave.getText())) {
                                    medicoDAO.agregarMedico(medicoDAO.getMedico());
                                    cuentaDAO.setCuenta(null);
                                    cuentaDAO.getCuenta().setUsuario(this.txtUsuario.getText());
                                    cuentaDAO.getCuenta().setClave(seguridad.Encriptar(clave));
                                    cuentaDAO.getCuenta().setPersona(medicoDAO.getMedico());
                                    cuentaDAO.setCuenta(cuentaDAO.getCuenta());
                                    cuentaDAO.agregarCuenta(cuentaDAO.getCuenta());
                                    limpiar();
                                    this.btnGuardar.setEnabled(false);
                                    this.btnNuevo.setEnabled(true);
                                    this.btnEditar.setEnabled(true);
                                    this.btnDarBaja.setEnabled(true);
                                    this.tblPersonalMedico.setEnabled(true);
                                    this.btnCancelar.setEnabled(false);
                                    this.activar_desactivarBuscar(true);
                                    activa_desactivar(false);
                                    JOptionPane.showMessageDialog(null, "Se almacenó correctamente");
                                } else {
                                    JOptionPane.showMessageDialog(null, "No existen coincidencias al confirmar su clave, verifique nuevamente", "ERROR: Clave no coincede", JOptionPane.WARNING_MESSAGE);
                                }
                            } else {
                                Medico m = buscarMedico();
                                m.setCedula(cedula);
                                m.setNombre(nombre);
                                m.setApellido(apellido);
                                m.setCorreo(correo);
                                m.setCelular(telefono);
                                m.setContacto_auxiliar(telefonoAuxiliar);
                                m.setDireccion(direccion);
                                m.setGenero(this.cboGenero.getSelectedItem().toString());
                                m.setEstado_civil(this.cboEstadoCivil.getSelectedItem().toString());
                                m.setFecha_nacimiento(fecha);
                                m.setEstado("activo");
                                m.setRol(rolDAO.buscarRolId(rolDAO.asignarValorRol(this.cboRol.getSelectedItem().toString())));
                                m.setEstado_disponibilidad("Disponible");
                                m.setEspecialidad(this.cboEspecialidad.getSelectedItem().toString());
                                medicoDAO.editarMedico(m);
                                limpiar();
                                this.btnGuardar.setEnabled(false);
                                this.btnNuevo.setEnabled(true);
                                this.btnEditar.setEnabled(true);
                                this.btnDarBaja.setEnabled(true);
                                this.tblPersonalMedico.setEnabled(true);
                                this.btnCancelar.setEnabled(false);
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
                    JOptionPane.showMessageDialog(null, "Los campos de Telefonos deben tener 10 números", "ERROR: Formato Cédula/Telefono", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "La cédula ingresa no es valida", "ERROR: Formato Cédula", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Llena correctamente los campos", "ERROR: Datos Personales", JOptionPane.WARNING_MESSAGE);
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

        PanelGestionar_Personal_Medico = new javax.swing.JPanel();
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
        cboRol = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        cboEspecialidad = new javax.swing.JComboBox<>();
        dcFechaNacimiento = new com.toedter.calendar.JDateChooser();
        jLabel16 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        btnNuevo = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPersonalMedico = new javax.swing.JTable();
        btnEditar = new javax.swing.JButton();
        btnDarBaja = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        txtClave = new javax.swing.JPasswordField();
        txtConfirmarClave = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Gestionar Personal Médico");

        PanelGestionar_Personal_Medico.setBackground(new java.awt.Color(153, 204, 255));

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
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCedulaKeyTyped(evt);
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

        jLabel11.setText("Rol:");

        cboRol.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "MEDICO", "LABORATORISTA", "ATENCION", "ADMINISTRADOR" }));
        cboRol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboRolActionPerformed(evt);
            }
        });

        jLabel12.setText("Especialidad:");

        cboEspecialidad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NINGUNA", "MEDICINA GENERAL", "GINECOLOGÍA", "ODONTOLOGÍA", "PEDIATRÍA", "DERMATOLOGÍA", "ANESTESIOLOGÍA", "ORTOPEDIA", "TRAUMATOLOGÍA", "PSIQUIATRÍA" }));
        cboEspecialidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboEspecialidadActionPerformed(evt);
            }
        });

        jLabel16.setText("Fecha de Nacimiento:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(jLabel7)
                    .addComponent(jLabel4))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtCorreo, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
                                    .addComponent(txtTelefono))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel6)
                                .addGap(4, 4, 4)
                                .addComponent(txtTelefonoAuxiliar))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel11)
                                .addGap(35, 35, 35)
                                .addComponent(cboRol, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel8)
                                .addGap(35, 35, 35)
                                .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel16)
                                .addGap(18, 18, 18)
                                .addComponent(dcFechaNacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(cboEstadoCivil, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cboGenero, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cboEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 27, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11)
                        .addComponent(cboRol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(dcFechaNacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16))
                        .addGap(5, 5, 5)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTelefonoAuxiliar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel12)
                        .addComponent(cboEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(cboEstadoCivil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9)
                        .addComponent(cboGenero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Buscar"));

        jLabel10.setText("Cédula:");

        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBuscarKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(94, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addGap(30, 30, 30)
                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(452, 452, 452))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(8, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        tblPersonalMedico.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblPersonalMedico);

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
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 922, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(btnNuevo)
                        .addGap(109, 109, 109)
                        .addComponent(btnCancelar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(135, 135, 135)
                        .addComponent(btnDarBaja)
                        .addGap(144, 144, 144)
                        .addComponent(btnGuardar)))
                .addGap(23, 23, 23))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevo)
                    .addComponent(btnGuardar)
                    .addComponent(btnEditar)
                    .addComponent(btnDarBaja)
                    .addComponent(btnCancelar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos de Usuario"));

        jLabel13.setText("Usuario:");

        jLabel14.setText("Clave:");

        jLabel15.setText("Confirmar Clave:");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
                    .addComponent(txtClave)
                    .addComponent(txtConfirmarClave)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtClave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtConfirmarClave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout PanelGestionar_Personal_MedicoLayout = new javax.swing.GroupLayout(PanelGestionar_Personal_Medico);
        PanelGestionar_Personal_Medico.setLayout(PanelGestionar_Personal_MedicoLayout);
        PanelGestionar_Personal_MedicoLayout.setHorizontalGroup(
            PanelGestionar_Personal_MedicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelGestionar_Personal_MedicoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelGestionar_Personal_MedicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(PanelGestionar_Personal_MedicoLayout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        PanelGestionar_Personal_MedicoLayout.setVerticalGroup(
            PanelGestionar_Personal_MedicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelGestionar_Personal_MedicoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelGestionar_Personal_MedicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(PanelGestionar_Personal_Medico, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cboGeneroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboGeneroActionPerformed

    }//GEN-LAST:event_cboGeneroActionPerformed

    private void cboRolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboRolActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboRolActionPerformed

    private void cboEspecialidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboEspecialidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboEspecialidadActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        Guardar();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnDarBajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDarBajaActionPerformed
        DarBaja();
    }//GEN-LAST:event_btnDarBajaActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        try {
            Editar();
        } catch (ParseException ex) {
            Logger.getLogger(gestionar_personal_medico.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnEditarActionPerformed

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

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        this.btnGuardar.setEnabled(true);
        this.btnEditar.setEnabled(false);
        this.btnDarBaja.setEnabled(false);
        this.btnNuevo.setEnabled(false);
        this.btnCancelar.setEnabled(true);
        this.activar_desactivarBuscar(false);
        activa_desactivar(true);
        this.tblPersonalMedico.setEnabled(false);
        limpiar();
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        limpiar();
        this.tblPersonalMedico.setEnabled(true);
        this.btnCancelar.setEnabled(false);
        this.btnNuevo.setEnabled(true);
        this.btnEditar.setEnabled(true);
        this.btnDarBaja.setEnabled(true);
        this.btnGuardar.setEnabled(false);
        activa_desactivar(false);
        activar_desactivarBuscar(true);
    }//GEN-LAST:event_btnCancelarActionPerformed

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
        tr = new TableRowSorter(tblPersonalMedico.getModel());
        tblPersonalMedico.setRowSorter(tr);
    }//GEN-LAST:event_txtBuscarKeyTyped

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
            java.util.logging.Logger.getLogger(gestionar_personal_medico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(gestionar_personal_medico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(gestionar_personal_medico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(gestionar_personal_medico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new gestionar_personal_medico().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JPanel PanelGestionar_Personal_Medico;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnDarBaja;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JComboBox<String> cboEspecialidad;
    private javax.swing.JComboBox<String> cboEstadoCivil;
    private javax.swing.JComboBox<String> cboGenero;
    private javax.swing.JComboBox<String> cboRol;
    private com.toedter.calendar.JDateChooser dcFechaNacimiento;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
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
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblPersonalMedico;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtCedula;
    private javax.swing.JPasswordField txtClave;
    private javax.swing.JPasswordField txtConfirmarClave;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtTelefono;
    private javax.swing.JTextField txtTelefonoAuxiliar;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
