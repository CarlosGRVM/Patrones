package Vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class GestionPaciente extends JFrame {

    private JTextField txtNombre, txtApellido, txtEdad, txtCorreo, txtTelefono;
    private JButton btnAgregar, btnActualizar, btnEliminar, btnLimpiar;
    private JTable tablaPacientes;
    private DefaultTableModel modelo;

    public GestionPaciente() {
        setTitle("Gestión de Pacientes");
        setSize(700, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // Labels y TextFields
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(20, 20, 80, 25);
        add(lblNombre);
        txtNombre = new JTextField();
        txtNombre.setBounds(100, 20, 150, 25);
        add(txtNombre);

        JLabel lblApellido = new JLabel("Apellido:");
        lblApellido.setBounds(270, 20, 80, 25);
        add(lblApellido);
        txtApellido = new JTextField();
        txtApellido.setBounds(350, 20, 150, 25);
        add(txtApellido);

        JLabel lblEdad = new JLabel("Edad:");
        lblEdad.setBounds(520, 20, 50, 25);
        add(lblEdad);
        txtEdad = new JTextField();
        txtEdad.setBounds(570, 20, 80, 25);
        add(txtEdad);

        JLabel lblCorreo = new JLabel("Correo:");
        lblCorreo.setBounds(20, 60, 80, 25);
        add(lblCorreo);
        txtCorreo = new JTextField();
        txtCorreo.setBounds(100, 60, 200, 25);
        add(txtCorreo);

        JLabel lblTelefono = new JLabel("Teléfono:");
        lblTelefono.setBounds(320, 60, 80, 25);
        add(lblTelefono);
        txtTelefono = new JTextField();
        txtTelefono.setBounds(400, 60, 150, 25);
        add(txtTelefono);

        // Botones
        btnAgregar = new JButton("Agregar");
        btnAgregar.setBounds(20, 100, 120, 30);
        add(btnAgregar);

        btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(160, 100, 120, 30);
        add(btnActualizar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(300, 100, 120, 30);
        add(btnEliminar);

        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBounds(440, 100, 120, 30);
        add(btnLimpiar);

        // Tabla de pacientes
        modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(new String[]{"ID", "Nombre", "Apellido", "Edad", "Correo", "Teléfono"});
        tablaPacientes = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tablaPacientes);
        scroll.setBounds(20, 150, 640, 200);
        add(scroll);
    }

    // Getters
    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public JTextField getTxtApellido() {
        return txtApellido;
    }

    public JTextField getTxtEdad() {
        return txtEdad;
    }

    public JTextField getTxtCorreo() {
        return txtCorreo;
    }

    public JTextField getTxtTelefono() {
        return txtTelefono;
    }

    public JButton getBtnAgregar() {
        return btnAgregar;
    }

    public JButton getBtnActualizar() {
        return btnActualizar;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }

    public JTable getTablaPacientes() {
        return tablaPacientes;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }
}
