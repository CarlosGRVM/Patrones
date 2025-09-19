package Vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class GestionMedico extends JFrame {

    private JTextField txtNombre, txtApellido, txtEspecialidad, txtHorario;
    private JButton btnAgregar, btnActualizar, btnEliminar, btnLimpiar;
    private JTable tablaMedicos;
    private DefaultTableModel modelo;

    public GestionMedico() {
        setTitle("Gestión de Médicos");
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

        JLabel lblEspecialidad = new JLabel("Especialidad:");
        lblEspecialidad.setBounds(20, 60, 80, 25);
        add(lblEspecialidad);
        txtEspecialidad = new JTextField();
        txtEspecialidad.setBounds(100, 60, 150, 25);
        add(txtEspecialidad);

        JLabel lblHorario = new JLabel("Horario:");
        lblHorario.setBounds(270, 60, 80, 25);
        add(lblHorario);
        txtHorario = new JTextField();
        txtHorario.setBounds(350, 60, 150, 25);
        add(txtHorario);

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

        // Tabla de médicos
        modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(new String[]{"ID", "Nombre", "Apellido", "Especialidad", "Horario"});
        tablaMedicos = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tablaMedicos);
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

    public JTextField getTxtEspecialidad() {
        return txtEspecialidad;
    }

    public JTextField getTxtHorario() {
        return txtHorario;
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

    public JTable getTablaMedicos() {
        return tablaMedicos;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }
}
