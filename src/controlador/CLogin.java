package Controlador;

import Modelo.*;
import Vista.*;
import java.sql.*;
import javax.swing.*;
import java.security.MessageDigest;

public class CLogin {

    private JTextField txtUsuario;
    private JPasswordField txtClave;
    // dentro de class CLogin:
    private Usuario mUsuario = new Usuario();
    private Medico mMedico = new Medico();
    private Paciente mPaciente = new Paciente();
    private Cita mCita = new Cita();

    public CLogin(JTextField txtUsuario, JPasswordField txtClave) {
        this.txtUsuario = txtUsuario;
        this.txtClave = txtClave;
    }

    public void iniciarSesion() {
        String usuario = txtUsuario.getText();
        String clave = new String(txtClave.getPassword());
        String claveEncriptada = encriptarSHA256(clave);

        try (Connection con = ConexionSQL.conectar()) {
            String sql = "SELECT u.idUsuario, u.nombre, u.apellido, r.nombre AS rolNombre "
                    + "FROM usuario u "
                    + "JOIN usuario_rol ru ON u.idUsuario = ru.idUsuario "
                    + "JOIN rol r ON ru.idRol = r.idRol "
                    + "WHERE u.correo=? AND u.clave=?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, usuario);
            ps.setString(2, claveEncriptada);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String rol = rs.getString("rolNombre"); // ahora sí es el rol
                abrirMenuPorRol(rol);
            } else {
                JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al iniciar sesión: " + ex.getMessage());
        }
    }

    private void abrirMenuPorRol(String rol) {
        if (rol == null) {
            JOptionPane.showMessageDialog(null, "Rol no reconocido: null");
            return;
        }

        switch (rol.trim().toLowerCase()) {
            case "admin":
                MenuAdmin menuAdmin = new MenuAdmin();

                // Conectar botones con controladores
                menuAdmin.getBtnUsuarios().addActionListener(e -> {
                    GestionUsuario vistaUsuario = new GestionUsuario();
                    new CUsuario(vistaUsuario, new Usuario());
                    vistaUsuario.setVisible(true);
                });

                menuAdmin.getBtnMedicos().addActionListener(e -> {
                    GestionMedico vistaMedico = new GestionMedico();
                    new CMedico(vistaMedico, new Medico());
                    vistaMedico.setVisible(true);
                });

                menuAdmin.getBtnPacientes().addActionListener(e -> {
                    GestionPaciente vistaPaciente = new GestionPaciente();
                    new CPaciente(vistaPaciente, new Paciente());
                    vistaPaciente.setVisible(true);
                });

                menuAdmin.getBtnCitas().addActionListener(e -> {
                    GestionCitas vistaCita = new GestionCitas();
                    new CCita(vistaCita, new Cita());
                    vistaCita.setVisible(true);
                });

                menuAdmin.setVisible(true);
                break;

            case "recepcionista":
                MenuRecepcionista menuRec = new MenuRecepcionista();

                menuRec.getBtnPacientes().addActionListener(e -> {
                    GestionPaciente vistaPaciente = new GestionPaciente();
                    new CPaciente(vistaPaciente, new Paciente());
                    vistaPaciente.setVisible(true);
                });

                menuRec.getBtnCitas().addActionListener(e -> {
                    GestionCitas vistaCita = new GestionCitas();
                    new CCita(vistaCita, new Cita());
                    vistaCita.setVisible(true);
                });

                menuRec.setVisible(true);
                break;

            case "medico":
                MenuMedico menuMed = new MenuMedico();

                // Ver todas las citas
                menuMed.getBtnVerCitas().addActionListener(e -> {
                    GestionCitas vistaCita = new GestionCitas();
                    new CCita(vistaCita, new Cita());
                    vistaCita.setVisible(true);
                });

                // Actualizar estado de las citas
                menuMed.getBtnActualizarEstado().addActionListener(e -> {
                    ActualizarEstadoCitas vistaActualizar = new ActualizarEstadoCitas();
                    new CActualizarEstadoCita(vistaActualizar);
                    vistaActualizar.setVisible(true);
                });

                menuMed.setVisible(true);
                break;

            default:
                JOptionPane.showMessageDialog(null, "Rol no reconocido: '" + rol + "'");
        }
    }

    private String encriptarSHA256(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(input.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception ex) {
            return null;
        }
    }
}
