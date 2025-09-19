package Vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ActualizarEstadoCitas extends JFrame {

    private JTable tablaCitas;
    private DefaultTableModel modelo;
    private JButton btnPendiente, btnConfirmada, btnCancelada;

    public ActualizarEstadoCitas() {
        setTitle("Actualizar Estado de Citas");
        setSize(700, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // Tabla de citas
        modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(new String[]{"ID", "Paciente", "Médico", "Fecha", "Hora", "Estado"});
        tablaCitas = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tablaCitas);
        scroll.setBounds(20, 20, 650, 250);
        add(scroll);

        // Botones de estado
        btnPendiente = new JButton("Pendiente");
        btnPendiente.setBounds(50, 300, 120, 30);
        add(btnPendiente);

        btnConfirmada = new JButton("Atendida");
        btnConfirmada.setBounds(200, 300, 120, 30);
        add(btnConfirmada);

        btnCancelada = new JButton("Cancelada");
        btnCancelada.setBounds(350, 300, 120, 30);
        add(btnCancelada);
    }

    // Getters
    public JTable getTablaCitas() {
        return tablaCitas;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public JButton getBtnPendiente() {
        return btnPendiente;
    }

    public JButton getBtnConfirmada() {
        return btnConfirmada;
    }

    public JButton getBtnCancelada() {
        return btnCancelada;
    }
}
