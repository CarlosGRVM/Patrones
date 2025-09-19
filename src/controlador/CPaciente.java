package Controlador;

import Modelo.ConexionSQL;
import Modelo.Paciente;
import Vista.GestionPaciente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;

public class CPaciente implements ActionListener {

    private GestionPaciente vista;
    private Paciente paciente;

    public CPaciente(GestionPaciente vista, Paciente paciente) {
        this.vista = vista;
        this.paciente = paciente;

        // Agregar listeners
        this.vista.getBtnAgregar().addActionListener(this);
        this.vista.getBtnActualizar().addActionListener(this);
        this.vista.getBtnEliminar().addActionListener(this);
        this.vista.getBtnLimpiar().addActionListener(this);

        // Cargar pacientes en la tabla
        listarPacientes();
        this.vista.getTablaPacientes().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = vista.getTablaPacientes().getSelectedRow();
                if (fila >= 0) {
                    vista.getTxtNombre().setText(vista.getModelo().getValueAt(fila, 1).toString());
                    vista.getTxtApellido().setText(vista.getModelo().getValueAt(fila, 2).toString());
                    vista.getTxtEdad().setText(vista.getModelo().getValueAt(fila, 3).toString());
                    vista.getTxtCorreo().setText(vista.getModelo().getValueAt(fila, 4).toString());
                    vista.getTxtTelefono().setText(vista.getModelo().getValueAt(fila, 5).toString());
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.getBtnAgregar()) {
            agregarPaciente();
        } else if (e.getSource() == vista.getBtnActualizar()) {
            actualizarPaciente();
        } else if (e.getSource() == vista.getBtnEliminar()) {
            eliminarPaciente();
        } else if (e.getSource() == vista.getBtnLimpiar()) {
            limpiarCampos();
        }
    }

    private void listarPacientes() {
        DefaultTableModel model = vista.getModelo();
        model.setRowCount(0); // Limpiar tabla
        try (Connection con = ConexionSQL.conectar()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM paciente");
            while (rs.next()) {
                Object[] fila = new Object[]{
                    rs.getInt("idPaciente"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getInt("edad"),
                    rs.getString("correo"),
                    rs.getString("telefono")
                };
                model.addRow(fila);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(vista, "Error al listar pacientes: " + ex.getMessage());
        }
    }

    private void agregarPaciente() {
        try (Connection con = ConexionSQL.conectar()) {
            String sql = "INSERT INTO paciente (nombre, apellido, edad, correo, telefono) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, vista.getTxtNombre().getText());
            ps.setString(2, vista.getTxtApellido().getText());
            ps.setInt(3, Integer.parseInt(vista.getTxtEdad().getText()));
            ps.setString(4, vista.getTxtCorreo().getText());
            ps.setString(5, vista.getTxtTelefono().getText());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(vista, "Paciente agregado correctamente");
            limpiarCampos();
            listarPacientes();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(vista, "Error al agregar paciente: " + ex.getMessage());
        }
    }

    private void actualizarPaciente() {
        int fila = vista.getTablaPacientes().getSelectedRow();
        if (fila >= 0) {
            int id = (int) vista.getModelo().getValueAt(fila, 0);
            try (Connection con = ConexionSQL.conectar()) {
                String sql = "UPDATE paciente SET nombre=?, apellido=?, edad=?, correo=?, telefono=? WHERE idPaciente=?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, vista.getTxtNombre().getText());
                ps.setString(2, vista.getTxtApellido().getText());
                ps.setInt(3, Integer.parseInt(vista.getTxtEdad().getText()));
                ps.setString(4, vista.getTxtCorreo().getText());
                ps.setString(5, vista.getTxtTelefono().getText());
                ps.setInt(6, id);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(vista, "Paciente actualizado correctamente");
                limpiarCampos();
                listarPacientes();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(vista, "Error al actualizar paciente: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(vista, "Seleccione un paciente para actualizar");
        }
    }

    private void eliminarPaciente() {
        int fila = vista.getTablaPacientes().getSelectedRow();
        if (fila >= 0) {
            int id = (int) vista.getModelo().getValueAt(fila, 0);
            try (Connection con = ConexionSQL.conectar()) {
                String sql = "DELETE FROM paciente WHERE idPaciente=?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, id);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(vista, "Paciente eliminado correctamente");
                limpiarCampos();
                listarPacientes();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(vista, "Error al eliminar paciente: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(vista, "Seleccione un paciente para eliminar");
        }
    }

    private void limpiarCampos() {
        vista.getTxtNombre().setText("");
        vista.getTxtApellido().setText("");
        vista.getTxtEdad().setText("");
        vista.getTxtCorreo().setText("");
        vista.getTxtTelefono().setText("");
        vista.getTablaPacientes().clearSelection();
    }
}
