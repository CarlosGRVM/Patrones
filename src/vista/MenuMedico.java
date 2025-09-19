package Vista;

import javax.swing.JButton;
import javax.swing.JFrame;

public class MenuMedico extends JFrame {

    private JButton btnVerCitas, btnActualizarEstado;

    public MenuMedico() {
        setTitle("Menú Médico");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        btnVerCitas = new JButton("Ver Citas");
        btnVerCitas.setBounds(50, 30, 300, 40);
        add(btnVerCitas);

        btnActualizarEstado = new JButton("Actualizar Estado de Cita");
        btnActualizarEstado.setBounds(50, 80, 300, 40);
        add(btnActualizarEstado);
    }

    // Getters para controladores
    public JButton getBtnVerCitas() {
        return btnVerCitas;
    }

    public JButton getBtnActualizarEstado() {
        return btnActualizarEstado;
    }
}
