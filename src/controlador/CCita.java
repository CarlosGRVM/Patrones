package Controlador;

import Modelo.ConexionSQL;
import Modelo.Cita;
import Vista.GestionCitas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;

public class CCita implements ActionListener {

    private GestionCitas vista;
    private Cita cita;

    public CCita(GestionCitas vista, Cita cita) {
        this.vista = vista;
        this.cita = cita;

        // Listeners
        this.vista.getBtnAgregar().addActionListener(this);
        this.vista.getBtnActualizar().addActionListener(this);
        this.vista.getBtnCancelar().addActionListener(this);
        this.vista.getBtnLimpiar().addActionListener(this);

        cargarPacientes();
        cargarMedicos();
        listarCitas();

        this.vista.getTablaCitas().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = vista.getTablaCitas().getSelectedRow();
                if (fila >= 0) {
                    vista.getCboPaciente().setSelectedItem(vista.getModelo().getValueAt(fila, 1).toString());
                    vista.getCboMedico().setSelectedItem(vista.getModelo().getValueAt(fila, 2).toString());
                    vista.getTxtFecha().setText(vista.getModelo().getValueAt(fila, 3).toString());
                    vista.getTxtHora().setText(vista.getModelo().getValueAt(fila, 4).toString());
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.getBtnAgregar()) {
            agendarCita();
        } else if (e.getSource() == vista.getBtnActualizar()) {
            actualizarCita();
        } else if (e.getSource() == vista.getBtnCancelar()) {
            cancelarCita();
        } else if (e.getSource() == vista.getBtnLimpiar()) {
            limpiarCampos();
        }
    }

    private void cargarPacientes() {
        try (Connection con = ConexionSQL.conectar()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT idPaciente, nombre, apellido FROM paciente");
            while (rs.next()) {
                vista.getCboPaciente().addItem(rs.getInt("idPaciente") + " - " + rs.getString("nombre") + " " + rs.getString("apellido"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(vista, "Error al cargar pacientes: " + ex.getMessage());
        }
    }

    private void cargarMedicos() {
        try (Connection con = ConexionSQL.conectar()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT m.idMedico, u.nombre, u.apellido FROM medico m JOIN usuario u ON m.idUsuario = u.idUsuario");
            while (rs.next()) {
                vista.getCboMedico().addItem(rs.getInt("idMedico") + " - " + rs.getString("nombre") + " " + rs.getString("apellido"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(vista, "Error al cargar médicos: " + ex.getMessage());
        }
    }

    private void listarCitas() {
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
            JOptionPane.showMessageDialog(vista, "Error al listar citas: " + ex.getMessage());
        }
    }

    private boolean validarDisponibilidad(int idMedico, String fecha, String hora) {
        try (Connection con = ConexionSQL.conectar()) {
            String sql = "SELECT * FROM cita WHERE idMedico=? AND fecha=? AND hora=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idMedico);
            ps.setDate(2, java.sql.Date.valueOf(fecha));
            ps.setTime(3, java.sql.Time.valueOf(hora + ":00"));
            ResultSet rs = ps.executeQuery();
            return !rs.next(); // true si no hay cita en esa fecha/hora
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(vista, "Error al validar disponibilidad: " + ex.getMessage());
            return false;
        }
    }

    private void agendarCita() {
        if (vista.getCboPaciente().getSelectedIndex() < 0 || vista.getCboMedico().getSelectedIndex() < 0) {
            JOptionPane.showMessageDialog(vista, "Seleccione paciente y médico");
            return;
        }
        int idPaciente = Integer.parseInt(vista.getCboPaciente().getSelectedItem().toString().split(" - ")[0]);
        int idMedico = Integer.parseInt(vista.getCboMedico().getSelectedItem().toString().split(" - ")[0]);
        String fecha = vista.getTxtFecha().getText();
        String hora = vista.getTxtHora().getText();

        if (!validarDisponibilidad(idMedico, fecha, hora)) {
            JOptionPane.showMessageDialog(vista, "El médico no está disponible en esa fecha/hora");
            return;
        }

        try (Connection con = ConexionSQL.conectar()) {
            String sql = "INSERT INTO cita(idPaciente, idMedico, fecha, hora, estado) VALUES (?, ?, ?, ?, 'Pendiente')";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idPaciente);
            ps.setInt(2, idMedico);
            ps.setDate(3, java.sql.Date.valueOf(fecha));
            ps.setTime(4, java.sql.Time.valueOf(hora + ":00"));
            ps.executeUpdate();
            JOptionPane.showMessageDialog(vista, "Cita agendada correctamente");
            limpiarCampos();
            listarCitas();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(vista, "Error al agendar cita: " + ex.getMessage());
        }
    }

    private void actualizarCita() {
        int fila = vista.getTablaCitas().getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(vista, "Seleccione una cita para actualizar");
            return;
        }

        int idCita = (int) vista.getModelo().getValueAt(fila, 0);
        int idPaciente = Integer.parseInt(vista.getCboPaciente().getSelectedItem().toString().split(" - ")[0]);
        int idMedico = Integer.parseInt(vista.getCboMedico().getSelectedItem().toString().split(" - ")[0]);
        String fecha = vista.getTxtFecha().getText();
        String hora = vista.getTxtHora().getText();

        if (!validarDisponibilidad(idMedico, fecha, hora)) {
            JOptionPane.showMessageDialog(vista, "El médico no está disponible en esa fecha/hora");
            return;
        }

        try (Connection con = ConexionSQL.conectar()) {
            String sql = "UPDATE cita SET idPaciente=?, idMedico=?, fecha=?, hora=? WHERE idCita=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idPaciente);
            ps.setInt(2, idMedico);
            ps.setDate(3, java.sql.Date.valueOf(fecha));
            ps.setTime(4, java.sql.Time.valueOf(hora + ":00"));
            ps.setInt(5, idCita);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(vista, "Cita actualizada correctamente");
            limpiarCampos();
            listarCitas();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(vista, "Error al actualizar cita: " + ex.getMessage());
        }
    }

    private void cancelarCita() {
        int fila = vista.getTablaCitas().getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(vista, "Seleccione una cita para cancelar");
            return;
        }

        int idCita = (int) vista.getModelo().getValueAt(fila, 0);
        try (Connection con = ConexionSQL.conectar()) {
            String sql = "UPDATE cita SET estado='Cancelada' WHERE idCita=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idCita);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(vista, "Cita cancelada correctamente");
            limpiarCampos();
            listarCitas();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(vista, "Error al cancelar cita: " + ex.getMessage());
        }
    }

    public void actualizarEstadoCita(int idCita, String nuevoEstado) {
        try (Connection con = ConexionSQL.conectar()) {
            String sql = "UPDATE cita SET estado=? WHERE idCita=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, nuevoEstado.trim());
            ps.setInt(2, idCita);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(vista, "Estado actualizado correctamente");
            listarCitas(); // refresca la tabla
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(vista, "Error al actualizar estado: " + ex.getMessage());
        }
    }

    private void limpiarCampos() {
        vista.getCboPaciente().setSelectedIndex(-1);
        vista.getCboMedico().setSelectedIndex(-1);
        vista.getTxtFecha().setText("");
        vista.getTxtHora().setText("");
        vista.getTablaCitas().clearSelection();
    }
}
