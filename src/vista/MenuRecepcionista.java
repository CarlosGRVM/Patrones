package Vista;

import javax.swing.JButton;
import javax.swing.JFrame;

public class MenuRecepcionista extends JFrame {

    private JButton btnPacientes, btnCitas;

    public MenuRecepcionista() {
        setTitle("Menú Recepcionista");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        btnPacientes = new JButton("Registrar Pacientes");
        btnPacientes.setBounds(50, 30, 300, 40);
        add(btnPacientes);

        btnCitas = new JButton("Gestionar Citas");
        btnCitas.setBounds(50, 80, 300, 40);
        add(btnCitas);
    }

    // Getters para controladores
    public JButton getBtnPacientes() {
        return btnPacientes;
    }

    public JButton getBtnCitas() {
        return btnCitas;
    }
}
