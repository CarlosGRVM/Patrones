package Controlador;

import Modelo.ConexionSQL;
import Modelo.Usuario;
import Vista.GestionUsuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;

public class CUsuario implements ActionListener {

    private GestionUsuario vista;
    private Usuario usuario;

    public CUsuario(GestionUsuario vista, Usuario usuario) {
        this.vista = vista;
        this.usuario = usuario;

        // Listeners
        this.vista.getBtnInsertar().addActionListener(this);
        this.vista.getBtnActualizar().addActionListener(this);
        this.vista.getBtnEliminar().addActionListener(this);
        this.vista.getBtnBuscar().addActionListener(this);
        this.vista.getBtnLimpiar().addActionListener(this);

        listarUsuarios();

        // Evento al seleccionar fila en la tabla
        this.vista.getTablaUsuarios().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = vista.getTablaUsuarios().getSelectedRow();
                if (fila >= 0) {
                    vista.getTxtIdUsuario().setText(vista.getModeloTabla().getValueAt(fila, 0).toString());
                    vista.getTxtNombre().setText(vista.getModeloTabla().getValueAt(fila, 1).toString());
                    vista.getTxtApellido().setText(vista.getModeloTabla().getValueAt(fila, 2).toString());
                    vista.getTxtCorreo().setText(vista.getModeloTabla().getValueAt(fila, 3).toString());
                    vista.getCboRol().setSelectedItem(vista.getModeloTabla().getValueAt(fila, 4).toString());
                }
            }
        });

        this.vista.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.getBtnInsertar()) {
            insertarUsuario();
        } else if (e.getSource() == vista.getBtnActualizar()) {
            actualizarUsuario();
        } else if (e.getSource() == vista.getBtnEliminar()) {
            eliminarUsuario();
        } else if (e.getSource() == vista.getBtnBuscar()) {
            buscarUsuario();
        } else if (e.getSource() == vista.getBtnLimpiar()) {
            limpiarCampos();
        }
    }

    private void listarUsuarios() {
        DefaultTableModel model = vista.getModeloTabla();
        model.setRowCount(0);
        try (Connection con = ConexionSQL.conectar()) {
            String sql = "SELECT u.idUsuario, u.nombre, u.apellido, u.correo, r.nombre AS rol "
                       + "FROM usuario u "
                       + "JOIN usuario_rol ur ON u.idUsuario = ur.idUsuario "
                       + "JOIN rol r ON ur.idRol = r.idRol";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                Object[] fila = new Object[]{
                        rs.getInt("idUsuario"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("correo"),
                        rs.getString("rol")
                };
                model.addRow(fila);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(vista, "Error al listar usuarios: " + ex.getMessage());
        }
    }

    private void insertarUsuario() {
        try (Connection con = ConexionSQL.conectar()) {
            // Insertar usuario
            String sqlUser = "INSERT INTO usuario(nombre, apellido, correo, clave) VALUES (?, ?, ?, ?)";
            PreparedStatement psUser = con.prepareStatement(sqlUser, Statement.RETURN_GENERATED_KEYS);
            psUser.setString(1, vista.getTxtNombre().getText());
            psUser.setString(2, vista.getTxtApellido().getText());
            psUser.setString(3, vista.getTxtCorreo().getText());
            psUser.setString(4, new String(vista.getTxtClave().getPassword())); // 🔒 clave sin encriptar (puedes encriptar aquí)
            psUser.executeUpdate();

            ResultSet rs = psUser.getGeneratedKeys();
            int idUsuario = 0;
            if (rs.next()) {
                idUsuario = rs.getInt(1);
            }

            // Asignar rol
            String sqlRol = "INSERT INTO usuario_rol(idUsuario, idRol) VALUES (?, (SELECT idRol FROM rol WHERE nombre=?))";
            PreparedStatement psRol = con.prepareStatement(sqlRol);
            psRol.setInt(1, idUsuario);
            psRol.setString(2, vista.getCboRol().getSelectedItem().toString());
            psRol.executeUpdate();

            JOptionPane.showMessageDialog(vista, "Usuario agregado correctamente");
            limpiarCampos();
            listarUsuarios();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(vista, "Error al insertar usuario: " + ex.getMessage());
        }
    }

    private void actualizarUsuario() {
        int fila = vista.getTablaUsuarios().getSelectedRow();
        if (fila >= 0) {
            int idUsuario = (int) vista.getModeloTabla().getValueAt(fila, 0);
            try (Connection con = ConexionSQL.conectar()) {
                String sql = "UPDATE usuario SET nombre=?, apellido=?, correo=? WHERE idUsuario=?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, vista.getTxtNombre().getText());
                ps.setString(2, vista.getTxtApellido().getText());
                ps.setString(3, vista.getTxtCorreo().getText());
                ps.setInt(4, idUsuario);
                ps.executeUpdate();

                JOptionPane.showMessageDialog(vista, "Usuario actualizado correctamente");
                limpiarCampos();
                listarUsuarios();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(vista, "Error al actualizar usuario: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(vista, "Seleccione un usuario para actualizar");
        }
    }

    private void eliminarUsuario() {
        int fila = vista.getTablaUsuarios().getSelectedRow();
        if (fila >= 0) {
            int idUsuario = (int) vista.getModeloTabla().getValueAt(fila, 0);
            try (Connection con = ConexionSQL.conectar()) {
                String sql = "DELETE FROM usuario WHERE idUsuario=?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, idUsuario);
                ps.executeUpdate();

                JOptionPane.showMessageDialog(vista, "Usuario eliminado correctamente");
                limpiarCampos();
                listarUsuarios();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(vista, "Error al eliminar usuario: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(vista, "Seleccione un usuario para eliminar");
        }
    }

    private void buscarUsuario() {
        String nombre = vista.getTxtNombre().getText();
        DefaultTableModel model = vista.getModeloTabla();
        model.setRowCount(0);
        try (Connection con = ConexionSQL.conectar()) {
            String sql = "SELECT u.idUsuario, u.nombre, u.apellido, u.correo, r.nombre AS rol "
                       + "FROM usuario u "
                       + "JOIN usuario_rol ur ON u.idUsuario = ur.idUsuario "
                       + "JOIN rol r ON ur.idRol = r.idRol "
                       + "WHERE u.nombre LIKE ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + nombre + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Object[] fila = new Object[]{
                        rs.getInt("idUsuario"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("correo"),
                        rs.getString("rol")
                };
                model.addRow(fila);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(vista, "Error al buscar usuario: " + ex.getMessage());
        }
    }

    private void limpiarCampos() {
        vista.getTxtIdUsuario().setText("");
        vista.getTxtNombre().setText("");
        vista.getTxtApellido().setText("");
        vista.getTxtCorreo().setText("");
        vista.getTxtClave().setText("");
        vista.getCboRol().setSelectedIndex(0);
        vista.getTablaUsuarios().clearSelection();
    }
}
