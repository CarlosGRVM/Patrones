package Controlador;

import Modelo.ConexionSQL;
import Modelo.Medico;
import Vista.GestionMedico;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;

public class CMedico implements ActionListener {

    private GestionMedico vista;
    private Medico medico;

    public CMedico(GestionMedico vista, Medico medico) {
        this.vista = vista;
        this.medico = medico;

        // Listeners
        this.vista.getBtnAgregar().addActionListener(this);
        this.vista.getBtnActualizar().addActionListener(this);
        this.vista.getBtnEliminar().addActionListener(this);
        this.vista.getBtnLimpiar().addActionListener(this);

        listarMedicos();

        this.vista.getTablaMedicos().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = vista.getTablaMedicos().getSelectedRow();
                if (fila >= 0) {
                    vista.getTxtNombre().setText(vista.getModelo().getValueAt(fila, 1).toString());
                    vista.getTxtApellido().setText(vista.getModelo().getValueAt(fila, 2).toString());
                    vista.getTxtEspecialidad().setText(vista.getModelo().getValueAt(fila, 3).toString());
                    vista.getTxtHorario().setText(vista.getModelo().getValueAt(fila, 4).toString());
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.getBtnAgregar()) {
            agregarMedico();
        } else if (e.getSource() == vista.getBtnActualizar()) {
            actualizarMedico();
        } else if (e.getSource() == vista.getBtnEliminar()) {
            eliminarMedico();
        } else if (e.getSource() == vista.getBtnLimpiar()) {
            limpiarCampos();
        }
    }

    private void listarMedicos() {
        DefaultTableModel model = vista.getModelo();
        model.setRowCount(0);
        try (Connection con = ConexionSQL.conectar()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT m.idMedico, u.nombre, u.apellido, m.especialidad, m.horarioDisponible "
                    + "FROM medico m JOIN usuario u ON m.idUsuario = u.idUsuario");
            while (rs.next()) {
                Object[] fila = new Object[]{
                    rs.getInt("idMedico"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("especialidad"),
                    rs.getString("horarioDisponible")
                };
                model.addRow(fila);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(vista, "Error al listar médicos: " + ex.getMessage());
        }
    }

    private void agregarMedico() {
        try (Connection con = ConexionSQL.conectar()) {
            String sqlUser = "INSERT INTO usuario(nombre, apellido, correo, clave) VALUES (?, ?, ?, ?)";
            PreparedStatement psUser = con.prepareStatement(sqlUser, Statement.RETURN_GENERATED_KEYS);
            psUser.setString(1, vista.getTxtNombre().getText());
            psUser.setString(2, vista.getTxtApellido().getText());
            psUser.setString(3, vista.getTxtNombre().getText().toLowerCase() + "@hospital.com"); // correo ficticio
            psUser.setString(4, "123456"); // contraseña temporal (debe cambiarse luego)
            psUser.executeUpdate();

            ResultSet rs = psUser.getGeneratedKeys();
            int idUsuario = 0;
            if (rs.next()) {
                idUsuario = rs.getInt(1);
            }

            String sqlMedico = "INSERT INTO medico(idUsuario, especialidad, horarioDisponible) VALUES (?, ?, ?)";
            PreparedStatement psMed = con.prepareStatement(sqlMedico);
            psMed.setInt(1, idUsuario);
            psMed.setString(2, vista.getTxtEspecialidad().getText());
            psMed.setString(3, vista.getTxtHorario().getText());
            psMed.executeUpdate();

            JOptionPane.showMessageDialog(vista, "Médico agregado correctamente");
            limpiarCampos();
            listarMedicos();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(vista, "Error al agregar médico: " + ex.getMessage());
        }
    }

    private void actualizarMedico() {
        int fila = vista.getTablaMedicos().getSelectedRow();
        if (fila >= 0) {
            int idMedico = (int) vista.getModelo().getValueAt(fila, 0);
            try (Connection con = ConexionSQL.conectar()) {
                // Actualizar tabla usuario
                String sqlUser = "UPDATE usuario u JOIN medico m ON u.idUsuario = m.idUsuario "
                        + "SET u.nombre=?, u.apellido=?, m.especialidad=?, m.horarioDisponible=? WHERE m.idMedico=?";
                PreparedStatement ps = con.prepareStatement(sqlUser);
                ps.setString(1, vista.getTxtNombre().getText());
                ps.setString(2, vista.getTxtApellido().getText());
                ps.setString(3, vista.getTxtEspecialidad().getText());
                ps.setString(4, vista.getTxtHorario().getText());
                ps.setInt(5, idMedico);
                ps.executeUpdate();

                JOptionPane.showMessageDialog(vista, "Médico actualizado correctamente");
                limpiarCampos();
                listarMedicos();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(vista, "Error al actualizar médico: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(vista, "Seleccione un médico para actualizar");
        }
    }

    private void eliminarMedico() {
        int fila = vista.getTablaMedicos().getSelectedRow();
        if (fila >= 0) {
            int idMedico = (int) vista.getModelo().getValueAt(fila, 0);
            try (Connection con = ConexionSQL.conectar()) {
                String sql = "DELETE u, m FROM usuario u JOIN medico m ON u.idUsuario = m.idUsuario WHERE m.idMedico=?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, idMedico);
                ps.executeUpdate();

                JOptionPane.showMessageDialog(vista, "Médico eliminado correctamente");
                limpiarCampos();
                listarMedicos();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(vista, "Error al eliminar médico: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(vista, "Seleccione un médico para eliminar");
        }
    }

    private void limpiarCampos() {
        vista.getTxtNombre().setText("");
        vista.getTxtApellido().setText("");
        vista.getTxtEspecialidad().setText("");
        vista.getTxtHorario().setText("");
        vista.getTablaMedicos().clearSelection();
    }
}
