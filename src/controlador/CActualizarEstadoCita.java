package Controlador;

import Modelo.ConexionSQL;
import Vista.ActualizarEstadoCitas;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class CActualizarEstadoCita {

    private ActualizarEstadoCitas vista;

    public CActualizarEstadoCita(ActualizarEstadoCitas vista) {
        this.vista = vista;

        cargarCitas();

        // Botones de estado
        this.vista.getBtnPendiente().addActionListener(e -> actualizarEstado("Pendiente"));
        this.vista.getBtnConfirmada().addActionListener(e -> actualizarEstado("Atendida"));
        this.vista.getBtnCancelada().addActionListener(e -> actualizarEstado("Cancelada"));
    }

    private void cargarCitas() {
        DefaultTableModel model = vista.getModelo();
        model.setRowCount(0);
        try (Connection con = ConexionSQL.conectar()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(
                    "SELECT c.idCita, p.nombre AS paciente, u.nombre AS medico, c.fecha, c.hora, c.estado "
                    + "FROM cita c "
                    + "JOIN paciente p ON c.idPaciente = p.idPaciente "
                    + "JOIN medico m ON c.idMedico = m.idMedico "
                    + "JOIN usuario u ON m.idUsuario = u.idUsuario"
            );
            while (rs.next()) {
                Object[] fila = new Object[]{
                        rs.getInt("idCita"),
                        rs.getString("paciente"),
                        rs.getString("medico"),
                        rs.getDate("fecha"),
                        rs.getTime("hora"),
                        rs.getString("estado")
                };
                model.addRow(fila);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(vista, "Error al cargar citas: " + ex.getMessage());
        }
    }

    private void actualizarEstado(String nuevoEstado) {
        int fila = vista.getTablaCitas().getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(vista, "Seleccione una cita para actualizar");
            return;
        }

        int idCita = (int) vista.getModelo().getValueAt(fila, 0);

        try (Connection con = ConexionSQL.conectar()) {
            String sql = "UPDATE cita SET estado=? WHERE idCita=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, nuevoEstado);
            ps.setInt(2, idCita);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(vista, "Estado actualizado a '" + nuevoEstado + "'");
            cargarCitas();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(vista, "Error al actualizar estado: " + ex.getMessage());
        }
    }
}
