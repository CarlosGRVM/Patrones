package Vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class GestionCitas extends JFrame {

    private JComboBox<String> cboPaciente, cboMedico;
    private JTextField txtFecha, txtHora;
    private JButton btnAgregar, btnActualizar, btnCancelar, btnLimpiar;
    private JTable tablaCitas;
    private DefaultTableModel modelo;

    public GestionCitas() {
        setTitle("Gestión de Citas");
        setSize(750, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // Labels y campos
        JLabel lblPaciente = new JLabel("Paciente:");
        lblPaciente.setBounds(20, 20, 80, 25);
        add(lblPaciente);
        cboPaciente = new JComboBox<>();
        cboPaciente.setBounds(100, 20, 150, 25);
        add(cboPaciente);

        JLabel lblMedico = new JLabel("Médico:");
        lblMedico.setBounds(270, 20, 80, 25);
        add(lblMedico);
        cboMedico = new JComboBox<>();
        cboMedico.setBounds(350, 20, 150, 25);
        add(cboMedico);

        JLabel lblFecha = new JLabel("Fecha (yyyy-mm-dd):");
        lblFecha.setBounds(20, 60, 150, 25);
        add(lblFecha);
        txtFecha = new JTextField();
        txtFecha.setBounds(160, 60, 120, 25);
        add(txtFecha);

        JLabel lblHora = new JLabel("Hora (HH:mm):");
        lblHora.setBounds(300, 60, 100, 25);
        add(lblHora);
        txtHora = new JTextField();
        txtHora.setBounds(390, 60, 80, 25);
        add(txtHora);

        // Botones
        btnAgregar = new JButton("Agendar");
        btnAgregar.setBounds(20, 100, 120, 30);
        add(btnAgregar);

        btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(160, 100, 120, 30);
        add(btnActualizar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(300, 100, 120, 30);
        add(btnCancelar);

        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBounds(440, 100, 120, 30);
        add(btnLimpiar);

        // Tabla de citas
        modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(new String[]{"ID", "Paciente", "Médico", "Fecha", "Hora", "Estado"});
        tablaCitas = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tablaCitas);
        scroll.setBounds(20, 150, 700, 200);
        add(scroll);
    }

    // Getters
    public JComboBox<String> getCboPaciente() {
        return cboPaciente;
    }

    public JComboBox<String> getCboMedico() {
        return cboMedico;
    }

    public JTextField getTxtFecha() {
        return txtFecha;
    }

    public JTextField getTxtHora() {
        return txtHora;
    }

    public JButton getBtnAgregar() {
        return btnAgregar;
    }

    public JButton getBtnActualizar() {
        return btnActualizar;
    }

    public JButton getBtnCancelar() {
        return btnCancelar;
    }

    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }

    public JTable getTablaCitas() {
        return tablaCitas;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }
}
