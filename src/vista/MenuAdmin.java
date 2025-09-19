package Vista;

import javax.swing.JButton;
import javax.swing.JFrame;

public class MenuAdmin extends JFrame {

    private JButton btnUsuarios, btnMedicos, btnPacientes, btnCitas;

    public MenuAdmin() {
        setTitle("Menú Admin");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        btnUsuarios = new JButton("Gestionar Usuarios");
        btnUsuarios.setBounds(50, 30, 300, 40);
        add(btnUsuarios);

        btnMedicos = new JButton("Gestionar Médicos");
        btnMedicos.setBounds(50, 80, 300, 40);
        add(btnMedicos);

        btnPacientes = new JButton("Gestionar Pacientes");
        btnPacientes.setBounds(50, 130, 300, 40);
        add(btnPacientes);

        btnCitas = new JButton("Gestionar Citas");
        btnCitas.setBounds(50, 180, 300, 40);
        add(btnCitas);
    }

    // Getters para controladores
    public JButton getBtnUsuarios() {
        return btnUsuarios;
    }

    public JButton getBtnMedicos() {
        return btnMedicos;
    }

    public JButton getBtnPacientes() {
        return btnPacientes;
    }

    public JButton getBtnCitas() {
        return btnCitas;
    }
}
